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

public class ConfigurationPropertyBoolean extends ConfigurationPropertyTypeBase<Boolean>
{
	public ConfigurationPropertyBoolean(Configuration config)
	{
		super(config);
	}

	@Override
	protected Property onInitialize(String category, String key, Boolean defaultValue)
	{
		return this.config.get(category, key, defaultValue);
	}

	@Override
	protected Boolean onGet()
	{
		return this.property.getBoolean(this.defaultValue);
	}

	@Override
	protected void onSet(Boolean value)
	{
		this.property.set(value);
	}
}