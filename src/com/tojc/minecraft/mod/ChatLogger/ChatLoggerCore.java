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
package com.tojc.minecraft.mod.ChatLogger;

import java.io.File;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import com.tojc.minecraft.mod.ChatLoggerPlus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginManager;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginOrderManager;
import com.tojc.minecraft.mod.ChatLogger.Writer.SpecialLogWriter;
import com.tojc.minecraft.mod.log.DebugLog;
import com.tojc.minecraft.mod.proxy.ProxyInterface;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ChatLoggerCore
{
	private ChatLoggerConfiguration config = null;
	private PluginManager pluginManager = null;

	private HandlerAndEventListener listener = null;
	private SpecialLogWriter writer = null;

	public ProxyInterface getSidedProxy()
	{
		return ChatLoggerPlus.proxy;
	}

	public ChatLoggerConfiguration getConfig()
	{
		return this.config;
	}

	public PluginManager getPluginManager()
	{
		return this.pluginManager;
	}

	public void onPreInit(FMLPreInitializationEvent event)
	{
		DebugLog.trace("onPreInit");

		this.config = new ChatLoggerConfiguration(event.getSuggestedConfigurationFile());
		DebugLog.loadConfig(this.config);

		this.listener = new HandlerAndEventListener(this);
	}

	public void onInit(FMLInitializationEvent event)
	{
		DebugLog.trace("onInit");

		this.writer = new SpecialLogWriter(this);

		if(this.config.getPluginScriptsEnabled().get())
		{
			this.pluginManager = new PluginManager(this.config);
		}
	}

	public void onPostInit(FMLPostInitializationEvent event)
	{
		DebugLog.trace("onPostInit");
	}

	public void onWorldLoad(String worldname)
	{
		this.writer.setWorldName(worldname);

		if(this.config.getPluginScriptsEnabled().get())
		{
			this.pluginManager.load();
		}
	}

	public void onWorldUnload(String worldname)
	{
		if(this.config.getPluginScriptsEnabled().get())
		{
			this.pluginManager.unload();
		}
	}

	public void onServerConnection(String servername)
	{
		this.writer.setServerName(servername);
	}

	public void onOpen()
	{
		this.writer.open();
	}

	public void onWrite(String message)
	{
		this.writer.write(message);
	}

	public void onFlush()
	{
		this.writer.flush();
	}

	public void onClose()
	{
		this.writer.close();
	}


	public String getPlayerName()
	{
		String result = null;
		Minecraft mc = Minecraft.getMinecraft();
		if((mc != null) && (mc.thePlayer != null))
		{
			result = mc.thePlayer.getEntityName();
		}
		return result;
	}

	public void sendLocalChatMessage(String message)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if((mc != null) && (mc.thePlayer != null))
		{
			mc.thePlayer.addChatMessage(message);
		}
	}

	public void sendGlobalChatMessage(String message)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if((mc != null) && (mc.thePlayer != null))
		{
			mc.thePlayer.sendChatMessage(message);
		}
	}
}
