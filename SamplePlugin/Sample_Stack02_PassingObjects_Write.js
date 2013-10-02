// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "Passing Objects(write)";
var version = "1.0.0";
var description = "sample plugin";
var auther = "Jaken";

// 受け渡すPassingItemオブジェクトを定義する。
function PassingItem(name)
{
    this.name = name;
    this.length = name.length;
}
// プロトタイプでtoStringメソッドを定義する。
PassingItem.prototype.toString = function()
{
    return "Name: " + this.name + ", Length: " + this.length;
};

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // Stack Passing への書き込み権限登録
        settings.registerPermissionWriteStack("Passing");
    },

    onChatMessage: function(env, chat)
    {
        // ユーザー名の取得（発言者）
        var username = chat.getUserName();

        // デバッグに値を表示して確認する。
        debug.log(name, "username: " + username);

        var value = null;

        // ユーザー名がnullの場合は、ユーザーの発言ではないので無視する。
        if(username != null)
        {
            // JavaのStringからJavaScriptのStringに型変換
            username = String(username);

            // 受け渡すPassingItemオブジェクトを生成する。
            value = new PassingItem(username);

            // デバッグに値を表示して確認する。
            debug.log(name, "PassingItem: " + value.toString());
        }

        // Stack Passing へ書き込む。
        chat.writeStack("Passing", value);
    },

    onFinalize: function()
    {
    },
};
