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
package com.tojc.minecraft.mod.ChatLogger.Writer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerCore;
import com.tojc.minecraft.mod.log.DebugLog;

import net.minecraft.client.Minecraft;

public class SpecialLogWriter implements WriterOperationInterface
{
	private ChatLoggerCore core = null;

	private ChatLogTextWriter writer = null;
	private WriterStatusController controller = null;

	private SimpleDateFormat datetimeformat = null;

	public SpecialLogWriter(ChatLoggerCore core)
	{
		this.core = core;
		this.writer = new ChatLogTextWriter(this.core.getConfig());
		this.controller = new WriterStatusController(this.writer, this);

		this.datetimeformat = new SimpleDateFormat(this.core.getConfig().getFormatDateTime().get());
	}

	public void setServerName(String servername)
	{
		this.writer.getLogFileNameManager().setServerName(servername);
	}

	public void setWorldName(String worldname)
	{
		this.writer.getLogFileNameManager().setWorldName(worldname);
	}

	public void open()
	{
		this.controller.open();
	}

	public void write(String output)
	{
		this.controller.write(output);
	}

	public void flush()
	{
		this.controller.flush();
	}

	public void close()
	{
		this.controller.close();
	}

	@Override
	public boolean hasPlayerChanged()
	{
		boolean result = false;
		String newPlayerName = this.core.getPlayerName();

		switch(this.controller.getState())
		{
			case Initialize:
				// 初期化中は、無効な名前は除外する。
				if((newPlayerName != null) && (newPlayerName.length() >= 1)
					&& (!newPlayerName.equals(this.writer.getLogFileNameManager().getPlayerName())))
				{
					result = true;
				}
				break;

			case Opened:
				// 呼び出してはいけない。
				throw new IllegalStateException("File has been opened.");

			case ClosedAfterglow:
				// クローズ後は、名前が取得できなくなった時点で終了。
				// それ以降の出力は、Initializeでバッファに出力され、openしないなら破棄される。
				if((newPlayerName == null) || (newPlayerName.length() == 0))
				{
					result = true;
				}
				else if(!newPlayerName.equals(this.writer.getLogFileNameManager().getPlayerName()))
				{
					result = true;
				}
				break;

			default:
				break;
		}
		return result;
	}

	@Override
	public void onPlayerChenged()
	{
		DebugLog.trace("logger.onPlayerChenged()");
		String newPlayerName = this.core.getPlayerName();

		// プレイヤーが変わったら、ログファイルも変更する。
		this.writer.getLogFileNameManager().setPlayerName(newPlayerName);
		this.writer.getLogFileNameManager().setFileBaseDate(LogFileNameManager.makeDate());
	}

	@Override
	public void onHeaderOutputTiming()
	{
		// ヘッダーの出力
		this.writer.onWriteLowLevel("--------------------------------------------------------------------------------");

		StringBuffer message = new StringBuffer();
		message.append(datetimeformat.format(this.writer.getLogFileNameManager().getFileBaseDate()));
		message.append(" : ");
		message.append("ChatLoggerPlus Logging start. (");
		message.append(this.writer.getLogFileNameManager().getServerName());
		message.append(" - ");
		message.append(this.writer.getLogFileNameManager().getWorldName());
		message.append(" - ");
		message.append(this.writer.getLogFileNameManager().getPlayerName());
		message.append(")");

		this.writer.onWriteLowLevel(message.toString());
		this.writer.onWriteLowLevel("--------------------------------------------------------------------------------");
	}

	@Override
	public void onOpenFileOperationCompleted()
	{
		String filename;
		try
		{
			filename = this.writer.getLogFileNameManager().getFullPathLogFile().getCanonicalPath();
		}
		catch (Exception e)
		{
			filename = "";
		}

		this.core.sendLocalChatMessage("§aChatLoggerPlus: §rLogging start.");
		this.core.sendLocalChatMessage("§aChatLoggerPlus: §r" + filename);
	}

	@Override
	public void onCloseFileOperationCompleted()
	{
	}

}
