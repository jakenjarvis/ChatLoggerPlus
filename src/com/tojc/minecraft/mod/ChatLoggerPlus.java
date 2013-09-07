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

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "com.tojc.minecraft.mod.ChatLoggerPlus", name = "ChatLoggerPlus")
public class ChatLoggerPlus
{
	@Instance("com.tojc.minecraft.mod.ChatLoggerPlus")
	protected static ChatLoggerPlus instance;

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
}
