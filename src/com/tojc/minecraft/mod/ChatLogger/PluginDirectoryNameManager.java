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

import java.io.File;

import cpw.mods.fml.common.Loader;

import net.minecraft.client.Minecraft;

public class PluginDirectoryNameManager
{
	private ChatLoggerConfiguration config = null;

	public PluginDirectoryNameManager(ChatLoggerConfiguration config)
	{
		this.config = config;
	}

	public File getPluginDirectoryFile()
	{
		File result = null;
		result = new File(getDefaultPluginDirectoryName());
		return result;
	}

	private String getDefaultPluginDirectoryName()
	{
		String defaultPluginDirectoryName = this.config.getDefaultPluginDirectoryName().get();

		String result = new File(Loader.instance().getConfigDir().toString(), defaultPluginDirectoryName).getPath();
		return result;
	}

}
