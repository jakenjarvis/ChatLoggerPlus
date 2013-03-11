package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.util.List;
import java.util.Map;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.ChatMessage;
import com.tojc.minecraft.mod.Exception.PluginPermissionDeniedException;
import com.tojc.minecraft.mod.log.DebugLog;

public class ChatMessageImpl implements ChatMessage
{
	private PluginSettingsImpl settings = null;
	private Map<String, Object> pluginStack = null;

	private String servername = "";
	private String worldname = "";

	private String messageOriginal = "";
	private String message = "";

	private List<String> messagesAfter = null;

	private RuntimeException exception = null;

	public static ChatMessageImpl clone(ChatMessageImpl src)
	{
		return new ChatMessageImpl(src.settings, src.servername, src.worldname, src.messageOriginal, src.message, src.messagesAfter);
	}

	public static ChatMessageImpl clone(ChatMessage ifsrc)
	{
		ChatMessageImpl src = (ChatMessageImpl)ifsrc;
		return new ChatMessageImpl(src.settings, src.servername, src.worldname, src.messageOriginal, src.message, src.messagesAfter);
	}

	public ChatMessageImpl(String servername, String worldname, String message)
	{
		this(null, servername, worldname, message, message, null);
	}

	private ChatMessageImpl(PluginSettingsImpl settings, String servername, String worldname, String messageOriginal, String message, List<String> messagesAfter)
	{
		this.settings = settings;

		this.servername = new String(servername);
		this.worldname = new String(worldname);

		this.messageOriginal = new String(messageOriginal);
		this.message = new String(message);

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
	public String getServerName()
	{
		return this.servername;
	}

	@Override
	public String getWorldName()
	{
		return this.worldname;
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
			DebugLog.info("Warning: readStack() will return null. not found key: %s", keyname);
		}

		return result;
	}

}
