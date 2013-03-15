package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.Configuration.ConfigurationPropertyArrayString;
import com.tojc.minecraft.mod.Crypto.SimpleEncryption;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginOrder
{
	private Map<String, Object> pluginStack = null;
	private PluginScreenOrder pluginScreenOrder = null;
	private PluginChatLogOrder pluginChatLogOrder = null;
	private Map<String, PluginInformation> masterMapping = null;

	public PluginOrder(ChatLoggerConfiguration config)
	{
		this.pluginStack = new HashMap<String, Object>();
		this.pluginScreenOrder = new PluginScreenOrder(config, this.pluginStack);
		this.pluginChatLogOrder = new PluginChatLogOrder(config, this.pluginStack);
	}

	public Map<String, Object> getPluginStack()
	{
		return this.pluginStack;
	}

	public Map<String, PluginInformation> getMasterMapping()
	{
		return this.masterMapping;
	}

	public void createMapping(List<PluginInformation> plugins)
	{
		this.masterMapping = new HashMap<String, PluginInformation>();
		for(PluginInformation info : plugins)
		{
			this.masterMapping.put(info.getPluginKey(), info);
		}
		this.pluginScreenOrder.createMapping(this.masterMapping);
		this.pluginChatLogOrder.createMapping(this.masterMapping);
	}

	public void load()
	{
		this.pluginScreenOrder.load();
		this.pluginChatLogOrder.load();
	}

	public void save()
	{
		this.pluginScreenOrder.save();
		this.pluginChatLogOrder.save();
	}

	public PluginScreenOrder getPluginScreenOrder()
	{
		return this.pluginScreenOrder;
	}

	public PluginChatLogOrder getPluginChatLogOrder()
	{
		return this.pluginChatLogOrder;
	}



	public static abstract class PluginOrderBase
	{
		private ChatLoggerConfiguration config = null;

		private Map<String, Object> pluginStack = null;
		private List<OrderKey> orderKeys = null;
		private Map<String, PluginStatus> mapping = null;

		public PluginOrderBase(ChatLoggerConfiguration config, Map<String, Object> pluginStack)
		{
			this.config = config;
			this.pluginStack = pluginStack;
			this.mapping = new HashMap<String, PluginStatus>();
		}

		public List<OrderKey> getOrderKey()
		{
			return this.orderKeys;
		}

		public Map<String, PluginStatus> getMapping()
		{
			return this.mapping;
		}

		protected abstract ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config);

		public void load()
		{
			// 形式を戻す
			this.orderKeys = new ArrayList<OrderKey>();
			// 固定長なので注意
			List<String> fixedLengthArray = this.getConfigurationProperty(this.config).get();
			for(String item : fixedLengthArray)
			{
				this.orderKeys.add(new OrderKey().set(item));
			}
		}

		public void save()
		{
			// ForgeのConfiguration.loadのArrayStringが腐ってるので、形式変換
			// パスを保存したいのに、文字列に￥や／を含むと落ちる。致命的。
			List<String> array = new ArrayList<String>();
			for(OrderKey item : this.orderKeys)
			{
				PluginStatus status = this.mapping.get(item.getKey());
				if(status != null)
				{
					OrderKey key = new OrderKey().set(status.getKey(), status.getStatus());
					array.add(key.toString());
				}
			}
			this.getConfigurationProperty(this.config).set(array);
		}

		public boolean checkOrder()
		{
			boolean result = true;
			List<String> stack = new ArrayList<String>();
			for(OrderKey item : this.orderKeys)
			{
				PluginStatus status = this.mapping.get(item.getKey());
				if(status.isEnabled())
				{
					for(String write : status.getPlugin().getSettings().getWriteStack())
					{
						// 重複する登録は認めない
						if(stack.contains(write))
						{
							result = false;
							break;
						}
					}
					if(!result)
					{
						break;
					}

					for(String read : status.getPlugin().getSettings().getReadStack())
					{
						// 上位に登録が無ければ認めない
						if(!stack.contains(read))
						{
							result = false;
							break;
						}
					}
					if(!result)
					{
						break;
					}
				}
			}
			return result;
		}

		public void createMapping(Map<String, PluginInformation> masterMapping)
		{
			for(OrderKey item : this.orderKeys)
			{
				PluginInformation plugin = masterMapping.get(item.getKey());
				PluginStatus status = new PluginStatus(item.getKey(), item.getReason(), plugin);
				this.mapping.put(status.getKey(), status);
			}
		}

		public ChatMessageImpl fireOnChatMessage(ChatMessageImpl chat, List<String> masterMessagesAfter)
		{
			ChatMessageImpl result = ChatMessageImpl.clone(chat);
			result.setMessagesAfter(masterMessagesAfter);

			for(OrderKey item : this.orderKeys)
			{
				PluginStatus status = this.mapping.get(item.getKey());
				if(status.isEnabled())
				{
					result.setSettings(status.getPlugin().getSettings());
					result.setPluginStack(this.pluginStack);
					status.getPlugin().onChatMessage(result);
				}
				else
				{
					DebugLog.info("onChatMessage() not call: %s", item);
				}
			}
			return result;
		}

	}


	public static class PluginScreenOrder extends PluginOrderBase
	{
		public PluginScreenOrder(ChatLoggerConfiguration config, Map<String, Object> pluginStack)
		{
			super(config, pluginStack);
		}

		@Override
		protected ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToScreen();
		}
	}

	public static class PluginChatLogOrder extends PluginOrderBase
	{
		public PluginChatLogOrder(ChatLoggerConfiguration config, Map<String, Object> pluginStack)
		{
			super(config, pluginStack);
		}

		@Override
		protected ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config)
		{
			return config.getPluginOrderToChatLog();
		}
	}

	public enum PluginStatusReason
	{
		Disabled(		false,	"Disabled",			"Plug-in is disabled."),
		Enabled(		true,	"Enabled",			"Plug-in is enabled."),
		ErrorMissing(	false,	"ErrorMissing",		"Plug-in is missing."),
		ErrorBug(		false,	"ErrorBug",			"Found a bug in the plug-in.");

		private PluginStatusReason(boolean code, String name, String description)
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

	    public static PluginStatusReason toPluginStatusReason(String name)
	    {
	    	PluginStatusReason result = Disabled;
	    	for (PluginStatusReason reason : values())
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

	private static class OrderKey
	{
		private static String SEPARATOR = ";";

		private String key = "";
		private PluginStatusReason reason = null;

		public OrderKey()
		{
			this.set("", PluginStatusReason.Disabled);
		}

		public OrderKey set(String savekey)
		{
			String[] tokens = savekey.split(SEPARATOR);
			if(tokens.length == 2)
			{
				this.key = tokens[0];
				this.reason = PluginStatusReason.toPluginStatusReason(tokens[1]);
			}
			else
			{
				this.key = savekey;
				this.reason = PluginStatusReason.Disabled;
			}
			return this;
		}

		public OrderKey set(String key, PluginStatusReason reason)
		{
			this.key = key;
			this.reason = reason;
			return this;
		}

		public String getKey()
		{
			return this.key;
		}
		public void setKey(String key)
		{
			this.key = key;
		}

		public PluginStatusReason getReason()
		{
			return this.reason;
		}
		public void setReason(PluginStatusReason reason)
		{
			this.reason = reason;
		}

		public String makeSaveKey()
		{
			return this.key + SEPARATOR + this.reason.getName();
		}

		@Override
	    public String toString()
	    {
	        return this.makeSaveKey();
	    }
	}

	public static class PluginStatus
	{
		private String key = "";
		private PluginInformation plugin = null;

		private PluginStatusReason status = null;

		public PluginStatus(String key, PluginStatusReason status, PluginInformation plugin)
		{
			this.key = key;
			this.plugin = plugin;

			if(!this.isError())
			{
				this.status = status;
			}
			else
			{
				this.status = PluginStatusReason.ErrorMissing;
			}
		}

		public String getKey()
		{
			return this.key;
		}

		public PluginStatusReason getStatus()
		{
			return this.status;
		}
		public void setStatus(PluginStatusReason status)
		{
			this.status = status;
		}

		public PluginInformation getPlugin()
		{
			return this.plugin;
		}

		public boolean isError()
		{
			return !((this.plugin != null) && (this.plugin.isEnabled()));
		}

		public boolean isEnabled()
		{
			return this.status.getCode();
		}
		public void setEnabled(boolean enabled)
		{
			if(!this.isError())
			{
				switch(this.status)
				{
					case Disabled:
					case Enabled:
						if(enabled)
						{
							this.status = PluginStatusReason.Enabled;
						}
						else
						{
							this.status = PluginStatusReason.Disabled;
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
				switch(this.status)
				{
					case Disabled:
					case Enabled:
						this.status = PluginStatusReason.ErrorBug;
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

		// TODO: UI support method

	}

}
