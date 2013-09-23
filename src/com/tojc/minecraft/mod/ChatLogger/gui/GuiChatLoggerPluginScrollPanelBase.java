package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;
import com.tojc.minecraft.mod.log.DebugLog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiChatLoggerPluginScrollPanelBase extends GuiChatLoggerScrollPanelBase<PluginOrderStatus>
{
    protected PluginType type = null;

	public GuiChatLoggerPluginScrollPanelBase(GuiScreen par1GuiScreen, PluginType type, TreeMap<Integer, PluginOrderStatus> mapPlugins, OnItemClickedListener<PluginOrderStatus> listener, Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
	{
		super(par1GuiScreen, mapPlugins, listener, mc, width, height, top, bottom, slotHeight);
		this.type = type;
	}

    @Override
    protected void drawBackground()
    {
        this.parentScreen.drawDefaultBackground();
    }
}
