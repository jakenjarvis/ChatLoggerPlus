package com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginEnvironment;

public class PluginEnvironmentImpl implements PluginEnvironment
{
	private String servername = "";
	private String worldname = "";
	private String playerName = "";

	public PluginEnvironmentImpl(String servername, String worldname, String playerName)
	{
		this.servername = new String(servername);
		this.worldname = new String(worldname);
		this.playerName = new String(playerName);
	}

	@Override
	public String getServerName()
	{
		return this.servername;
	}

	@Override
	public String getWorldName()
	{
		return this.worldname;
	}

	@Override
	public String getPlayerName()
	{
		return this.playerName;
	}
}
