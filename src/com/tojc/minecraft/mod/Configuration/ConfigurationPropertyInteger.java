package com.tojc.minecraft.mod.Configuration;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigurationPropertyInteger extends ConfigurationPropertyTypeBase<Integer>
{
	public ConfigurationPropertyInteger(Configuration config)
	{
		super(config);
	}

	@Override
	protected Property onInitialize(String category, String key, Integer defaultValue)
	{
		return this.config.get(category, key, defaultValue);
	}

	@Override
	protected Integer onGet()
	{
		return this.property.getInt(this.defaultValue);
	}

	@Override
	protected void onSet(Integer value)
	{
		this.property.value = String.valueOf(value);
	}
}
