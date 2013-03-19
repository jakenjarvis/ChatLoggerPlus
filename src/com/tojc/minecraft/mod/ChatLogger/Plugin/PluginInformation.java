package com.tojc.minecraft.mod.ChatLogger.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import java.net.URLDecoder;
import java.security.PrivilegedActionException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.ChatMessage;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginInterface;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.PluginSettings;
import com.tojc.minecraft.mod.Exception.PluginPermissionDeniedException;
import com.tojc.minecraft.mod.Exception.PluginScriptRuntimeException;
import com.tojc.minecraft.mod.log.DebugLog;

public class PluginInformation implements PluginInterface
{
	private File pluginFile = null;
	private File pluginBaseDir = null;
	private String pluginKey = "";
	private int interfaceVersion = 0;

	private PluginInterface plugin = null;
	private PluginSettingsImpl settings = null;

	private String name = "";
	private String version = "";
	private String description = "";
	private String auther = "";

	public PluginInformation(File pluginBaseDir, File pluginFile, int interfaceVersion)
	{
		this.pluginFile = pluginFile;

		this.pluginBaseDir = pluginBaseDir;
		this.pluginKey = makePluginKey();
		this.interfaceVersion = interfaceVersion;
	}

	private String makePluginKey()
	{
		String result = "";
		if((this.pluginFile != null) && (this.pluginFile.length() >= 1))
		{
			try
			{
				//相対パス化
				URI uri = this.pluginBaseDir.toURI().relativize(this.pluginFile.toURI());
				//File file = new File(uri.toString());
				File file = new File(URLDecoder.decode(uri.toString() , "UTF-8"));
				result = file.toString();
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	public File getPluginFile()
	{
		return this.pluginFile;
	}

	public File getPluginBaseDir()
	{
		return this.pluginBaseDir;
	}

	public String getPluginKey()
	{
		return this.pluginKey;
	}

	public int getInterfaceVersion()
	{
		return this.interfaceVersion;
	}
	public void setInterfaceVersion(int interfaceVersion)
	{
		this.interfaceVersion = interfaceVersion;
	}

	public PluginInterface getPlugin()
	{
		return this.plugin;
	}
	public void setPlugin(PluginInterface plugin)
	{
		this.plugin = plugin;
	}

	public boolean isEnabled()
	{
		return (this.plugin != null);
	}

	public PluginSettingsImpl getSettings()
	{
		return this.settings;
	}
	public void setSettings(PluginSettingsImpl settings)
	{
		this.settings = settings;
	}


	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return this.version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getAuther()
	{
		return this.auther;
	}
	public void setAuther(String auther)
	{
		this.auther = auther;
	}


	@Override
	public void onInitialize(PluginSettings settings)
	{
		if(this.isEnabled())
		{
			try
			{
				this.plugin.onInitialize(settings);
			}
			catch(Exception e)
			{
				Exception ex = makePluginScriptRuntimeException(e, this.pluginFile);
				DebugLog.info("[%s].onInitialize() Exception : %s", this.getName(), ex.toString());
				this.setPlugin(null);
			}
		}
	}

	@Override
	public void onChatMessage(ChatMessage chat)
	{
		if(this.isEnabled())
		{
			ChatMessageImpl backup = ChatMessageImpl.clone(chat);
			try
			{
				this.plugin.onChatMessage(chat);
				if(!this.settings.getMessageModification())
				{
					if(backup.getMessage() != null)
					{
						if(!backup.getMessage().equals(chat.getMessage()))
						{
							throw new PluginPermissionDeniedException("Permission MessageModification is required!");
						}
					}
					else
					{
						if(chat.getMessage() != null)
						{
							throw new PluginPermissionDeniedException("Permission MessageModification is required!");
						}
					}
				}
			}
			catch(Exception e)
			{
				Exception ex = ((ChatMessageImpl)chat).getException();
				if(ex == null)
				{
					ex = makePluginScriptRuntimeException(e, this.pluginFile);
				}
				DebugLog.info("[%s].onChatMessage() Exception : %s", this.getName(), ex.toString());
				// 例外時は強制的に元に戻す。
				chat.setMessage(backup.getMessage());
				this.setPlugin(null);
			}
		}
	}

	@Override
	public void onFinalize()
	{
		if(this.isEnabled())
		{
			try
			{
				this.plugin.onFinalize();
			}
			catch(Exception e)
			{
				Exception ex = makePluginScriptRuntimeException(e, this.pluginFile);
				DebugLog.info("[%s].onFinalize() Exception : %s", this.getName(), ex.toString());
				this.setPlugin(null);
			}
		}
	}

    public static Exception makePluginScriptRuntimeException(Exception e, File pluginFile)
    {
    	Exception result = null;
    	if(e instanceof PluginPermissionDeniedException)
    	{
			result = e;
    	}
    	else if(e instanceof PluginScriptRuntimeException)
    	{
			result = e;
    	}
    	else
    	{
			// MEMO: どうあがいても、Script内で発生した「JavaからThrowしたException」を拾えない。
			// ここでターゲットとしているエラーは、ChatMessageImplクラス内でThrowしているPluginPermissionDeniedExceptionである。
			// Script内でregisterPermissionWriteStackで登録せずに、writeStackを呼び出した場合に発生させる。

			// これは、ScriptEngine内で握りつぶされているようだ。（@alalwwwさんありがとう！）
			// また、WrappedExceptionも内包しているようだが、internalクラスのため、catchすることもできない。
			// それぞれのException独自メソッドgetUndeclaredThrowable()やgetException()も調べたが拾えなかった。
			//（ドキュメントにあるようにgetCause()と基本的に同じ）

			// MEMO: 本来、ScriptExceptionは、ビルド時に判断できる検査例外である。
			// このため、UndeclaredThrowableExceptionでラップされ、PrivilegedActionExceptionで検査例外であることを示している。
			// つまり、「検査例外されるべき例外が、実行時に発生した」状態を表している。

			// MEMO: 発生するエラー（この中に、ターゲットのPluginPermissionDeniedExceptionが含まれない）
			// java.lang.reflect.UndeclaredThrowableException
			// java.security.PrivilegedActionException
			// sun.org.mozilla.javascript.internal.WrappedException
			// javax.script.ScriptException

        	Throwable cause = e;
        	while (cause != null)
    		{
    			if(cause instanceof ScriptException)
    			{
    				ScriptException ex = (ScriptException)cause;
    				if(ex.getFileName() != null)
    				{
    					// ファイル名が取得できた場合は、有効なScriptExceptionだと判断する。
    					result = new PluginScriptRuntimeException(ex.getMessage(), ex.getFileName(), ex.getLineNumber(), ex.getColumnNumber());
    				}
    				else
    				{
    					// 今回のケースでは、ScriptExceptionの中身がカラなので、ファイル名だけでも補完し、新しく生成する。
    					result = new PluginScriptRuntimeException(ex.getClass().getName(), pluginFile.toString(), -1, -1);
    				}
    			}
    			cause = cause.getCause();
    		}

        	if(result == null)
    		{
    			// ScriptExceptionが含まれなかった場合
    			result = new PluginScriptRuntimeException(e);
    		}
    	}
		return result;
    }
}
