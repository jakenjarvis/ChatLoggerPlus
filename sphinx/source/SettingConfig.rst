.. _setting_config:

configファイルの設定
##################################################
configフォルダの中にある、com.tojc.minecraft.mod.ChatLoggerPlus.cfgファイルをテキストエディタ等で編集すると、ChatLoggerPlusの動作を変更できます。

.. danger::
    このconfigファイルを編集する場合は、 **Windows標準のメモ帳** は絶対に使わないでください。読み込みエラーの原因になります。
    秀丸エディタやSakuraエディタなどのテキストエディタを使用し、UTF-8（BOM無し）で保存してください。


general
**************************************************

| 【一般設定】

generalという文字から始まり、括弧で括られた部分の設定です::

    general {
        # true/false
        B:PluginScriptsEnabled=false

        # The format of date and time on outputs to the beginning of the log.
        S:FormatDateTime=yyyy/MM/dd-HH:mm:ss
    }


PluginScriptsEnabled
==================================================

| 【チャットログを解析するプラグイン処理の有効、無効切替】

true/falseの２値選択です。trueだとプラグインを処理し、falseだとログ出力のみ行います。
デフォルトはfalseになっていますので、プラグインを利用する場合は、trueにしてください。

※falseの場合は、ChatLoggerPlus Ver0.x.x系と同等の処理となります。

※trueの場合は、後述のフォーマットコードの塗りつぶし処理は行われなくなります。（同様の処理はプラグインで行ってください）

FormatDateTime
==================================================

| 【ログ出力日時フォーマット】

ログファイル内に出力する日時用フォーマットを指定します。
yyyy/MM/dd-HH:mm:ssといった、日時の出力内容をカスタマイズできます。
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。


relativepath
**************************************************

| 【相対パス】

