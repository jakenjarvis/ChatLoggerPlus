package com.tojc.minecraft.mod.ChatLoggerPlusPlugin.v1;

/**
 * 環境情報へアクセスするためのインターフェイスです。
 * @author Jaken
 */
public interface PluginEnvironment
{
	/**
	 * サーバ名を取得します。
	 * ※サーバによってはIPアドレスの場合があります。
	 * @return サーバ名文字列
	 */
	public String getServerName();

	/**
	 * ワールド名を取得します。
	 * @return ワールド名文字列
	 */
	public String getWorldName();

	/**
	 * 現在プレイ中のユーザー名を取得します。
	 * @return プレイヤー名文字列
	 */
	public String getPlayerName();
}
