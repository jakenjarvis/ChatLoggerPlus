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

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ChatLoggerConfiguration
{
	private Configuration config = null;

	private Property chatLoggerEnabled = null;
	private Property formatDateTime = null;
	private Property formatReplaceDate = null;
	private Property formatReplaceTime = null;

	private Property defaultReplaceLogFileFolderName = null;
	private Property defaultReplaceLogFileName = null;

	private Property enforcementReplaceLogFileFullPathFileName = null;

	private static String CATEGORY_LOGOUTPUT_FORMAT = "logoutputformat";
	private static String CATEGORY_FILENAME_FORMAT = "filenameformat";

	private static String CATEGORY_RELATIVE_PATH = "relativepath";
	private static String CATEGORY_ABSOLUTE_PATH = "absolutepath";
	//

	public ChatLoggerConfiguration(File file)
	{
		this.config = new Configuration(file);
		try
		{
			this.config.load();

			// TODO: エラーになったら、読み飛ばしちゃうので、いずれ修正。

			this.chatLoggerEnabled = this.config.get(Configuration.CATEGORY_GENERAL, "ChatLoggerEnabled", true);
			this.chatLoggerEnabled.comment = "true/false";

			this.defaultReplaceLogFileFolderName = this.config.get(CATEGORY_RELATIVE_PATH, "DefaultReplaceLogFileFolderName", "chatlog/%SERVERNAME%/%PLAYERNAME%");
			this.defaultReplaceLogFileFolderName.comment = "Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%";

			this.defaultReplaceLogFileName = this.config.get(CATEGORY_RELATIVE_PATH, "DefaultReplaceLogFileName", "ChatLog_%DATE%.log");
			this.defaultReplaceLogFileName.comment = "Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%";

			this.enforcementReplaceLogFileFullPathFileName = this.config.get(CATEGORY_ABSOLUTE_PATH, "EnforcementReplaceLogFileFullPathFileName", "");
			this.enforcementReplaceLogFileFullPathFileName.comment = "Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.";

			this.formatDateTime = this.config.get(CATEGORY_LOGOUTPUT_FORMAT, "FormatDateTime", "yyyy/MM/dd-HH:mm:ss");
			this.formatReplaceDate = this.config.get(CATEGORY_FILENAME_FORMAT, "FormatReplaceDate", "yyyyMMdd");
			this.formatReplaceTime = this.config.get(CATEGORY_FILENAME_FORMAT, "FormatReplaceTime", "HHmmss");
		}
		catch(Exception e)
		{
			DebugLog.log(Level.SEVERE, e, "Failed to read the configuration file.");
		}
		finally
		{
			this.config.save();
		}
	}


	public boolean getChatLoggerEnabled()
	{
		return this.chatLoggerEnabled.getBoolean(true);
	}
	public void setChatLoggerEnabled(boolean chatLoggerEnabled)
	{
		this.chatLoggerEnabled.value = String.valueOf(chatLoggerEnabled);
		this.config.save();
	}


	public String getFormatDateTime()
	{
		return this.formatDateTime.value;
	}
	public void setFormatDateTime(String formatDateTime)
	{
		this.formatDateTime.value = formatDateTime;
		this.config.save();
	}

	public String getFormatReplaceDate()
	{
		return this.formatReplaceDate.value;
	}
	public void setFormatReplaceDate(String formatReplaceDate)
	{
		this.formatReplaceDate.value = formatReplaceDate;
		this.config.save();
	}

	public String getFormatReplaceTime()
	{
		return this.formatReplaceTime.value;
	}
	public void setFormatReplaceTime(String formatReplaceTime)
	{
		this.formatReplaceTime.value = formatReplaceTime;
		this.config.save();
	}


	public String getDefaultReplaceLogFileFolderName()
	{
		return this.defaultReplaceLogFileFolderName.value;
	}
	public void setDefaultReplaceLogFileFolderName(String defaultReplaceLogFileFolderName)
	{
		this.defaultReplaceLogFileFolderName.value = defaultReplaceLogFileFolderName;
		this.config.save();
	}

	public String getDefaultReplaceLogFileName()
	{
		return this.defaultReplaceLogFileName.value;
	}
	public void setDefaultReplaceLogFileName(String defaultReplaceLogFileName)
	{
		this.defaultReplaceLogFileName.value = defaultReplaceLogFileName;
		this.config.save();
	}


	public String getEnforcementReplaceLogFileFullPathFileName()
	{
		return this.enforcementReplaceLogFileFullPathFileName.value;
	}
	public void setEnforcementReplaceLogFileFullPathFileName(String enforcementReplaceLogFileFullPathFileName)
	{
		this.enforcementReplaceLogFileFullPathFileName.value = enforcementReplaceLogFileFullPathFileName;
		this.config.save();
	}

}
