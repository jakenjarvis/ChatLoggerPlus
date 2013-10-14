.. _plugin_dev_preparation:

プラグイン開発準備
##################################################

テキストエディタの準備
**************************************************
基本的にUTF-8のテキストファイルを正しく扱えるエディタであれば、なんでもOKです。

ただし、Windows標準の「メモ帳」は「BOM有り」で保存してしまうので、トラブルの原因になります。
どれを使えばいいか分からない場合は、秀丸エディタやSakuraエディタなどのテキストエディタを調べてみてください。


configの設定
**************************************************
「com.tojc.minecraft.mod.ChatLoggerPlus.cfg」の「outputofdebuglog」の項目を全てtrueにすることをお勧めします。

特にスクリプトを作る時は、以下の項目が重要です。

.. code-block:: ini

    OutputLoggingScript=true
    OutputLoggingMessageOriginal=true
    OutputLoggingMessageDuringChatLog=true
    OutputLoggingMessageDuringScreen=true
    OutputLoggingMessageLastChatLog=true
    OutputLoggingMessageLastScreen=true

Minecraftの起動方法
**************************************************
スクリプトからdebug.logメソッドで出力したログを見るために、実行時にログが閲覧できるようにします。

Windowsであれば、以下のようなバッチファイルを作り、このバッチファイルからMinecraftを起動するようにすると、ログが閲覧できます。
（以下は、Jakenが使っているバッチファイルの例です。それぞれの環境にあわせてパスやメモリ量を変更してください）

minecraft.cmd

.. code-block:: minecraft.cmd

    "C:\Program Files\Java\jre6\bin\java.exe" -Xms1024m -Xmx3072m -jar Minecraft.exe

※ここで出力されるログの種類は、上記「outputofdebuglog」の設定で決定されます。

