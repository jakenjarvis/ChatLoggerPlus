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

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

public class GuiChatLoggerPluginSelectScrollPanel extends GuiChatLoggerPluginScrollPanelBase
{
	public GuiChatLoggerPluginSelectScrollPanel(
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
				62
				);
	}

	@Override
	protected void drawSlot(int index, int xPosition, int yPosition, int slotHeight, Tessellator tessellator)
	{
		String pluginName = "";
		String pluginDescription = "";
		String pluginAuther = "";
		String pluginPath = "";
		String pluginState = "";

		PluginOrderStatus plugin = this.getItems().get(index);
		if(plugin != null)
		{
			String statetitlecolor = "";
			String statecolor = "";
			switch(plugin.getPluginState())
			{
				case Disabled:
					statetitlecolor += EnumChatFormatting.GRAY;
					statecolor += EnumChatFormatting.DARK_GRAY;
					break;
				case Enabled:
					statetitlecolor += EnumChatFormatting.WHITE;
					statecolor += EnumChatFormatting.GRAY;
					break;
				case Error:
					statetitlecolor += EnumChatFormatting.DARK_RED;
					statecolor += EnumChatFormatting.RED;
					break;
			}

			if(!plugin.isError())
			{
				pluginName = statetitlecolor + plugin.getPlugin().getName() + " " + plugin.getPlugin().getVersion();
				pluginDescription = statecolor + plugin.getPlugin().getDescription();
				pluginAuther = statecolor + "Auther: " + plugin.getPlugin().getAuther();
				pluginPath = statecolor + "Path: " + plugin.getPluginOrderKey().getKey();
				pluginState = statecolor + plugin.getPluginState().getDescription();
			}
			else
			{
				pluginName = statetitlecolor + plugin.getPluginOrderKey().getKey();
				pluginDescription = "";
				pluginAuther = "";
				pluginPath = statecolor + "Path: " + plugin.getPluginOrderKey().getKey();
				pluginState = statecolor + plugin.getPluginState().getDescription();
			}
		}

        if(this.getSelected() == index)
        {
            this.parentScreen.drawString(this.mc.fontRenderer, EnumChatFormatting.YELLOW + ">", xPosition, yPosition + 0, 0xFFFFFFFF);
        }

        this.parentScreen.drawString(this.mc.fontRenderer, pluginName, xPosition + 5, yPosition + 0, 0xFFFFFFFF);
        //this.parentScreen.drawString(this.mc.fontRenderer, pluginDescription, xPosition + 5, yPosition + 10, 0xFFFFFFFF);

        List list = this.mc.fontRenderer.listFormattedStringToWidth(pluginDescription, 210);
        int pos = 10;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); pos += 9)
        {
        	if(pos >= 28)
        	{
        		break;
        	}
        	this.parentScreen.drawString(this.mc.fontRenderer, EnumChatFormatting.GRAY + (String)iterator.next(), xPosition + 10, yPosition + pos, 0xFFFFFFFF);
        }

        this.parentScreen.drawString(this.mc.fontRenderer, pluginAuther, xPosition + 5, yPosition + 30, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginPath, xPosition + 5, yPosition + 40, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginState, xPosition + 10, yPosition + 50, 0xFFFFFFFF);
	}
}
