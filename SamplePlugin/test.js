// ファイル形式はUTF-8で保存すること

importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "test";
var version = "1.0";
var description = "test script";
var auther = "Jaken";

var plugin = new PluginInterface()
{
	onInitialize: function(settings)
	{
		println("onInitialize");
	},

	onChatMessage: function(chat)
	{
		println("onChatMessage");
	},

	onFinalize: function()
	{
		println("onFinalize");
	},
};
