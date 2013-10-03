PluginEnvironment
=================

.. java:package:: com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1
   :noindex:

.. java:type:: public interface PluginEnvironment

   環境情報へアクセスするためのインターフェイスです。

   :author: Jaken

Methods
-------
getPlayerName
^^^^^^^^^^^^^

.. java:method:: public String getPlayerName()
   :outertype: PluginEnvironment

   現在プレイ中のユーザー名を取得します。

   :return: プレイヤー名文字列

getServerName
^^^^^^^^^^^^^

.. java:method:: public String getServerName()
   :outertype: PluginEnvironment

   サーバ名を取得します。 ※サーバによってはIPアドレスの場合があります。

   :return: サーバ名文字列

getWorldName
^^^^^^^^^^^^

.. java:method:: public String getWorldName()
   :outertype: PluginEnvironment

   ワールド名を取得します。

   :return: ワールド名文字列

