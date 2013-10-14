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
package com.tojc.minecraft.mod.ChatLogger.Plugin;

public enum PluginState
{
	Disabled(	false,	"Disabled",			"Plug-in is disabled."),
	Enabled(	true,	"Enabled",			"Plug-in is enabled."),
	Error(		false,	"Error",			"Found a problem in the plug-in.");

	private PluginState(boolean code, String name, String description)
	{
    	this.code = code;
        this.name = name;
        this.description = description;
	}

    private final boolean code;
    private final String name;
    private final String description;

    public boolean getCode()
    {
    	return this.code;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    public static PluginState toPluginState(String name)
    {
    	PluginState result = Disabled;
    	for (PluginState reason : values())
    	{
    		if(reason.getName().equalsIgnoreCase(name))
    		{
    			result = reason;
    			break;
    		}
    	}
    	return result;
    }
}
