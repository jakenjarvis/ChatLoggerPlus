.. _plugin_template:

JavaScriptによるプラグインテンプレート
##################################################
以下に、最小限のプラグインテンプレートを示します。
インターフェイスが認識できればいいので、これ以外にもいくつか書き方はあります。

.. literalinclude:: ../../SamplePlugin/template.js
    :language: javascript
    :linenos:
    :encoding: utf-8

このテンプレートをダウンロードする場合は、以下のtemplate.jsを右クリックして「名前を付けて保存」で保存してください。

:download:`template.js <../../SamplePlugin/template.js>` 


プラグイン骨格の説明
**************************************************

importPackage定義
==================================================
.. code-block:: javascript

    importPackage(com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1);

プラグインインターフェイスを定義しているパッケージ名を指定しています。
これは、このままおまじないとして固定で定義してください。


プラグインの基本情報の定義
==================================================
.. code-block:: javascript

    var name = "plugin name";
    var version = "1.0.0";
    var description = "plugin description";
    var auther = "your name";

プラグインの名前、バージョン、説明、作者名をここで定義します。
この値は利用者が画面上で確認できるようにするためのものです。

詳細は :ref:`global_value` も参照ください。


プラグインの本体の定義
==================================================
.. code-block:: javascript

    var plugin = new PluginInterface(){};

プラグインの処理を定義します。
ここには、 :java:ref:`PluginInterface` を実装したオブジェクトを渡さなければなりません。

インターフェイスの詳細は、 :java:ref:`PluginInterface` を参照してください。

インターフェイスを実装してさえいれば、異なる書き方でも構いません。（JavaScriptでは、いくつかの記述方法があります）


ログ出力について
**************************************************
.. code-block:: javascript

    debug.log(name, "ログに出力する内容");

デバッグ用のログ出力メソッドです。グローバルに定義されているため、どこからでも呼び出せます。
プラグイン基本情報のnameと、ログに出力したい文字を渡してください。

また、このメソッドは、com.tojc.minecraft.mod.ChatLoggerPlus.cfgファイルの設定を参照して、実際にログを出力するかを判断します。
プラグインを配布するときもdebug.logを記述したまま配布可能です。

※設定は、outputofdebuglogのOutputLoggingScriptをtrueにすることで、出力できるようになります。

.. note::
    一般公開前のDevelop版では、ログ出力をprintlnで行っていましたが、別途ログ出力用のdebug.logメソッドを用意しました。
    これは、config設定で出力の有無を設定できるようになっているため、利用者が出力の有無を切り替えることができます。
    このため、出来るだけdebug.logメソッドを使うようにしてください。

