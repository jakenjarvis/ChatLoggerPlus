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
		String typename = this.getTranslationKey();
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
			List item = this.getSubComponents();
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
			List item = this.getSubComponents();
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
		String typename = this.getTranslationKey();
		String playername = null;
		//String playermessage = null;
		if((typename != null) && typename.equalsIgnoreCase("chat.type.text"))
		{
			List item = this.getSubComponents();
			if(item.size() == 2)
			{
				playername = ((Object)item.get(0)).toString();
				//playermessage = ((Object)item.get(1)).toString();
				result = ChatMessageComponent.createFromTranslationWithSubstitutions(typename, new Object[] {playername, text});
			}
		}
		return result;
	}
}
