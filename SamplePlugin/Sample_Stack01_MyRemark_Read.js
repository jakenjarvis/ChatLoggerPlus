// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "My remark(read)";
var version = "1.0.0";
var description = "sample plugin";
var auther = "Jaken";

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // Stack MyRemark への読み込み権限登録
        settings.registerPermissionReadStack("MyRemark");
        // チャットを書き換えるために、パーミッション取得。
        settings.registerPermissionMessageModification();
    },

    onChatMessage: function(env, chat)
    {
        // Stack MyRemark から結果を読み込み（数値）
        var myRemark = chat.readStack("MyRemark");

        // デバッグに値を表示して確認する。
        debug.log(name, "myRemark: " + myRemark);

        // MyRemarkが1の時だけチャット加工処理を行う。
        if(myRemark == 1)
        {
            // チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
            var chatmessage = chat.getMessage();

            // 他のプラグインによって、チャットメッセージが削除されている場合は
            // nullになっているため、チェックしてから処理を行うようにする。
            if(chatmessage != null)
            {
                // JavaのStringからJavaScriptのStringに型変換
                var message = String(chatmessage);

                // 前後にカラー記号を入れる。Gray表示とする。
                var newmessage = "§7" + message + "§r";

                // 編集したメッセージをセット。
                // これを呼び出すためには、MessageModificationのパーミッションが必要。
                chat.setMessage(newmessage);
            }
        }
    },

    onFinalize: function()
    {
    },
};
