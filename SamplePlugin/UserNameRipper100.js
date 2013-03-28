// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "UserNameRipper Plugin";
var version = "1.0.0";
var description = "Remove the user name stored in stack.";
var auther = "Jaken";

var plugin = new PluginInterface()
{
	onInitialize: function(settings)
	{
		// Stack UserName への書き込み権限登録
		settings.registerPermissionWriteStack("UserName");
		// チャットを書き換えるために、パーミッション取得。
		settings.registerPermissionMessageModification(true);
	},

	onChatMessage: function(chat)
	{
		// チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
		var chatmessage = chat.getMessage();

		// JavaのStringからJavaScriptのStringに型変換（matchを使うために）
		var message = String(chatmessage);

		var username = "";

		// サーバによってユーザー名の表示方法が異なるので注意
		var result = message.match(/^<([^>]+)>\s(.*)/);
		if(result != null)
		{
			username = result[1];
			message = result[2];
		}

		// Stack UserName へ結果を書き込み
		chat.writeStack("UserName", username);

		// 編集したメッセージをセット。
		// これを呼び出すためには、MessageModificationのパーミッションが必要。
		chat.setMessage(message);
	},

	onFinalize: function()
	{
	},
};
