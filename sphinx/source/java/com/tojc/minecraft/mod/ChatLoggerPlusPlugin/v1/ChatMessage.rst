ChatMessage
===========

.. java:package:: com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1
   :noindex:

.. java:type:: public interface ChatMessage

   チャットメッセージに対する操作を行うためのインターフェイスです。

   :author: Jaken

Methods
-------
addAfterMessage
^^^^^^^^^^^^^^^

.. java:method:: public void addAfterMessage(String message)
   :outertype: ChatMessage

   チャットメッセージを追加します。 追加したメッセージは、処理中のメッセージが表示された後、順番に出力されます。 これは、ローカルチャットへ追加するだけで、サーバに送られることはありません。（利用者だけが見える） 注意：これを実現するためにチャットメッセージを横取りする必要があるので、他のチャット処理MODと競合する可能性があります。 このメソッドを使用するためには、パーミッションAddAfterMessageが必要です。

   :param message: チャットメッセージ文字列

getMessage
^^^^^^^^^^

.. java:method:: public String getMessage()
   :outertype: ChatMessage

   チャットメッセージを取得します。 このプラグインが処理する前に、別のプラグインによって加工されている場合は、その内容が反映された状態で渡されます。 ※取得できるチャットメッセージにはユーザー名を含みません。 ※他のプラグインによって、チャットメッセージが削除されている場合、nullが返却される可能性があります。 このためnullチェックを必ず行ってください。

   :return: チャットメッセージ文字列

getMessageOriginal
^^^^^^^^^^^^^^^^^^

.. java:method:: public String getMessageOriginal()
   :outertype: ChatMessage

   オリジナルのチャットメッセージを取得します。 他のプラグインの影響を受けずに、オリジナルのメッセージがほしい場合に使います。 ※取得できるチャットメッセージにはユーザー名を含みません。

   :return: チャットメッセージ文字列

getOriginalJsonString
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String getOriginalJsonString()
   :outertype: ChatMessage

   オリジナルのJSONメッセージを取得します。 （Minecraft1.6以降でチャットメッセージの扱いが大きく変わっています）

   :return: JSON形式の文字列

getUserName
^^^^^^^^^^^

.. java:method:: public String getUserName()
   :outertype: ChatMessage

   発言したユーザー名を取得します。 メッセージがユーザーによる発言ではない場合はnullが返却されます。 （Minecraft1.6以降で確実にユーザー名が取得できるようになり、メッセージと分断可能になりました）

   :return: ユーザー名の文字列

readStack
^^^^^^^^^

.. java:method:: public Object readStack(String keyname)
   :outertype: ChatMessage

   スタックから指定のキー文字列に関連付けされているオブジェクトを取得します。 このメソッドを使うプラグインよりも前に、別のプラグインの \ :java:ref:`ChatMessage.writeStack(String,Object)`\  によって スタックにあらかじめ値が登録されている必要があります。 このメソッドを使用するためには、パーミッションReadStackが必要です。

   :param keyname: スタックから受け取るキー文字列
   :return: キー文字列に登録されている値 ※null、文字列、数値、配列やクラスオブジェクトなどが受け取れます。

setMessage
^^^^^^^^^^

.. java:method:: public void setMessage(String message)
   :outertype: ChatMessage

   チャットメッセージを設定します。 ※nullをセットすると、チャットの発言自体を削除できますが、nullを想定していない他のチャット処理MODと競合する可能性があります。 このメソッドを使用するためには、パーミッションMessageModificationが必要です。

   :param message: チャットメッセージ文字列

writeStack
^^^^^^^^^^

.. java:method:: public void writeStack(String keyname, Object value)
   :outertype: ChatMessage

   スタックへ指定のキー文字列に関連付けした任意のオブジェクトをセットします。 任意のキー文字列を指定できます。 指定したキー文字列を知るプラグインは \ :java:ref:`ChatMessage.readStack(String)`\  にて値を受け取ることができます。 このメソッドを使用するためには、パーミッションWriteStackが必要です。

   :param keyname: スタックに登録するキー文字列
   :param value: スタックに登録する値 ※null、文字列、数値、配列やクラスオブジェクトなども渡せます。

