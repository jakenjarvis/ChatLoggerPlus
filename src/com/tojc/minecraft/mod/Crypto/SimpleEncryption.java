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
package com.tojc.minecraft.mod.Crypto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SimpleEncryption
{
	public static String encrypt(String key, String text) throws Exception
	{
		SecretKeySpec spec = new SecretKeySpec(key.getBytes("UTF-8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, spec);

		byte[] bytes = cipher.doFinal(text.getBytes("UTF-8"));
		return byteArrayToHexString(bytes);
	}

	public static String decrypt(String key, String encrypted) throws Exception
	{
		SecretKeySpec spec = new SecretKeySpec(key.getBytes("UTF-8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, spec);

		byte[] bytes = hexStringToByteArray(encrypted);
		return new String(cipher.doFinal(bytes), "UTF-8");
	}

	private static byte[] hexStringToByteArray(String hex)
	{
		byte[] bytes = new byte[hex.length() / 2];
		for (int index = 0; index < bytes.length; index++)
		{
			bytes[index] = (byte)Integer.parseInt(hex.substring(index * 2, (index + 1) * 2), 16);
		}
		return bytes;
	}

	private static String byteArrayToHexString(byte[] bytes)
	{
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (int index = 0; index < bytes.length; index++)
		{
			int bt = bytes[index] & 0xff;
			if (bt < 0x10)
			{
				buffer.append("0");
			}
			buffer.append(Integer.toHexString(bt));
		}
		return buffer.toString();
	}

}
