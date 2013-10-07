.. _caution_object_type:

オブジェクト型に対する意識（重要）
##################################################
いくつかのサンプルスクリプトのソースを見ると、次のような処理を見かけるでしょう。

.. code-block:: javascript

    onChatMessage: function(env, chat)
    {
        // チャットメッセージを取得（別のプラグインで加工された可能性のあるメッセージ）
        var chatmessage = chat.getMessage();

        // 他のプラグインによって、チャットメッセージが削除されている場合は
        // nullになっているため、チェックしてから処理を行うようにする。
        if(chatmessage != null)
        {
            // JavaのStringからJavaScriptのStringに型変換（replaceを使うために）
            var message = String(chatmessage);

            // （省略）
        }
    },

ここで特に重要なのが、なんてことない、この１行です。

.. code-block:: javascript

    var message = String(chatmessage);

これは、 **Java言語の文字列型** と、 **JavaScript言語の文字列型** が異なるので、JavaScriptのString型に変換する処理になります。

まず、chat.getMessage()で取得した文字列は、Java言語のString型を取得します。
このままでは、JavaScriptのstring.replaceや、string.substring、string.splitといったメソッドが使えません。
そのため、JavaScriptのString型を生成することで、型を置き換えます。

このように、ところどころ、「オブジェクトの型が何か。JavaのオブジェクトかJavaScriptのオブジェクトか？」を意識する必要があります。

※逆にJava言語のString型としてそのまま使えるので、JavaScriptのメソッドにこだわらなければ、 **Javaのメソッドもスクリプト内から呼び出せます。** 
サンプルでは、「JavaScript使い」の方のために、できるだけJavaScript側で統一しています。

.. tip::
    Java言語とJavaScript言語は、同じJavaという言葉が使われているのですが、これはまったく異なるの別のプログラミング言語です。

.. tip::
    MinecraftもChatLoggerPlusもJava言語で作られたプログラムです。
    そんな中、ChatLoggerPlusはプラグインの機能を実現するために、Java言語の上でJavaScript言語を動かしています。
    これがJavaScriptのスクリプトの中で、Java言語のオブジェクトを意識しなければならない主な原因となっています。

