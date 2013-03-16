package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderKey;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;
import com.tojc.minecraft.mod.log.DebugLog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiChatLoggerPluginSortMenu extends GuiScreen
	implements
		GuiChatLoggerPluginScrollPanel.OnElementClickedListener,
		GuiChatLoggerPluginSelect.OnPluginSelectedListener
{
    protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private ChatLoggerCore core = null;
    private PluginType type = null;

    private GuiChatLoggerPluginScrollPanel scrollPanel = null;

    private PluginOrderController controller = null;
    private PluginOrderStatus selectPlugin = null;

	public GuiChatLoggerPluginSortMenu(GuiScreen par1GuiScreen, ChatLoggerCore core, PluginType type)
	{
    	super();
        this.parentScreen = par1GuiScreen;
		this.core = core;
		this.type = type;

		this.controller = this.core.getPluginManager().getPluginOrderManager().getPluginOrderController(this.type);
		this.selectPlugin = null;
	}

	@Override
	public void initGui()
	{
    	DebugLog.info("GuiChatLoggerPluginSortMenu.initGui()");

		TreeMap<Integer, PluginOrderStatus> mapPlugins = this.controller.getOrderTreeMap();

        this.scrollPanel = new GuiChatLoggerPluginScrollPanel(this, this.type, mapPlugins, this, this.mc, this.width, this.height, 16, (this.height - 70) + 4, 32);
        StringTranslate var1 = StringTranslate.getInstance();

        this.buttonList.clear();
		this.buttonList.add(new GuiButton(100, this.width / 2 +  50, this.height -63,  50, 16, var1.translateKey("gui.up")));
		this.buttonList.add(new GuiButton(101, this.width / 2 +  50, this.height -46,  50, 16, var1.translateKey("gui.down")));

		this.buttonList.add(new GuiButton(102,          0 +  0 + 15, this.height -58,  90, 20, "Delete"));
		this.buttonList.add(new GuiButton(103, this.width - 90 - 15, this.height -58,  90, 20, "Add"));

		this.buttonList.add(new GuiButton(104, this.width / 2 - 100, this.height -58, 100, 20, "Enabled/Disabled"));

		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height -28, var1.translateKey("gui.done")));

        this.scrollPanel.registerScrollButtons(this.buttonList, 7, 8);

        this.screenTitle = this.type.getName() + " Plugin";
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			switch(par1GuiButton.id)
			{
				case 100:	// Up
			    	this.controller.executeOrderUp(this.selectPlugin);
					this.controller.saveSetting();
					this.updateScrollPanel();
					break;

				case 101:	// Down
			    	this.controller.executeOrderDown(this.selectPlugin);
					this.controller.saveSetting();
					this.updateScrollPanel();
					break;

				case 102:	// Delete
			    	this.controller.executeDelete(this.selectPlugin);
					this.controller.saveSetting();
					this.updateScrollPanel();
					break;

				case 103:	// Add
					GuiChatLoggerPluginSelect screenmenu = new GuiChatLoggerPluginSelect(this, this.core, PluginType.Screen, this);
					this.mc.displayGuiScreen(screenmenu);
					break;

				case 104:	// Enabled/Disabled
			    	this.controller.executeStateToggle(this.selectPlugin);
					this.controller.saveSetting();
					this.updateScrollPanel();
					break;

				case 200:
					this.controller.saveSetting();
		            this.mc.displayGuiScreen(this.parentScreen);
					break;
			}
		}
	}

	@Override
	public void onElementClicked(int index, PluginOrderStatus plugin)
	{
		this.selectPlugin = plugin;
	}

	@Override
	public void onPluginSelected(PluginOrderStatus plugin)
	{
    	DebugLog.info("onPluginSelected : %s", plugin.getPluginOrderKey().toString());

    	this.controller.executeAdd(plugin);
		this.controller.saveSetting();
		this.updateScrollPanel();
	}

	public void updateScrollPanel()
	{
		TreeMap<Integer, PluginOrderStatus> mapPlugins = this.scrollPanel.getMapPlugins();
		this.controller.updateOrderTreeMap(mapPlugins);
	}



	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
        this.drawDefaultBackground();
        this.scrollPanel.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 4, 0xffffff);

        super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}

	@Override
	public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
	}

	@Override
	public void drawWorldBackground(int par1)
	{
		super.drawWorldBackground(par1);
	}

	@Override
	public void drawBackground(int par1)
	{
		super.drawBackground(par1);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return super.doesGuiPauseGame();
	}

	@Override
	public void confirmClicked(boolean par1, int par2)
	{
		super.confirmClicked(par1, par2);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

}
