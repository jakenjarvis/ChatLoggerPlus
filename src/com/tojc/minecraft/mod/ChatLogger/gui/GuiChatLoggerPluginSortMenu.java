package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiChatLoggerPluginSortMenu extends GuiScreen implements GuiChatLoggerPluginScrollPanel.OnElementClickedListener
{
    protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private ChatLoggerCore core = null;
    private PluginType type = null;

    private GuiChatLoggerPluginScrollPanel scrollPanel = null;

	public GuiChatLoggerPluginSortMenu(GuiScreen par1GuiScreen, ChatLoggerCore core, PluginType type)
	{
    	super();
        this.parentScreen = par1GuiScreen;
		this.core = core;
		this.type = type;
	}

	@Override
	public void initGui()
	{
		PluginOrderController controller = this.core.getPluginManager().getPluginOrderManager().getPluginOrderController(this.type);
		TreeMap<Integer, PluginOrderStatus> mapPlugins = controller.getOrderTreeMap();

        this.scrollPanel = new GuiChatLoggerPluginScrollPanel(this, this.type, mapPlugins, this, this.mc, this.width, this.height, 16, (this.height - 70) + 4, 32);
        StringTranslate var1 = StringTranslate.getInstance();

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
				case 0:
					break;

				case 200:
		            this.mc.displayGuiScreen(this.parentScreen);
					break;
			}
		}
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

	@Override
	public void onElementClicked(int index, PluginOrderStatus plugin)
	{
	}

}
