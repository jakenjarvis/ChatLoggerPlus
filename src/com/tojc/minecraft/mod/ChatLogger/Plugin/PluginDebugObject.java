package com.tojc.minecraft.mod.ChatLogger.Plugin;

import com.tojc.minecraft.mod.log.DebugLog;

public class PluginDebugObject
{
	public PluginDebugObject()
	{
	}

	public void log(String pluginName, String message)
	{
		DebugLog.script("{" + pluginName + "}: " + message);
	}
}
