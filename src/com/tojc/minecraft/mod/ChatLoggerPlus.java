// このファイルはUFT-8です。
package com.tojc.minecraft.mod;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "com.tojc.minecraft.mod.ChatLoggerPlus", name = "ChatLoggerPlus", version = "0.1.0")
public class ChatLoggerPlus
{
	@Instance("com.tojc.minecraft.mod.ChatLoggerPlus")
    public static ChatLoggerPlus instance;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLLog.log(Level.INFO, "ChatLoggerPlus: preInit");
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		FMLLog.log(Level.INFO, "ChatLoggerPlus: init");
	}

	@Mod.PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		FMLLog.log(Level.INFO, "ChatLoggerPlus: postInit");
	}
}
