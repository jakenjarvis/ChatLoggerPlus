package com.tojc.minecraft.mod.proxy;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerKeyHandler;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientSideProxy implements ProxyInterface
{
	@Override
	public void registerEventListener(ChatLoggerCore core)
	{
		if(core.getConfig().getPluginScriptsEnabled().get())
		{
			// KeyBinding
			KeyBindingRegistry.registerKeyBinding(new ChatLoggerKeyHandler(core));
		}
	}
}
