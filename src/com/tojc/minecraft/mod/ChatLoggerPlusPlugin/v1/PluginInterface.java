package com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1;

public interface PluginInterface
{
	public void onInitialize(PluginSettings settings);
	public void onChatMessage(ChatMessage chat);
	public void onFinalize();
}
