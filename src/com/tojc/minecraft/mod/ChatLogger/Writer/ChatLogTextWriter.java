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

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.LogFileNameManager;
import com.tojc.minecraft.mod.ChatLogger.Writer.WriterStatusController.WriterState;
import com.tojc.minecraft.mod.log.DebugLog;

public class ChatLogTextWriter implements WriterInterface
{
	private ChatLoggerConfiguration config = null;
	private LogFileNameManager manager = null;

	private File logfile = null;
	private boolean fileopen = false;

	private PrintWriter pw = null;
	private BufferedWriter bw = null;
	private OutputStreamWriter osw = null;
	private FileOutputStream fos = null;

	private List<String> buffer = new LinkedList<String>();

	private int output_count = 0;

	private SimpleDateFormat datetimeformat = null;

	public ChatLogTextWriter(ChatLoggerConfiguration config)
	{
		this.config = config;
		this.manager = new LogFileNameManager(this.config);
		this.buffer.clear();
		this.datetimeformat = new SimpleDateFormat(this.config.getFormatDateTime().get());
	}

	@Override
	public LogFileNameManager getLogFileNameManager()
	{
		return this.manager;
	}

	@Override
	public boolean onOpen()
	{
		boolean result = false;

		if(this.fileopen)
		{
			throw new IllegalStateException("File has already been opened.");
		}

		if(this.manager.isState())
		{
			this.logfile = this.manager.getFullPathLogFile();
			File dir = this.logfile.getParentFile();
			if(!dir.exists())
			{
				dir.mkdirs();
			}

			try
			{
				this.fos = new FileOutputStream(this.logfile, true);
				this.osw = new OutputStreamWriter(this.fos, "UTF-8");
				this.bw = new BufferedWriter(this.osw);
				this.pw = new PrintWriter(this.bw);

				this.fileopen = true;

				result = true;

				DebugLog.trace("logger.open(logfile)");
			}
			catch(Exception e)
			{
				DebugLog.error(e, "Failed to open the chat log file.");
			}
		}
		return result;
	}

	@Override
	public void onEnqueueBuffer(String output)
	{
		if(this.fileopen)
		{
			throw new IllegalStateException("File has already been opened.");
		}

		if(output != null)
		{
			String line = createLineString(output);

			DebugLog.trace("logger.write(buffer): " + line);
			this.buffer.add(line);
		}
	}

	@Override
	public void onWrite(String output)
	{
		if(!this.fileopen)
		{
			throw new IllegalStateException("File is not open yet.");
		}

		if(output != null)
		{
			String line = createLineString(output);

			DebugLog.trace("logger.write(logfile): " + line);
			println_write(line);
		}
	}

	@Override
	public void onWriteLowLevel(String output)
	{
		if(!this.fileopen)
		{
			throw new IllegalStateException("File is not open yet.");
		}

		if(output != null)
		{
			println_write(output);
		}
	}

	@Override
	public void onDequeueBuffer()
	{
		if(!this.fileopen)
		{
			throw new IllegalStateException("File is not open yet.");
		}

		if(this.buffer.size() >= 1)
		{
			// バッファの出力
			int count = 0;
			for(String buffermessage : this.buffer)
			{
				this.println_write(buffermessage);
				count++;
			}
			DebugLog.trace("logger.write(buffer to logfile): " + count + " line.");

			this.flush(true);

			this.buffer.clear();
		}
	}

	@Override
	public void onFlush()
	{
		if(!this.fileopen)
		{
			throw new IllegalStateException("File is not open yet.");
		}

		this.flush(true);
	}

	@Override
	public boolean onClose()
	{
		boolean result = false;

		if(!this.fileopen)
		{
			throw new IllegalStateException("File is not open yet.");
		}

		this.prudent_close(this.pw);
		this.prudent_close(this.bw);
		this.prudent_close(this.osw);
		this.prudent_close(this.fos);

		this.fileopen = false;
		result = true;

		DebugLog.trace("logger.close(logfile)");
		return result;
	}


	private String createLineString(String output)
	{
		StringBuffer result = new StringBuffer();
		result.append(datetimeformat.format(LogFileNameManager.makeDate()));
		result.append(" : ");
		result.append(output);
		return result.toString();
	}

	private void println_write(String linestring)
	{
		this.pw.println(linestring);
		this.flush(false);
	}

	private void flush(boolean forcibly)
	{
		this.output_count = (this.output_count >= Integer.MAX_VALUE) ? 0 : this.output_count + 1;

		if(forcibly)
		{
			this.pw.flush();
		}
		else
		{
			if((this.output_count % 20) == 0)
			{
				this.pw.flush();
			}
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
			DebugLog.warning(e, "Failed to close the chat log file.");
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
			this.prudent_close(this.pw);
			this.prudent_close(this.bw);
			this.prudent_close(this.osw);
			this.prudent_close(this.fos);

			disposed = true;
		}
	}
}
