package com.tojc.minecraft.mod;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class DebugLog
{
	private static String HEADER = "ChatLoggerPlus : ";

    public static void log(Level level, String format, Object... data)
    {
		FMLLog.log(level, HEADER + format, data);
    }

    public static void log(Level level, Throwable ex, String format, Object... data)
    {
		FMLLog.log(level, ex, HEADER + format, data);
    }

    public static void severe(String format, Object... data)
    {
		FMLLog.severe(HEADER + format, data);
    }

    public static void warning(String format, Object... data)
    {
		FMLLog.warning(HEADER + format, data);
    }

    public static void info(String format, Object... data)
    {
		FMLLog.info(HEADER + format, data);
    }

    public static void fine(String format, Object... data)
    {
		FMLLog.fine(HEADER + format, data);
    }

    public static void finer(String format, Object... data)
    {
		FMLLog.finer(HEADER + format, data);
    }

    public static void finest(String format, Object... data)
    {
		FMLLog.finest(HEADER + format, data);
    }

    public static Logger getLogger()
    {
        return FMLLog.getLogger();
    }
}
