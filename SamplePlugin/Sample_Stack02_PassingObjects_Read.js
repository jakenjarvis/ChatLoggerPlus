// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "Passing Objects(read)";
var version = "1.0.0";
var description = "sample plugin";
var auther = "Jaken";

var plugin = new PluginInterface()
{
    onInitialize: function(settings)
    {
        // Stack Passing への読み込み権限登録
        settings.registerPermissionReadStack("Passing");
    },

    onChatMessage: function(chat)
    {
        // Stack Passing から結果を読み込み（PassingItemオブジェクト）
        var value = chat.readStack("Passing");
        if(value != null)
        {
            // デバッグに値を表示して確認する。
            debug.log(name, "PassingItem: " + value.toString());
            debug.log(name, "PassingItem.name: " + value.name);
            debug.log(name, "PassingItem.length: " + value.length);

            // 出力例
            // [ SCRIPT    ]: {Passing Objects(read)}: PassingItem: Name: Player977, Length: 9
            // [ SCRIPT    ]: {Passing Objects(read)}: PassingItem.name: Player977
            // [ SCRIPT    ]: {Passing Objects(read)}: PassingItem.length: 9

            // ここでの注目ポイントは、このスクリプト内には定義していないPassingItemオブジェクトの
            // ・メンバー変数のnameやlengthにアクセス出来ている事（１つのスタックで複数の値を渡すことが出来る）
            // ・toStringメソッドにアクセス出来ている事（処理をWrite側で定義できる）
            // です。これを活用すれば、プラグイン間でのAPI提供も可能です。
        }
    },

    onFinalize: function()
    {
    },
};