relativepathという文字から始まり、括弧で括られた部分の設定です::

    relativepath {
        # Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%
        S:DefaultReplaceLogFileFullPathFileName=chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

「absolutepath 絶対パス」が有効な場合、この項目は無効になります。

DefaultReplaceLogFileFullPathFileName
==================================================

| 【デフォルトログファイル名（シンボル置換）】

.minecraftフォルダからの相対パスで、保存するログファイル名を指定します。拡張子を含めて記述してください。フォルダの区切り記号は「/」で区切ってください。
また、この項目は、後述の「シンボル置換処理」が入ります。


absolutepath
**************************************************

| 【絶対パス】

absolutepathという文字から始まり、括弧で括られた部分の設定です::

    absolutepath {
        # Replace : %SERVERNAME% %WORLDNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.
        S:EnforcementReplaceLogFileFullPathFileName=d:/chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

EnforcementReplaceLogFileFullPathFileName
==================================================

| 【強制フルパスログファイル名（シンボル置換）】

絶対パスでログファイルを指定します。フォルダの区切り記号は「/」で区切ってください。
この項目に値を入れると、相対パスの設定が無視されます。（絶対パスの設定が優先）
また、この項目は、後述の「シンボル置換処理」が入ります。

標準設定では、空になっており、相対パスが有効になっています。


シンボル置換処理について
**************************************************
ファイル名を指定する場合、以下のキーワードを使うことによって、ある程度、動的に名前を決定できます。
%で括った以下のシンボルのみ利用できます。

    * %SERVERNAME%
        サーバ名「（IPアドレスまたはドメイン名）:ポート番号」
    * %WORLDNAME%
        ワールド名「ログインしたワールド名」
    * %PLAYERNAME%
        プレイヤー名「ログインしているユーザー名」
    * %DATE%
        日付
    * %TIME%
        時間

日付と時間は、後述の「ファイル名フォーマット」の指定に従って出力内容が置換されます。
また、サーバにログイン（ワールドに接続）したときの日時を基準にファイル名が作成されます。
そのため、夜中0時を過ぎたからといって、ログファイルが切り替わるわけではありません。

.. note::
    ローカルサーバの場合、%SERVERNAME%が固定で「MpServer」になってしまうため、ChatLoggerPlusはこれを「LocalServer」に置き換える処理を行っています。


filenameformat
**************************************************

| 【ファイル名フォーマット】

filenameformatという文字から始まり、括弧で括られた部分の設定です::

    filenameformat {
        S:FormatReplaceDate=yyyyMMdd
        S:FormatReplaceTime=HHmmss
    }

yyyyMMdd、HHmmssといった、ファイル名で使う日付と時間の出力内容をカスタマイズできます。
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。

FormatReplaceDate
==================================================

| 【日付用フォーマット】

シンボル置換処理で「%DATE%：日付」を指定した場合の出力フォーマットを指定します。

FormatReplaceTime
==================================================

| 【時間用フォーマット】

シンボル置換処理で「%TIME%：時間」を指定した場合の出力フォーマットを指定します。

pluginscriptsdisabledgeneral
**************************************************

| 【プラグイン無効時の一般設定】

pluginscriptsdisabledgeneralという文字から始まり、括弧で括られた部分の設定です::

    pluginscriptsdisabledgeneral {
        # true/false, true=fill / false=Not modify
        B:FillFormattingCodesEnabled=false
        S:FillFormattingCodesRegex=§[0-9a-fk-or]
        S:FillFormattingCodesReplace=
    }

.. attention::
    PluginScriptsEnabledが **falseの場合だけ** 、この設定全体が有効になります。

.. note::
    これは、以前のChatLoggerPlus Ver0.x.x系に存在していた、以下の一般設定とほぼ同じものです。
    プラグインを使わない場合でも、過去のバージョンと同じような処理が行えるようにするための救済措置です。

    ChatLoggerPlus Ver0.x.x系の一般設定です::

        general {
            # true/false, true=fill / false=Not modify
            B:FillColorCodeEnabled=false
            S:FillColorCodeRegex=§[0-9a-fk-or]
            S:FillColorCodeReplace=
            （・・・省略・・・）
        }


FillFormattingCodesEnabled
==================================================

| 【フォーマットコードの塗りつぶしの有効、無効切替】

ログファイル出力前に、フォーマットコードを塗りつぶす処理を行うかどうかを切り替えます。
trueだと、塗りつぶしを行い、falseだと何もしません。
※デフォルトでは、ログを加工しないように、「無効」になっています。

この処理は、以下のFillFormattingCodesRegexとFillFormattingCodesReplaceによって、内容を変更できます。

.. attention::
    PluginScriptsEnabledがtrueの場合、FillFormattingCodesEnabledがtrueでも、フォーマットコードの塗りつぶし処理は行われません。


フォーマットコードとは
--------------------------------------------------
「文字の色を変更して表示するため」の「固定文字列」のことです。
たとえば、マルチで誰かに直接話しかけられた場合は、以下のようなメッセージが出力されます::

    2012/11/17-02:53:41 : §dFrom testuser§d: 今度二人で遊ぼうぜ！

FillFormattingCodesRegexとFillFormattingCodesReplaceの設定がデフォルトの場合、このメッセージは以下のように加工されます::

    2012/11/17-02:53:41 : From testuser: 今度二人で遊ぼうぜ！

このように、フォーマットコードがログに不要な場合、FillFormattingCodesEnabledをtrueにすると、ずいぶん見やすくなります。
フォーマットコードにどのような種類があるのか知りたい場合は、 `こちら <http://minecraft.gamepedia.com/Formatting_codes>`_ を参照ください。


FillFormattingCodesRegex
==================================================

| 【塗りつぶし条件（正規表現）】

ここで指定した条件が、ログに出力する１行すべてで評価されます。
行頭の日時の出力部分に対しても、有効となっていますので注意してください。

.. warning::
    「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
    通常はデフォルト設定のままで使用してください。

FillFormattingCodesReplace
==================================================

| 【塗りつぶし文字列】

正規表現の条件に一致した文字と置換する文字を指定します。通常は空欄にします。

.. warning::
    「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
    通常はデフォルト設定のままで使用してください。


pluginsettings
**************************************************

| 【プラグイン設定】

pluginsettingsという文字から始まり、括弧で括られた部分の設定です::

    pluginsettings {
        S:DefaultPluginDirectoryName=ChatLoggerPlusPlugins

        # Please do not modify.
        S:PluginOrderToChatLog <
         >

        # Please do not modify.
        S:PluginOrderToScreen <
         >
    }

これは、プラグインに関する設定項目です。

DefaultPluginDirectoryName
==================================================

| 【デフォルトプラグインフォルダ名】

プラグインを格納するフォルダ名を指定します。
通常はデフォルト設定のままで使用してください。

PluginOrderToChatLog
==================================================

| 【データ保存：プラグイン順序ChatLog用】

基本的にChatLoggerPlusのデータ保存として使っていますので、手動で変更しないでください。

PluginOrderToScreen
==================================================

| 【データ保存：プラグイン順序Screen用】

基本的にChatLoggerPlusのデータ保存として使っていますので、手動で変更しないでください。


outputofdebuglog
**************************************************

| 【デバッグログの出力設定】

outputofdebuglogという文字から始まり、括弧で括られた部分の設定です::

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

標準出力へのデバッグログ出力を制御できます。主に開発用です。
通常はデフォルト設定のままで使用してください。

.. hint::
    プラグインを作成する方は、すべての設定をtrueにすると、デバッグしやすいでしょう。


