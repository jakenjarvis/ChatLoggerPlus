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

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;

import com.tojc.minecraft.mod.ChatLogger.ChatLoggerConfiguration;
import com.tojc.minecraft.mod.ChatLogger.LogFileNameManager;
import com.tojc.minecraft.mod.log.DebugLog;

public class WriterStatusController
{
	public enum WriterState
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

	private WriterInterface writer = null;
	private WriterOperationInterface operation = null;
	private WriterState state = null;

	public WriterStatusController(WriterInterface writer, WriterOperationInterface operation)
	{
		this.writer = writer;
		this.operation = operation;
		this.state = WriterState.Initialize;
	}


	public boolean isState()
	{
		return (this.state == WriterState.Opened);
	}
	public WriterState getState()
	{
		return this.state;
	}
	public void setState(WriterState state)
	{
		DebugLog.trace("this.state: " + this.state + " -> " + state);
		this.state = state;
	}

	public void open()
	{
		switch(this.getState())
		{
			case Initialize:
			case ClosedAfterglow:
				if(this.operation.hasPlayerChanged())
				{
					this.operation.onPlayerChenged();
					this.setState(WriterState.Initialize);
				}
				break;

			case Opened:
				break;
			default:
				break;
		}

		WriterState prestate = this.getState();

		switch(this.getState())
		{
			case Initialize:
			case ClosedAfterglow:
				boolean open = this.writer.onOpen();
				if(open)
				{
					this.setState(WriterState.Opened);
				}

				if((prestate == WriterState.Initialize) && (this.getState() == WriterState.Opened))
				{
					this.operation.onHeaderOutputTiming();
					this.writer.onDequeueBuffer();
					this.operation.onOpenFileOperationCompleted();
				}
				break;

			case Opened:
				break;
			default:
				break;
		}
	}

	public void write(String output)
	{
		switch(this.getState())
		{
			case Initialize:
				this.writer.onEnqueueBuffer(output);

				// オープンを試みる。
				this.open();
				break;

			case Opened:
				this.writer.onDequeueBuffer();
				this.writer.onWrite(output);
				break;

			case ClosedAfterglow:
				// minecraftforge-src-1.5.2-7.8.0.684にて、ネザーなどのワールド移動の時、
				// onWorldEvent_Unload()より先にonWorldEvent_Load()が発生し、Openedに戻すタイミングが無いことを確認。
				// writeのタイミングでPlayerNameが正しく取得できる場合は、Openedに戻すことにする。

				if(this.operation.hasPlayerChanged())
				{
					// 毎回書き込むたびに、open/closeする。
					this.open();
					this.write(output);
					this.close();

					// 状態が変化しているので、再チェックが必要
					if(this.operation.hasPlayerChanged())
					{
						this.operation.onPlayerChenged();
						this.setState(WriterState.Initialize);
					}
				}
				else
				{
					// ワールド移動のみで、ログアウトしていないと判断
					this.open();
					this.write(output);
					// closeしない
				}
				break;

			default:
				break;
		}
	}

	public void flush()
	{
		switch(this.getState())
		{
			case Opened:
				this.writer.onFlush();
				break;

			case ClosedAfterglow:
				if(this.operation.hasPlayerChanged())
				{
					this.operation.onPlayerChenged();
					this.setState(WriterState.Initialize);
				}
				break;

			default:
				break;
		}
	}

	public void close()
	{
		WriterState prestate = this.getState();

		switch(this.getState())
		{
			case Initialize:
				break;

			case Opened:
				boolean close = this.writer.onClose();
				if(close)
				{
					this.setState(WriterState.ClosedAfterglow);
				}

				if((prestate == WriterState.Opened) && (this.getState() == WriterState.ClosedAfterglow))
				{
					this.operation.onCloseFileOperationCompleted();
				}
				break;

			case ClosedAfterglow:
				break;

			default:
				break;
		}
	}

}
