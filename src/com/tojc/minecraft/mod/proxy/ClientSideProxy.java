package com.tojc.minecraft.mod.proxy;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerKeyHandler;

import cpw.mods.fml.client.registry.KeyBindingRegistry;

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
