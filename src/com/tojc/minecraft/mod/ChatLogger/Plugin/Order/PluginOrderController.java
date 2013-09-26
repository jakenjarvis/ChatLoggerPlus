/*
 * ChatLoggerPlus (Minecraft MOD)
 *
 * Copyright (C) 2012 Members of the ChatLoggerPlus project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1.ChatMessageImpl;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1.PluginInformation;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;
import com.tojc.minecraft.mod.Crypto.SimpleEncryption;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginOrderController
{
	private ChatLoggerConfiguration config = null;
	private Map<String, PluginInformation> masterMapping = null;
	private PluginType type = null;

	private Map<String, Object> pluginStack = null;

	private List<PluginOrderKey> orderKeys = null;
	private Map<String, PluginOrderStatus> mapping = null;

	public PluginOrderController(ChatLoggerConfiguration config, PluginType type, Map<String, Object> pluginStack)
	{
		this.config = config;
		this.type = type;
		this.pluginStack = pluginStack;
		this.mapping = new HashMap<String, PluginOrderStatus>();
	}

	public Map<String, PluginInformation> getMasterMapping()
	{
		return this.masterMapping;
	}
	public void setMasterMapping(Map<String, PluginInformation> masterMapping)
	{
		this.masterMapping = masterMapping;
	}

	public List<PluginOrderKey> getPluginOrderKey()
	{
		return this.orderKeys;
	}

	public Map<String, PluginOrderStatus> getMapping()
	{
		return this.mapping;
	}

	public void createMapping()
	{
		for(PluginOrderKey item : this.orderKeys)
		{
			PluginInformation plugin = this.masterMapping.get(item.getKey());
			PluginOrderStatus status = new PluginOrderStatus(this.type, item, plugin);
			this.mapping.put(status.getPluginOrderKey().getKey(), status);
		}
	}

	public void loadSetting()
	{
		// 形式を戻す
		this.orderKeys = new ArrayList<PluginOrderKey>();
		// 固定長なので注意
		List<String> fixedLengthArray = this.type.getIndividual().getConfigurationProperty(this.config).get();
		for(String item : fixedLengthArray)
		{
			this.orderKeys.add(new PluginOrderKey(item));
		}
	}

	public void saveSetting()
	{
		List<String> array = new ArrayList<String>();
		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			if(status != null)
			{
				array.add(status.getPluginOrderKey().makeEncOrderKeyString());
			}
		}
		this.type.getIndividual().getConfigurationProperty(this.config).set(array);
	}

	public String makeOrderCheckMessage()
	{
		String result = "";
		boolean error = false;
		List<String> stackWrite = new ArrayList<String>();

		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			if(status.isEnabled())
			{
				for(String write : status.getPlugin().getSettings().getWriteStack())
				{
					// 重複する登録は認めない
					if(stackWrite.contains(write))
					{
						error = true;
						result = "Write permission is duplicated: " + write;
						break;
					}
					else
					{
						stackWrite.add(write);
					}
				}
				if(error)
				{
					break;
				}

				for(String read : status.getPlugin().getSettings().getReadStack())
				{
					// 上位に登録が無ければ認めない
					if(!stackWrite.contains(read))
					{
						error = true;
						result = "Write permission is necessary before read permission: " + read;
						break;
					}
				}
				if(error)
				{
					break;
				}
			}
		}
		return result;
	}

	public ChatMessageImpl fireOnChatMessage(ChatMessageImpl chat, List<String> masterMessagesAfter)
	{
		ChatMessageImpl result = ChatMessageImpl.clone(chat);
		result.setMessagesAfter(masterMessagesAfter);

		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			if(status.isEnabled())
			{
				DebugLog.trace("onChatMessage() call: %s", item.toString());
				result.setSettings(status.getPlugin().getSettings());
				result.setPluginStack(this.pluginStack);
				status.getPlugin().onChatMessage(result);

				switch(this.type)
				{
					case ChatLog:
						DebugLog.message_during_chatlog(result.getMessage() + " / {" + item.getKey() + "}");
						break;

					case Screen:
						DebugLog.message_during_screen(result.getMessage() + " / {" + item.getKey() + "}");
						break;

					default:
						break;
				}

				//MEMO: スクリプト側で削除済みメッセージを文字列操作すると文字列に変換されてしまうケースがある。
				if((!chat.getMessageOriginal().equals(result.getMessage()))
					&& (result.getMessage() != null)
					&& (result.getMessage().equals("null")))
				{
					DebugLog.warning("This plugin was destroyed deleted messages: {" + item.getKey() + "}. Please add the check for null to the plugin.");
				}
			}
			else
			{
				DebugLog.trace("onChatMessage() not call: %s", item.toString());
			}
		}
		return result;
	}

	public TreeMap<Integer, PluginOrderStatus> getOrderTreeMap()
	{
		TreeMap<Integer, PluginOrderStatus> result = new TreeMap<Integer, PluginOrderStatus>();
		int index = 0;
		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			result.put(index, status);
			index++;
		}
		return result;
	}

	public void updateOrderTreeMap(TreeMap<Integer, PluginOrderStatus> mapPlugins)
	{
		int index = 0;
		mapPlugins.clear();
		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			mapPlugins.put(index, status);
			index++;
		}
	}

	public int findPluginOrderKeyIndex(PluginOrderKey targetKey)
	{
		int result = -1;
		String key = targetKey.getKey();
		for(PluginOrderKey item : this.orderKeys)
		{
			if(item.getKey().equals(key))
			{
				result = this.orderKeys.indexOf(item);
				break;
			}
		}
		return result;
	}


	public boolean executeOrderUp(PluginOrderStatus plugin)
	{
		boolean result = false;
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				if(findKey >= 1)
				{
					Collections.swap(this.orderKeys, findKey, findKey - 1);
					result = true;
				}
			}
		}
		return result;
	}

	public boolean executeOrderDown(PluginOrderStatus plugin)
	{
		boolean result = false;
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				if(findKey < this.orderKeys.size() - 1)
				{
					Collections.swap(this.orderKeys, findKey, findKey + 1);
					result = true;
				}
			}
		}
		return result;
	}

	public boolean executeAdd(PluginOrderStatus plugin)
	{
		boolean result = false;
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			DebugLog.trace("executeAdd() findKey: %d", findKey);
			if(findKey == -1)
			{
				if(!plugin.isError())
				{
					this.orderKeys.add(plugin.getPluginOrderKey());
					this.createMapping();
					result = true;
				}
			}
		}
		return result;
	}

	public boolean executeDelete(PluginOrderStatus plugin)
	{
		boolean result = false;
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				this.orderKeys.remove(findKey);
				result = true;
			}
		}
		return result;
	}

	public boolean executeStateToggle(PluginOrderStatus plugin)
	{
		boolean result = false;
		if(plugin != null)
		{
			PluginOrderStatus target = this.mapping.get(plugin.getPluginOrderKey().getKey());
			if(target != null)
			{
				target.setEnabled(!target.isEnabled());
				result = true;
			}
		}
		return result;
	}

}
