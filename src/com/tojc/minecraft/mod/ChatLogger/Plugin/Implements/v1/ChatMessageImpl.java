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
package com.tojc.minecraft.mod.ChatLogger.Plugin.Implements.v1;

import java.util.List;
import java.util.Map;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.ChatMessage;
import com.tojc.minecraft.mod.Exception.PluginPermissionDeniedException;
import com.tojc.minecraft.mod.log.DebugLog;

public class ChatMessageImpl implements ChatMessage
{
	private PluginSettingsImpl settings = null;
	private Map<String, Object> pluginStack = null;

	private String originalJson = "";

	private String userName = null;
	private String messageOriginal = "";
	private String message = "";

	private List<String> messagesAfter = null;

	private RuntimeException exception = null;

	public static ChatMessageImpl clone(ChatMessageImpl src)
	{
		return new ChatMessageImpl(src.settings, src.originalJson, src.userName, src.messageOriginal, src.message, src.messagesAfter);
	}

	public static ChatMessageImpl clone(ChatMessage ifsrc)
	{
		ChatMessageImpl src = (ChatMessageImpl)ifsrc;
		return new ChatMessageImpl(src.settings, src.originalJson, src.userName, src.messageOriginal, src.message, src.messagesAfter);
	}

	public ChatMessageImpl(String originalJson, String userName, String message)
	{
		this(null, originalJson, userName, message, message, null);
	}

	private ChatMessageImpl(PluginSettingsImpl settings, String originalJson, String userName, String messageOriginal, String message, List<String> messagesAfter)
	{
		this.settings = settings;

		this.originalJson = new String(originalJson);

		this.userName = null;
		if(userName != null)
		{
			this.userName = new String(userName);
		}
		this.messageOriginal = new String(messageOriginal);
		if(message != null)
		{
			this.message = new String(message);
		}
		else
		{
			this.message = null;
		}
		this.messagesAfter = messagesAfter;
	}

	public PluginSettingsImpl getSettings()
	{
		return this.settings;
	}
	public void setSettings(PluginSettingsImpl settings)
	{
		this.settings = settings;
	}

	public Map<String, Object> getPluginStack()
	{
		return this.pluginStack;
	}
	public void setPluginStack(Map<String, Object> pluginStack)
	{
		this.pluginStack = pluginStack;
	}

	public List<String> getMessagesAfter()
	{
		return this.messagesAfter;
	}
	public void setMessagesAfter(List<String> messagesAfter)
	{
		this.messagesAfter = messagesAfter;
	}

	public Exception getException()
	{
		return this.exception;
	}

	@Override
	public String getOriginalJsonString()
	{
		return this.originalJson;
	}

	@Override
	public String getUserName()
	{
		return this.userName;
	}

	@Override
	public String getMessageOriginal()
	{
		return this.messageOriginal;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}
	@Override
	public void setMessage(String message)
	{
		this.message = message;
	}

	@Override
	public void addAfterMessage(String message)
	{
		if(!this.settings.getAddAfterMessage())
		{
			this.exception = new PluginPermissionDeniedException("Permission AddAfterMessage is required!");
			throw this.exception;
		}
		this.messagesAfter.add(message);
	}

	@Override
	public void writeStack(String keyname, Object value)
	{
		if(!this.settings.getWriteStack().contains(keyname))
		{
			this.exception = new PluginPermissionDeniedException("Permission WriteStack is required! : " + keyname);
			throw this.exception;
		}
		this.pluginStack.put(keyname, value);
	}

	@Override
	public Object readStack(String keyname)
	{
		Object result = null;
		if(!this.settings.getReadStack().contains(keyname))
		{
			this.exception = new PluginPermissionDeniedException("Permission ReadStack is required! : " + keyname);
			throw this.exception;
		}

		result = this.pluginStack.get(keyname);
		if(result == null)
		{
			DebugLog.warning("readStack() will return null. not found key: %s", keyname);
		}

		return result;
	}
}
