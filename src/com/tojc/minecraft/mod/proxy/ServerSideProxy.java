package com.tojc.minecraft.mod.proxy;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerSideProxy implements ProxyInterface
{
	@Override
	public void registerEventListener(ChatLoggerCore core)
	{
	}
}
