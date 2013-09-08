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

import com.tojc.minecraft.mod.ChatLogger.SpecialLogWriter.FileOperationCompletedEvent;
import com.tojc.minecraft.mod.ChatLogger.SpecialLogWriter.FileOperationCompletedListener;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginManager;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginOrderManager;
import com.tojc.minecraft.mod.log.DebugLog;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ChatLoggerCore implements FileOperationCompletedListener
{
	private ChatLoggerConfiguration config = null;
	private PluginManager pluginManager = null;

	private HandlerAndEventListener listener = null;
	private SpecialLogWriter writer = null;

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
		DebugLog.info("onPreInit");

		this.config = new ChatLoggerConfiguration(event.getSuggestedConfigurationFile());

		if(this.config.getChatLoggerEnabled().get())
		{
			this.pluginManager = new PluginManager(this.config);

			this.listener = new HandlerAndEventListener(this);
			this.writer = new SpecialLogWriter(this.config, this);
		}
	}

	public void onInit(FMLInitializationEvent event)
	{
		DebugLog.info("onInit");
	}

	public void onPostInit(FMLPostInitializationEvent event)
	{
		DebugLog.info("onPostInit");
	}

	public void onWorldLoad(String worldname)
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.setWorldName(worldname);
			this.pluginManager.load();
		}
	}

	public void onWorldUnload(String worldname)
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.pluginManager.unload();
		}
	}

	public void onServerConnection(String servername)
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.setServerName(servername);
		}
	}

	public void onOpen()
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.open();
		}
	}

	@Override
	public void onOpenFileOperationCompleted(FileOperationCompletedEvent e)
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.sendLocalChatMessage("§aChatLoggerPlus: §rLogging start.");
			this.sendLocalChatMessage("§aChatLoggerPlus: §r" + e.getFileName());
		}
	}

	public void onWrite(String message)
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.write(message);
		}
	}

	public void onFlush()
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.flush();
		}
	}

	public void onClose()
	{
		if(this.config.getChatLoggerEnabled().get())
		{
			this.writer.close();
		}
	}

	@Override
	public void onCloseFileOperationCompleted(FileOperationCompletedEvent e)
	{
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
