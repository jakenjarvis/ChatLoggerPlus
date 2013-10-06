.. _global_value:

global
##################################################

スクリプトの中からグローバルにアクセスできるオブジェクト等です。

:author: Jaken


Methods
**************************************************
debug.log
==================================================

.. java:method:: public void log(String pluginName, String message)
   :outertype: PluginDebugObject

   デバッグログを出力します。実際に出力されるかどうかは、com.tojc.minecraft.mod.ChatLoggerPlus.cfgファイルの設定に依存します。

   :param pluginName: プラグイン名
   :param message: ログに出力する文字列


Fields
**************************************************
name
==================================================

.. java:field:: public String name

    プラグインの名前を表す必須フィールドです。文字列型。
    プラグインは必ずこの名称でフィールドを定義しなければなりません。


version
==================================================

.. java:field:: public String version

    プラグインのバージョンを表す必須フィールドです。文字列型。
    プラグインは必ずこの名称でフィールドを定義しなければなりません。


description
==================================================

.. java:field:: public String description

    プラグインの説明を表す必須フィールドです。文字列型。
    プラグインは必ずこの名称でフィールドを定義しなければなりません。


auther
==================================================

.. java:field:: public String auther

    プラグインの作者名を表す必須フィールドです。文字列型。
    プラグインは必ずこの名称でフィールドを定義しなければなりません。


plugin
==================================================

.. java:field:: public PluginInterface plugin

    プラグインの本体を表す必須フィールドです。 :java:ref:`PluginInterface` を実装したオブジェクトを渡さなければなりません。
    プラグインは必ずこの名称でフィールドを定義しなければなりません。

