package com.tojc.minecraft.mod.ChatLogger;

import java.util.List;

import net.minecraft.util.ChatMessageComponent;

public class ChatMessageComponentWrapper extends ChatMessageComponent
{
	public ChatMessageComponentWrapper()
	{
		super();
	}

	public ChatMessageComponentWrapper(ChatMessageComponent par1ChatMessageComponent)
	{
		super(par1ChatMessageComponent);
	}

	public ChatMessageComponentWrapper(ChatMessageComponentWrapper par1ChatMessageComponent)
	{
		super(par1ChatMessageComponent);
	}

	public boolean isChatTypeText()
	{
		boolean result = false;
		String typename = this.func_111074_g();
		if((typename != null) && typename.equalsIgnoreCase("chat.type.text"))
		{
			result = true;
		}
		return result;
	}

	public String getPlayerNameFromChatTypeText()
	{
		String result = null;
		if(this.isChatTypeText())
		{
			List item = this.func_111069_h();
			if(item.size() == 2)
			{
				result = ((Object)item.get(0)).toString();
			}
		}
		return result;
	}

	public String getPlayerMessageFromChatTypeText()
	{
		String result = null;
		if(this.isChatTypeText())
		{
			List item = this.func_111069_h();
			if(item.size() == 2)
			{
				result = ((Object)item.get(1)).toString();
			}
		}
		return result;
	}

	public ChatMessageComponent replaceChatTypeText(String text)
	{
		ChatMessageComponent result = null;
		String typename = this.func_111074_g();
		String playername = null;
		//String playermessage = null;
		if((typename != null) && typename.equalsIgnoreCase("chat.type.text"))
		{
			List item = this.func_111069_h();
			if(item.size() == 2)
			{
				playername = ((Object)item.get(0)).toString();
				//playermessage = ((Object)item.get(1)).toString();
				result = ChatMessageComponent.func_111082_b(typename, new Object[] {playername, text});
			}
		}
		return result;
	}
}
