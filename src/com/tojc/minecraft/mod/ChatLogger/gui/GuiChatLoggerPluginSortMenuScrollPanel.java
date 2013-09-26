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
package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

public class GuiChatLoggerPluginSortMenuScrollPanel extends GuiChatLoggerPluginScrollPanelBase
{
	public GuiChatLoggerPluginSortMenuScrollPanel(
			GuiScreen par1GuiScreen,
			PluginType type,
			TreeMap<Integer, PluginOrderStatus> mapPlugins,
			OnItemClickedListener<PluginOrderStatus> listener,
			Minecraft mc
			)
	{
		super(par1GuiScreen, type, mapPlugins, listener, mc,
				par1GuiScreen.width,
				par1GuiScreen.height,
				16,
				(par1GuiScreen.height - 70) + 4,
				42
				);
	}

	@Override
	protected void drawSlot(int index, int xPosition, int yPosition, int slotHeight, Tessellator tessellator)
	{
		String pluginName = "";
		String pluginPath = "";
		String pluginState = "";
		String pluginStack = "";

		PluginOrderStatus plugin = this.getItems().get(index);
		if(plugin != null)
		{
			String statetitlecolor = "";
			String statecolor = "";
			boolean permissioncolor = false;
			switch(plugin.getPluginState())
			{
				case Disabled:
					statetitlecolor += EnumChatFormatting.GRAY;
					statecolor += EnumChatFormatting.DARK_GRAY;
					break;
				case Enabled:
					statetitlecolor += EnumChatFormatting.WHITE;
					statecolor += EnumChatFormatting.GRAY;
					permissioncolor = true;
					break;
				case Error:
					statetitlecolor += EnumChatFormatting.DARK_RED;
					statecolor += EnumChatFormatting.RED;
					break;
			}

			if(!plugin.isError())
			{
				pluginName = "" + index + ": " + statetitlecolor + plugin.getPlugin().getName() + " " + plugin.getPlugin().getVersion()
						+ EnumChatFormatting.WHITE + " / " + plugin.getPlugin().getAuther();
				pluginPath = statecolor + "Path: " + plugin.getPluginOrderKey().getKey();
				pluginState = statecolor + plugin.getPluginState().getDescription();
				pluginStack = statecolor + plugin.makePermissionString(permissioncolor);
			}
			else
			{
				pluginName = "" + index + ": " + statetitlecolor + plugin.getPluginOrderKey().getKey();
				pluginPath = statecolor + "Path: " + plugin.getPluginOrderKey().getKey();
				pluginState = statecolor + plugin.getPluginState().getDescription();
				pluginStack = "";
			}
		}

        if(this.getSelected() == index)
        {
            this.parentScreen.drawString(this.mc.fontRenderer, EnumChatFormatting.YELLOW + ">", xPosition, yPosition + 0, 0xFFFFFFFF);
        }

        this.parentScreen.drawString(this.mc.fontRenderer, pluginName, xPosition + 5, yPosition + 0, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginPath, xPosition + 5, yPosition + 10, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginState, xPosition + 10, yPosition + 20, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginStack, xPosition + 5, yPosition + 30, 0xFFFFFFFF);
	}
}
