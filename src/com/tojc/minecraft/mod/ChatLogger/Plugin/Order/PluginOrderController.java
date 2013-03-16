package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.Plugin.ChatMessageImpl;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginInformation;
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

	public boolean checkOrder()
	{
		boolean result = true;
		List<String> stack = new ArrayList<String>();
		for(PluginOrderKey item : this.orderKeys)
		{
			PluginOrderStatus status = this.mapping.get(item.getKey());
			if(status.isEnabled())
			{
				for(String write : status.getPlugin().getSettings().getWriteStack())
				{
					// 重複する登録は認めない
					if(stack.contains(write))
					{
						result = false;
						break;
					}
				}
				if(!result)
				{
					break;
				}

				for(String read : status.getPlugin().getSettings().getReadStack())
				{
					// 上位に登録が無ければ認めない
					if(!stack.contains(read))
					{
						result = false;
						break;
					}
				}
				if(!result)
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
				result.setSettings(status.getPlugin().getSettings());
				result.setPluginStack(this.pluginStack);
				status.getPlugin().onChatMessage(result);
			}
			else
			{
				DebugLog.info("onChatMessage() not call: %s", item);
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
			DebugLog.info("findPluginOrderKeyIndex() key=%s, item.getKey()=%s", key, item.getKey());
			if(item.getKey().equals(key))
			{
				result = this.orderKeys.indexOf(item);
				DebugLog.info("findPluginOrderKeyIndex() key=%s, item.getKey()=%s, result: %d", key, item.getKey(), result);
				break;
			}
		}
		return result;
	}


	public void executeOrderUp(PluginOrderStatus plugin)
	{
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				if(findKey >= 1)
				{
					Collections.swap(this.orderKeys, findKey, findKey - 1);
				}
			}
		}
	}

	public void executeOrderDown(PluginOrderStatus plugin)
	{
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				if(findKey < this.orderKeys.size() - 1)
				{
					Collections.swap(this.orderKeys, findKey, findKey + 1);
				}
			}
		}
	}

	public void executeAdd(PluginOrderStatus plugin)
	{
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			DebugLog.info("executeAdd() findKey: %d", findKey);
			if(findKey == -1)
			{
				if(!plugin.isError())
				{
					this.orderKeys.add(plugin.getPluginOrderKey());
					this.createMapping();
				}
			}
		}
	}

	public void executeDelete(PluginOrderStatus plugin)
	{
		if(plugin != null)
		{
			int findKey = this.findPluginOrderKeyIndex(plugin.getPluginOrderKey());
			if(findKey != -1)
			{
				this.orderKeys.remove(findKey);
			}
		}
	}

	public void executeStateToggle(PluginOrderStatus plugin)
	{
		if(plugin != null)
		{
			PluginOrderStatus target = this.mapping.get(plugin.getPluginOrderKey().getKey());
			if(target != null)
			{
				target.setEnabled(!target.isEnabled());
			}
		}
	}

}
