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
