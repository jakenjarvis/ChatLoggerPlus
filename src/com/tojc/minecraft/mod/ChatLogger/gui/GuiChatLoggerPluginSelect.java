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

import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginInformation;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiChatLoggerPluginSelect extends GuiScreen implements GuiChatLoggerScrollPanelBase.OnItemClickedListener<PluginOrderStatus>
{
	public interface OnPluginSelectedListener
	{
		void onPluginSelected(PluginOrderStatus plugin);
	}

	protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private ChatLoggerCore core = null;
    private PluginType type = null;
    private OnPluginSelectedListener listener = null;

    private GuiChatLoggerPluginScrollPanelBase scrollPanel = null;

    private PluginOrderStatus resultPlugin = null;

	public GuiChatLoggerPluginSelect(GuiScreen par1GuiScreen, ChatLoggerCore core, PluginType type, OnPluginSelectedListener listener)
	{
    	super();
        this.parentScreen = par1GuiScreen;
		this.core = core;
		this.type = type;
		this.listener = listener;

		this.resultPlugin = null;
	}

	@Override
	public void initGui()
	{
		TreeMap<Integer, PluginOrderStatus> mapPlugins = this.core.getPluginManager().getPluginOrderManager().getMasterTreeMap();

        this.scrollPanel = new GuiChatLoggerPluginSelectScrollPanel(this, this.type, mapPlugins, this, this.mc);

		this.buttonList.add(new GuiButton(  0, this.width / 2 -  90 - 10, this.height -28,  90, 20, I18n.getString("gui.cancel")));
		this.buttonList.add(new GuiButton(200, this.width / 2 -   0 + 10, this.height -28,  90, 20, I18n.getString("gui.done")));

        this.scrollPanel.registerScrollButtons(7, 8);

        this.screenTitle = "Select Plugin (" + this.type.getName() + ")";
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			switch(par1GuiButton.id)
			{
				case 0:		// Cancel
					this.resultPlugin = null;
		            this.mc.displayGuiScreen(this.parentScreen);
					break;

				case 200:	// Done
					if(this.resultPlugin != null)
					{
						if(this.listener != null)
						{
							this.listener.onPluginSelected(this.resultPlugin);
						}
					}
		            this.mc.displayGuiScreen(this.parentScreen);
					break;
			}
		}
	}

	@Override
	public void onItemClicked(int index, PluginOrderStatus item)
	{
		this.resultPlugin = item;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
        this.drawDefaultBackground();
        this.scrollPanel.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 4, 0xffffff);

        super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}

	@Override
	public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
	}

	@Override
	public void drawWorldBackground(int par1)
	{
		super.drawWorldBackground(par1);
	}

	@Override
	public void drawBackground(int par1)
	{
		super.drawBackground(par1);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return super.doesGuiPauseGame();
	}

	@Override
	public void confirmClicked(boolean par1, int par2)
	{
		super.confirmClicked(par1, par2);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}
}
