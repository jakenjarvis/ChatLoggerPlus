// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "FormattingCodesRemover Plugin";
var version = "1.0.0";
var description = "Remove the color code.";
var auther = "Jaken";

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // チャットを書き換えるために、パーミッション取得。
        settings.registerPermissionMessageModification(true);
    },

    onChatMessage: function(chat)
    {
        // チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
        var chatmessage = chat.getMessage();

        // JavaのStringからJavaScriptのStringに型変換（replaceを使うために）
        var message = String(chatmessage);

        // フォーマットコードを削除する。
        var newmessage = message.replace(/§[0-9a-fk-or]/g, "");

        // 編集したメッセージをセット。
        // これを呼び出すためには、MessageModificationのパーミッションが必要。
        chat.setMessage(newmessage);
    },

    onFinalize: function()
    {
    },
};
