package com.tojc.minecraft.mod.ChatLogger.Plugin;

public enum PluginState
{
	Disabled(	false,	"Disabled",			"Plug-in is disabled."),
	Enabled(	true,	"Enabled",			"Plug-in is enabled."),
	Error(		false,	"Error",			"Found a problem in the plug-in.");

	private PluginState(boolean code, String name, String description)
	{
    	this.code = code;
        this.name = name;
        this.description = description;
	}

    private final boolean code;
    private final String name;
    private final String description;

    public boolean getCode()
    {
    	return this.code;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    public static PluginState toPluginState(String name)
    {
    	PluginState result = Disabled;
    	for (PluginState reason : values())
    	{
    		if(reason.getName().equalsIgnoreCase(name))
    		{
    			result = reason;
    			break;
    		}
    	}
    	return result;
    }
}
