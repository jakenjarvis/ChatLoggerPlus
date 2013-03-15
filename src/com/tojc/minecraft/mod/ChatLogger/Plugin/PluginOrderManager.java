package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;
import com.tojc.minecraft.mod.Crypto.SimpleEncryption;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginOrderManager
{
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

	public Map<String, Object> getPluginStack()
	{
		return this.pluginStack;
	}

	public Map<String, PluginInformation> getMasterMapping()
	{
		return this.masterMapping;
	}

	public void createMapping(List<PluginInformation> plugins)
	{
		this.masterMapping = new HashMap<String, PluginInformation>();
		for(PluginInformation info : plugins)
		{
			this.masterMapping.put(info.getPluginKey(), info);
		}

		for(Map.Entry<PluginType, PluginOrderController> entry : this.pluginControllers.entrySet())
		{
			entry.getValue().createMapping(this.masterMapping);
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

}
