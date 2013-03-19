package com.tojc.minecraft.mod.ChatLogger.Plugin.Order;

import net.minecraft.util.EnumChatFormatting;

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
			this.key.setState(PluginState.Error);
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

				case Error:
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
					this.key.setState(PluginState.Error);
					break;

				case Error:
					break;
				default:
					break;
			}
		}
	}

	public String makePermissionString(boolean inColorCode)
	{
		StringBuilder result = new StringBuilder();

		String colorDefault = "";
		String colorWriteStackTitle = "";
		String colorReadStackTitle = "";
		String colorMessageModification = "";
		String colorAddAfterMessage = "";
		String colorWriteStack = "";
		String colorReadStack = "";

		if(inColorCode)
		{
			colorDefault = "" + EnumChatFormatting.GRAY;
			colorWriteStackTitle = "" + EnumChatFormatting.DARK_PURPLE;
			colorReadStackTitle = "" + EnumChatFormatting.DARK_AQUA;
			colorMessageModification = "" + EnumChatFormatting.YELLOW;
			colorAddAfterMessage = "" + EnumChatFormatting.GREEN;
			colorWriteStack = "" + EnumChatFormatting.LIGHT_PURPLE;
			colorReadStack = "" + EnumChatFormatting.AQUA;
		}

		boolean messageModification = plugin.getSettings().getMessageModification();
		boolean addAfterMessage = plugin.getSettings().getAddAfterMessage();
		String writeStack = plugin.getSettings().makeWriteStackListString();
		String readStack = plugin.getSettings().makeReadStackListString();

		if((!messageModification) && (!addAfterMessage) && (writeStack.length() <= 0) && (readStack.length() <= 0))
		{
			result.append(colorDefault);
			result.append(" This does not use the permission.");
		}
		else
		{
			boolean separator = false;

			result.append(colorDefault);
			result.append("Permission: ");

			if(messageModification)
			{
				if(separator)
				{
					result.append(colorDefault);
					result.append(", ");
					separator = false;
				}

				result.append(colorMessageModification);
				result.append("Modify");

				separator = true;
			}

			if(addAfterMessage)
			{
				if(separator)
				{
					result.append(colorDefault);
					result.append(", ");
					separator = false;
				}

				result.append(colorAddAfterMessage);
				result.append("Add");

				separator = true;
			}

			if(writeStack.length() >= 1)
			{
				if(separator)
				{
					result.append(colorDefault);
					result.append(", ");
					separator = false;
				}

				result.append(colorWriteStackTitle);
				result.append("WS/");
				result.append(colorWriteStack);
				result.append(writeStack);

				separator = true;
			}

			if(readStack.length() >= 1)
			{
				if(separator)
				{
					result.append(colorDefault);
					result.append(", ");
					separator = false;
				}

				result.append(colorReadStackTitle);
				result.append("RS/");
				result.append(colorReadStack);
				result.append(readStack);

				separator = true;
			}
		}
		return result.toString();
	}

}
