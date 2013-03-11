package com.tojc.minecraft.mod.ChatLogger;

public class ClientChatMessageManager
{
	private ChatLoggerConfiguration config = null;

	private String messageOriginal = "";
	private String messageOutputScreen = "";
	private String messageOutputChatLog = "";

	public ClientChatMessageManager(ChatLoggerConfiguration config, String message)
	{
		this.config = config;

		this.messageOriginal = message;
		this.messageOutputScreen = new String(message);
		this.messageOutputChatLog = new String(message);

		// カラーコードの削除
		this.messageOutputChatLog = replaceFillColorCode(this.messageOutputChatLog);

		// TODO: 特定ユーザのカラー表示:ユーザー名を判定して、発言部分に指定色を塗る。
		// TODO: 特定ユーザのNG:ユーザー名を判定して、発言全体を削除する。
		// TODO: 特定ワードのNG:指定されたワードを、指定した文字で塗りつぶす。


	}

	private String replaceFillColorCode(String message)
	{
		String result = message;
		if(this.config.getFillColorCodeEnabled().get())
		{
			String regex = this.config.getFillColorCodeRegex().get();
			String replace = this.config.getFillColorCodeReplace().get();
			result = result.replaceAll(regex, replace);
		}
		return result;
	}







	public String outputScreen()
	{
		return this.messageOutputScreen;
	}

	public String outputChatLog()
	{
		return this.messageOutputChatLog;
	}
}
