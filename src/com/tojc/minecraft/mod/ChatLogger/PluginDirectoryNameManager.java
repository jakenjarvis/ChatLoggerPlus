package com.tojc.minecraft.mod.ChatLogger;

import java.io.File;

import cpw.mods.fml.common.Loader;

import net.minecraft.client.Minecraft;

public class PluginDirectoryNameManager
{
	private ChatLoggerConfiguration config = null;

	public PluginDirectoryNameManager(ChatLoggerConfiguration config)
	{
		this.config = config;
	}

	public File getPluginDirectoryFile()
	{
		File result = null;
		result = new File(getDefaultPluginDirectoryName());
		return result;
	}

	private String getDefaultPluginDirectoryName()
	{
		String defaultPluginDirectoryName = this.config.getDefaultPluginDirectoryName().get();

		String result = new File(Loader.instance().getConfigDir().toString(), defaultPluginDirectoryName).getPath();
		return result;
	}

}
