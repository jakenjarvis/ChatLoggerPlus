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

public class GuiChatLoggerPluginScrollPanel extends GuiSlot
{
	public interface OnElementClickedListener
	{
		void onElementClicked(int index, PluginOrderStatus plugin);
	}

    protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private TreeMap<Integer, PluginOrderStatus> mapPlugins = null;
    private PluginType type = null;
    private OnElementClickedListener listener = null;
    private Minecraft mc;

    private int selected = -1;

	public GuiChatLoggerPluginScrollPanel(GuiScreen par1GuiScreen, PluginType type, TreeMap<Integer, PluginOrderStatus> mapPlugins, OnElementClickedListener listener, Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
	{
        super(mc, width, height, top, bottom, slotHeight);
        this.parentScreen = par1GuiScreen;
		this.type = type;
		this.mapPlugins = mapPlugins;
        this.listener = listener;
        this.mc = mc;
	}

	@Override
	protected int getSize()
	{
		return this.mapPlugins.size();
	}

	@Override
	protected void elementClicked(int i, boolean flag)
	{
        if (!flag)
        {
        	this.selected = i;
        	DebugLog.info("elementClicked: %d", i);

    		if(this.selected != -1)
    		{
            	if(this.listener != null)
            	{
            		this.listener.onElementClicked(this.selected, this.mapPlugins.get(this.selected));
            	}
    		}
        }
	}

	@Override
	protected boolean isSelected(int par1)
	{
		return (this.selected == par1);
	}

	@Override
	protected void drawBackground()
	{
        //this.parentScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int index, int xPosition, int yPosition, int slotHeight, Tessellator tessellator)
	{
		String pluginName = "";
		String pluginPath = "";
		String pluginState = "";

		PluginOrderStatus plugin = this.mapPlugins.get(index);
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
				case ErrorMissing:
					statetitlecolor += EnumChatFormatting.GOLD;
					statecolor += EnumChatFormatting.YELLOW;
					break;
				case ErrorBug:
					statetitlecolor += EnumChatFormatting.DARK_RED;
					statecolor += EnumChatFormatting.RED;
					break;
			}

			pluginName = "" + index + ": " + statetitlecolor + plugin.getPlugin().getName() + " " + plugin.getPlugin().getVersion()
						+ EnumChatFormatting.WHITE + " / " + plugin.getPlugin().getAuther();
			pluginPath = EnumChatFormatting.GRAY + "Path: " + plugin.getPluginOrderKey().getKey();
			pluginState = statecolor + plugin.getPluginState().getDescription();
		}

        if(this.selected == index)
        {
            this.parentScreen.drawString(this.mc.fontRenderer, EnumChatFormatting.YELLOW + ">", xPosition, yPosition + 0, 0xFFFFFFFF);
        }

        this.parentScreen.drawString(this.mc.fontRenderer, pluginName, xPosition + 5, yPosition + 0, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginPath, xPosition + 5, yPosition + 10, 0xFFFFFFFF);
        this.parentScreen.drawString(this.mc.fontRenderer, pluginState, xPosition + 5, yPosition + 20, 0xFFFFFFFF);
	}
}
