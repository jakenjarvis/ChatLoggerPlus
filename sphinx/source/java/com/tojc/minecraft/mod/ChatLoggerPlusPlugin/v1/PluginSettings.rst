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

.. java:method:: public void registerPermissionAddAfterMessage()
   :outertype: PluginSettings

   チャットメッセージを追加するパーミッションを要求します。 スクリプトで \ :java:ref:`ChatMessage.addAfterMessage(String)`\  を使用する場合は宣言が必要です。

registerPermissionMessageModification
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionMessageModification()
   :outertype: PluginSettings

   チャットメッセージを加工するパーミッションを要求します。 スクリプトで \ :java:ref:`ChatMessage.setMessage(String)`\  を使用する場合は宣言が必要です。

registerPermissionReadStack
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionReadStack(String keyname)
   :outertype: PluginSettings

   スタックを読み込むパーミッションを要求します。（実質、既に存在するスタックなら読み書きできます） スクリプトで \ :java:ref:`ChatMessage.readStack(String)`\  を使用する場合は宣言が必要です。

   :param keyname: アクセス対象のスタックから読み取るキー文字列

registerPermissionWriteStack
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public void registerPermissionWriteStack(String keyname)
   :outertype: PluginSettings

   スタックに書き込むパーミッションを要求します。（実質新規作成の意味合いです） スクリプトで \ :java:ref:`ChatMessage.writeStack(String,Object)`\  を使用する場合は宣言が必要です。

   :param keyname: アクセス対象のスタックに登録するキー文字列

