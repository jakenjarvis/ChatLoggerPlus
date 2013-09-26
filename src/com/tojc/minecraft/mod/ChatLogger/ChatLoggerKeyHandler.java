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

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import com.tojc.minecraft.mod.ChatLogger.gui.GuiChatLoggerOptionMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ChatLoggerKeyHandler extends KeyHandler
{
	private final static String KEY_SETTINGS = "ChatLoggerPlus settings";

	private ChatLoggerCore core = null;
	private ChatLoggerConfiguration config = null;

	public ChatLoggerKeyHandler(ChatLoggerCore core)
	{
		super(
			new KeyBinding[]
			{
				new KeyBinding(KEY_SETTINGS, Keyboard.KEY_L)
			},
			new boolean[]
			{
				false
			});

		this.core = core;
		this.config = this.core.getConfig();
	}

	@Override
	public String getLabel()
	{
		return "ChatLoggerPlus";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if((tickEnd) && (types.contains(TickType.CLIENT)) && (mc != null))
		{
			if(kb.keyDescription.equals(KEY_SETTINGS))
			{
				if(mc.currentScreen == null)
				{
					mc.displayGuiScreen(new GuiChatLoggerOptionMenu(this.core));
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
}
