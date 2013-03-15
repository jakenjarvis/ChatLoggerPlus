package com.tojc.minecraft.mod.ChatLogger.Plugin.Type;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;

public interface PluginTypeInterface
{
	public ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config);
}
