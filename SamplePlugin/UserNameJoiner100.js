// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "UserNameJoiner Plugin";
var version = "1.0.0";
var description = "Join the user name stored in stack.";
var auther = "Jaken";

var plugin = new PluginInterface()
{
	onInitialize: function(settings)
	{
		// Stack UserName への読み込み権限登録
		settings.registerPermissionReadStack("UserName");
		// チャットを書き換えるために、パーミッション取得。
		settings.registerPermissionMessageModification(true);
	},

	onChatMessage: function(chat)
	{
		// Stack UserName から結果を読み込み（文字列）
		var username = chat.readStack("UserName");

		// チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
		var chatmessage = chat.getMessage();

		var message = "<" + username + "> " + chatmessage;

		// 編集したメッセージをセット。
		// これを呼び出すためには、MessageModificationのパーミッションが必要。
		chat.setMessage(message);
	},

	onFinalize: function()
	{
	},
};
