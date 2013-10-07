.. _download:

ダウンロード
##################################################
MinecraftForgeのバージョンは、動作確認しているバージョンです。前後のバージョンでも動作する可能性が非常に高いです。
（ただし、明記しているForgeバージョン以外での動作確認はしておりません）


ChatLoggerPlus v0.x.x系からv1.x.x系にアップデートする方へ注意
********************************************************************************
メジャーバージョンアップに伴い、configの設定項目の見直しを行いました。廃止・追加した項目、名称を変更した項目があります。
以前にChatLoggerPlus v0.x.x系をお使いの方は、以下の手順で導入してください。

 #. com.tojc.minecraft.mod.ChatLoggerPlus.cfgをバックアップ後、configファイルを削除し、Minecraftを起動する。（新しいconfigファイルが生成されます）
 #. バックアップを元に、config設定を移植する。
    この時、FillColorCodeEnabledを使っていた方は要注意です。必ず :ref:`setting_config` のpluginscriptsdisabledgeneralを参照してください。

特に設定を変更していなかった方も、 **configファイルを削除してから** ChatLoggerPlus v1.x.x系を導入することをお勧めします。（古い設定が残る可能性があります）

ChatLoggerPlus v1.x.x系から新しく使い始める方は、特に何も意識せずインストールして使うことができます。


ChatLoggerPlus v0.1.6をお使いの方へ注意
**************************************************
ChatLoggerPlus v0.1.6で修正した部分で、他のMODと競合する問題が発生しました。
このため、競合しないように回避したバージョン「ChatLoggerPlus v0.1.6a」を急きょ用意いたしました。
詳しくは `こちら <http://forum.minecraftuser.jp/viewtopic.php?f=13&t=7929&start=40#p125146>`_ のDesrulerさんの書き込み以降をご覧ください。

他にも、競合するMODを見つけた方は、是非ご報告ください。
※1.6.1用は用意していませんが、需要があるようでしたら準備しますので、お気軽に声をかけてください。（でも、たぶん、1.6.2用がそのまま動く気がします）


Minecraft 1.5.1と1.5.2をお使いの方へ注意
**************************************************
ChatLoggerPlus v0.1.4では、Forgeのバージョンによっては、「ネザーなどのワールド移動後に「ChatLoggerPlus: Logging start.」が発言の度に出力される。」という不具合が発生する場合があります。
これはForgeの挙動変更による影響です。
この場合は、1.5.1をプレイ中の方も、1.5.2用の「ChatLoggerPlus v0.1.5 Minecraft 1.5.2 / Forge 7.8.0.684(srg)」を利用してください。
srgビルドですので、そのまま動作するかと思います。（未確認なので、動作確認された方は報告頂けると助かります）

※以下は、ゲストさん、ルーターさん、森林軽風さんにご報告頂きました。ありがとうございます！

    [発生した環境]※この環境では、[ChatLoggerPlus v0.1.5 Minecraft 1.5.2 / Forge 7.8.0.684(srg)]を使用してください。
        * minecraftforge-universal-1.5.2-7.8.0.688
        * minecraftforge-universal-1.5.2-7.8.0.684
        * minecraftforge-universal-1.5.1-7.7.2.682

    [発生しなかった環境]
        * minecraftforge-universal-1.5.1-7.7.1.639

上記の結果から、639から682の間のどこかに、境目があります。（こちらでは特定してません）

※v0.1.4とv0.1.5で機能の差はありません。


Minecraft 1.4.5をお使いの方へ注意
**************************************************
Forge 6.4.0.397とForge 6.4.2.448の間で、Forgeの作りが変わっているため、ChatLoggerPlusも影響を受けています。
導入しているForgeのバージョンが448に近い場合は、極力v0.1.3を使ってください。（v0.1.2でも動作するかもしれませんが非推奨）
逆に、Forgeのバージョンが397に近い場合は、v0.1.2を使ってください。

※v0.1.2とv0.1.3で機能の差はありません。

    Forge448でv0.1.2の作りだと、使っていたイベントの挙動が変更されてしまったため、発言の度にログファイルへのOpen/Closeが行われ、非効率的な動作をしてしまいます。
    Forge467では再現しなかったため、一時的なForgeの不具合の可能性もありますが、v0.1.3では処理方法を変更し、Forge448以降の作りに最適化しています。


通常版ダウンロード
**************************************************

.. csv-table:: ChatLoggerPlus v1.0.0
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.6.4", "9.11.0.883", `[v100_mc164_forge9110883_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100_mc164_forge9110883_srg.zip>`_ , "2013/10/00", "上記注意参照"
    "1.6.2", "9.10.1.871", `[v100_mc162_forge9101871_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100_mc162_forge9101871_srg.zip>`_ , "2013/10/00", "上記注意参照"

