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

import java.util.ArrayList;
import java.util.List;

/**
 * プラグインの設定や登録を行うためのインターフェイスです。
 * @author Jaken
 */
public interface PluginSettings
{
	/**
	 * チャットメッセージを加工するパーミッションを要求します。
	 * スクリプトで {@link ChatMessage#setMessage(String)} を使用する場合は宣言が必要です。
	 * @see ChatMessage#setMessage(String)
	 */
	public void registerPermissionMessageModification();

	/**
	 * チャットメッセージを追加するパーミッションを要求します。
	 * スクリプトで {@link ChatMessage#addAfterMessage(String)} を使用する場合は宣言が必要です。
	 * @see ChatMessage#addAfterMessage(String)
	 */
	public void registerPermissionAddAfterMessage();

	/**
	 * スタックに書き込むパーミッションを要求します。（実質新規作成の意味合いです）
	 * スクリプトで {@link ChatMessage#writeStack(String, Object)} を使用する場合は宣言が必要です。
	 * @see ChatMessage#writeStack(String, Object)
	 * @param keyname アクセス対象のスタックに登録するキー文字列
	 */
	public void registerPermissionWriteStack(String keyname);

	/**
	 * スタックを読み込むパーミッションを要求します。（実質、既に存在するスタックなら読み書きできます）
	 * スクリプトで {@link ChatMessage#readStack(String)} を使用する場合は宣言が必要です。
	 * @see ChatMessage#readStack(String)
	 * @param keyname アクセス対象のスタックから読み取るキー文字列
	 */
	public void registerPermissionReadStack(String keyname);
}
