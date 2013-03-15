package com.tojc.minecraft.mod.ChatLogger;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import com.tojc.minecraft.mod.ChatLogger.gui.GuiChatLoggerOptionMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class ChatLoggerKeyHandler extends KeyHandler
{
	private final static String KEY_SETTINGS = "ChatLoggerPlus settings";

	private ChatLoggerCore core = null;
	private ChatLoggerConfiguration config = null;

	public ChatLoggerKeyHandler(ChatLoggerCore core)
	{
		super(
			new KeyBinding[]
			{
				new KeyBinding(KEY_SETTINGS, Keyboard.KEY_L)
			},
			new boolean[]
			{
				false
			});

		this.core = core;
		this.config = this.core.getConfig();
	}

	@Override
	public String getLabel()
	{
		return "ChatLoggerPlus";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if((tickEnd) && (types.contains(TickType.CLIENT)) && (mc != null))
		{
			if(kb.keyDescription.equals(KEY_SETTINGS))
			{
				if(mc.currentScreen == null)
				{
					mc.displayGuiScreen(new GuiChatLoggerOptionMenu(this.core));
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}
}
