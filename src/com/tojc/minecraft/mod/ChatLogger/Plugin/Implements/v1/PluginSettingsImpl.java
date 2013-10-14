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

import java.util.ArrayList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginSettings;

public class PluginSettingsImpl implements PluginSettings
{
	private boolean messageModification = false;
	private boolean addAfterMessage = false;
	private List<String> writeStack = null;
	private List<String> readStack = null;

	public PluginSettingsImpl()
	{
		this.messageModification = false;
		this.addAfterMessage = false;
		this.writeStack = new ArrayList<String>();
		this.readStack = new ArrayList<String>();
	}

	private PluginSettingsImpl(PluginSettingsImpl src)
	{
		this();
		this.messageModification = src.getMessageModification();
		this.addAfterMessage = src.getAddAfterMessage();
		for(String key : src.getWriteStack())
		{
			this.writeStack.add(new String(key));
		}
		for(String key : src.getReadStack())
		{
			this.readStack.add(new String(key));
		}
	}

	public static PluginSettingsImpl clone(PluginSettingsImpl src)
	{
		return new PluginSettingsImpl(src);
	}

	@Override
	public void registerPermissionMessageModification()
	{
		this.messageModification = true;
	}

	@Override
	public void registerPermissionAddAfterMessage()
	{
		this.addAfterMessage = true;
	}

	@Override
	public void registerPermissionWriteStack(String keyname)
	{
		if(!this.writeStack.contains(keyname))
		{
			this.writeStack.add(keyname);
		}
	}

	@Override
	public void registerPermissionReadStack(String keyname)
	{
		if(!this.readStack.contains(keyname))
		{
			this.readStack.add(keyname);
		}
	}

	public boolean getMessageModification()
	{
		return this.messageModification;
	}

	public boolean getAddAfterMessage()
	{
		return this.addAfterMessage;
	}

	public List<String> getWriteStack()
	{
		return this.writeStack;
	}
	public String makeWriteStackListString()
	{
		StringBuilder result = new StringBuilder();
		String comma = "";
		for(String stack : this.writeStack)
		{
			result.append(comma);
			result.append(stack);
			comma = ", ";
		}
		return result.toString();
	}

	public List<String> getReadStack()
	{
		return this.readStack;
	}
	public String makeReadStackListString()
	{
		StringBuilder result = new StringBuilder();
		String comma = "";
		for(String stack : this.readStack)
		{
			result.append(comma);
			result.append(stack);
			comma = ", ";
		}
		return result.toString();
	}

}
