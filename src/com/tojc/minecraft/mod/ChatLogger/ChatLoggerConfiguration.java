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
package com.tojc.minecraft.mod.ChatLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyBoolean;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyString;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ChatLoggerConfiguration
{
	private static String CATEGORY_FILENAME_FORMAT = "filenameformat";
	private static String CATEGORY_RELATIVE_PATH = "relativepath";
	private static String CATEGORY_ABSOLUTE_PATH = "absolutepath";
	private static String CATEGORY_PLUGIN_SETTINGS = "pluginsettings";

	private Configuration config = null;

	private ConfigurationPropertyBoolean chatLoggerEnabled = null;
	private ConfigurationPropertyString formatDateTime = null;
	private ConfigurationPropertyBoolean fillColorCodeEnabled = null;
	private ConfigurationPropertyString fillColorCodeRegex = null;
	private ConfigurationPropertyString fillColorCodeReplace = null;
	private ConfigurationPropertyString defaultPluginDirectoryName = null;

	private ConfigurationPropertyString defaultReplaceLogFileFullPathFileName = null;
	private ConfigurationPropertyString enforcementReplaceLogFileFullPathFileName = null;

	private ConfigurationPropertyString formatReplaceDate = null;
	private ConfigurationPropertyString formatReplaceTime = null;

	private ConfigurationPropertyArrayString pluginOrderToScreen = null;
	private ConfigurationPropertyArrayString pluginOrderToChatLog = null;

	public ChatLoggerConfiguration(File file)
	{
		this.config = new Configuration(file);

		this.config.load();

		// create property
		this.chatLoggerEnabled = new ConfigurationPropertyBoolean(this.config);
		this.formatDateTime = new ConfigurationPropertyString(this.config);
		this.fillColorCodeEnabled = new ConfigurationPropertyBoolean(this.config);
		this.fillColorCodeRegex = new ConfigurationPropertyString(this.config);
		this.fillColorCodeReplace = new ConfigurationPropertyString(this.config);
		this.defaultPluginDirectoryName = new ConfigurationPropertyString(this.config);

		this.defaultReplaceLogFileFullPathFileName = new ConfigurationPropertyString(this.config);
		this.enforcementReplaceLogFileFullPathFileName = new ConfigurationPropertyString(this.config);

		this.formatReplaceDate = new ConfigurationPropertyString(this.config);
		this.formatReplaceTime = new ConfigurationPropertyString(this.config);

		this.pluginOrderToScreen = new ConfigurationPropertyArrayString(this.config);
		this.pluginOrderToChatLog = new ConfigurationPropertyArrayString(this.config);

		// initialize

		// CATEGORY_GENERAL
		this.chatLoggerEnabled.initialize(Configuration.CATEGORY_GENERAL, "ChatLoggerEnabled", true, "true/false");
		this.formatDateTime.initialize(Configuration.CATEGORY_GENERAL, "FormatDateTime", "yyyy/MM/dd-HH:mm:ss", null);
		this.fillColorCodeEnabled.initialize(Configuration.CATEGORY_GENERAL, "FillColorCodeEnabled", false, "true/false, true=fill / false=Not modify");
		this.fillColorCodeRegex.initialize(Configuration.CATEGORY_GENERAL, "FillColorCodeRegex", "§[0-9a-fk-or]", null);
		this.fillColorCodeReplace.initialize(Configuration.CATEGORY_GENERAL, "FillColorCodeReplace", "", null);
		this.defaultPluginDirectoryName.initialize(Configuration.CATEGORY_GENERAL, "DefaultPluginDirectoryName", com.tojc.minecraft.mod.ChatLoggerPlus.class.getName() + ".plugins", null);

		// CATEGORY_RELATIVE_PATH
		this.defaultReplaceLogFileFullPathFileName.initialize(CATEGORY_RELATIVE_PATH, "DefaultReplaceLogFileFullPathFileName", "chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log", "Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%");
		this.enforcementReplaceLogFileFullPathFileName.initialize(CATEGORY_ABSOLUTE_PATH, "EnforcementReplaceLogFileFullPathFileName", "", "Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.");

		// CATEGORY_FILENAME_FORMAT
		this.formatReplaceDate.initialize(CATEGORY_FILENAME_FORMAT, "FormatReplaceDate", "yyyyMMdd", null);
		this.formatReplaceTime.initialize(CATEGORY_FILENAME_FORMAT, "FormatReplaceTime", "HHmmss", null);

		// CATEGORY_PLUGIN_SETTINGS
		this.pluginOrderToScreen.initialize(CATEGORY_PLUGIN_SETTINGS, "PluginOrderToScreen", new ArrayList<String>(), "Please do not modify.");
		this.pluginOrderToChatLog.initialize(CATEGORY_PLUGIN_SETTINGS, "PluginOrderToChatLog", new ArrayList<String>(), "Please do not modify.");

		this.config.save();
	}

	public ConfigurationPropertyBoolean getChatLoggerEnabled()
	{
		return this.chatLoggerEnabled;
	}

	public ConfigurationPropertyString getFormatDateTime()
	{
		return this.formatDateTime;
	}

	public ConfigurationPropertyBoolean getFillColorCodeEnabled()
	{
		return this.fillColorCodeEnabled;
	}

	public ConfigurationPropertyString getFillColorCodeRegex()
	{
		return this.fillColorCodeRegex;
	}

	public ConfigurationPropertyString getFillColorCodeReplace()
	{
		return this.fillColorCodeReplace;
	}

	public ConfigurationPropertyString getDefaultPluginDirectoryName()
	{
		return this.defaultPluginDirectoryName;
	}

	public ConfigurationPropertyString getDefaultReplaceLogFileFullPathFileName()
	{
		return this.defaultReplaceLogFileFullPathFileName;
	}

	public ConfigurationPropertyString getEnforcementReplaceLogFileFullPathFileName()
	{
		return this.enforcementReplaceLogFileFullPathFileName;
	}

	public ConfigurationPropertyString getFormatReplaceDate()
	{
		return this.formatReplaceDate;
	}

	public ConfigurationPropertyString getFormatReplaceTime()
	{
		return this.formatReplaceTime;
	}

	public ConfigurationPropertyArrayString getPluginOrderToScreen()
	{
		return this.pluginOrderToScreen;
	}

	public ConfigurationPropertyArrayString getPluginOrderToChatLog()
	{
		return this.pluginOrderToChatLog;
	}

}