.. csv-table:: ChatLoggerPlus v0.1.6a `[README v0.1.6a] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v016_mc162_forge9100804_srg/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.6.2", "9.10.0.804", `[v016a_mc162_forge9100804_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v016a_mc162_forge9100804_srg.zip>`_ , "2013/09/02", "上記注意参照 MOD競合回避版"

.. csv-table:: ChatLoggerPlus v0.1.6 `[README v0.1.6] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v016_mc162_forge9100804_srg/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.6.2", "9.10.0.804", `[v016_mc162_forge9100804_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v016_mc162_forge9100804_srg.zip>`_ , "2013/08/31", "上記注意参照"
    "1.6.1", "8.9.0.775", `[v016_mc161_forge890775_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v016_mc161_forge890775_srg.zip>`_ , "2013/08/31", "上記注意参照"

.. csv-table:: ChatLoggerPlus v0.1.5 `[README v0.1.5] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v015_mc152_forge780684_srg/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.5.2", "7.8.0.684", `[v015_mc152_forge780684_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v015_mc152_forge780684_srg.zip>`_ , "2013/05/13", "上記注意参照"

.. csv-table:: ChatLoggerPlus v0.1.4 `[README v0.1.4] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v014_mc147_forge662534/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.5.2", "7.8.0.684", `[v014_mc152_forge780684_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v014_mc152_forge780684_srg.zip>`_ , "2013/05/11", "不具合あり"
    "1.5.1", "7.7.0.605", `[v014_mc151_forge770605.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v014_mc151_forge770605.zip>`_ , "2013/03/24", "上記注意参照"
    "1.5.0", "7.7.0.582", `[v014_mc150_forge770582.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v014_mc150_forge770582.zip>`_ , "2013/03/20", ""
    "1.5.0(pre)", "7.7.0.569", `[v014_mc150_forge770569.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v014_mc150_forge770569.zip>`_ , "2013/03/12", ""
    "1.4.7", "6.6.2.534", `[v014_mc147_forge662534.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v014_mc147_forge662534.zip>`_ , "2013/03/12", ""

.. csv-table:: ChatLoggerPlus v0.1.3 `[README v0.1.3] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v013_mc147_forge660497/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.4.7", "6.6.0.497", `[v013_mc147_forge660497.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v013_mc147_forge660497.zip>`_ , "2013/01/12", ""
    "1.4.6", "6.5.0.467", `[v013_mc146_forge650467.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v013_mc146_forge650467.zip>`_ , "2012/12/23", ""
    "1.4.5", "6.4.2.448", `[v013_mc145_forge642448.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v013_mc145_forge642448.zip>`_ , "2012/12/23", "上記注意参照"

.. csv-table:: ChatLoggerPlus v0.1.2 `[README v0.1.2] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v012_mc145_forge640397/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.4.5", "6.4.0.397", `[v012_mc145_forge640397.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v012_mc145_forge640397.zip>`_ , "2012/12/11", "上記注意参照"
    "1.4.4", "6.3.0.378", `[v012_mc144_forge630378.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v012_mc144_forge630378.zip>`_ , "2012/12/11", ""
    "1.4.2", "6.0.1.355", `[v012_mc142_forge601355.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v012_mc142_forge601355.zip>`_ , "2012/12/11", ""


.. warning::
    以下は、「チャット文字に％を含むと落ちる」という致命的な不具合があります。

.. csv-table:: ChatLoggerPlus v0.1.1 `[README v0.1.1] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v011_mc145_forge640394/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.4.5", "6.4.0.397", `[v011_mc145_forge640397.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v011_mc145_forge640397.zip>`_ , "2012/11/27", "不具合あり"
    "1.4.5", "6.4.0.394", `[v011_mc145_forge640394.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v011_mc145_forge640394.zip>`_ , "2012/11/21", "不具合あり"
    "1.4.4", "6.3.0.378", `[v011_mc144_forge630378.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v011_mc144_forge630378.zip>`_ , "2012/11/21", "不具合あり"
    "1.4.2", "6.0.1.355", `[v011_mc142_forge601355.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v011_mc142_forge601355.zip>`_ , "2012/11/21", "不具合あり"


.. csv-table:: ChatLoggerPlus v0.1.0 `[README] <https://github.com/jakenjarvis/ChatLoggerPlus/blob/ChatLoggerPlus_v010_mc144_forge630372/README.md>`_ 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.4.4", "6.3.0.378", `[v010_mc144_forge630378.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v010_mc144_forge630378.zip>`_ , "2012/11/18", "不具合あり"
    "1.4.4", "6.3.0.372", `[v010_mc144_forge630372.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v010_mc144_forge630372.zip>`_ , "2012/11/15", "不具合あり"
    "1.4.2", "6.0.1.355", `[v010_mc142_forge601355.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v010_mc142_forge601355.zip>`_ , "2012/11/15", "不具合あり"


開発版ダウンロード
**************************************************
以下は、開発版です。これはv0.x.x系の頃にプリリリース版として配布したものです。

.. note::
    以下は通常版v1.0.0にて統合しました。また、SamplePluginはAPI仕様を変更したため、通常版v1.0.0では使えません。

.. csv-table:: ChatLoggerPlus **Develop** v1.0.0
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "1.5.2", "7.8.0.684", `[v100dev20130513_mc152_forge780684_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100dev20130513_mc152_forge780684_srg.zip>`_ , "2013/05/13", ""
    "1.5.2", "7.8.0.684", `[v100dev20130511_mc152_forge780684_srg.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100dev20130511_mc152_forge780684_srg.zip>`_ , "2013/05/11", "不具合あり"
    "1.5.1", "7.7.0.605", `[v100dev20130324_mc151_forge770605.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100dev20130324_mc151_forge770605.zip>`_ , "2013/03/24", ""
    "1.5.0", "7.7.0.582", `[v100dev20130324_mc150_forge770582.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/ChatLoggerPlus_v100dev20130324_mc150_forge770582.zip>`_ , "2013/03/24", ""

.. csv-table:: ChatLoggerPlus **Develop v1.0.0用 SamplePlugin** 
    :header: "Minecraft", "Forge", "FILE", "DATE", "備考"
    :widths: 5, 15, 45, 5, 30

    "", "",  `[20130328SamplePluginPack.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/20130328SamplePluginPack.zip>`_ , "2013/03/28", "SamplePluginPack 20130328"
    "", "",  `[minerabot101.zip] <http://jakenjarvis.github.io/ChatLoggerPlus/release/minerabot101.zip>`_ , "2013/04/19", "MiNeRa bot v1.0.1"


