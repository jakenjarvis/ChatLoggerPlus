PluginInterface
===============

.. java:package:: com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1
   :noindex:

.. java:type:: public interface PluginInterface

   プラグインの骨格となるインターフェイスです。

   :author: Jaken

Methods
-------
onChatMessage
^^^^^^^^^^^^^

.. java:method:: public void onChatMessage(PluginEnvironment env, ChatMessage chat)
   :outertype: PluginInterface

   ここで、チャットのメッセージに対する処理を行います。 これは通常、ユーザーがチャット発言をしたとき、画面に表示する前に呼び出されます。 ここでのチャット編集は、ローカルのみに反映されます。※サーバへ送信されることはありません。

   :param env: 環境オブジェクト \ :java:ref:`PluginEnvironment`\
   :param chat: チャットオブジェクト \ :java:ref:`ChatMessage`\

onFinalize
^^^^^^^^^^

.. java:method:: public void onFinalize()
   :outertype: PluginInterface

   ここで、プラグインの終了処理を行います。 これは通常、サーバ切断時に呼び出されますが、タイミングを意識しない作りにしてください。

onInitialize
^^^^^^^^^^^^

.. java:method:: public void onInitialize(PluginSettings settings)
   :outertype: PluginInterface

   ここで、プラグインの初期化処理を行います。 これは通常、サーバ接続時に呼び出されますが、タイミングを意識しない作りにしてください。 パーミッションが必要なプラグインの場合は、ここで登録を行います。

   :param settings: 設定オブジェクト \ :java:ref:`PluginSettings`\

