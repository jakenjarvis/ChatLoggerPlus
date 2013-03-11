package com.tojc.minecraft.mod.Exception;

import java.io.File;

public class PluginScriptRuntimeException extends javax.script.ScriptException
{
	public PluginScriptRuntimeException(Exception e)
	{
		super(e);
	}

	public PluginScriptRuntimeException(String string)
	{
		super(string);
	}

	public PluginScriptRuntimeException(String message, String fileName, int lineNumber)
	{
		super(message, fileName, lineNumber);
	}

	public PluginScriptRuntimeException(String message, String fileName, int lineNumber, int columnNumber)
	{
		super(message, fileName, lineNumber, columnNumber);
	}
}
