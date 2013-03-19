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

public abstract class GuiChatLoggerPluginScrollPanelBase extends GuiSlot
{
	public interface OnElementClickedListener
	{
		void onElementClicked(int index, PluginOrderStatus plugin);
	}

    protected final GuiScreen parentScreen;
    protected TreeMap<Integer, PluginOrderStatus> mapPlugins = null;
    protected PluginType type = null;
    protected OnElementClickedListener listener = null;
    protected Minecraft mc;

    private int selected = -1;

	public GuiChatLoggerPluginScrollPanelBase(GuiScreen par1GuiScreen, PluginType type, TreeMap<Integer, PluginOrderStatus> mapPlugins, OnElementClickedListener listener, Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
	{
        super(mc, width, height, top, bottom, slotHeight);
        this.parentScreen = par1GuiScreen;
		this.type = type;
		this.mapPlugins = mapPlugins;
        this.listener = listener;
        this.mc = mc;
	}

	public TreeMap<Integer, PluginOrderStatus> getMapPlugins()
	{
		return this.mapPlugins;
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
        	//DebugLog.info("elementClicked: %d", i);

        	if(this.listener != null)
        	{
        		if(this.selected != -1)
        		{
            		this.listener.onElementClicked(this.selected, this.mapPlugins.get(this.selected));
        		}
        		else
        		{
            		this.listener.onElementClicked(this.selected, null);
        		}
        	}
        }
	}

	@Override
	protected boolean isSelected(int par1)
	{
		return (this.selected == par1);
	}

	public int getSelected()
	{
		return this.selected;
	}
	public void setSelected(int selected)
	{
		this.selected = selected;
	}

	@Override
	protected void drawBackground()
	{
        this.parentScreen.drawDefaultBackground();
	}
}
