ChatLoggerPlus (Minecraft MOD)
==============
[Minecraft 非公式日本ユーザーフォーラム ChatLoggerPlus公開トピックへのリンク](http://forum.minecraftuser.jp/viewtopic.php?f=13&t=7929)

# MODの利用について
* このMODを利用する場合は、すべて自己責任でお願いいたします。
* 開発者は、いかなる損害、損失、個人情報の流出等に対しての責任は負いません。
* 利用者は、この条件「自己責任」に同意し、利用者がログの責任を持つものとします。

## ログファイルの取り扱いについて
「会話の内容を記録する」という性質上、ログファイルの取り扱いには十分ご注意ください。

※他者との会話の内容を、無断で公開すると、トラブルの原因になりますので自粛してください。
名誉棄損や個人情報流出、権利侵害といった問題に発展する可能性があります。

## 動画での紹介等について
特に制限を設けていませんので、ご自由にお使いください。

# 特徴
ChatLoggerPlusは、一般ユーザー向け、チャットログ保存MODです。  
自分がプレイしているときに、 **画面に表示された会話ログ** を、ファイルに出力します。  
「いつ、誰と、どんな会話をしたか」が記録に残るので、コミュニケーションの役に立ちます。  
また、プレイヤーアカウント名によって、フォルダを分けて保存することも出来るので、複数アカウントを使い分けてる方も利用できます。
（ただし、ログファイルは暗号化されていませんので、会話内容は丸見えです。ご注意ください）

# 動作環境
MinecraftForge (ModLoader, is not supported)

ChatLoggerPlusは、クライアント側に導入するMODになります。  
※サーバ側での会話ログ保存は、別のMODをご利用ください。動作確認はしておりません。

# インストール
通常のForge対応MODと同様にmodsフォルダへ入れてください。  
既存の処理を書き換えるようなことはしていませんので、スムーズに導入できるかと思います。

--------------------------------------------------
# 設定
com.tojc.minecraft.mod.ChatLoggerPlus.cfgファイルをテキストエディタ等で編集すると、動作を変更できます。  
※このcfgファイルを編集する場合は、Windows標準のメモ帳を使わないでください。読み込みエラーの原因になります。  

## general：一般設定

    general {
        # true/false
        B:PluginScriptsEnabled=false

        # The format of date and time on outputs to the beginning of the log.
        S:FormatDateTime=yyyy/MM/dd-HH:mm:ss
    }

### PluginScriptsEnabled：チャットログを解析するプラグインスクリプト処理の有効、無効切替
true/falseの２値選択です。trueだとスクリプトを処理し、falseだとログ出力のみ行います。
デフォルトはfalseになっていますので、プラグインスクリプトを利用する場合は、trueにしてください。

※falseの場合は、ChatLoggerPlus Ver0.x.x系と同等の処理となります。
※trueの場合は、後述のフォーマットコードの塗りつぶし処理は行われなくなります。（同様の処理はプラグインスクリプトで行ってください）

### FormatDateTime：ログ出力日時フォーマット
ログファイル内に出力する日時用フォーマットを指定します。
yyyy/MM/dd-HH:mm:ssといった、日時の出力内容をカスタマイズできます。  
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。

## relativepath：相対パス
「absolutepath 絶対パス」が有効な場合、この項目は無効になります。

    relativepath {
        # Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%
        S:DefaultReplaceLogFileFullPathFileName=chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

### DefaultReplaceLogFileFullPathFileName：デフォルトログファイル名（シンボル置換）
.minecraftフォルダからの相対パスで、保存するログファイル名を指定します。拡張子を含めて記述してください。フォルダの区切り記号は「/」で区切ってください。
また、この項目は、後述の「シンボル置換処理」が入ります。

## absolutepath：絶対パス

    absolutepath {
        # Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.
        S:EnforcementReplaceLogFileFullPathFileName=d:/chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

### EnforcementReplaceLogFileFullPathFileName：強制フルパスログファイル名（シンボル置換）
絶対パスでログファイルを指定します。フォルダの区切り記号は「/」で区切ってください。
この項目に値を入れると、相対パスの設定が無視されます。（絶対パスの設定が優先）
また、この項目は、後述の「シンボル置換処理」が入ります。

標準設定では、空になっており、相対パスが有効になっています。

## シンボル置換処理について
ファイル名を指定する場合、以下のキーワードを使うことによって、ある程度、動的に名前を決定できます。
%で括った以下のシンボルのみ利用できます。

* %SERVERNAME%  
サーバ名「（IPアドレスまたはドメイン名）:ポート番号」
* %WORLDNAME%  
ワールド名「ログインしたワールド名」※ローカルサーバの場合MpServerになってしまうようです。
* %PLAYERNAME%  
プレイヤー名「ログインしているユーザー名」
* %DATE%  
日付
* %TIME%  
時間

日付と時間は、後述の「ファイル名フォーマット」の指定に従って出力内容が置換されます。  
また、サーバにログイン（ワールドに接続）したときの日時を基準にファイル名が作成されます。
そのため、夜中0時を過ぎたからといって、ログファイルが切り替わるわけではありません。

## filenameformat：ファイル名フォーマット
yyyyMMdd、HHmmssといった、ファイル名で使う日付と時間の出力内容をカスタマイズできます。  
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。

    filenameformat {
        S:FormatReplaceDate=yyyyMMdd
        S:FormatReplaceTime=HHmmss
    }

### FormatReplaceDate：日付用フォーマット
シンボル置換処理で「%DATE%：日付」を指定した場合の出力フォーマットを指定します。

### FormatReplaceTime：時間用フォーマット
シンボル置換処理で「%TIME%：時間」を指定した場合の出力フォーマットを指定します。

## pluginscriptsdisabledgeneral：プラグインスクリプト無効時の一般設定
PluginScriptsEnabledがfalseの場合だけ、この設定全体が有効になります。
※注意：PluginScriptsEnabledがtrueの場合、FillFormattingCodesEnabledがtrueでも、フォーマットコードの塗りつぶし処理は行われません。

    pluginscriptsdisabledgeneral {
        # true/false, true=fill / false=Not modify
        B:FillFormattingCodesEnabled=false
        S:FillFormattingCodesRegex=§[0-9a-fk-or]
        S:FillFormattingCodesReplace=
    }

また、以前のChatLoggerPlus Ver0.x.x系に存在していた以下の一般設定とほぼ同じものです。  

    general {
        # true/false, true=fill / false=Not modify
        B:FillColorCodeEnabled=false
        S:FillColorCodeRegex=§[0-9a-fk-or]
        S:FillColorCodeReplace=
    }

これは、プラグインスクリプトを使わない場合でも、過去のバージョンと同じような処理が行えるようにするための救済措置です。  

### FillFormattingCodesEnabled：フォーマットコードの塗りつぶしの有効、無効切替
ログファイル出力前に、フォーマットコードを塗りつぶす処理を行うかどうかを切り替えます。
trueだと、塗りつぶしを行い、falseだと何もしません。  
※デフォルトでは、ログを加工しないように、「無効」になっています。

この処理は、以下のFillFormattingCodesRegexとFillFormattingCodesReplaceによって、内容を変更できます。

#### FillFormattingCodesRegex：塗りつぶし条件（正規表現）
※「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
通常はデフォルト設定のままで使用してください。

ここで指定した条件が、ログに出力する１行すべてで評価されます。
行頭の日時の出力部分に対しても、有効となっていますので注意してください。

#### FillFormattingCodesReplace：塗りつぶし文字列
※「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
通常はデフォルト設定のままで使用してください。

#### フォーマットコードとは
「文字の色を変更して表示するため」の「固定文字列」のことです。
たとえば、マルチで誰かに直接話しかけられた場合は、以下のようなメッセージが出力されます。

    例）
    2012/11/17-02:53:41 : §dFrom testuser§d: 今度二人で遊ぼうぜ！

FillFormattingCodesRegexとFillFormattingCodesReplaceの設定がデフォルトの場合、このメッセージは以下のように加工されます。

    例）
    2012/11/17-02:53:41 : From testuser: 今度二人で遊ぼうぜ！

このように、フォーマットコードがログに不要な場合、FillFormattingCodesEnabledをtrueにすると、ずいぶん見やすくなります。  
フォーマットコードにどのような種類があるのか知りたい場合は、[こちら](http://minecraft.gamepedia.com/Formatting_codes)を参照ください。  

## pluginsettings：プラグインスクリプト設定
プラグインスクリプトに関する設定項目です。

    pluginsettings {
        S:DefaultPluginDirectoryName=ChatLoggerPlusPlugins

        # Please do not modify.
        S:PluginOrderToChatLog <
         >

        # Please do not modify.
        S:PluginOrderToScreen <
         >
    }

### DefaultPluginDirectoryName：デフォルトプラグインフォルダ名
プラグインスクリプトを格納するフォルダ名を指定します。  
通常はデフォルト設定のままで使用してください。

### PluginOrderToChatLog：データ保存：プラグイン順序ChatLog用
基本的にChatLoggerPlusのデータ保存として使っていますので、手動で変更しないでください。

### PluginOrderToScreen：データ保存：プラグイン順序Screen用
基本的にChatLoggerPlusのデータ保存として使っていますので、手動で変更しないでください。

## outputofdebuglog：デバッグログの出力設定
標準出力へのデバッグログ出力を制御できます。主に開発用です。
通常はデフォルト設定のままで使用してください。

    outputofdebuglog {
        B:OutputLoggingTrace=false
        B:OutputLoggingWarning=true
        B:OutputLoggingError=true

        B:OutputLoggingMessageOriginal=false
        B:OutputLoggingMessageDuringChatLog=false
        B:OutputLoggingMessageDuringScreen=false
        B:OutputLoggingMessageLastChatLog=false
        B:OutputLoggingMessageLastScreen=false

        B:OutputLoggingScript=false
    }

※プラグインスクリプトを作成する方は、すべての設定をtrueにすると、デバッグしやすいでしょう。

--------------------------------------------------

# 更新履歴
[こちら](https://github.com/jakenjarvis/ChatLoggerPlus/commits/master)をご覧ください。

# 改造や再配布について
GPLライセンスに基づき、オープンソースとしてソースコードを公開しています。
ライセンスに従い、自由に改変し、公開してください。

## バージョン管理
githubにて、ソースコードの管理をしております。  
[github / ChatLoggerPlus](https://github.com/jakenjarvis/ChatLoggerPlus)  
希望者は、githubのCollaboratorsに追加し、リポジトリにアクセスできるようにしますので、githubアカウントを作成した上でご連絡ください。

手を入れた内容が、明らかにChatLoggerPlusとは別の物になる場合は、Forkしてそちらで別管理してください。
「ちょっと手を入れただけのもの」や「機能追加」であれば、遠慮なくリポジトリをいじって改変してください！  
（類似MODを乱立させたくはないので、ご協力お願いいたします）

## 共同開発者
開発に関与した方をここに記載していきます。  
[Jaken](https://github.com/jakenjarvis)

## License
[GNU General Public License Version 3](http://www.gnu.org/licenses/gpl.html)  
よくわからない場合は、[Wikipedia - GNU General Public License](http://ja.wikipedia.org/wiki/GPL)も参照ください。

# ダウンロード
[こちら](https://www.dropbox.com/sh/dfvb3hu7np7zv5c/6jZcMRxtZD)から、該当するファイルをダウンロードしてください。
※[Minecraft 非公式日本ユーザーフォーラム ChatLoggerPlus公開トピック](http://forum.minecraftuser.jp/viewtopic.php?f=13&t=7929)からのダウンロードのほうが、整理している分、わかりやすいかもしれません。

