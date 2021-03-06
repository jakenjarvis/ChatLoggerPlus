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
package com.tojc.minecraft.mod;

import java.util.logging.Level;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.log.DebugLog;
import com.tojc.minecraft.mod.proxy.ProxyInterface;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "com.tojc.minecraft.mod.ChatLoggerPlus", name = "ChatLoggerPlus")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ChatLoggerPlus
{
	@Instance("com.tojc.minecraft.mod.ChatLoggerPlus")
	protected static ChatLoggerPlus instance;

	@SidedProxy(
		clientSide = "com.tojc.minecraft.mod.proxy.ClientSideProxy",
		serverSide = "com.tojc.minecraft.mod.proxy.ServerSideProxy")
	public static ProxyInterface proxy;

	private ChatLoggerCore core = new ChatLoggerCore();

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		this.core.onPreInit(event);
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		this.core.onInit(event);
	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		this.core.onPostInit(event);
	}


    @EventHandler
    public void onFMLServerAboutToStartEvent(FMLServerAboutToStartEvent event)
    {
		this.core.onFMLServerAboutToStartEvent(event);
    }

    @EventHandler
    public void onFMLServerStartingEvent(FMLServerStartingEvent event)
    {
		this.core.onFMLServerStartingEvent(event);
    }

    @EventHandler
    public void onFMLServerStartedEvent(FMLServerStartedEvent event)
    {
		this.core.onFMLServerStartedEvent(event);
    }

    @EventHandler
    public void onFMLServerStoppingEvent(FMLServerStoppingEvent event)
    {
		this.core.onFMLServerStoppingEvent(event);
    }

    @EventHandler
    public void onFMLServerStoppedEvent(FMLServerStoppedEvent event)
    {
		this.core.onFMLServerStoppedEvent(event);
    }
}
