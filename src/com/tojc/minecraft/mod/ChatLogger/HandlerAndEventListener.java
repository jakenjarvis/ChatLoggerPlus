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
package com.tojc.minecraft.mod.ChatLogger;

import com.tojc.minecraft.mod.ChatLogger.CurrentScreenMonitor.CurrentScreenChangedEvent;
import com.tojc.minecraft.mod.ChatLogger.CurrentScreenMonitor.CurrentScreenChangedListener;
import com.tojc.minecraft.mod.log.DebugLog;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.network.IChatListener;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;

public class HandlerAndEventListener implements IConnectionHandler, IChatListener, CurrentScreenChangedListener
{
	private ChatLoggerCore core = null;
	private CurrentScreenMonitor screenmonitor = null;

	private String servername = "";
	private String worldname = "";

	public HandlerAndEventListener(ChatLoggerCore core)
	{
		this.core = core;

		// serverChat, clientChat
		NetworkRegistry.instance().registerChatListener(this);

		// playerLoggedIn, clientLoggedIn
		// connectionReceived, connectionOpened, connectionClosed
		NetworkRegistry.instance().registerConnectionHandler(this);

		// KeyBinding
		KeyBindingRegistry.registerKeyBinding(new ChatLoggerKeyHandler(this.core));

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
		DebugLog.info("serverChat: " + message.message);
		return message;
	}

	@Override
	public Packet3Chat clientChat(NetHandler handler, Packet3Chat message)
	{
		// MEMO:「/」から始まるコマンドは、ここは呼び出されない。
		DebugLog.info("clientChat: " + message.message);

		ChatMessageComponent component = ChatMessageComponent.func_111078_c(message.message);
		String target = component.func_111068_a(true);
		//DebugLog.info("    target = " + target);

		ClientChatMessageManager chatmanager = new ClientChatMessageManager(this.core, this.servername, this.worldname, target);
		message.message = component.func_111062_i();

		//TODO: 画面出力部分は保留
		//message.message = chatmanager.outputScreen();
		//// TODO: 直前に吐いてしまう。タイミング悪すぎる
		//for(String output : chatmanager.outputScreenAfterMessages())
		//{
		//	this.core.sendLocalChatMessage(output);
		//}

		this.core.onWrite(chatmanager.outputChatLog());
		for(String output : chatmanager.outputChatLogAfterMessages())
		{
			this.core.onWrite(output);
		}

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

		//DebugLog.info("onCommandEvent: " + commandline.toString().trim());

		this.core.onWrite(commandline.toString().trim());
	}

	@ForgeSubscribe
	public void onClientChatReceivedEvent(ClientChatReceivedEvent event)
	{
		// MEMO:「/」から始まるコマンドは、ここは呼び出されない。
		//DebugLog.info("onClientChatReceivedEvent: " + event.message);
		//this.core.onWrite(event.message);

		// 表示処理を制御するため、 net.minecraft.client.multiplayer.NetClientHandler クラスの handleChat の処理を全てここで行う。

		//Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("test 1");
		if(event.message != null)
		{
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ChatMessageComponent.func_111078_c(event.message).func_111068_a(true));
		}
		//Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage("test 2");

		event.message = null;
	}

	@ForgeSubscribe
	public void onWorldEvent_Load(WorldEvent.Load event)
	{
		DebugLog.info("onWorldEvent_Load()");
		DebugLog.info("getWorldName() : " + event.world.getWorldInfo().getWorldName());

		String name = event.world.getWorldInfo().getWorldName();
		if(!name.equals("MpServer"))
		{
			this.worldname = name;
		}

		this.core.onWorldLoad(this.worldname);
		this.core.onOpen();
	}

	@ForgeSubscribe
	public void onWorldEvent_Save(WorldEvent.Save event)
	{
		DebugLog.info("onWorldEvent_Save()");
		this.core.onFlush();
	}

	@ForgeSubscribe
	public void onWorldEvent_Unload(WorldEvent.Unload event)
	{
		DebugLog.info("onWorldEvent_Unload()");
		this.core.onWorldUnload(this.worldname);
		this.core.onClose();
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
		this.servername = server + "-" + port;
		DebugLog.info("connectionOpened A: " + this.servername);

		this.core.onServerConnection(this.servername);
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
	{
		this.servername = "LocalServer";
		DebugLog.info("connectionOpened B: " + server.getServerHostname() + ":" + server.getServerPort() + ": " + server.getWorldName());

		this.core.onServerConnection(this.servername);
	}

	@Override
	public void connectionClosed(INetworkManager manager)
	{
		// MEMO: minecraftforge-src-1.4.5-6.4.2.448にて、複数回呼ばれることを確認したため、使用不可。
		// connectionOpenedと対でないため、ここでのCloseは誤動作になる。
		//（チャット発言の度にconnectionClosedされる。呼出し元が複数個所に追加されている）

		// MEMO: minecraftforge-src-1.4.6-6.5.0.467にて、直った？この先も不安なので、WorldEventを使うことにする。
		DebugLog.info("connectionClosed");
		//this.core.onClose();
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
	{
		DebugLog.info("clientLoggedIn");
		//this.core.onOpen();
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
