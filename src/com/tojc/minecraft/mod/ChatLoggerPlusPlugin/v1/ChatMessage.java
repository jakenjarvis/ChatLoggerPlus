package com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1;

public interface ChatMessage
{
	public String getServerName();
	public String getWorldName();

	public String getOriginalJsonString();

	public String getPlayerName();
	public String getMessageOriginal();
	public String getMessage();
	public void setMessage(String message);

	public void addAfterMessage(String message);

	public void writeStack(String keyname, Object value);
	public Object readStack(String keyname);
}
