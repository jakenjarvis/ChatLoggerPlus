// ファイル形式はUTF-8で保存すること
// Please file format to UTF-8.
importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

var name = "plugin name";
var version = "1.0.0";
var description = "plugin description";
var auther = "your name";

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
