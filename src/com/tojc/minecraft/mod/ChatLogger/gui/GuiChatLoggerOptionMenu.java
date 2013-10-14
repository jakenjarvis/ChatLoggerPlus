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

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiChatLoggerOptionMenu extends GuiScreen
{
    protected String screenTitle = "ChatLoggerPlus settings";

    private ChatLoggerCore core = null;

    public GuiChatLoggerOptionMenu(ChatLoggerCore core)
    {
    	super();
    	this.core = core;
    }

	@Override
	public void initGui()
	{
        this.buttonList.clear();

		if(this.core.getConfig().getPluginScriptsEnabled().get())
		{
			this.buttonList.add(new GuiButton(101, this.width / 2 - 152, this.height / 6 + 96 - 6, 150, 20, "Screen Plugin"));
			this.buttonList.add(new GuiButton(102, this.width / 2 +   2, this.height / 6 + 96 - 6, 150, 20, "ChatLog Plugin"));
		}

		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.getString("gui.done")));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			switch(par1GuiButton.id)
			{
				case 0:
					break;

				case 101:
					GuiChatLoggerPluginSortMenu screenmenu = new GuiChatLoggerPluginSortMenu(this, this.core, PluginType.Screen);
					this.mc.displayGuiScreen(screenmenu);
					break;

				case 102:
					GuiChatLoggerPluginSortMenu chatlog = new GuiChatLoggerPluginSortMenu(this, this.core, PluginType.ChatLog);
					this.mc.displayGuiScreen(chatlog);
					break;

				case 200:
	                this.mc.displayGuiScreen(null);
	                this.mc.setIngameFocus();
	                this.mc.sndManager.resumeAllSounds();
					break;
			}
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 15, 0xffffff);
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
