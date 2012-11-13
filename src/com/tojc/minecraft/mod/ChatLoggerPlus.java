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
	public void onPreInit(FMLPreInitializationEvent event)
	{
		DebugLog.info("onPreInit");
	}

	@Mod.Init
	public void onInit(FMLInitializationEvent event)
	{
		DebugLog.info("onInit");
	}

	@Mod.PostInit
	public void onPostInit(FMLPostInitializationEvent event)
	{
		DebugLog.info("onPostInit");
	}
}
