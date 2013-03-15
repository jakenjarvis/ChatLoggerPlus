package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.ArrayList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
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
		void onElementClicked(int index);
	}

    protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private ChatLoggerConfiguration config = null;
    private OnElementClickedListener listener = null;
    private Minecraft mc;

    private int selected = -1;

	public GuiChatLoggerPluginScrollPanel(GuiScreen par1GuiScreen, ChatLoggerConfiguration config, OnElementClickedListener listener, Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
	{
        super(mc, width, height, top, bottom, slotHeight);
        this.parentScreen = par1GuiScreen;
        this.config = config;
        this.listener = listener;
        this.mc = mc;
	}

	@Override
	protected int getSize()
	{
		return 10;
	}

	@Override
	protected void elementClicked(int i, boolean flag)
	{
        if (!flag)
        {
        	this.selected = i;
        	DebugLog.info("elementClicked: %d", i);

        	if(this.listener != null)
        	{
        		this.listener.onElementClicked(this.selected);
        	}
        }
	}

	@Override
	protected boolean isSelected(int par1)
	{
		return true;
	}

	@Override
	protected void drawBackground()
	{
        //this.parentScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int index, int xPosition, int yPosition, int slotHeight, Tessellator tessellator)
	{
        if(this.selected == index)
        {
            this.parentScreen.drawString(this.mc.fontRenderer, EnumChatFormatting.RED + ">", xPosition, yPosition + 1, 0xFFFFFFFF);
        }

        String str = "test" + index;
        this.parentScreen.drawString(this.mc.fontRenderer, str, xPosition + 5, yPosition + 1, 0xFFFFFFFF);
	}
}
