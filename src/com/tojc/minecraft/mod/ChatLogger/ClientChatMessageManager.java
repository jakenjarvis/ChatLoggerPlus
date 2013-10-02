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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginManager;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1.ChatMessageImpl;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1.PluginEnvironmentImpl;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.ChatMessage;

public class ClientChatMessageManager
{
	private ChatLoggerConfiguration config = null;
	private PluginManager pluginManager = null;

	private ChatMessageImpl messageOriginal = null;
	private ChatMessageImpl messageOutputScreen = null;
	private ChatMessageImpl messageOutputChatLog = null;

	private List<String> messageOutputScreenAfter = null;
	private List<String> messageOutputChatLogAfter = null;

	public ClientChatMessageManager(ChatLoggerCore core, PluginEnvironmentImpl env, ChatMessageImpl chatMessage)
	{
		this.config = core.getConfig();
		this.pluginManager = core.getPluginManager();

		this.messageOriginal = chatMessage;

		this.messageOutputScreenAfter = new ArrayList<String>();
		this.messageOutputChatLogAfter = new ArrayList<String>();

		if(this.config.getPluginScriptsEnabled().get())
		{
			this.messageOutputScreen = this.pluginManager.getPluginOrderManager().getPluginScreenOrderController()
					.fireOnChatMessage(env, this.messageOriginal, this.messageOutputScreenAfter);

			this.messageOutputChatLog = this.pluginManager.getPluginOrderManager().getPluginChatLogOrderController()
					.fireOnChatMessage(env, this.messageOriginal, this.messageOutputChatLogAfter);
		}
		else
		{
			this.messageOutputScreen = ChatMessageImpl.clone(chatMessage);
			this.messageOutputChatLog = ChatMessageImpl.clone(chatMessage);

			// フォーマットコードの削除
			this.messageOutputChatLog.setMessage(this.replaceFillFormattingCodes(this.messageOutputChatLog.getMessage()));
		}
	}

	private String replaceFillFormattingCodes(String message)
	{
		String result = message;
		if(this.config.getFillFormattingCodesEnabled().get())
		{
			String regex = this.config.getFillFormattingCodesRegex().get();
			String replace = this.config.getFillFormattingCodesReplace().get();
			result = result.replaceAll(regex, replace);
		}
		return result;
	}



	public String outputScreen()
	{
		return this.messageOutputScreen.getMessage();
	}

	public List<String> outputScreenAfterMessages()
	{
		return this.messageOutputScreenAfter;
	}

	public String outputChatLog()
	{
		return this.messageOutputChatLog.getMessage();
	}

	public List<String> outputChatLogAfterMessages()
	{
		return this.messageOutputChatLogAfter;
	}

}
