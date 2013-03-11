package com.tojc.minecraft.mod.ChatLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLogger.Plugin.ChatMessageImpl;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginManager;
import com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1.ChatMessage;

public class ClientChatMessageManager
{
	private ChatLoggerConfiguration config = null;
	private PluginManager pluginManager = null;

	private ChatMessageImpl messageOriginal = null;
	private ChatMessageImpl messageOutputScreen = null;
	private ChatMessageImpl messageOutputChatLog = null;

	private List<String> messageOutputScreenAfter = null;
	private List<String> messageOutputChatLogAfter = null;

	public ClientChatMessageManager(ChatLoggerCore core, String servername, String worldname, String message)
	{
		this.config = core.getConfig();
		this.pluginManager = core.getPluginManager();

		this.messageOriginal = new ChatMessageImpl(servername, worldname, message);

		this.messageOutputScreenAfter = new ArrayList<String>();
		this.messageOutputChatLogAfter = new ArrayList<String>();

		this.messageOutputScreen = this.pluginManager.getPluginOrder().getPluginScreenOrder()
				.fireOnChatMessage(this.messageOriginal, this.messageOutputScreenAfter);

		this.messageOutputChatLog = this.pluginManager.getPluginOrder().getPluginChatLogOrder()
				.fireOnChatMessage(this.messageOriginal, this.messageOutputChatLogAfter);

		// TODO: 特定ユーザのカラー表示:ユーザー名を判定して、発言部分に指定色を塗る。
		// TODO: 特定ユーザのNG:ユーザー名を判定して、発言全体を削除する。
		// TODO: 特定ワードのNG:指定されたワードを、指定した文字で塗りつぶす。
		// TODO: 特定条件を切っ掛けに何かActionする。
		// TODO: 特定条件を切っ掛けに何か発言する。（ローカル、世界、固定文、リストランダム、コマンド実行）
		// TODO: サーバによって、条件を変えたい。（ユーザー名の検出方法など）

		// 判定：サーバ名、ワールド名、ユーザー名、正規表現（グループ指定）、キーワードリスト
		// 動作：置換、削除、外部CMD実行、発言
	}

	private String replaceFillColorCode(String message)
	{
		// カラーコードの削除
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
		return this.messageOutputScreen.getMessage();
	}

	public List<String> outputScreenAfterMessages()
	{
		return this.messageOutputScreenAfter;
	}

	public String outputChatLog()
	{
		return this.messageOutputChatLog.getMessage();
	}

	public List<String> outputChatLogAfterMessages()
	{
		return this.messageOutputChatLogAfter;
	}

}
