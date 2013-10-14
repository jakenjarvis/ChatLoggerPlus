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
package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1.PluginInformation;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderKey;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;
import com.tojc.minecraft.mod.Crypto.SimpleEncryption;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginOrderManager
{
	private List<PluginInformation> plugins = null;
	private Map<String, Object> pluginStack = null;
	private Map<PluginType, PluginOrderController> pluginControllers = null;

	private Map<String, PluginInformation> masterMapping = null;

	public PluginOrderManager(ChatLoggerConfiguration config)
	{
		this.pluginStack = new HashMap<String, Object>();
		this.pluginControllers = new HashMap<PluginType, PluginOrderController>();

		for(PluginType type : PluginType.values())
		{
			this.pluginControllers.put(type, new PluginOrderController(config, type, this.pluginStack));
		}
	}

	public List<PluginInformation> getPlugins()
	{
		return this.plugins;
	}
	public void setPlugins(List<PluginInformation> plugins)
	{
		this.plugins = plugins;
	}

	public Map<String, Object> getPluginStack()
	{
		return this.pluginStack;
	}

	public Map<String, PluginInformation> getMasterMapping()
	{
		return this.masterMapping;
	}

	public void createMapping()
	{
		this.masterMapping = new HashMap<String, PluginInformation>();
		for(PluginInformation info : this.plugins)
		{
			this.masterMapping.put(info.getPluginKey(), info);
		}

		for(Map.Entry<PluginType, PluginOrderController> entry : this.pluginControllers.entrySet())
		{
			entry.getValue().setMasterMapping(this.masterMapping);
			entry.getValue().createMapping();
		}
	}

	public void loadSetting()
	{
		for(Map.Entry<PluginType, PluginOrderController> entry : this.pluginControllers.entrySet())
		{
			entry.getValue().loadSetting();
		}
	}

	public void saveSetting()
	{
		for(Map.Entry<PluginType, PluginOrderController> entry : this.pluginControllers.entrySet())
		{
			entry.getValue().saveSetting();
		}
	}

	public PluginOrderController getPluginOrderController(PluginType type)
	{
		return this.pluginControllers.get(type);
	}

	public PluginOrderController getPluginScreenOrderController()
	{
		return this.pluginControllers.get(PluginType.Screen);
	}

	public PluginOrderController getPluginChatLogOrderController()
	{
		return this.pluginControllers.get(PluginType.ChatLog);
	}

	public TreeMap<Integer, PluginOrderStatus> getMasterTreeMap()
	{
		TreeMap<Integer, PluginOrderStatus> result = new TreeMap<Integer, PluginOrderStatus>();
		int index = 0;
		for(Map.Entry<String, PluginInformation> entry : this.masterMapping.entrySet())
		{
			PluginOrderKey key = new PluginOrderKey(entry.getKey(), PluginState.Enabled);
			PluginOrderStatus status = new PluginOrderStatus(null, key, entry.getValue());
			result.put(index, status);
			index++;
		}
		return result;
	}

}
