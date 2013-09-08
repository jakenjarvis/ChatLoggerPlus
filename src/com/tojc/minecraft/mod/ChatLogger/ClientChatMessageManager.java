package com.tojc.minecraft.mod.ChatLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLogger.Plugin.ChatMessageImpl;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginManager;
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

	public ClientChatMessageManager(ChatLoggerCore core, ChatMessageImpl chatMessage)
	{
		this.config = core.getConfig();
		this.pluginManager = core.getPluginManager();

		this.messageOriginal = chatMessage;

		this.messageOutputScreenAfter = new ArrayList<String>();
		this.messageOutputChatLogAfter = new ArrayList<String>();

		this.messageOutputScreen = this.pluginManager.getPluginOrderManager().getPluginScreenOrderController()
				.fireOnChatMessage(this.messageOriginal, this.messageOutputScreenAfter);

		this.messageOutputChatLog = this.pluginManager.getPluginOrderManager().getPluginChatLogOrderController()
				.fireOnChatMessage(this.messageOriginal, this.messageOutputChatLogAfter);
	}

	private String replaceFillColorCode(String message)
	{
		// カラーコードの削除
		String result = message;
		if(this.config.getFillColorCodeEnabled().get())
		{
			String regex = this.config.getFillColorCodeRegex().get();
			String replace = this.config.getFillColorCodeReplace().get();
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
