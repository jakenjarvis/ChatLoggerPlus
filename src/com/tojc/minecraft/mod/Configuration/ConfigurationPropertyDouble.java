package com.tojc.minecraft.mod.Configuration;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigurationPropertyDouble extends ConfigurationPropertyTypeBase<Double>
{
	public ConfigurationPropertyDouble(Configuration config)
	{
		super(config);
	}

	@Override
	protected Property onInitialize(String category, String key, Double defaultValue)
	{
		return this.config.get(category, key, defaultValue);
	}

	@Override
	protected Double onGet()
	{
		return this.property.getDouble(this.defaultValue);
	}

	@Override
	protected void onSet(Double value)
	{
		this.property.set(value);
	}
}
