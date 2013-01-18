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

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import com.tojc.minecraft.mod.log.DebugLog;

import net.minecraft.client.Minecraft;

public class SpecialLogWriter
{
	private ChatLoggerConfiguration config = null;
	private FileOperationCompletedListener listener = null;

	private LogFileNameManager logfilemanager = null;

	private WriterState state = WriterState.Initialize;

	private File logfile = null;

	private PrintWriter pw = null;
	private BufferedWriter bw = null;
	private OutputStreamWriter osw = null;
	private FileOutputStream fos = null;

	private SimpleDateFormat datetimeformat;

	private List<String> buffer = new LinkedList<String>();

	private int output_count = 0;
	private boolean isflush = false;

	private enum WriterState
	{
		// 初期化中の状態：bufferへ蓄積してファイルには出力しない。
		// プレイヤー名やサーバ名など、ファイル名作成に必要な情報が取得できるまでの間は、この状態を維持する。
		Initialize,

		// ファイルオープン中の状態：Writerに書き込んで、たまにflushする。
		// BufferedWriterでバッファリングしているが、定期的にflushしてディスクに書くタイミングを与える。
		// これにより、MCが突然死した場合も、ある程度のログ内容は保証したい。
		Opened,

		// ファイルクローズ後の状態	：ユーザー名が変更されるまでは、直接ファイルに書き込む。
		// 通信のタイミングによって、サーバ切断後のメッセージが来た場合の対処を考慮している（未検証）
		// また、プレイヤー名やサーバ名など、ファイル名作成に必要な情報がそろっている事が条件
		// プレイヤー名等が取得できなくなったor変更された時点で、Initialize状態へ移行する。
		ClosedAfterglow
	}

	public interface FileOperationCompletedListener extends EventListener
	{
		public void onOpenFileOperationCompleted(FileOperationCompletedEvent e);
		public void onCloseFileOperationCompleted(FileOperationCompletedEvent e);
	}

	public class FileOperationCompletedEvent extends EventObject
	{
		private String filename;

		public FileOperationCompletedEvent(Object source, String filename)
		{
			super(source);
			this.filename = filename;
		}

		public String getFileName()
		{
			return this.filename;
		}
	}

	public SpecialLogWriter(ChatLoggerConfiguration config, FileOperationCompletedListener listener)
	{
		this.config = config;
		this.listener = listener;
		this.logfilemanager = new LogFileNameManager(config);
		this.datetimeformat = new SimpleDateFormat(this.config.getFormatDateTime().get());
		this.buffer.clear();
	}

	private String getPlayerName()
	{
		String result = null;
		Minecraft mc = Minecraft.getMinecraft();
		if((mc != null) && (mc.thePlayer != null))
		{
			result = mc.thePlayer.getEntityName();
		}
		return result;
	}

	private void onCheckPlayerChenged()
	{
		boolean execute = false;
		String newPlayerName = getPlayerName();

		switch(this.state)
		{
			case Initialize:
				// 初期化中は、無効な名前は除外する。
				if((newPlayerName != null) && (newPlayerName.length() >= 1)
					&& (!newPlayerName.equals(this.logfilemanager.getPlayerName())))
				{
					execute = true;
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
					execute = true;
				}
				else if(!newPlayerName.equals(this.logfilemanager.getPlayerName()))
				{
					execute = true;
				}
				break;

			default:
				break;
		}

		if(execute)
		{
			DebugLog.info("logger.onCheckPlayerChenged(true)");

			// プレイヤーが変わったら、ログファイルも変更する。
			this.logfilemanager.setPlayerName(newPlayerName);
			this.logfilemanager.setFileBaseDate(LogFileNameManager.makeDate());

			this.state = WriterState.Initialize;

			if(this.logfilemanager.isState())
			{
				this.logfile = this.logfilemanager.getFullPathLogFile();
				File dir = this.logfile.getParentFile();
				if(!dir.exists())
				{
					dir.mkdirs();
				}
			}
		}
	}

	public void setServerName(String servername)
	{
		this.logfilemanager.setServerName(servername);
	}

	public void setWorldName(String worldname)
	{
		this.logfilemanager.setWorldName(worldname);
	}

