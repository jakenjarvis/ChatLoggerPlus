package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

public abstract class GuiChatLoggerScrollPanelBase<T> extends GuiSlot
{
    public interface OnItemClickedListener<T>
    {
        void onItemClicked(int index, T item);
    }

    protected final GuiScreen parentScreen;
    private Map<Integer, T> mapItems = null;
    private OnItemClickedListener<T> listener = null;
    protected Minecraft mc;

    private int selected = -1;

    public GuiChatLoggerScrollPanelBase(GuiScreen par1GuiScreen, Map<Integer, T> mapItems, OnItemClickedListener<T> listener, Minecraft mc, int width, int height, int top, int bottom, int slotHeight)
    {
        super(mc, width, height, top, bottom, slotHeight);
        this.parentScreen = par1GuiScreen;
        this.mapItems = mapItems;
        this.listener = listener;
        this.mc = mc;
    }

    public Map<Integer, T> getItems()
    {
        return this.mapItems;
    }

    @Override
    protected int getSize()
    {
        return this.mapItems.size();
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
                    this.listener.onItemClicked(this.selected, this.mapItems.get(this.selected));
                }
                else
                {
                    this.listener.onItemClicked(this.selected, null);
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
}
