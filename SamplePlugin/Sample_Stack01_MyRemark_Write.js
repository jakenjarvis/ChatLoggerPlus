// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "My remark(write)";
var version = "1.0.0";
var description = "sample plugin";
var auther = "Jaken";

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // Stack MyRemark への書き込み権限登録
        settings.registerPermissionWriteStack("MyRemark");
    },

    onChatMessage: function(env, chat)
    {
        // プレイヤー名を取得
        var playername = env.getPlayerName();
        // ユーザー名の取得（発言者）
        var username = chat.getUserName();

        // デバッグに値を表示して確認する。
        debug.log(name, "playername: " + playername);
        debug.log(name, "username: " + username);

        // ユーザー名とプレイヤー名が等しい場合は1とし、それ以外の場合は0とする。
        var myRemark = 0;

        // ユーザー名がnullの場合は、ユーザーの発言ではないので無視する。
        if(username != null)
        {
            // JavaのStringからJavaScriptのStringに型変換
            playername = String(playername);
            username = String(username);

            if(playername == username)
            {
                // ユーザー名とプレイヤー名が等しい場合は MyRemark を 1 とする。
                myRemark = 1;
            }
        }

        // デバッグに値を表示して確認する。
        debug.log(name, "myRemark: " + myRemark);

        // Stack MyRemark へ書き込む。
        chat.writeStack("MyRemark", myRemark);
    },

    onFinalize: function()
    {
    },
};
