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

import java.util.EnumMap;
import java.util.logging.Level;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;

public class DebugLog
{
	public interface StringFunction
	{
		String generate();
	}

	private enum LogType
	{
		Error(					"[ ERROR     ]: ", Level.SEVERE),
		Warning(				"[ WARNING   ]: ", Level.WARNING),
		Trace(					"[ TRACE     ]: ", Level.INFO),
		Script(					"[ SCRIPT    ]: ", Level.INFO),
		MessageOriginal(		"[ ORIGINAL  ]: ", Level.INFO),
		MessageDuringChatLog(	"[DURING_CHAT]: ", Level.INFO),
		MessageDuringScreen(	"[DURING_SCR ]: ", Level.INFO),
		MessageLastChatLog(		"[ LAST_CHAT ]: ", Level.INFO),
		MessageLastScreen(		"[ LAST_SCR  ]: ", Level.INFO);

		private LogType(String name, Level level)
		{
			this.name = name;
			this.level = level;
		}

		private final String name;
		private final Level level;

		public String getName()
		{
			return this.name;
		}

		public Level getLevel()
		{
			return this.level;
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}

	private static EnumMap<LogType, Boolean> flagOutput = new EnumMap<LogType, Boolean>(LogType.class);

	public static void loadConfig(ChatLoggerConfiguration config)
	{
		if(config != null)
		{
			flagOutput.put(LogType.Error, config.getOutputLoggingError().get());
			flagOutput.put(LogType.Warning, config.getOutputLoggingWarning().get());
			flagOutput.put(LogType.Trace, config.getOutputLoggingTrace().get());
			flagOutput.put(LogType.Script, config.getOutputLoggingScript().get());
			flagOutput.put(LogType.MessageOriginal, config.getOutputLoggingMessageOriginal().get());
			flagOutput.put(LogType.MessageDuringChatLog, config.getOutputLoggingMessageDuringChatLog().get());
			flagOutput.put(LogType.MessageDuringScreen, config.getOutputLoggingMessageDuringScreen().get());
			flagOutput.put(LogType.MessageLastChatLog, config.getOutputLoggingMessageLastChatLog().get());
			flagOutput.put(LogType.MessageLastScreen, config.getOutputLoggingMessageLastScreen().get());
		}
	}

	private static boolean canOutputLogging(LogType type)
	{
		boolean result = true;
		Boolean target = flagOutput.get(type);
		if(target != null)
		{
			result = (boolean)target;
		}
		return result;
	}

	private static void log(LogType type, String format, Object... data)
	{
		if(canOutputLogging(type))
		{
			ChatLoggerPlusDebugLogger.log(type.getLevel(), type.toString() + format, data);
		}
	}

	private static void log(LogType type, Throwable ex, String format, Object... data)
	{
		if(canOutputLogging(type))
		{
			ChatLoggerPlusDebugLogger.log(type.getLevel(), ex, type.toString() + format, data);
		}
	}

	private static void log(LogType type, StringFunction func)
	{
		if(canOutputLogging(type))
		{
			if(func != null)
			{
				ChatLoggerPlusDebugLogger.log(type.getLevel(), type.toString() + func.generate());
			}
		}
	}


	public static void error(String format, Object... data)
	{
		log(LogType.Error, format, data);
	}
	public static void error(Throwable ex, String format, Object... data)
	{
		log(LogType.Error, ex, format, data);
	}


	public static void warning(String format, Object... data)
	{
		log(LogType.Warning, format, data);
	}
	public static void warning(Throwable ex, String format, Object... data)
	{
		log(LogType.Warning, ex, format, data);
	}


	public static void trace(String format, Object... data)
	{
		log(LogType.Trace, format, data);
	}


	public static void script(String format, Object... data)
	{
		log(LogType.Script, format, data);
	}


	public static void message_original(String format, Object... data)
	{
		log(LogType.MessageOriginal, format, data);
	}
	public static void message_original(StringFunction func)
	{
		log(LogType.MessageOriginal, func);
	}


	public static void message_during_chatlog(String format, Object... data)
	{
		log(LogType.MessageDuringChatLog, format, data);
	}
	public static void message_during_chatlog(StringFunction func)
	{
		log(LogType.MessageDuringChatLog, func);
	}


	public static void message_during_screen(String format, Object... data)
	{
		log(LogType.MessageDuringScreen, format, data);
	}
	public static void message_during_screen(StringFunction func)
	{
		log(LogType.MessageDuringScreen, func);
	}


	public static void message_last_chatlog(String format, Object... data)
	{
		log(LogType.MessageLastChatLog, format, data);
	}
	public static void message_last_chatlog(StringFunction func)
	{
		log(LogType.MessageLastChatLog, func);
	}


	public static void message_last_screen(String format, Object... data)
	{
		log(LogType.MessageLastScreen, format, data);
	}
	public static void message_last_screen(StringFunction func)
	{
		log(LogType.MessageLastScreen, func);
	}

}
