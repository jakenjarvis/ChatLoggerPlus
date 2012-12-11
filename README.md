ChatLoggerPlus (Minecraft MOD)
==============
[Minecraft 非公式日本ユーザーフォーラム ChatLoggerPlus公開トピックへのリンク](http://forum.minecraftuser.jp/viewtopic.php?f=13&t=7929)

# MODの利用について
* このMODを利用する場合は、すべて自己責任でお願いいたします。
* 開発者は、いかなる損害、損失、個人情報の流出等に対しての責任は負いません。
* 利用者は、この条件「自己責任」に同意し、利用者がログの責任を持つものとします。

# ログファイルの取り扱いについて
「会話の内容を記録する」という性質上、ログファイルの取り扱いには十分ご注意ください。

※他者との会話の内容を、無断で公開すると、トラブルの原因になりますので自粛してください。
名誉棄損や個人情報流出、権利侵害といった問題に発展する可能性があります。

# 特徴
ChatLoggerPlusは、一般ユーザー向け、チャットログ保存MODです。  
自分がプレイしているときに、 **画面に表示された会話ログ** を、ファイルに出力します。  
「いつ、誰と、どんな会話をしたか」が記録に残るので、コミュニケーションの役に立ちます。  
また、プレイヤーアカウント名によって、フォルダを分けて保存することも出来るので、複数アカウントを使い分けてる方も利用できます。
（ただし、ログファイルは暗号化されていませんので、会話内容は丸見えです。ご注意ください）

# Require 動作環境
MinecraftForge (ModLoader, is not supported)

ChatLoggerPlusは、クライアント側に導入するMODになります。  
※サーバ側での会話ログ保存は、別のMODをご利用ください。

# インストール
他のサイトを参考に、通常のForge対応MODと同様に導入してください。  
既存の処理を書き換えるようなことはしていませんので、スムーズに導入できるかと思います。

# 動画での紹介等について
特に制限を設けていませんので、ご自由にお使いください。

--------------------------------------------------
# Configuration 設定
com.tojc.minecraft.mod.ChatLoggerPlus.cfgファイルをテキストエディタ等で編集すると、動作を変更できます。  

## general 一般設定

    general {
       # true/false
       ChatLoggerEnabled=true
       # true/false, true=fill / false=Not modify
       FillColorCodeEnabled=true
       FillColorCodeRegex=§[0-9a-f]
       FillColorCodeReplace=
       FormatDateTime=yyyy/MM/dd-HH:mm:ss
    }

### ChatLoggerEnabled チャットログの有効、無効切替
true/falseの２値選択です。trueだと、ログを出力し、falseだと、何もしません。
MODを外さずに、ログ出力をOFFにしたい場合は、falseにします。
通常は常にtrueを指定してください。

### FormatDateTime ログ出力日時フォーマット
ログファイル内に出力する日時用フォーマットを指定します。
yyyy/MM/dd-HH:mm:ssといった、日時の出力内容をカスタマイズできます。  
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。

### FillColorCodeEnabled カラーコードの塗りつぶしの有効、無効切替
ログファイル出力前に、カラーコードを塗りつぶす処理を行うかどうかを切り替えます。
trueだと、塗りつぶしを行い、falseだと何もしません。  
※デフォルトでは、ログを加工しないように、「無効」になっています。

この処理は、以下のFillColorCodeRegexとFillColorCodeReplaceによって、内容を変更できます。

#### FillColorCodeRegex 塗りつぶし条件（正規表現）
※「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
通常はデフォルト設定のままで使用してください。

ここで指定した条件が、ログに出力する１行すべてで評価されます。
行頭の日時の出力部分に対しても、有効となっていますので注意してください。

#### FillColorCodeReplace 塗りつぶし文字列
※「正規表現」が何かわからない場合、この項目を触らないようにしてください。ログを破損する原因になります。
通常はデフォルト設定のままで使用してください。

#### カラーコードとは
「文字の色を変更して表示するため」の「固定文字列」のことです。
たとえば、マルチで誰かに直接話しかけられた場合は、以下のようなメッセージが出力されます。

    例）
    2012/11/17-02:53:41 : §dFrom testuser§d: 今度二人で遊ぼうぜ！

FillColorCodeRegexとFillColorCodeReplaceの設定がデフォルトの場合、このメッセージは以下のように加工されます。

    例）
    2012/11/17-02:53:41 : From testuser: 今度二人で遊ぼうぜ！

このように、カラーコードがログに不要な場合、FillColorCodeEnabledをtrueにすると、ずいぶん見やすくなります。  

## relativepath 相対パス
「absolutepath 絶対パス」が有効な場合、この項目は無効になります。

    relativepath {
       # Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%
       DefaultReplaceLogFileFullPathFileName=chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

### DefaultReplaceLogFileFullPathFileName デフォルトログファイル名（シンボル置換）
.minecraftフォルダからの相対パスで、保存するログファイル名を指定します。拡張子を含めて記述してください。フォルダの区切り記号は「/」で区切ってください。
また、この項目は、後述の「シンボル置換処理」が入ります。

## absolutepath 絶対パス

    absolutepath {
       # Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%  * If null, the relativepath is used.
       EnforcementReplaceLogFileFullPathFileName=d:/chatlog/%SERVERNAME%/%PLAYERNAME%/ChatLog_%DATE%.log
    }

### EnforcementReplaceLogFileFullPathFileName 強制フルパスログファイル名（シンボル置換）
絶対パスでログファイルを指定します。フォルダの区切り記号は「/」で区切ってください。
この項目に値を入れると、相対パスの設定が無視されます。（絶対パスの設定が優先）
また、この項目は、後述の「シンボル置換処理」が入ります。

標準設定では、空になっており、相対パスが有効になっています。

## Replace シンボル置換処理
ファイル名を指定する場合、以下のキーワードを使うことによって、ある程度、動的に名前を決定できます。
%で括った以下のシンボルのみ利用できます。

* %SERVERNAME%  
サーバ名「（IPアドレスまたはドメイン名）:ポート番号」
* %PLAYERNAME%  
プレイヤー名「ログインしているユーザー名」
* %DATE%  
日付
* %TIME%  
時間

日付と時間は、後述の「ファイル名フォーマット」の指定に従って出力内容が置換されます。  
また、サーバにログイン（ワールドに接続）したときの日時を基準にファイル名が作成されます。
そのため、夜中0時を過ぎたからといって、ログファイルが切り替わるわけではありません。

## filenameformat ファイル名フォーマット
yyyyMMdd、HHmmssといった、ファイル名で使う日付と時間の出力内容をカスタマイズできます。  
書式は、javaのString#format()に準じます。
詳しくは、「java format 書式」あたりをキーワードに検索してみてください。

    filenameformat {
       FormatReplaceDate=yyyyMMdd
       FormatReplaceTime=HHmmss
    }

### FormatReplaceDate 日付用フォーマット
シンボル置換処理で「%DATE%：日付」を指定した場合の出力フォーマットを指定します。

### FormatReplaceTime 時間用フォーマット
シンボル置換処理で「%TIME%：時間」を指定した場合の出力フォーマットを指定します。

--------------------------------------------------

# History 更新履歴
[こちら](https://github.com/jakenjarvis/ChatLoggerPlus/commits/master)をご覧ください。

# 改造や再配布について
GPLライセンスに基づき、オープンソースとしてソースコードを公開しています。
ライセンスに従い、自由に改変し、公開してください。

MODは、性質上「作って公開して終わり」ではありません。
MinecraftやModLoader、MinecraftForgeは、常にバージョンアップしていきます。
公開したMODを更新しなければ、困るのは愛用してくれている利用者です。
ですが、MOD開発者がMinecraftから離れたり、仕事が忙しかったり、モチベーションが維持できなくなったり・・・更新できなくなる理由はたくさんあります。

他の「MOD開発できる人」が「ちょっと手を入れる」だけで、新しいバージョンに対応できる場合もあるでしょう。
育ちきったMODを真似て新しく１から作っても、品質が異なる類似MODが乱立するだけです。（日本語チャットMODとか・・・ﾁﾗｯ）  
１つのMODを、力を合わせていいMODに育てればいいじゃありませんか。

そんな考え方を他のMOD開発者にもしてほしくて、一石を投じるつもりでGPLにしてみました。
たとえ私が手を入れなくなっても、長く使われる定番MODになることを期待しています。

こんな思いを込めたので、「ChatLogger」＋「何か」が名前の由来です。
この「何か」を埋めるのはあなたかもしれません。

## MOD開発者の方へ。「明確なライセンスでオープンソース開発にしませんか？」
ただ、ソースコードを見せることだけが「オープンソース」ではありません。
「ソースコードのライセンスを明確にする」ということは、「このソースコードをこのように扱ってほしい」と宣言するためのものです。
また、上記のように、利用者に「MODを使い続けられる可能性」を示すこともできます。  
何より、後続のMOD開発者に知識を与えるチャンスにもなりますので、是非、手元のMODを「ライセンスを明確に宣言したオープンソース開発」にすることをご検討ください。

## バージョン管理
githubにて、ソースコードの管理をしております。  
[github / ChatLoggerPlus](https://github.com/jakenjarvis/ChatLoggerPlus)  
希望者は、githubのCollaboratorsに追加し、リポジトリにアクセスできるようにしますので、githubアカウントを作成した上でご連絡ください。

手を入れた内容が、明らかにChatLoggerPlusとは別の物になる場合は、Forkしてそちらで別管理してください。
「ちょっと手を入れただけのもの」や「機能追加」であれば、遠慮なくリポジトリをいじって改変してください！  
（類似MODを乱立させたくはないので、ご協力お願いいたします）

## Collaborators
開発に関与した方をここに記載していきます。  
[Jaken](https://github.com/jakenjarvis)

## License
[GNU General Public License Version 3](http://www.gnu.org/licenses/gpl.html)  
よくわからない場合は、[Wikipedia - GNU General Public License](http://ja.wikipedia.org/wiki/GPL)も参照ください。

# ダウンロード
[こちら](https://github.com/jakenjarvis/ChatLoggerPlus/downloads)から、該当するファイルをダウンロードしてください。
