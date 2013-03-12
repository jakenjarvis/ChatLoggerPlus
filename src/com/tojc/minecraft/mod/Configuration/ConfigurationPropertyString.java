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
