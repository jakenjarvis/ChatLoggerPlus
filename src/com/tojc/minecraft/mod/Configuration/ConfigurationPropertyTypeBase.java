package com.tojc.minecraft.mod.Configuration;

import java.util.logging.Level;

import com.tojc.minecraft.mod.log.DebugLog;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public abstract class ConfigurationPropertyTypeBase<T>
{
	protected Configuration config = null;
	protected Property property = null;

	protected String category = "";
	protected String key = "";
	protected T defaultValue = null;

	public ConfigurationPropertyTypeBase(Configuration config)
	{
		this.config = config;
	}

	public void initialize(String category, String key, T defaultValue, String comment)
	{
		this.category = category;
		this.key = key;
		this.defaultValue = defaultValue;

		try
		{
			this.property = this.onInitialize(category, key, defaultValue);
		}
		catch(Exception e)
		{
			DebugLog.error(e, "Failed to read the configuration file. : " + category + " -> " + key);
		}

		if((comment != null) && (comment.length() >= 1))
		{
			this.property.comment = comment;
		}
	}

	public String getCategory()
	{
		return this.category;
	}

	public String getKey()
	{
		return this.key;
	}

	public T getDefaultValue()
	{
		return this.defaultValue;
	}

	public String getComment()
	{
		return this.property.comment;
	}
	public void setComment(String comment)
	{
		this.property.comment = comment;
	}


	public T get()
	{
		return this.onGet();
	}

	public void set(T value)
	{
		this.onSet(value);
		this.config.save();
	}

	protected abstract Property onInitialize(String category, String key, T defaultValue);
	protected abstract T onGet();
	protected abstract void onSet(T value);
}
