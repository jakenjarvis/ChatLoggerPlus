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
	private final static String CATEGORY_FILENAME_FORMAT = "filenameformat";
	private final static String CATEGORY_RELATIVE_PATH = "relativepath";
	private final static String CATEGORY_ABSOLUTE_PATH = "absolutepath";
	private final static String CATEGORY_PLUGIN_SETTINGS = "pluginsettings";
	private final static String CATEGORY_PLUGIN_SCRIPTS_DISABLED_GENERAL = "pluginscriptsdisabledgeneral";
	private final static String CATEGORY_OUTPUT_OF_DEBUG_LOG = "outputofdebuglog";

	private Configuration config = null;

	private ConfigurationPropertyBoolean pluginScriptsEnabled = null;
	private ConfigurationPropertyString formatDateTime = null;

	private ConfigurationPropertyBoolean fillFormattingCodesEnabled = null;
	private ConfigurationPropertyString fillFormattingCodesRegex = null;
	private ConfigurationPropertyString fillFormattingCodesReplace = null;

	private ConfigurationPropertyString defaultReplaceLogFileFullPathFileName = null;
	private ConfigurationPropertyString enforcementReplaceLogFileFullPathFileName = null;

	private ConfigurationPropertyString formatReplaceDate = null;
	private ConfigurationPropertyString formatReplaceTime = null;

	private ConfigurationPropertyString defaultPluginDirectoryName = null;
	private ConfigurationPropertyArrayString pluginOrderToScreen = null;
	private ConfigurationPropertyArrayString pluginOrderToChatLog = null;

	private ConfigurationPropertyBoolean outputLoggingError = null;
	private ConfigurationPropertyBoolean outputLoggingWarning = null;
	private ConfigurationPropertyBoolean outputLoggingTrace = null;
	private ConfigurationPropertyBoolean outputLoggingScript = null;
	private ConfigurationPropertyBoolean outputLoggingMessageOriginal = null;
	private ConfigurationPropertyBoolean outputLoggingMessageDuringChatLog = null;
	private ConfigurationPropertyBoolean outputLoggingMessageDuringScreen = null;
	private ConfigurationPropertyBoolean outputLoggingMessageLastChatLog = null;
	private ConfigurationPropertyBoolean outputLoggingMessageLastScreen = null;

	public ChatLoggerConfiguration(File file)
	{
		this.config = new Configuration(file);

		this.config.load();

		// create property
		this.pluginScriptsEnabled = new ConfigurationPropertyBoolean(this.config);
		this.formatDateTime = new ConfigurationPropertyString(this.config);

		this.fillFormattingCodesEnabled = new ConfigurationPropertyBoolean(this.config);
		this.fillFormattingCodesRegex = new ConfigurationPropertyString(this.config);
		this.fillFormattingCodesReplace = new ConfigurationPropertyString(this.config);

		this.defaultReplaceLogFileFullPathFileName = new ConfigurationPropertyString(this.config);
		this.enforcementReplaceLogFileFullPathFileName = new ConfigurationPropertyString(this.config);

		this.formatReplaceDate = new ConfigurationPropertyString(this.config);
		this.formatReplaceTime = new ConfigurationPropertyString(this.config);

		this.defaultPluginDirectoryName = new ConfigurationPropertyString(this.config);
		this.pluginOrderToScreen = new ConfigurationPropertyArrayString(this.config);
		this.pluginOrderToChatLog = new ConfigurationPropertyArrayString(this.config);

		this.outputLoggingError = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingWarning = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingTrace = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingScript = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingMessageOriginal = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingMessageDuringChatLog = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingMessageDuringScreen = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingMessageLastChatLog = new ConfigurationPropertyBoolean(this.config);
		this.outputLoggingMessageLastScreen = new ConfigurationPropertyBoolean(this.config);

		// initialize

		// CATEGORY_GENERAL
		this.pluginScriptsEnabled.initialize(Configuration.CATEGORY_GENERAL, "PluginScriptsEnabled", false, "true/false");
		this.formatDateTime.initialize(Configuration.CATEGORY_GENERAL, "FormatDateTime", "yyyy/MM/dd-HH:mm:ss", "The format of date and time on outputs to the beginning of the log.");

		// CATEGORY_PLUGIN_SCRIPTS_DISABLED_GENERAL
		this.fillFormattingCodesEnabled.initialize(CATEGORY_PLUGIN_SCRIPTS_DISABLED_GENERAL, "FillFormattingCodesEnabled", false, "true/false, true=fill / false=Not modify");
		this.fillFormattingCodesRegex.initialize(CATEGORY_PLUGIN_SCRIPTS_DISABLED_GENERAL, "FillFormattingCodesRegex", "§[0-9a-fk-or]", null);
		this.fillFormattingCodesReplace.initialize(CATEGORY_PLUGIN_SCRIPTS_DISABLED_GENERAL, "FillFormattingCodesReplace", "", null);

		// CATEGORY_RELATIVE_PATH
		this.defaultReplaceLogFileFullPathFileName.initialize(CATEGORY_RELATIVE_PATH, "DefaultReplaceLogFileFullPathFileName", "chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log", "Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%");
		this.enforcementReplaceLogFileFullPathFileName.initialize(CATEGORY_ABSOLUTE_PATH, "EnforcementReplaceLogFileFullPathFileName", "", "Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.");

		// CATEGORY_FILENAME_FORMAT
		this.formatReplaceDate.initialize(CATEGORY_FILENAME_FORMAT, "FormatReplaceDate", "yyyyMMdd", null);
		this.formatReplaceTime.initialize(CATEGORY_FILENAME_FORMAT, "FormatReplaceTime", "HHmmss", null);

		// CATEGORY_PLUGIN_SETTINGS
		this.defaultPluginDirectoryName.initialize(CATEGORY_PLUGIN_SETTINGS, "DefaultPluginDirectoryName", "ChatLoggerPlusPlugins", null);
		this.pluginOrderToScreen.initialize(CATEGORY_PLUGIN_SETTINGS, "PluginOrderToScreen", new ArrayList<String>(), "Please do not modify.");
		this.pluginOrderToChatLog.initialize(CATEGORY_PLUGIN_SETTINGS, "PluginOrderToChatLog", new ArrayList<String>(), "Please do not modify.");

		// CATEGORY_OUTPUT_OF_DEBUG_LOG
		this.outputLoggingError.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingError", true, null);
		this.outputLoggingWarning.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingWarning", true, null);
		this.outputLoggingTrace.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingTrace", false, null);
		this.outputLoggingScript.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingScript", false, null);
		this.outputLoggingMessageOriginal.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingMessageOriginal", false, null);
		this.outputLoggingMessageDuringChatLog.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingMessageDuringChatLog", false, null);
		this.outputLoggingMessageDuringScreen.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingMessageDuringScreen", false, null);
		this.outputLoggingMessageLastChatLog.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingMessageLastChatLog", false, null);
		this.outputLoggingMessageLastScreen.initialize(CATEGORY_OUTPUT_OF_DEBUG_LOG, "OutputLoggingMessageLastScreen", false, null);

		this.config.save();
	}

	public ConfigurationPropertyBoolean getPluginScriptsEnabled()
	{
		return this.pluginScriptsEnabled;
	}

	public ConfigurationPropertyString getFormatDateTime()
	{
		return this.formatDateTime;
	}

	public ConfigurationPropertyBoolean getFillFormattingCodesEnabled()
	{
		return this.fillFormattingCodesEnabled;
	}

	public ConfigurationPropertyString getFillFormattingCodesRegex()
	{
		return this.fillFormattingCodesRegex;
	}

	public ConfigurationPropertyString getFillFormattingCodesReplace()
	{
		return this.fillFormattingCodesReplace;
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

	public ConfigurationPropertyString getDefaultPluginDirectoryName()
	{
		return this.defaultPluginDirectoryName;
	}

	public ConfigurationPropertyArrayString getPluginOrderToScreen()
	{
		return this.pluginOrderToScreen;
	}

	public ConfigurationPropertyArrayString getPluginOrderToChatLog()
	{
		return this.pluginOrderToChatLog;
	}

	public ConfigurationPropertyBoolean getOutputLoggingError()
	{
		return this.outputLoggingError;
	}

	public ConfigurationPropertyBoolean getOutputLoggingWarning()
	{
		return this.outputLoggingWarning;
	}

	public ConfigurationPropertyBoolean getOutputLoggingTrace()
	{
		return this.outputLoggingTrace;
	}

	public ConfigurationPropertyBoolean getOutputLoggingScript()
	{
		return this.outputLoggingScript;
	}

	public ConfigurationPropertyBoolean getOutputLoggingMessageOriginal()
	{
		return this.outputLoggingMessageOriginal;
	}

	public ConfigurationPropertyBoolean getOutputLoggingMessageDuringChatLog()
	{
		return this.outputLoggingMessageDuringChatLog;
	}

	public ConfigurationPropertyBoolean getOutputLoggingMessageDuringScreen()
	{
		return this.outputLoggingMessageDuringScreen;
	}

	public ConfigurationPropertyBoolean getOutputLoggingMessageLastChatLog()
	{
		return this.outputLoggingMessageLastChatLog;
	}

	public ConfigurationPropertyBoolean getOutputLoggingMessageLastScreen()
	{
		return this.outputLoggingMessageLastScreen;
	}

}
