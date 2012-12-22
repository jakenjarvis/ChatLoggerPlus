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
package com.tojc.minecraft.mod;

import com.tojc.minecraft.mod.CurrentScreenMonitor.CurrentScreenChangedEvent;
import com.tojc.minecraft.mod.CurrentScreenMonitor.CurrentScreenChangedListener;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.network.IChatListener;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;

public class HandlerAndEventListener implements IConnectionHandler, IChatListener, CurrentScreenChangedListener
{
	private CurrentScreenMonitor screenmonitor = null;
	private ChatLoggerCore core = null;

	public HandlerAndEventListener(ChatLoggerCore core)
	{
		this.core = core;

		// serverChat, clientChat
		NetworkRegistry.instance().registerChatListener(this);

		// playerLoggedIn, clientLoggedIn
		// connectionReceived, connectionOpened, connectionClosed
		NetworkRegistry.instance().registerConnectionHandler(this);

		// etc. @ForgeSubscribe
		MinecraftForge.EVENT_BUS.register(this);

		// onCurrentScreenChanged
		this.screenmonitor = new CurrentScreenMonitor(this);
	}

	@Override
	public Packet3Chat serverChat(NetHandler handler, Packet3Chat message)
	{
		// MEMO:一番最初に呼ばれ、<プレイヤー名>の文字が付く前の値が取得できる。
		// マルチサーバへ接続した場合は、クライアント側はこのイベントが発生しない。
		//「/」から始まるコマンドは、ここは呼び出されない。
		//DebugLog.info("serverChat: " + message.message);
		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message)
	{
		// MEMO:「/」から始まるコマンドは、ここは呼び出されない。
		//DebugLog.info("clientChat: " + message.message);
		return message;
	}

	@ForgeSubscribe
	public void onCommandEvent(CommandEvent event)
	{
		// MEMO: コマンドが分割されてくるので、入力した内容を正確に把握できない。
		// とりあえず、それっぽく復元する。
		StringBuffer commandline = new StringBuffer();
		commandline.append("/");
		commandline.append(event.command.getCommandName());
		commandline.append(" ");
		commandline.append(join(event.parameters, " "));

		this.core.onWrite(commandline.toString().trim());
	}

	@ForgeSubscribe
	public void onClientChatReceivedEvent(ClientChatReceivedEvent event)
	{
		// MEMO:「/」から始まるコマンドは、ここは呼び出されない。
		this.core.onWrite(event.message);
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
	{
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
	{
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
	{
		DebugLog.info("connectionOpened A: " + server + ":" + port);
		this.core.onServerConnection(server + "-" + port);
		//TODO: とりあえず、-にして対応したが、ここは:で渡し、ファイル名にする前に置換するように変更する。
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
	{
		DebugLog.info("connectionOpened B: " + server.getServerHostname() + ":" + server.getServerPort());
		this.core.onServerConnection("LocalServer");
	}

	@Override
	public void connectionClosed(INetworkManager manager)
	{
		DebugLog.info("connectionClosed");
		this.core.onClose();
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
	{
		DebugLog.info("clientLoggedIn");
		this.core.onOpen();
	}

	@Override
	public void onCurrentScreenChanged(CurrentScreenChangedEvent e)
	{
		DebugLog.info("onCurrentScreenChanged : "
				+ e.getPreviousScreenType().toString()
				+ " -> "
				+ e.getCurrentScreenType().toString()
				);

		switch(e.getCurrentScreenType())
		{
			case GuiChat:					// チャット入力画面は、頻度が高すぎるので除外する。
			case UnknownExtendsGuiChat:		// 未登録のチャット継承画面も除外しておく。
			case Unknown:					// プレイ中または未登録画面
				break;

			default:
				this.core.onFlush();
				break;
		}
	}


	private static String join(String[] array, String with)
	{
		StringBuffer result = new StringBuffer();
		String separator = "";
		for(String item : array)
		{
			result.append(separator);
			result.append(item);
			separator = with;
		}
		return result.toString();
	}

}
