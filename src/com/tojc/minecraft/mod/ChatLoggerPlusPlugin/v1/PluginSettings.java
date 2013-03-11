package com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1;

import java.util.ArrayList;
import java.util.List;

public interface PluginSettings
{
	public void registerPermissionMessageModification(boolean modification);

	public void registerPermissionWriteStack(String keyname);
	public void registerPermissionReadStack(String keyname);
}
