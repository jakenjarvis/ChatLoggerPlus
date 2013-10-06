// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "ReplaceThree";
var version = "1.0.1";
var description = "sample plugin";
var auther = "Jaken";

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // チャットを書き換えるために、パーミッション取得。
        settings.registerPermissionMessageModification();
    },

    onChatMessage: function(env, chat)
    {
        // チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
        var chatmessage = chat.getMessage();

        // 他のプラグインによって、チャットメッセージが削除されている場合は
        // nullになっているため、チェックしてから処理を行うようにする。
        if(chatmessage != null)
        {
            // JavaのStringからJavaScriptのStringに型変換（replaceを使うために）
            var message = String(chatmessage);

            // 文字列3を置換する。
            var newmessage = message.replace(/3/g, '§aAho§r');

            // 編集したメッセージをセット。
            // これを呼び出すためには、MessageModificationのパーミッションが必要。
            chat.setMessage(newmessage);
        }
    },

    onFinalize: function()
    {
    },
};
