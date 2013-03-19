package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.util.ArrayList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginSettings;

public class PluginSettingsImpl implements PluginSettings
{
	private boolean messageModification = false;
	private boolean addAfterMessage = false;
	private List<String> writeStack = null;
	private List<String> readStack = null;

	public PluginSettingsImpl()
	{
		this.messageModification = false;
		this.addAfterMessage = false;
		this.writeStack = new ArrayList<String>();
		this.readStack = new ArrayList<String>();
	}

	private PluginSettingsImpl(PluginSettingsImpl src)
	{
		this();
		this.messageModification = src.getMessageModification();
		this.addAfterMessage = src.getAddAfterMessage();
		for(String key : src.getWriteStack())
		{
			this.writeStack.add(new String(key));
		}
		for(String key : src.getReadStack())
		{
			this.readStack.add(new String(key));
		}
	}

	public static PluginSettingsImpl clone(PluginSettingsImpl src)
	{
		return new PluginSettingsImpl(src);
	}

	@Override
	public void registerPermissionMessageModification(boolean modification)
	{
		this.messageModification = modification;
	}

	@Override
	public void registerPermissionAddAfterMessage(boolean adding)
	{
		this.addAfterMessage = adding;
	}

	@Override
	public void registerPermissionWriteStack(String keyname)
	{
		if(!this.writeStack.contains(keyname))
		{
			this.writeStack.add(keyname);
		}
	}

	@Override
	public void registerPermissionReadStack(String keyname)
	{
		if(!this.readStack.contains(keyname))
		{
			this.readStack.add(keyname);
		}
	}

	public boolean getMessageModification()
	{
		return this.messageModification;
	}

	public boolean getAddAfterMessage()
	{
		return this.addAfterMessage;
	}

	public List<String> getWriteStack()
	{
		return this.writeStack;
	}
	public String makeWriteStackListString()
	{
		StringBuilder result = new StringBuilder();
		String comma = "";
		for(String stack : this.writeStack)
		{
			result.append(comma);
			result.append(stack);
			comma = ", ";
		}
		return result.toString();
	}

	public List<String> getReadStack()
	{
		return this.readStack;
	}
	public String makeReadStackListString()
	{
		StringBuilder result = new StringBuilder();
		String comma = "";
		for(String stack : this.readStack)
		{
			result.append(comma);
			result.append(stack);
			comma = ", ";
		}
		return result.toString();
	}

}
