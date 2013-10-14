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
package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginState;
import com.tojc.minecraft.mod.Crypto.SimpleEncryption;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginOrderKey
{
	private static String SEPARATOR = ";";
	private static final String ENCKEY = "ChatLogger";

	private static String convertStringToInternalFormat(String item)
	{
		String result = "";
		try
		{
			result = SimpleEncryption.encrypt(ENCKEY, item);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private static String convertInternalFormatToString(String internalFormat)
	{
		String result = null;
		try
		{
			result = SimpleEncryption.decrypt(ENCKEY, internalFormat);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private String key = "";
	private PluginState state = null;

	public PluginOrderKey()
	{
		this.set("", PluginState.Disabled);
	}

	public PluginOrderKey(String savekey)
	{
		this.set(savekey);
	}

	public PluginOrderKey(String key, PluginState state)
	{
		this.set(key, state);
	}

	public PluginOrderKey set(String savekey)
	{
		String deckey = PluginOrderKey.convertInternalFormatToString(savekey);
		String[] tokens = deckey.split(SEPARATOR);
		if(tokens.length == 2)
		{
			this.key = tokens[0];
			this.state = PluginState.toPluginState(tokens[1]);
		}
		else
		{
			this.key = deckey;
			this.state = PluginState.Disabled;
		}
		return this;
	}

	public PluginOrderKey set(String key, PluginState state)
	{
		this.key = key;
		this.state = state;
		return this;
	}

	private String makeOrderKeyString()
	{
		return this.key + SEPARATOR + this.state.getName();
	}

	public String getKey()
	{
		return this.key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}

	public PluginState getState()
	{
		return this.state;
	}
	public void setState(PluginState state)
	{
		this.state = state;
	}

	public String makeEncOrderKeyString()
	{
		return PluginOrderKey.convertStringToInternalFormat(this.makeOrderKeyString());
	}

	@Override
    public String toString()
    {
        return this.makeOrderKeyString();
    }
}
