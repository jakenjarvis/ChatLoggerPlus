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

import java.util.EnumSet;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;

/**
 * @author Jaken
 * Minecraft.getMinecraft().currentScreenの状態を監視し、変更があったら通知するクラス
 * ITickHandlerを実装しており、変更があれば、tickEndのタイミングでonCurrentScreenChangedを通知します。
 */
public class CurrentScreenMonitor implements ITickHandler
{
	private GuiScreen previousScreen;

	private Side side;
	private EnumSet<TickType> ticks;

	private List<CurrentScreenChangedListener> listenerCurrentScreenChangedList;

	/**
	 * コンストラクタ
	 */
	public CurrentScreenMonitor()
	{
		this(Side.CLIENT, EnumSet.of(TickType.CLIENT));
	}

	/**
	 * コンストラクタ
	 * @param listener イベント通知を受け取るリスナの登録
	 */
	public CurrentScreenMonitor(CurrentScreenChangedListener listener)
	{
		this(Side.CLIENT, EnumSet.of(TickType.CLIENT));
		this.addCurrentScreenChangedListener(listener);
	}

	/**
	 * コンストラクタ
	 * @param side TickRegistry#registerTickHandlerのパラメータ
	 * @param ticks ITickHandler#ticksのパラメータ
	 */
	public CurrentScreenMonitor(Side side, EnumSet<TickType> ticks)
	{
		this.previousScreen = Minecraft.getMinecraft().currentScreen;
		this.ticks = ticks;

		TickRegistry.registerTickHandler(this, side);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(this.previousScreen != Minecraft.getMinecraft().currentScreen)
		{
			CurrentScreenChangedEvent e = new CurrentScreenChangedEvent(this,
					this.previousScreen,
					Minecraft.getMinecraft().currentScreen
					);
			fireCurrentScreenChanged(e);
			this.previousScreen = Minecraft.getMinecraft().currentScreen;
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return this.ticks;
	}

	@Override
	public String getLabel()
	{
		return null;
	}

	/**
	 * イベント呼び出し
	 * @param e イベントパラメータ
	 */
	private void fireCurrentScreenChanged(CurrentScreenChangedEvent e)
	{
		if(this.listenerCurrentScreenChangedList != null)
		{
			for(CurrentScreenChangedListener listener : this.listenerCurrentScreenChangedList)
			{
				listener.onCurrentScreenChanged(e);
			}
		}
	}

	/**
	 * イベント通知を受け取るリスナの登録
	 * @param listener イベント通知を受け取るリスナ
	 */
	public synchronized void addCurrentScreenChangedListener(CurrentScreenChangedListener listener)
	{
		if(this.listenerCurrentScreenChangedList == null)
		{
			this.listenerCurrentScreenChangedList = new LinkedList<CurrentScreenChangedListener>();
		}
		this.listenerCurrentScreenChangedList.add(listener);
	}

	/**
	 * イベント通知を受け取るリスナの登録解除
	 * @param listener イベント通知を受け取るリスナ
	 */
	public synchronized void removeCurrentScreenChangedListener(CurrentScreenChangedListener listener)
	{
		this.listenerCurrentScreenChangedList.remove(listener);
	}

	/**
	 * @author Jaken
	 * リスナインターフェイス
	 */
	public interface CurrentScreenChangedListener extends EventListener
	{
		public void onCurrentScreenChanged(CurrentScreenChangedEvent e);
	}

	/**
	 * @author Jaken
	 * イベントパラメータクラス
	 */
	public class CurrentScreenChangedEvent extends EventObject
	{
		private final GuiScreen previousScreen;
		private final GuiScreen currentScreen;
		private final GuiScreenType previousScreenType;
		private final GuiScreenType currentScreenType;

		public CurrentScreenChangedEvent(Object source, GuiScreen previousScreen, GuiScreen currentScreen)
		{
			super(source);
			this.previousScreen = previousScreen;
			this.currentScreen = currentScreen;
			this.previousScreenType = GuiScreenType.toGuiScreenType(this.previousScreen);
			this.currentScreenType = GuiScreenType.toGuiScreenType(this.currentScreen);
		}

		/**
		 * @return 変更前のGuiScreenを返却します。
		 */
		public GuiScreen getPreviousScreen()
		{
			return this.previousScreen;
		}

		/**
		 * @return 変更後のGuiScreenを返却します。（Minecraft.getMinecraft().currentScreenと同等）
		 */
		public GuiScreen getCurrentScreen()
		{
			return this.currentScreen;
		}

		/**
		 * @return 変更前のGuiScreenTypeを返却します。未知のGuiScreenはUnknownとなります。
		 */
		public GuiScreenType getPreviousScreenType()
		{
			return this.previousScreenType;
		}

		/**
		 * @return 変更後のGuiScreenTypeを返却します。未知のGuiScreenはUnknownとなります。
		 */
		public GuiScreenType getCurrentScreenType()
		{
			return this.currentScreenType;
		}
	}

	/**
	 * @author Jaken
	 * GuiScreenを継承しているクラスを列挙しています。
	 */
	public enum GuiScreenType
	{
		Unknown("Unknown", null),

		// extends GuiScreen(fml)
		GuiModList("GuiModList", cpw.mods.fml.client.GuiModList.class),
		GuiModsMissingForServer("GuiModsMissingForServer", cpw.mods.fml.client.GuiModsMissingForServer.class),

		// extends GuiScreen
		GuiAchievements("GuiAchievements",net.minecraft.src.GuiAchievements.class),
		GuiChat("GuiChat",net.minecraft.src.GuiChat.class),
		GuiCommandBlock("GuiCommandBlock",net.minecraft.src.GuiCommandBlock.class),
		GuiConnecting("GuiConnecting",net.minecraft.src.GuiConnecting.class),
		GuiContainer("GuiContainer",net.minecraft.src.GuiContainer.class),
		GuiControls("GuiControls",net.minecraft.src.GuiControls.class),
		GuiCreateFlatWorld("GuiCreateFlatWorld",net.minecraft.src.GuiCreateFlatWorld.class),
		GuiCreateWorld("GuiCreateWorld",net.minecraft.src.GuiCreateWorld.class),
		GuiDisconnected("GuiDisconnected",net.minecraft.src.GuiDisconnected.class),
		GuiDownloadTerrain("GuiDownloadTerrain",net.minecraft.src.GuiDownloadTerrain.class),
		GuiEditSign("GuiEditSign",net.minecraft.src.GuiEditSign.class),
		GuiErrorScreen("GuiErrorScreen",net.minecraft.src.GuiErrorScreen.class),
		GuiFlatPresets("GuiFlatPresets",net.minecraft.src.GuiFlatPresets.class),
		GuiGameOver("GuiGameOver",net.minecraft.src.GuiGameOver.class),
		GuiIngameMenu("GuiIngameMenu",net.minecraft.src.GuiIngameMenu.class),
		GuiLanguage("GuiLanguage",net.minecraft.src.GuiLanguage.class),
		GuiMainMenu("GuiMainMenu",net.minecraft.src.GuiMainMenu.class),
		GuiMemoryErrorScreen("GuiMemoryErrorScreen",net.minecraft.src.GuiMemoryErrorScreen.class),
		GuiMultiplayer("GuiMultiplayer",net.minecraft.src.GuiMultiplayer.class),
		GuiOptions("GuiOptions",net.minecraft.src.GuiOptions.class),
		GuiProgress("GuiProgress",net.minecraft.src.GuiProgress.class),
		GuiRenameWorld("GuiRenameWorld",net.minecraft.src.GuiRenameWorld.class),
		GuiScreenAddServer("GuiScreenAddServer",net.minecraft.src.GuiScreenAddServer.class),
		GuiScreenBook("GuiScreenBook",net.minecraft.src.GuiScreenBook.class),
		GuiScreenDemo("GuiScreenDemo",net.minecraft.src.GuiScreenDemo.class),
		GuiScreenServerList("GuiScreenServerList",net.minecraft.src.GuiScreenServerList.class),
		GuiSelectWorld("GuiSelectWorld",net.minecraft.src.GuiSelectWorld.class),
		GuiShareToLan("GuiShareToLan",net.minecraft.src.GuiShareToLan.class),
		GuiSnooper("GuiSnooper",net.minecraft.src.GuiSnooper.class),
		GuiStats("GuiStats",net.minecraft.src.GuiStats.class),
		GuiTexturePacks("GuiTexturePacks",net.minecraft.src.GuiTexturePacks.class),
		GuiVideoSettings("GuiVideoSettings",net.minecraft.src.GuiVideoSettings.class),
		GuiWinGame("GuiWinGame",net.minecraft.src.GuiWinGame.class),
		GuiYesNo("GuiYesNo",net.minecraft.src.GuiYesNo.class),
		ModLoader("ModLoader",net.minecraft.src.ModLoader.class),
		//NetClientWebTextures("NetClientWebTextures",net.minecraft.src.NetClientWebTextures.class),
		ScreenChatOptions("ScreenChatOptions",net.minecraft.src.ScreenChatOptions.class),

		// extends GuiContainer
		GuiBeacon("GuiBeacon",net.minecraft.src.GuiBeacon.class),
		GuiBrewingStand("GuiBrewingStand",net.minecraft.src.GuiBrewingStand.class),
		GuiChest("GuiChest",net.minecraft.src.GuiChest.class),
		GuiCrafting("GuiCrafting",net.minecraft.src.GuiCrafting.class),
		GuiDispenser("GuiDispenser",net.minecraft.src.GuiDispenser.class),
		GuiEnchantment("GuiEnchantment",net.minecraft.src.GuiEnchantment.class),
		GuiFurnace("GuiFurnace",net.minecraft.src.GuiFurnace.class),
		GuiMerchant("GuiMerchant",net.minecraft.src.GuiMerchant.class),
		GuiRepair("GuiRepair",net.minecraft.src.GuiRepair.class),
		InventoryEffectRenderer("InventoryEffectRenderer",net.minecraft.src.InventoryEffectRenderer.class),

		// extends GuiChat
		GuiSleepMP("GuiSleepMP", net.minecraft.src.GuiSleepMP.class),

		// extends InventoryEffectRenderer
		GuiContainerCreative("GuiContainerCreative", net.minecraft.src.GuiContainerCreative.class),
		GuiInventory("GuiInventory", net.minecraft.src.GuiInventory.class),

		// extends GuiYesNo
		GuiConfirmOpenLink("GuiConfirmOpenLink", net.minecraft.src.GuiConfirmOpenLink.class),

		// extends GuiConfirmOpenLink
		//GuiChatConfirmLink("GuiChatConfirmLink", net.minecraft.src.GuiChatConfirmLink.class),


		// extends GuiScreen(super) ※特殊な扱い（他のMODで追加されたGuiScreenを疑似判定する）
		UnknownExtendsGuiScreen("UnknownExtendsGuiScreen", net.minecraft.src.GuiScreen.class);

		private GuiScreenType(String name, Class<?> cls)
		{
			this.name = name;
			this.cls = cls;
		}

		private final String name;
		private final Class<?> cls;

		/**
		 * @return GuiScreenのクラスを返却します。
		 */
		public Class<?> getGuiScreenClass()
		{
			return this.cls;
		}

		/**
		 * @param targetguiscreen GuiScreenのインスタンス
		 * @return インスタンスのクラスを判定し、該当するGuiScreenTypeを返却します。
		 */
		public static GuiScreenType toGuiScreenType(GuiScreen targetguiscreen)
		{
			GuiScreenType result = Unknown;
	        for (GuiScreenType screen : values())
	        {
	        	if((screen != null) && (targetguiscreen != null))
	        	{
		        	if((screen.getGuiScreenClass() == targetguiscreen.getClass())
		        			&& (screen != UnknownExtendsGuiScreen))
		        	{
		                result = screen;
		                break;
		        	}
	        	}
	        }

	        if(result == Unknown)
	        {
	        	// 未登録のGuiScreenを継承したクラスを判定する。
	        	if(targetguiscreen != null)
	        	{
		        	if(UnknownExtendsGuiScreen.getGuiScreenClass().isAssignableFrom(targetguiscreen.getClass()))
		        	{
		                result = UnknownExtendsGuiScreen;
		        	}
	        	}
	        }
	        return result;
	    }

		@Override
		public String toString()
		{
			return this.name;
		}
	}

}
