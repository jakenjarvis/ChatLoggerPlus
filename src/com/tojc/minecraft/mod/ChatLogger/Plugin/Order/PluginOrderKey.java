package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginState;

public class PluginOrderKey
{
	private static String SEPARATOR = ";";

	private String key = "";
	private PluginState state = null;

	public PluginOrderKey()
	{
		this.set("", PluginState.Disabled);
	}

	public PluginOrderKey(String savekey)
	{
		this.set(savekey);
	}

	public PluginOrderKey(String key, PluginState state)
	{
		this.set(key, state);
	}

	public PluginOrderKey set(String savekey)
	{
		String[] tokens = savekey.split(SEPARATOR);
		if(tokens.length == 2)
		{
			this.key = tokens[0];
			this.state = PluginState.toPluginState(tokens[1]);
		}
		else
		{
			this.key = savekey;
			this.state = PluginState.Disabled;
		}
		return this;
	}

	public PluginOrderKey set(String key, PluginState state)
	{
		this.key = key;
		this.state = state;
		return this;
	}

	private String makeOrderKeyString()
	{
		return this.key + SEPARATOR + this.state.getName();
	}

	public String getKey()
	{
		return this.key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}

	public PluginState getState()
	{
		return this.state;
	}
	public void setState(PluginState state)
	{
		this.state = state;
	}

	@Override
    public String toString()
    {
        return this.makeOrderKeyString();
    }
}
