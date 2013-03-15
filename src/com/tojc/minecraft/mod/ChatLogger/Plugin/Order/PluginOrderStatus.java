package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginInformation;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginState;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

public class PluginOrderStatus
{
	private PluginType type = null;
	private PluginOrderKey key = null;
	private PluginInformation plugin = null;

	public PluginOrderStatus(PluginType type, PluginOrderKey key, PluginInformation plugin)
	{
		this.type = type;
		this.key = key;
		this.plugin = plugin;

		if(this.isError())
		{
			this.key.setState(PluginState.ErrorMissing);
		}
	}

	public boolean isError()
	{
		return !((this.plugin != null) && (this.plugin.isEnabled()));
	}

	public PluginType getPluginType()
	{
		return this.type;
	}

	public PluginOrderKey getPluginOrderKey()
	{
		return this.key;
	}

	public PluginInformation getPlugin()
	{
		return this.plugin;
	}
	public void setPlugin(PluginInformation plugin)
	{
		this.plugin = plugin;
	}

	public PluginState getPluginState()
	{
		return this.key.getState();
	}
	public void setPluginState(PluginState state)
	{
		this.key.setState(state);
	}

	public boolean isEnabled()
	{
		return this.key.getState().getCode();
	}
	public void setEnabled(boolean enabled)
	{
		if(!this.isError())
		{
			switch(this.key.getState())
			{
				case Disabled:
				case Enabled:
					if(enabled)
					{
						this.key.setState(PluginState.Enabled);
					}
					else
					{
						this.key.setState(PluginState.Disabled);
					}
					break;

				case ErrorMissing:
					break;
				case ErrorBug:
					break;
				default:
					break;
			}
		}
		else
		{
			switch(this.key.getState())
			{
				case Disabled:
				case Enabled:
					this.key.setState(PluginState.ErrorBug);
					break;

				case ErrorMissing:
					break;
				case ErrorBug:
					break;
				default:
					break;
			}
		}
	}
}
