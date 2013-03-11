package com.tojc.minecraft.mod.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigurationPropertyArrayString extends ConfigurationPropertyTypeBase<List<String>>
{
	public ConfigurationPropertyArrayString(Configuration config)
	{
		super(config);
	}

	@Override
	protected Property onInitialize(String category, String key, List<String> defaultValue)
	{
		return this.config.get(category, key, defaultValue.toArray(new String[defaultValue.size()]));
	}

	@Override
	protected List<String> onGet()
	{
		return Arrays.asList(this.property.valueList);
	}

	@Override
	protected void onSet(List<String> value)
	{
		this.property.valueList = value.toArray(new String[value.size()]);
	}
}
