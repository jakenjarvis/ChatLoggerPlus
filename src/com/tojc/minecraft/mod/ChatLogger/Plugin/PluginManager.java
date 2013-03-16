package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.PluginDirectoryNameManager;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderKey;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginInterface;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginSettings;
import com.tojc.minecraft.mod.Exception.PluginScriptRuntimeException;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginManager
{
	private enum PluginManagerStatus
	{
		Initialize,
		Loaded,
		Unloaded
	}

	private ChatLoggerConfiguration config = null;
	private PluginManagerStatus status = PluginManagerStatus.Initialize;

	private File pluginDir = null;
	private ScriptEngineManager manager = null;
	private List<ScriptEngineFactory> factorys = null;
	private List<PluginInformation> plugins = null;

	private PluginOrderManager pluginOrderManager = null;

	public PluginManager(ChatLoggerConfiguration config)
	{
		this.config = config;
		this.status = PluginManagerStatus.Initialize;

		PluginDirectoryNameManager pluginDirectoryName = new PluginDirectoryNameManager(this.config);

		this.pluginDir = pluginDirectoryName.getPluginDirectoryFile();

		// MEMO: http://docs.oracle.com/javase/jp/6/technotes/guides/scripting/programmer_guide/index.html
		// MEMO: http://www.techscore.com/tech/Java/JavaSE/JavaSE6/5/
		// MEMO: http://www.javainthebox.net/laboratory/JavaSE6/scripting/scripting.html

		this.manager = new ScriptEngineManager();
		this.factorys = this.manager.getEngineFactories();
		for(ScriptEngineFactory factory : this.factorys)
		{
			DebugLog.info("ScriptEngine: %s %s, %s %s",
					factory.getEngineName(),
					factory.getEngineVersion(),
					factory.getLanguageName(),
					factory.getLanguageVersion());
			for(String extension : factory.getExtensions())
			{
				DebugLog.info("Valid Extension: " + extension);
	        }
		}

		this.pluginOrderManager = new PluginOrderManager(this.config);
	}

	public void load()
	{
		switch(this.status)
		{
			case Initialize:
			case Unloaded:
				if(!this.pluginDir.exists())
				{
					this.pluginDir.mkdirs();
				}

				if(this.plugins == null)
				{
					// new load
					this.plugins = new ArrayList<PluginInformation>();
				}
				else
				{
					// reload
					this.unload();
				}

				findPluginDirectory(this.pluginDir);

				for(PluginInformation plugin : this.plugins)
				{
					if(plugin.getInterfaceVersion() >= 1)
					{
						//load
						PluginSettingsImpl settings = new PluginSettingsImpl();
						plugin.onInitialize(settings);
						plugin.setSettings(PluginSettingsImpl.clone(settings));
					}
					else
					{
						//invalidation(Plug-in file is not found. or load error.)
						DebugLog.info("Plug-in file is not found. or load error. : %s", plugin.getPluginFile().toString());
						plugin.setPlugin(null);
					}
				}

				this.pluginOrderManager.loadSetting();
				// for test
				//this.pluginOrder.getPluginChatLogOrder().getOrderKey().add(this.plugins.get(0).getPluginFile().toString());
				//this.pluginOrder.getPluginChatLogOrder().getOrderKey().add(this.plugins.get(1).getPluginFile().toString());
				//this.pluginOrder.getPluginChatLogOrder().getOrderKey().add(this.plugins.get(2).getPluginFile().toString());
				//this.pluginOrder.getPluginChatLogOrder().getOrderKey().add(this.plugins.get(3).getPluginFile().toString());

				//this.pluginOrderManager.getPluginScreenOrderController().getPluginOrderKey().add(new PluginOrderKey(this.plugins.get(0).getPluginKey(), PluginState.Enabled));

				// for test
				//this.pluginOrderManager.saveSetting();
				this.pluginOrderManager.setPlugins(this.plugins);
				this.pluginOrderManager.createMapping();

				this.status = PluginManagerStatus.Loaded;
				break;

			default:
				break;
		}
	}

	public void unload()
	{
		switch(this.status)
		{
			case Loaded:
				for(PluginInformation plugin : this.plugins)
				{
					//unload
					plugin.onFinalize();
					//reset
					plugin.setInterfaceVersion(0);
				}
				this.status = PluginManagerStatus.Unloaded;
				break;

			default:
				break;
		}
	}

	public PluginOrderManager getPluginOrderManager()
	{
		return this.pluginOrderManager;
	}

	public PluginInformation findPluginList(File pluginfile)
	{
		PluginInformation result = null;
		for(PluginInformation info : this.plugins)
		{
			if(info.getPluginFile().toString().equals(pluginfile.toString()))
			{
				result = info;
				break;
			}
		}
		return result;
	}

	private void findPluginDirectory(File plugindirectory)
	{
		File[] files = plugindirectory.listFiles();
		if(files != null)
		{
			for(File file : files)
			{
				if(!file.exists())
				{
					continue;
				}
				else if(file.isDirectory())
				{
					findPluginDirectory(file);
				}
				else if(file.isFile())
				{
					findPlugin(file);
				}
			}
		}
	}

	private void findPlugin(File pluginfile)
	{
		String extension = getExtension(pluginfile.getName());
		ScriptEngine engine = this.manager.getEngineByExtension(extension);
		if(engine != null)
		{
			PluginInformation plugin = findPluginList(pluginfile);
			if(plugin == null)
			{
				DebugLog.info("find new plugin: %s", pluginfile.getName());
				plugin = loadPlugin(pluginfile, engine);
				if(plugin != null)
				{
					this.plugins.add(plugin);
				}
			}
			else
			{
				DebugLog.info("find known plugin: %s", pluginfile.getName());
				PluginInformation loadplugin = loadPlugin(pluginfile, engine);
				if(loadplugin != null)
				{
					plugin.setPlugin(loadplugin.getPlugin());
					plugin.setInterfaceVersion(loadplugin.getInterfaceVersion());
				}
			}
		}
	}

	private String getExtension(String filename)
	{
        String ext[] = filename.split("\\.");
        return ext[ext.length - 1];
    }

	private PluginInformation loadPlugin(File pluginfile, ScriptEngine engine)
	{
		PluginInformation result = null;
		InputStreamReader reader = null;
		try
		{
			//reader = new FileReader(pluginfile);
			reader = new InputStreamReader(new FileInputStream(pluginfile), "UTF-8");
			if(engine instanceof Compilable)
			{
				//DebugLog.info("compile: %s", pluginfile.getName());
				CompiledScript script = ((Compilable)engine).compile(reader);
				script.eval();
			}
			else
			{
				engine.eval(reader);
			}
			reader.close();
			reader = null;

			String name = loadStringValue(engine, "name", pluginfile.getName());
			String version = loadStringValue(engine, "version", "1.0");
			String description = loadStringValue(engine, "description", "");
			String auther = loadStringValue(engine, "auther", "");

			Object pluginObject = engine.get("plugin");
			if(pluginObject == null)
			{
				throw new Exception("UnimplementedException: 'plugin' object can not be found. 'var plugin = new PluginInterface(){}'");
			}

			Invocable inv = (Invocable)engine;
			PluginInterface plugin = inv.getInterface(pluginObject, com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginInterface.class);
			if(plugin != null)
			{
				result = new PluginInformation(this.pluginDir, pluginfile, 1);
				result.setPlugin(plugin);
				result.setName(name);
				result.setVersion(version);
				result.setDescription(description);
				result.setAuther(auther);
			}

			if(plugin == null)
			{
				throw new Exception("UnimplementedException: Interface is not implemented.");
			}
		}
		catch(Exception e)
		{
			result = null;
			PluginScriptRuntimeException ex = new PluginScriptRuntimeException(e);
			DebugLog.info("[%s].loadPlugin() Exception : %s", pluginfile.getName(), ex.toString());
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(IOException e1)
				{
				}
			}
		}
		return result;
	}

	private String loadStringValue(ScriptEngine engine, String key, String defaultValue)
	{
		String result = "";
		try
		{
			result = (String)engine.get(key);
			if(result == null)
			{
				result = defaultValue;
			}
		}
		catch(Exception e)
		{
			result = defaultValue;
		}
		return result;
	}

}
