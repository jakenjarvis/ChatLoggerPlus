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
	private static final String ENCKEY = "ChatLogger";

	private static String convertStringToInternalFormat(String item)
	{
		String result = "";
		try
		{
			result = SimpleEncryption.encrypt(ENCKEY, item);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private static String convertInternalFormatToString(String internalFormat)
	{
		String result = null;
		try
		{
			result = SimpleEncryption.decrypt(ENCKEY, internalFormat);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

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
			this.masterMapping.put(info.getPluginFile().toString(), info);
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
		private List<String> orderKey = null;
		private Map<String, PluginStatus> mapping = null;

		public PluginOrderBase(ChatLoggerConfiguration config, Map<String, Object> pluginStack)
		{
			this.config = config;
			this.pluginStack = pluginStack;
			this.mapping = new HashMap<String, PluginStatus>();
		}

		public List<String> getOrderKey()
		{
			return this.orderKey;
		}

		public Map<String, PluginStatus> getMapping()
		{
			return this.mapping;
		}

		protected abstract ConfigurationPropertyArrayString getConfigurationProperty(ChatLoggerConfiguration config);

		public void load()
		{
			// 形式を戻す
			this.orderKey = new ArrayList<String>();
			// 固定長なので注意
			List<String> fixedLengthArray = this.getConfigurationProperty(this.config).get();
			for(String item : fixedLengthArray)
			{
				this.orderKey.add(PluginOrder.convertInternalFormatToString(item));
			}
		}

		public void save()
		{
			// ForgeのConfiguration.loadのArrayStringが腐ってるので、形式変換
			// パスを保存したいのに、文字列に￥や／を含むと落ちる。致命的。
			List<String> array = new ArrayList<String>();
			for(String item : this.orderKey)
			{
				array.add(PluginOrder.convertStringToInternalFormat(item));
			}
			this.getConfigurationProperty(this.config).set(array);
		}

		public boolean checkOrder()
		{
			boolean result = true;
			List<String> stack = new ArrayList<String>();
			for(String item : this.orderKey)
			{
				PluginStatus status = this.mapping.get(item);
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
			for(String item : this.orderKey)
			{
				PluginInformation plugin = masterMapping.get(item);
				PluginStatus status = new PluginStatus(item, plugin);
				this.mapping.put(item, status);
			}
		}

		public ChatMessageImpl fireOnChatMessage(ChatMessageImpl chat, List<String> masterMessagesAfter)
		{
			ChatMessageImpl result = ChatMessageImpl.clone(chat);
			result.setMessagesAfter(masterMessagesAfter);

			for(String item : this.orderKey)
			{
				PluginStatus status = this.mapping.get(item);
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


	public static class PluginStatus
	{
		private String key = "";
		private PluginInformation plugin = null;

		public PluginStatus(String key, PluginInformation plugin)
		{
			this.key = key;
			this.plugin = plugin;
		}

		public String getKey()
		{
			return this.key;
		}

		public PluginInformation getPlugin()
		{
			return this.plugin;
		}

		public boolean isEnabled()
		{
			return ((this.plugin != null) && (this.plugin.isEnabled()));
		}

		// TODO: UI support method

	}

}
