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
		this.property.value = String.valueOf(value);
	}
}
