.. java:import:: java.util ArrayList

.. java:import:: java.util List

PluginSettings
==============

.. java:package:: com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1
   :noindex:

.. java:type:: public interface PluginSettings

   プラグインの設定や登録を行うためのインターフェイスです。

   :author: Jaken

Methods
-------
registerPermissionAddAfterMessage
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionAddAfterMessage(boolean adding)
   :outertype: PluginSettings

   チャットメッセージを追加するパーミッションを要求します。 スクリプトでaddAfterMessageを使用する場合は宣言が必要です。

   :param adding: パーミッションを要求する場合はtrueを指定してください。

registerPermissionMessageModification
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionMessageModification(boolean modification)
   :outertype: PluginSettings

   チャットメッセージを加工するパーミッションを要求します。 スクリプトでsetMessageを使用する場合は宣言が必要です。

   :param modification: パーミッションを要求する場合はtrueを指定してください。

registerPermissionReadStack
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionReadStack(String keyname)
   :outertype: PluginSettings

   スタックを読み込むパーミッションを要求します。（実質、既に存在するスタックなら読み書きできます） スクリプトでreadStackを使用する場合は宣言が必要です。

   :param keyname: アクセス対象のスタックから読み取るキー文字列

registerPermissionWriteStack
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionWriteStack(String keyname)
   :outertype: PluginSettings

   スタックに書き込むパーミッションを要求します。（実質新規作成の意味合いです） スクリプトでwriteStackを使用する場合は宣言が必要です。

   :param keyname: アクセス対象のスタックに登録するキー文字列

