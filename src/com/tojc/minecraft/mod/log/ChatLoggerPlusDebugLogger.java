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
package com.tojc.minecraft.mod.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class ChatLoggerPlusDebugLogger
{
	private final static Logger logger = getLoggerInstance();

	private static Logger getLoggerInstance()
	{
		Logger result = Logger.getLogger("ChatLoggerPlus");	// com.tojc.minecraft.mod.ChatLoggerPlus
		result.setParent(FMLLog.getLogger());
		return result;
	}

	// severe,		warning,	info,		config,		fine,		finer,		finest
	// 致命的		警告		情報		設定		詳細低		詳細中		詳細高

    public static void log(Level level, String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.log(level, String.format(format, data));
    	}
    	else
    	{
    		logger.log(level, format);
    	}
    }

    public static void log(Level level, Throwable ex, String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.log(level, String.format(format, data), ex);
    	}
    	else
    	{
    		logger.log(level, format, ex);
    	}
    }

    public static void severe(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.severe(String.format(format, data));
    	}
    	else
    	{
    		logger.severe(format);
    	}
    }

    public static void warning(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.warning(String.format(format, data));
    	}
    	else
    	{
    		logger.warning(format);
    	}
    }

    public static void info(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.info(String.format(format, data));
    	}
    	else
    	{
    		logger.info(format);
    	}
    }

    public static void config(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.config(String.format(format, data));
    	}
    	else
    	{
    		logger.config(format);
    	}
    }

    public static void fine(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.fine(String.format(format, data));
    	}
    	else
    	{
    		logger.fine(format);
    	}
    }

    public static void finer(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.finer(String.format(format, data));
    	}
    	else
    	{
    		logger.finer(format);
    	}
    }

    public static void finest(String format, Object... data)
    {
    	if(data.length >= 1)
    	{
    		logger.finest(String.format(format, data));
    	}
    	else
    	{
    		logger.finest(format);
    	}
    }

}
