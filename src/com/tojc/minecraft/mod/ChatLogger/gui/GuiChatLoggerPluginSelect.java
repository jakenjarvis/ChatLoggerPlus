package com.tojc.minecraft.mod.ChatLogger.gui;

import java.util.Map;
import java.util.TreeMap;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.ChatLogger.Plugin.PluginInformation;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderController;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Order.PluginOrderStatus;
import com.tojc.minecraft.mod.ChatLogger.Plugin.Type.PluginType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

@SideOnly(Side.CLIENT)
public class GuiChatLoggerPluginSelect extends GuiScreen implements GuiChatLoggerPluginScrollPanel.OnElementClickedListener
{
	public interface OnPluginSelectedListener
	{
		void onPluginSelected(PluginOrderStatus plugin);
	}

	protected String screenTitle = "";

    private final GuiScreen parentScreen;
    private ChatLoggerCore core = null;
    private PluginType type = null;
    private OnPluginSelectedListener listener = null;

    private GuiChatLoggerPluginScrollPanel scrollPanel = null;

    private PluginOrderStatus resultPlugin = null;

	public GuiChatLoggerPluginSelect(GuiScreen par1GuiScreen, ChatLoggerCore core, PluginType type, OnPluginSelectedListener listener)
	{
    	super();
        this.parentScreen = par1GuiScreen;
		this.core = core;
		this.type = type;
		this.listener = listener;

		this.resultPlugin = null;
	}

	@Override
	public void initGui()
	{
		TreeMap<Integer, PluginOrderStatus> mapPlugins = this.core.getPluginManager().getPluginOrderManager().getMasterTreeMap();

        this.scrollPanel = new GuiChatLoggerPluginScrollPanel(this, this.type, mapPlugins, this, this.mc, this.width, this.height, 16, (this.height - 40) + 4, 32);
        StringTranslate var1 = StringTranslate.getInstance();

		this.buttonList.add(new GuiButton(  0, this.width / 2 -  90 - 10, this.height -28,  90, 20, var1.translateKey("gui.cancel")));
		this.buttonList.add(new GuiButton(200, this.width / 2 -   0 + 10, this.height -28,  90, 20, var1.translateKey("gui.done")));

        this.scrollPanel.registerScrollButtons(this.buttonList, 7, 8);

        this.screenTitle = "Select Plugin (" + this.type.getName() + ")";
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			switch(par1GuiButton.id)
			{
				case 0:		// Cancel
					this.resultPlugin = null;
		            this.mc.displayGuiScreen(this.parentScreen);
					break;

				case 200:	// Done
					if(this.resultPlugin != null)
					{
						if(this.listener != null)
						{
							this.listener.onPluginSelected(this.resultPlugin);
						}
					}
		            this.mc.displayGuiScreen(this.parentScreen);
					break;
			}
		}
	}

	@Override
	public void onElementClicked(int index, PluginOrderStatus plugin)
	{
		this.resultPlugin = plugin;
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
