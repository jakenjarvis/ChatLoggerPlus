.. _formatting_codes_remover:

FormattingCodesRemover：カラーコード削除
##################################################
メッセージ中にある、カラーコードを除外するためのプラグインです。
機能としては、configのFillFormattingCodesEnabledをtrueにした時の処理と同等です。

特に設定等はありませんので、Addボタンで追加すれば使うことができます。

Screenプラグインとしても使えますが、ChatLogプラグインとして使用することをお勧めします。

フォーマットコードにどのような種類があるのか知りたい場合は、 `こちら <http://minecraft.gamepedia.com/Formatting_codes>`_ を参照ください。


ダウンロード
**************************************************
このプラグインをダウンロードする場合は、以下のFormattingCodesRemover.jsを右クリックして「名前を付けて保存」で保存してください。

:download:`FormattingCodesRemover.js <../../../SamplePlugin/FormattingCodesRemover.js>` 


ソースコード
**************************************************
このプラグインは、チャット文字列を加工するプラグインとして、非常にシンプルで分かりやすい内容となっています。
プラグインの作成にチャレンジする方は、是非、処理内容を確認してみてください。

.. literalinclude:: ../../../SamplePlugin/FormattingCodesRemover.js
    :language: javascript
    :linenos:
    :encoding: utf-8

