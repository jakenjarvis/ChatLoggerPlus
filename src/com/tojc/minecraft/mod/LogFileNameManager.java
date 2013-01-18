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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.minecraft.client.Minecraft;

public class LogFileNameManager
{
	private ChatLoggerConfiguration config = null;

	private String servername = "";
	private String playername = "";
	private Date filebasedate = null;

	private SimpleDateFormat dateformat;
	private SimpleDateFormat timeformat;

	public LogFileNameManager(ChatLoggerConfiguration config)
	{
		this.config = config;

		this.dateformat = new SimpleDateFormat(this.config.getFormatReplaceDate().get());
		this.timeformat = new SimpleDateFormat(this.config.getFormatReplaceTime().get());
	}

	public void setServerName(String servername)
	{
		this.servername = servername;
	}
	public String getServerName()
	{
		return this.servername;
	}

	public void setPlayerName(String playername)
	{
		this.playername = playername;
	}
	public String getPlayerName()
	{
		return this.playername;
	}

	public void setFileBaseDate(Date filebasedate)
	{
		this.filebasedate = filebasedate;
	}
	public Date getFileBaseDate()
	{
		return this.filebasedate;
	}


	public File getFullPathLogFile()
	{
		if(!isState())
		{
			throw new IllegalStateException("Necessary information is not enough.");
		}

		File result = null;
		String enforcementPath = this.config.getEnforcementReplaceLogFileFullPathFileName().get();
		if(enforcementPath.trim().length() != 0)
		{
			//強制指定
			result = new File(replaceSymbol(enforcementPath));
		}
		else
		{
			//デフォルト自動生成
			result = new File(getDefaultLogFileFullPathFileName());
		}
		return result;
	}




	public boolean isState()
	{
		// ファイルに書き込む準備が出来ているか
		boolean result = false;
		if(((this.servername != null) && (this.servername.length() >= 1))
			&& ((this.playername != null) && (this.playername.length() >= 1))
			&& (this.filebasedate != null))
		{
			result = true;
		}
		return result;
	}


	public static Date makeDate()
	{
		return Calendar.getInstance().getTime();
	}

	private String getDefaultLogFileFullPathFileName()
	{
		String replaceDefaultLogFile = replaceSymbol(this.config.getDefaultReplaceLogFileFullPathFileName().get());
		String result = new File(Minecraft.getMinecraftDir().toString(), replaceDefaultLogFile).getPath();
		return result;
	}

	private String replaceSymbol(String target)
	{
		String result = target;
		// Replace : %SERVERNAME% %PLAYERNAME% %DATE% %TIME%
		result = result.replace("%SERVERNAME%", this.servername);
		result = result.replace("%PLAYERNAME%", this.playername);
		result = result.replace("%DATE%", this.dateformat.format(this.filebasedate));
		result = result.replace("%TIME%", this.timeformat.format(this.filebasedate));
		return result;
	}

}
