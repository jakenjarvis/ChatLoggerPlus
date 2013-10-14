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
