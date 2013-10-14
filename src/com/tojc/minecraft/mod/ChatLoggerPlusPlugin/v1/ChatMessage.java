/*
 * ChatLoggerPlus (Minecraft MOD)
 *
 * Copyright (C) 2012 Members of the ChatLoggerPlus project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1;

/**
 * チャットメッセージに対する操作を行うためのインターフェイスです。
 * @author Jaken
 */
public interface ChatMessage
{
	/**
	 * オリジナルのJSONメッセージを取得します。
	 *
	 * （Minecraft1.6以降でチャットメッセージの扱いが大きく変わっています）
	 * @return JSON形式の文字列
	 */
	public String getOriginalJsonString();

	/**
	 * 発言したユーザー名を取得します。
	 *
	 * メッセージがユーザーによる発言ではない場合はnullが返却されます。
	 * （Minecraft1.6以降で確実にユーザー名が取得できるようになり、メッセージと分断可能になりました）
	 * @return ユーザー名の文字列
	 */
	public String getUserName();

	/**
	 * オリジナルのチャットメッセージを取得します。
	 *
	 * 他のプラグインの影響を受けずに、オリジナルのメッセージがほしい場合に使います。
	 * ※取得できるチャットメッセージにはユーザー名を含みません。
	 * @return チャットメッセージ文字列
	 */
	public String getMessageOriginal();

	/**
	 * チャットメッセージを取得します。
	 *
	 * このプラグインが処理する前に、別のプラグインによって加工されている場合は、その内容が反映された状態で渡されます。
	 * ※取得できるチャットメッセージにはユーザー名を含みません。
	 * ※他のプラグインによって、チャットメッセージが削除されている場合、nullが返却される可能性があります。
	 * このためnullチェックを必ず行ってください。
	 * @return チャットメッセージ文字列
	 */
	public String getMessage();

	/**
	 * チャットメッセージを設定します。
	 *
	 * ※nullをセットすると、チャットの発言自体を削除できますが、nullを想定していない他のチャット処理MODと競合する可能性があります。
	 * このメソッドを使用するためには、 {@link PluginInterface#onInitialize(PluginSettings)} にて、あらかじめ
	 *  {@link PluginSettings#registerPermissionMessageModification()} を呼び出さなければなりません。
	 * @see PluginSettings#registerPermissionMessageModification()
	 * @param message チャットメッセージ文字列
	 */
	public void setMessage(String message);

	/**
	 * チャットメッセージを追加します。
	 *
	 * 追加したメッセージは、処理中のメッセージが表示された後、順番に出力されます。
	 * これは、ローカルチャットへ追加するだけで、サーバに送られることはありません。（利用者だけが見える）
	 * 注意：これを実現するためにチャットメッセージを横取りする必要があるので、他のチャット処理MODと競合する可能性があります。
	 * このメソッドを使用するためには、 {@link PluginInterface#onInitialize(PluginSettings)} にて、あらかじめ
	 *  {@link PluginSettings#registerPermissionAddAfterMessage()} を呼び出さなければなりません。
	 * @see PluginSettings#registerPermissionAddAfterMessage()
	 * @param message チャットメッセージ文字列
	 */
	public void addAfterMessage(String message);

	/**
	 * スタックへ指定のキー文字列に関連付けした任意のオブジェクトをセットします。
	 *
	 * 任意のキー文字列を指定できます。
	 * 指定したキー文字列を知るプラグインは {@link ChatMessage#readStack(String)} にて値を受け取ることができます。
	 * このメソッドを使用するためには、 {@link PluginInterface#onInitialize(PluginSettings)} にて、あらかじめ
	 *  {@link PluginSettings#registerPermissionWriteStack(String)} を呼び出さなければなりません。
	 * @see PluginSettings#registerPermissionWriteStack(String)
	 * @param keyname スタックに登録するキー文字列
	 * @param value スタックに登録する値 ※null、文字列、数値、配列やクラスオブジェクトなども渡せます。
	 */
	public void writeStack(String keyname, Object value);

	/**
	 * スタックから指定のキー文字列に関連付けされているオブジェクトを取得します。
	 *
	 * このメソッドを使うプラグインよりも前に、別のプラグインの {@link ChatMessage#writeStack(String, Object)} によって
	 * スタックにあらかじめ値が登録されている必要があります。
	 * このメソッドを使用するためには、 {@link PluginInterface#onInitialize(PluginSettings)} にて、あらかじめ
	 *  {@link PluginSettings#registerPermissionReadStack(String)} を呼び出さなければなりません。
	 * @see PluginSettings#registerPermissionReadStack(String)
	 * @param keyname スタックから受け取るキー文字列
	 * @return キー文字列に登録されている値 ※null、文字列、数値、配列やクラスオブジェクトなどが受け取れます。
	 */
	public Object readStack(String keyname);
}
