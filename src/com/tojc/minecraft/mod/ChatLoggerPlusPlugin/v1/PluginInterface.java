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
 * プラグインの骨格となるインターフェイスです。
 * @author Jaken
 */
public interface PluginInterface
{
	/**
	 * ここで、プラグインの初期化処理を行います。
	 *
	 * これは通常、サーバ接続時に呼び出されますが、タイミングを意識しない作りにしてください。
	 * パーミッションが必要なプラグインの場合は、ここで登録を行います。
	 * @param settings 設定オブジェクト {@link PluginSettings}
	 */
	public void onInitialize(PluginSettings settings);

	/**
	 * ここで、チャットのメッセージに対する処理を行います。
	 *
	 * これは通常、ユーザーがチャット発言をしたとき、画面に表示する前に呼び出されます。
	 * ここでのチャット編集は、ローカルのみに反映されます。※サーバへ送信されることはありません。
	 * @param env 環境オブジェクト {@link PluginEnvironment}
	 * @param chat チャットオブジェクト {@link ChatMessage}
	 */
	public void onChatMessage(PluginEnvironment env, ChatMessage chat);

	/**
	 * ここで、プラグインの終了処理を行います。
	 *
	 * これは通常、サーバ切断時に呼び出されますが、タイミングを意識しない作りにしてください。
	 */
	public void onFinalize();
}