	public void open()
	{
		switch(this.state)
		{
			case Initialize:
			case ClosedAfterglow:
				onCheckPlayerChenged();

				if(this.logfilemanager.isState())
				{
					try
					{
						this.fos = new FileOutputStream(this.logfile, true);
						this.osw = new OutputStreamWriter(this.fos, "UTF-8");
						this.bw = new BufferedWriter(this.osw);
						this.pw = new PrintWriter(this.bw);

						this.state = WriterState.Opened;

						DebugLog.info("logger.open(logfile)");
					}
					catch(Exception e)
					{
						DebugLog.log(Level.SEVERE, e, "Failed to open the chat log file.");
						e.printStackTrace();
					}

					if(this.state == WriterState.Opened)
					{
						// ヘッダーの出力
						this.pw.println("--------------------------------------------------------------------------------");

						StringBuffer message = new StringBuffer();
						message.append(datetimeformat.format(this.logfilemanager.getFileBaseDate()));
						message.append(" : ");
						message.append("ChatLoggerPlus Logging start. (");
						message.append(this.logfilemanager.getServerName());
						message.append(" - ");
						message.append(this.logfilemanager.getPlayerName());
						message.append(")");

						this.pw.println(message.toString());
						this.pw.println("--------------------------------------------------------------------------------");

						// バッファの出力
						for(String buffermessage : this.buffer)
						{
							this.println_write(buffermessage);
						}
						this.pw.flush();

						this.buffer.clear();

						if(this.listener != null)
						{
							String filename = "";
							try
							{
								filename = this.logfile.getCanonicalPath();
								FileOperationCompletedEvent e = new FileOperationCompletedEvent(this, filename);
								this.listener.onOpenFileOperationCompleted(e);
							}
							catch(Exception e1)
							{
								DebugLog.log(Level.SEVERE, e1, "Failed to get file name.");
								e1.printStackTrace();
							}
						}
					}
				}
				break;

			default:
				// 無視する
				break;
		}
	}

	private void println_write(String message)
	{
		String output_message = new String(message);
		if(this.config.getFillColorCodeEnabled().get())
		{
			String regex = this.config.getFillColorCodeRegex().get();
			String replace = this.config.getFillColorCodeReplace().get();
			output_message = output_message.replaceAll(regex, replace);
		}
		this.pw.println(output_message);

		this.isflush = true;
		this.output_count = (this.output_count >= Integer.MAX_VALUE) ? 0 : this.output_count + 1;
		if((this.output_count % 10) == 0)
		{
			this.flush();
		}
	}

	public void write(String output)
	{
		StringBuffer message = new StringBuffer();
		message.append(datetimeformat.format(LogFileNameManager.makeDate()));
		message.append(" : ");
		message.append(output);

		switch(this.state)
		{
			case Initialize:
				DebugLog.info("logger.write(buffer): " + message.toString());
				this.buffer.add(message.toString());

				// オープンを試みる。
				this.open();
				break;

			case Opened:
				DebugLog.info("logger.write(logfile): " + message.toString());
				println_write(message.toString());
				break;

			case ClosedAfterglow:
				// 毎回書き込むたびに、open/closeする。
				this.open();
				this.write(output);
				this.close();

				onCheckPlayerChenged();
				break;

			default:
				break;
		}
	}

	public void flush()
	{
		switch(this.state)
		{
			case Opened:
				if(this.isflush)
				{
					this.pw.flush();
					DebugLog.info("logger.flush()");

					this.isflush = false;
				}
				break;

			case ClosedAfterglow:
				onCheckPlayerChenged();
				break;

			default:
				break;
		}
	}

	public void close()
	{
		switch(this.state)
		{
			case Initialize:
				break;

			case Opened:
				this.state = WriterState.ClosedAfterglow;

				prudent_close(this.pw);
				prudent_close(this.bw);
				prudent_close(this.osw);
				prudent_close(this.fos);
				DebugLog.info("logger.close()");
				this.isflush = false;

				if(this.listener != null)
				{
					String filename = "";
					try
					{
						filename = this.logfile.getCanonicalPath();
						FileOperationCompletedEvent e = new FileOperationCompletedEvent(this, filename);
						this.listener.onOpenFileOperationCompleted(e);
					}
					catch(Exception e1)
					{
						DebugLog.log(Level.SEVERE, e1, "Failed to get file name.");
						e1.printStackTrace();
					}
				}
				break;

			case ClosedAfterglow:
				break;

			default:
				break;
		}
	}

	private void prudent_close(Closeable object)
	{
		try
		{
			if(object != null)
			{
				object.close();
			}
		}
		catch(Exception e)
		{
			DebugLog.log(Level.SEVERE, e, "Failed to close the chat log file.");
			e.printStackTrace();
		}
		finally
		{
			object = null;
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		try
		{
			super.finalize();
		}
		finally
		{
			dispose();
		}
	}

	private boolean disposed = false;
	private void dispose()
	{
		if(!disposed)
		{
			// finalizeでcloseが走ることは期待しないが、GCの時に片づけられるように、念のため呼び出しておく。
			// 通常は、エラーで閉じるタイミングを失ったとしても、JVMによって閉じられるはず。
			this.close();
			disposed = true;
		}
	}
}
