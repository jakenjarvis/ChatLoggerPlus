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
package com.tojc.minecraft.mod.Configuration;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigurationPropertyString extends ConfigurationPropertyTypeBase<String>
{
	public ConfigurationPropertyString(Configuration config)
	{
		super(config);
	}

	@Override
	protected Property onInitialize(String category, String key, String defaultValue)
	{
		return this.config.get(category, key, defaultValue);
	}

	@Override
	protected String onGet()
	{
		return this.property.getString();
	}

	@Override
	protected void onSet(String value)
	{
		this.property.set(value);
	}
}
