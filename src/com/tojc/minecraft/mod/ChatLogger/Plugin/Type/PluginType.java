package com.tojc.minecraft.mod.ChatLogger.Plugin.Type;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;

public enum PluginType
{
	Screen("Screen", new PluginTypeInterface()
	{
		@Override
		public ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToScreen();
		}
	}),

	ChatLog("ChatLog", new PluginTypeInterface()
	{
		@Override
		public ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToChatLog();
		}
	});

	private PluginType(String name, PluginTypeInterface individual)
	{
		this.name = name;
		this.individual = individual;
	}

    private final String name;
    private final PluginTypeInterface individual;

    public String getName()
    {
		return this.name;
	}

    public PluginTypeInterface getIndividual()
	{
		return this.individual;
	}

    @Override
    public String toString()
    {
        return this.name;
    }

    public static PluginType toPluginType(String name)
    {
    	PluginType result = Screen;
    	for (PluginType type : values())
    	{
    		if(type.getName().equalsIgnoreCase(name))
    		{
    			result = type;
    			break;
    		}
    	}
    	return result;
    }
}
