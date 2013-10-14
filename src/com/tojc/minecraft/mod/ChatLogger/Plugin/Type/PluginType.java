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
package com.tojc.minecraft.mod.ChatLogger.Plugin.Type;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;

public enum PluginType
{
	Screen("Screen", new PluginTypeInterface()
	{
		@Override
		public ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToScreen();
		}
	}),

	ChatLog("ChatLog", new PluginTypeInterface()
	{
		@Override
		public ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToChatLog();
		}
	});

	private PluginType(String name, PluginTypeInterface individual)
	{
		this.name = name;
		this.individual = individual;
	}

    private final String name;
    private final PluginTypeInterface individual;

    public String getName()
    {
		return this.name;
	}

    public PluginTypeInterface getIndividual()
	{
		return this.individual;
	}

    @Override
    public String toString()
    {
        return this.name;
    }

    public static PluginType toPluginType(String name)
    {
    	PluginType result = Screen;
    	for (PluginType type : values())
    	{
    		if(type.getName().equalsIgnoreCase(name))
    		{
    			result = type;
    			break;
    		}
    	}
    	return result;
    }
}
