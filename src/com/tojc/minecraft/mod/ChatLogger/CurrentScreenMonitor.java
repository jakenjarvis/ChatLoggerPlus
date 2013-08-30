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

import java.util.EnumSet;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.relauncher.Side;
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
		GuiSortingProblem("GuiSortingProblem", cpw.mods.fml.client.GuiSortingProblem.class),

		// extends GuiScreen
		GuiAchievements("GuiAchievements", net.minecraft.client.gui.achievement.GuiAchievements.class),
		GuiChat("GuiChat", net.minecraft.client.gui.GuiChat.class),
		GuiCommandBlock("GuiCommandBlock", net.minecraft.client.gui.GuiCommandBlock.class),
		GuiConnecting("GuiConnecting", net.minecraft.client.multiplayer.GuiConnecting.class),
		GuiContainer("GuiContainer", net.minecraft.client.gui.inventory.GuiContainer.class),
		GuiControls("GuiControls", net.minecraft.client.gui.GuiControls.class),
		GuiCreateFlatWorld("GuiCreateFlatWorld", net.minecraft.client.gui.GuiCreateFlatWorld.class),
		GuiCreateWorld("GuiCreateWorld", net.minecraft.client.gui.GuiCreateWorld.class),
		GuiDisconnected("GuiDisconnected", net.minecraft.client.gui.GuiDisconnected.class),
		GuiDownloadTerrain("GuiDownloadTerrain", net.minecraft.client.gui.GuiDownloadTerrain.class),
		GuiEditSign("GuiEditSign", net.minecraft.client.gui.inventory.GuiEditSign.class),
		GuiErrorScreen("GuiErrorScreen", net.minecraft.client.gui.GuiErrorScreen.class),
		GuiFlatPresets("GuiFlatPresets", net.minecraft.client.gui.GuiFlatPresets.class),
		GuiGameOver("GuiGameOver", net.minecraft.client.gui.GuiGameOver.class),
		GuiIngameMenu("GuiIngameMenu", net.minecraft.client.gui.GuiIngameMenu.class),
		GuiLanguage("GuiLanguage", net.minecraft.client.gui.GuiLanguage.class),
		GuiMainMenu("GuiMainMenu", net.minecraft.client.gui.GuiMainMenu.class),
		GuiMemoryErrorScreen("GuiMemoryErrorScreen", net.minecraft.client.gui.GuiMemoryErrorScreen.class),
		GuiMultiplayer("GuiMultiplayer", net.minecraft.client.gui.GuiMultiplayer.class),
		GuiOptions("GuiOptions", net.minecraft.client.gui.GuiOptions.class),
		//GuiProgress("GuiProgress", net.minecraft.client.gui.GuiProgress.class),
		GuiRenameWorld("GuiRenameWorld", net.minecraft.client.gui.GuiRenameWorld.class),
		GuiScreen("GuiScreen", net.minecraft.client.gui.GuiScreen.class),
		GuiScreenAddServer("GuiScreenAddServer", net.minecraft.client.gui.GuiScreenAddServer.class),
		GuiScreenBackup("GuiScreenBackup", net.minecraft.client.gui.mco.GuiScreenBackup.class),
		//GuiScreenBackupSelectionList("GuiScreenBackupSelectionList", net.minecraft.client.gui.mco.GuiScreenBackupSelectionList.class),
		GuiScreenBook("GuiScreenBook", net.minecraft.client.gui.GuiScreenBook.class),
		GuiScreenClientOutdated("GuiScreenClientOutdated", net.minecraft.client.mco.GuiScreenClientOutdated.class),
		GuiScreenConfigureWorld("GuiScreenConfigureWorld", net.minecraft.client.gui.GuiScreenConfigureWorld.class),
		GuiScreenConfirmation("GuiScreenConfirmation", net.minecraft.client.gui.GuiScreenConfirmation.class),
		GuiScreenDemo("GuiScreenDemo", net.minecraft.client.gui.GuiScreenDemo.class),
		GuiScreenDisconnectedOnline("GuiScreenDisconnectedOnline", net.minecraft.client.gui.GuiScreenDisconnectedOnline.class),
		GuiScreenEditOnlineWorld("GuiScreenEditOnlineWorld", net.minecraft.client.gui.GuiScreenEditOnlineWorld.class),
		GuiScreenInvite("GuiScreenInvite", net.minecraft.client.gui.GuiScreenInvite.class),
		GuiScreenLongRunningTask("GuiScreenLongRunningTask", net.minecraft.client.gui.GuiScreenLongRunningTask.class),
		GuiScreenMcoWorldTemplate("GuiScreenMcoWorldTemplate", net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate.class),
		//GuiScreenMcoWorldTemplateSelectionList("GuiScreenMcoWorldTemplateSelectionList", net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplateSelectionList.class),
		GuiScreenOnlineServers("GuiScreenOnlineServers", net.minecraft.client.gui.GuiScreenOnlineServers.class),
		GuiScreenPendingInvitation("GuiScreenPendingInvitation", net.minecraft.client.gui.mco.GuiScreenPendingInvitation.class),
		GuiScreenSelectLocation("GuiScreenSelectLocation", net.minecraft.client.gui.GuiScreenSelectLocation.class),
		GuiScreenServerList("GuiScreenServerList", net.minecraft.client.gui.GuiScreenServerList.class),
		GuiScreenSubscription("GuiScreenSubscription", net.minecraft.client.gui.GuiScreenSubscription.class),
		GuiScreenTemporaryResourcePackSelect("GuiScreenTemporaryResourcePackSelect", net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect.class),
		GuiSelectWorld("GuiSelectWorld", net.minecraft.client.gui.GuiSelectWorld.class),
		GuiShareToLan("GuiShareToLan", net.minecraft.client.gui.GuiShareToLan.class),
		//GuiSlotOnlineServerList("GuiSlotOnlineServerList", net.minecraft.client.gui.GuiSlotOnlineServerList.class),
		GuiSnooper("GuiSnooper", net.minecraft.client.gui.GuiSnooper.class),
		GuiStats("GuiStats", net.minecraft.client.gui.achievement.GuiStats.class),
		//GuiTexturePacks("GuiTexturePacks", net.minecraft.client.texturepacks.GuiTexturePacks.class),
		GuiVideoSettings("GuiVideoSettings", net.minecraft.client.gui.GuiVideoSettings.class),
		GuiWinGame("GuiWinGame", net.minecraft.client.gui.GuiWinGame.class),
		GuiYesNo("GuiYesNo", net.minecraft.client.gui.GuiYesNo.class),
		//NetClientWebTextures("NetClientWebTextures", net.minecraft.client.multiplayer.NetClientWebTextures.class),
		ScreenChatOptions("ScreenChatOptions", net.minecraft.client.gui.ScreenChatOptions.class),

		// extends GuiScreenSelectLocation
		//GuiScreenPendingInvitationList("GuiScreenPendingInvitationList", net.minecraft.client.gui.mco.GuiScreenPendingInvitationList.class),

		// extends GuiContainer
		GuiBeacon("GuiBeacon", net.minecraft.client.gui.inventory.GuiBeacon.class),
		GuiBrewingStand("GuiBrewingStand", net.minecraft.client.gui.inventory.GuiBrewingStand.class),
		GuiChest("GuiChest", net.minecraft.client.gui.inventory.GuiChest.class),
		GuiCrafting("GuiCrafting", net.minecraft.client.gui.inventory.GuiCrafting.class),
		GuiDispenser("GuiDispenser", net.minecraft.client.gui.inventory.GuiDispenser.class),
		GuiEnchantment("GuiEnchantment", net.minecraft.client.gui.GuiEnchantment.class),
		GuiFurnace("GuiFurnace", net.minecraft.client.gui.inventory.GuiFurnace.class),
		GuiHopper("GuiHopper", net.minecraft.client.gui.GuiHopper.class),
		GuiMerchant("GuiMerchant", net.minecraft.client.gui.GuiMerchant.class),
		GuiRepair("GuiRepair", net.minecraft.client.gui.GuiRepair.class),
		InventoryEffectRenderer("InventoryEffectRenderer", net.minecraft.client.renderer.InventoryEffectRenderer.class),

		// extends GuiChat
		GuiSleepMP("GuiSleepMP", net.minecraft.client.gui.GuiSleepMP.class),

		// extends InventoryEffectRenderer
		GuiContainerCreative("GuiContainerCreative", net.minecraft.client.gui.inventory.GuiContainerCreative.class),
		GuiInventory("GuiInventory", net.minecraft.client.gui.inventory.GuiInventory.class),

		// extends GuiYesNo
		GuiConfirmOpenLink("GuiConfirmOpenLink", net.minecraft.client.gui.GuiConfirmOpenLink.class),
		GuiIdMismatchScreen("GuiIdMismatchScreen", cpw.mods.fml.client.GuiIdMismatchScreen.class),
		//GuiScreenConfirmation("GuiScreenConfirmation", net.minecraft.client.gui.GuiScreenConfirmation.class),

		// extends GuiConfirmOpenLink
		//GuiChatConfirmLink("GuiChatConfirmLink", net.minecraft.client.gui.GuiChatConfirmLink.class),

		// extends ScreenWithCallback
		ScreenWithCallback("ScreenWithCallback", net.minecraft.client.gui.mco.ScreenWithCallback.class),
		//GuiScreenCreateOnlineWorld("GuiScreenCreateOnlineWorld", net.minecraft.client.gui.GuiScreenCreateOnlineWorld.class),
		//GuiScreenResetWorld("GuiScreenResetWorld", net.minecraft.client.gui.GuiScreenResetWorld.class),


		// extends GuiScreen(super) ※特殊な扱い（他のMODで追加されたGuiScreenを疑似判定する）
		UnknownExtendsGuiScreen("UnknownExtendsGuiScreen", net.minecraft.client.gui.GuiScreen.class),

		// extends GuiChat(super) ※特殊な扱い（他のMODで追加されたGuiChatを疑似判定する）
		UnknownExtendsGuiChat("UnknownExtendsGuiChat", net.minecraft.client.gui.GuiChat.class);

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
		        			&& (screen != UnknownExtendsGuiScreen)
		        			&& (screen != UnknownExtendsGuiChat)
		        			)
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

		        	if(UnknownExtendsGuiChat.getGuiScreenClass().isAssignableFrom(targetguiscreen.getClass()))
		        	{
		                result = UnknownExtendsGuiChat;
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
