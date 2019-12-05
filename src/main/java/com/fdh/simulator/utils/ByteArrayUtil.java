/**
 * 
 */
package com.fdh.simulator.utils;


import java.util.List;
import java.util.Locale;

public class ByteArrayUtil {
	/**
	 * 小端转大端
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] littleToBig(byte[] bytes) {
		if (bytes == null) {
			throw new NullPointerException();
		}
		byte[] temp = new byte[bytes.length];
		for (int i = bytes.length - 1; i >= 0; i--) {
			temp[i] = bytes[bytes.length - 1 - i];
		}
		return temp;
	}
	
	/**
	 * 将byte转为十六进制字符串，并大写
	 * 
	 * @param b
	 * @return
	 */
	public static String Bytes2HexString(byte b){
	    String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return "0x" + hex.toUpperCase();
	}

	/**
	 * 大端转小端
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] bigToLittle(byte[] bytes) {
		return littleToBig(bytes);
	}

	/**
	 * 大端转字节
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte toByte(byte[] bytes) {
		return bytes[0];
	}

	/**
	 * 字节转字符
	 * 
	 * @param b
	 * @return
	 */
	public static char toChar(byte b) {
		return (char) (b & 0xff);
	}

	/**
	 * 字符转字节数组
	 * 
	 * @param ch
	 * @return
	 */
	public static byte[] toBytes(char ch) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (ch >> 8 & 0xff);
		bytes[1] = (byte) (ch & 0xff);
		return bytes;
	}

	/**
	 * 字符转字节
	 * 
	 * @param ch
	 * @return
	 */
	public static byte toLittleBytes(char ch) {

		return (byte) ch;
	}

	/**
	 * 字节数组转字符（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static char toChar(byte[] bytes) {
		return (char) ((bytes[0] << 8 & 0xff00) | (bytes[1] & 0xff));
	}

	/**
	 * 字节数组转字符
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static char toChar(byte[] bytes, int offset) {
		return (char) ((bytes[offset] << 8 & 0xff00) | (bytes[offset + 1] & 0xff));
	}

	/**
	 * 字节数组转字符数组
	 * 
	 * @param b
	 * @return
	 */
	public static char[] toCharArray(byte[] b) {
		char[] chs = new char[b.length];
		int offset = 0;
		for (int i = 0; i < b.length; i++) {
			chs[i] = toChar(b, offset);
			offset += 2;
		}
		return chs;
	}

	/**
	 * 字节数组转字符数组
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static char[] toCharArray(byte[] bytes, int srcPos) {
		char[] chs = new char[(bytes.length - srcPos) >> 1];
		int offset = 0;
		for (int i = 0; i < chs.length; i++) {
			chs[i] = toChar(bytes, srcPos + offset);
			offset += 2;
		}
		return chs;
	}

	/**
	 * 字节数组转字符数组
	 * 
	 * @param bytes
	 * @param srcPos
	 * 
	 * @param charCount
	 * 
	 * @return
	 */
	public static char[] toCharArray(byte[] bytes, int srcPos, int charCount) {
		char[] chs = new char[charCount];
		int offset = 0;
		for (int i = 0; i < charCount; i++) {
			chs[i] = toChar(bytes, srcPos + offset);
			offset += 2;
		}
		return chs;
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @see #unsignedCharToShort(byte)
	 */
	public static char[] littleToCharArray(byte[] bytes) {
		char[] charArray = new char[bytes.length];
		for (int i = 0; i < charArray.length; i++) {
			charArray[i] = toChar(bytes[i]);
		}
		return charArray;
	}

	/**
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static char[] littleToCharArray(byte[] bytes, int srcPos, int count) {
		char[] charArray = new char[count];
		for (int i = 0; i < count; i++) {
			charArray[i] = toChar(bytes[srcPos + i]);
		}
		return charArray;
	}

	/**
	 * 整型转字节数组（大端）
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] toBytes(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (i >> 24 & 0xff);
		bytes[1] = (byte) (i >> 16 & 0xff);
		bytes[2] = (byte) (i >> 8 & 0xff);
		bytes[3] = (byte) (i & 0xff);
		return bytes;
	}

	/**
	 * 整型转字节数组（小端）
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] toLittleBytes(int i) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (i >> 24 & 0xff);
		bytes[2] = (byte) (i >> 16 & 0xff);
		bytes[1] = (byte) (i >> 8 & 0xff);
		bytes[0] = (byte) (i & 0xff);
		return bytes;
	}

	/**
	 * 字节数组转整型（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		return toInt(bytes, 0);
	}

	/**
	 * 字节数组转整型（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static int toInt(byte[] bytes, int off) {
		return toInt(bytes, off, bytes.length);
	}

	/**
	 * 字节数组转整型（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param len
	 * @return
	 */
	public static int toInt(byte[] bytes, int off, int len) {
		if (len == 0)
			return 0;
		int rt = 0;
		len = len > 4 ? 4 : len;
		int m = 0;
		for (int i = off + len - 1; i >= off && i >= 0; i--) {
			rt += (bytes[i] & 0xff) << m;
			m += 8;
		}
		return rt;
	}
	
	public static String toIntStr(byte[] bytes,String split){
		StringBuffer sb = new StringBuffer("");
		split = (split==null?"":split);
		for(int i=0;i<bytes.length;i++){
			sb.append((i>0?split:"")+String.valueOf(bytes[i]&0xff));
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static long unsignedIntToLong(byte[] bytes, int srcPos) {
		long rt = 0;
		rt += (long) (bytes[srcPos + 3] & 0xff) << 24;
		rt += (long) (bytes[srcPos + 2] & 0xff) << 16;
		rt += (long) (bytes[srcPos + 1] & 0xff) << 8;
		rt += (long) (bytes[srcPos] & 0xff);
		return rt;
	}

	/**
	 * 字节数组转整型数组（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static int[] toIntArray(byte[] bytes) {
		int[] intArray = new int[bytes.length >> 2];
		int offset = 0;
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = toInt(bytes, offset);
			offset += 4;
		}
		return intArray;
	}

	/**
	 * 字节数组转整型数组（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static int[] toIntArray(byte[] bytes, int srcPos, int count) {
		int[] intArray = new int[count];
		int offset = 0;
		for (int i = 0; i < count; i++) {
			intArray[i] = toInt(bytes, offset + srcPos);
			offset += 4;
		}
		return intArray;
	}

	/**
	 * 字节数组转整型数组（小端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static int[] littleToIntArray(byte[] bytes) {
		int[] intArray = new int[bytes.length >> 2];
		int offset = 0;
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = 0;
			intArray[i] += (bytes[offset + 3] & 0xff) << 24;
			intArray[i] += (bytes[offset + 2] & 0xff) << 16;
			intArray[i] += (bytes[offset + 1] & 0xff) << 8;
			intArray[i] += (bytes[offset + 0] & 0xff);
			offset += 4;
		}
		return intArray;
	}

	/**
	 * 字节数组转整型数组（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static int[] littleToIntArray(byte[] bytes, int srcPos, int count) {
		int[] intArray = new int[count];
		int offset = 0;
		for (int i = 0; i < count; i++) {
			intArray[i] = 0;
			int index = offset + srcPos;
			intArray[i] += (bytes[index + 3] & 0xff) << 24;
			intArray[i] += (bytes[index + 2] & 0xff) << 16;
			intArray[i] += (bytes[index + 1] & 0xff) << 8;
			intArray[i] += (bytes[index + 0] & 0xff);
			offset += 4;
		}
		return intArray;
	}

	/**
	 * 字节数组转整型（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static int littleToInt(byte[] bytes) {
		return littleToInt(bytes, bytes.length);
	}

	/**
	 * 字节数组转整型（小端）
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static int littleToInt(byte[] bytes, int offset) {
		return littleToInt(bytes, offset, bytes.length);

	}

	/**
	 * 字节数组转整型（小端）
	 * 
	 * @param bytes
	 * @param off
	 * @param len
	 * @return
	 */
	public static int littleToInt(byte[] bytes, int off, int len) {
		if (len == 0)
			return 0;
		int rt = 0;
		len = len > 4 ? 4 : len;
		int m = 0;
		for (int i = off; i < off + len; i++) {
			rt += (bytes[i] & 0xff) << m;
			m += 8;
		}
		return rt;
	}

	/**
	 * 短整型转字节数组(大端)
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] toBytes(short s) {
		byte[] bytes = new byte[2];
		bytes[0] = (byte) (s >> 8 & 0xff);
		bytes[1] = (byte) (s & 0xff);
		return bytes;
	}

	/**
	 * 短整型转字节数组(小端)
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] toLittleBytes(short s) {
		byte[] bytes = new byte[2];
		bytes[1] = (byte) (s >> 8 & 0xff);
		bytes[0] = (byte) (s & 0xff);
		return bytes;
	}

	/**
	 * 短整型转无符号字符
	 * 
	 * @param s
	 * @return
	 */
	public static byte toUnsignedChar(short s) {
		return (byte) s;
	}

	public static short toShort(byte data) {
		return (short) (data & 0xff);
	}

	/**
	 * 字节数组转短整型（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte[] bytes) {
		return toShort(bytes, bytes.length);
	}

	/**
	 * 字节数组转短整型（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static short toShort(byte[] bytes, int srcPos) {
		return toShort(bytes, 0, srcPos);
	}

	/**
	 * 字节数组转短整型（大端）
	 * 
	 * @param bytes
	 * @param off
	 * @param len
	 * @return
	 */
	public static short toShort(byte[] bytes, int off, int len) {
		if (len == 0)
			return 0;
		short rt = 0;
		if (len == 1) {
//			rt = (short) bytes[off];
			rt = (short)(bytes[off] & 0xff);
		}
		else if (len == 2) {
			rt += (bytes[off] << 8 & 0xff00);
			rt += (bytes[off + 1] & 0xff);
		}
		return rt;
	}

	/**
	 * 无符号短整型转整形
	 * 
	 * @param bytes
	 * @return
	 */
	public static int unsignedShortToInt(byte[] bytes) {
		int rt = 0;
		rt += (int) (bytes[1] & 0xff) << 8;
		rt += (int) (bytes[0] & 0xff);
		return rt;
	}

	/**
	 * 无符号短整型转整形
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static int unsignedShortToInt(byte[] bytes, int srcPos) {
		int rt = 0;
		rt += (int) (bytes[srcPos + 1] & 0xff) << 8;
		rt += (int) (bytes[srcPos] & 0xff);
		return rt;
	}

	/**
	 * 无符号字符型转短整形
	 * 
	 * @param b
	 * @return
	 */
	public static short unsignedCharToShort(byte b) {
		return (short) (b & 0xff);
	}

	/**
	 * 字节数组转短整型数组（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static short[] toShortArray(byte[] bytes) {
		short[] shortArray = new short[bytes.length >> 1];
		int offset = 0;
		for (int i = 0; i < shortArray.length; i++) {
			shortArray[i] = toShort(bytes, offset);
			offset += 4;
		}
		return shortArray;
	}

	/**
	 * 字节数组转短整型数组（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static short[] toShortArray(byte[] bytes, int srcPos, int count) {
		short[] shortArray = new short[count];
		int offset = 0;
		for (int i = 0; i < count; i++) {
			shortArray[i] = toShort(bytes, srcPos + offset);
			offset += 4;
		}
		return shortArray;
	}

	/**
	 * 字节数组转短整型（小端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static short littleToShort(byte[] bytes) {
		return littleToShort(bytes, 0);
	}

	public static short littleToShort(byte data) {
		return toShort(data);
	}

	/**
	 * 字节数组转短整型（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static short littleToShort(byte[] bytes, int offset) {
		return littleToShort(bytes, offset, bytes.length);
	}

	/**
	 * 字节数组转短整型（小端）
	 * 
	 * @param bytes
	 * @param offset
	 * @param len
	 * @return
	 */
	public static short littleToShort(byte[] bytes, int offset, int len) {
		short rt = 0;
		if (len > 1)
			rt += (bytes[offset + 1] << 8 & 0xff00);
		if (len > 0)
			rt += (bytes[offset] & 0xff);
		return rt;
	}

	/**
	 * 字节数组转短整型数组（小端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static short[] littleToShortArray(byte[] bytes) {
		short[] shortArray = new short[bytes.length >> 1];
		int offset = 0;
		for (int i = 0; i < shortArray.length; i++) {
			shortArray[i] = littleToShort(bytes, offset);
			offset += 4;
		}
		return shortArray;
	}

	/**
	 * 字节数组转短整型数组（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static short[] littleToShortArray(byte[] bytes, int srcPos, int count) {
		short[] shortArray = new short[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			shortArray[i] = littleToShort(bytes, offset);
			offset += 4;
		}
		return shortArray;
	}

	/**
	 * 长整型转字节数组
	 * 
	 * @param l
	 * @return
	 */
	public static byte[] toBytes(long l) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (l >> 56 & 0xff);
		bytes[1] = (byte) (l >> 48 & 0xff);
		bytes[2] = (byte) (l >> 40 & 0xff);
		bytes[3] = (byte) (l >> 32 & 0xff);
		bytes[4] = (byte) (l >> 24 & 0xff);
		bytes[5] = (byte) (l >> 16 & 0xff);
		bytes[6] = (byte) (l >> 8 & 0xff);
		bytes[7] = (byte) (l >> 0 & 0xff);
		return bytes;
	}

	/**
	 * 长整型转字节数组
	 * 
	 * @param l
	 * @param len
	 * @return
	 */
	public static byte[] toBytes(long l, int len) {
		byte[] bytes = new byte[len];
		if (len - 8 > 0)
			bytes[len - 8] = (byte) (l >> 56 & 0xff);
		if (len - 7 > 0)
			bytes[len - 7] = (byte) (l >> 48 & 0xff);
		if (len - 6 > 0)
			bytes[len - 6] = (byte) (l >> 40 & 0xff);
		if (len - 5 > 0)
			bytes[len - 5] = (byte) (l >> 32 & 0xff);
		if (len - 4 > 0)
			bytes[len - 4] = (byte) (l >> 24 & 0xff);
		if (len - 3 > 0)
			bytes[len - 3] = (byte) (l >> 16 & 0xff);
		if (len - 2 > 0)
			bytes[len - 2] = (byte) (l >> 8 & 0xff);
		if (len - 1 > 0)
			bytes[len - 1] = (byte) (l >> 0 & 0xff);
		return bytes;
	}

	/**
	 * 长整型转字节数组
	 * 
	 * @param l
	 * @return
	 */
	public static byte[] toLittleBytes(long l) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (l >> 24 & 0xff);
		bytes[2] = (byte) (l >> 16 & 0xff);
		bytes[1] = (byte) (l >> 8 & 0xff);
		bytes[0] = (byte) (l >> 0 & 0xff);
		return bytes;
	}

	/**
	 * 长整型转字节数组
	 * 
	 * @param l
	 * @return
	 */
	public static byte[] toUnsignedLong(long l) {
		return toLittleBytes(l);
	}

	/**
	 * 字节数组转长整型（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static long toLong(byte[] bytes) {
		return toLong(bytes, 0, bytes.length);
	}

	/**
	 * 长整型转字节数组（大端）
	 * 
	 * @param bytes
	 * @param off
	 * @return
	 */
	public static long toLong(byte[] bytes, int off) {
		/*
		 * return (((long) bytes[0] & 0xff) << 56) | (((long) bytes[1] & 0xff)
		 * << 48) | (((long) bytes[2] & 0xff) << 40) | (((long) bytes[3] & 0xff)
		 * << 32) | (((long) bytes[4] & 0xff) << 24) | (((long) bytes[5] & 0xff)
		 * << 16) | (((long) bytes[6] & 0xff) << 8) | (((long) bytes[7] & 0xff)
		 * << 0);
		 */
		return toLong(bytes, off, bytes.length);
	}

	/**
	 * 长整型转字节数组（大端）
	 * 
	 * @param bytes
	 * @param off
	 * @param len
	 * @return
	 */
	public static long toLong(byte[] bytes, int off, int len) {
		if (len == 0)
			return 0L;
		long rt = 0;
		len = len > 8 ? 8 : len;
		int m = 0;
		for (int i = off + len - 1; i >= off && i > 0; i--) {
			rt += ((long) (bytes[i]) & 0xff) << m;
			m += 8;
		}
		return rt;
	}

	/**
	 * 字节数组转长整型数组（大端）
	 * 
	 * @param bytes
	 * @return
	 */
	public static long[] toLongArray(byte[] bytes) {
		long[] longArray = new long[bytes.length >> 3];
		int offset = 0;
		for (int i = 0; i < longArray.length; i++) {
			longArray[i] = toLong(bytes, offset);
			offset += 8;
		}
		return longArray;
	}

	/**
	 * 字节数组转长整型数组（大端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static long[] toLongArray(byte[] bytes, int srcPos, int count) {
		long[] longArray = new long[count];
		int offset = srcPos;
		for (int i = 0; i < longArray.length; i++) {
			longArray[i] = toLong(bytes, offset);
			offset += 8;
		}
		return longArray;
	}

	/**
	 * 字节数组转长整型（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */

	public static long littleToLong(byte[] bytes) {
		return littleToLong(bytes, 0);
	}

	/**
	 * 字节数组转长整型（小端）
	 * 
	 * @param bytes
	 * @param offset
	 * @return
	 */
	public static long littleToLong(byte[] bytes, int offset) {
		return littleToLong(bytes, 0, offset);
	}

	/**
	 * 字节数组转长整型（小端）
	 * 
	 * @param bytes
	 * @param off
	 * @param len
	 * @return
	 */
	public static long littleToLong(byte[] bytes, int off, int len) {
		if (len == 0)
			return 0;
		long rt = 0;
		len = len > 8 ? 8 : len;
		int m = 0;
		for (int i = off; i < off + len; i++) {
			rt += ((long) bytes[i] & 0xff) << m;
			m += 8;
		}
		return rt;
	}

	/**
	 * 字节数组转长整型
	 * 
	 * @param bytes
	 * @return
	 */
	public static long unsignedLongToLong(byte[] bytes) {
		return (((long) bytes[3] & 0xff) << 24) | (((long) bytes[2] & 0xff) << 16) | (((long) bytes[1] & 0xff) << 8)
				| (((long) bytes[0] & 0xff) << 0);
	}

	/**
	 * 字节数组转长整型
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static long unsignedLongToLong(byte[] bytes, int srcPos) {
		return (((long) bytes[srcPos + 3] & 0xff) << 24) | (((long) bytes[srcPos + 2] & 0xff) << 16)
				| (((long) bytes[srcPos + 1] & 0xff) << 8) | (((long) bytes[srcPos] & 0xff) << 0);
	}

	/**
	 * 字节数组转长整型数组（小端）
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static long[] littleToLongArray(byte[] bytes, int srcPos, int count) {
		long[] longArray = new long[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			longArray[i] = littleToLong(bytes, offset);
			offset += 8;
		}
		return longArray;
	}

	/**
	 * 
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] toBytes(float f) {
		return toBytes(Float.floatToIntBits(f));
	}

	public static byte[] toLittleBytes(float f) {
		return toLittleBytes(Float.floatToIntBits(f));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static float toFloat(byte[] bytes) {
		return Float.intBitsToFloat(toInt(bytes));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static float toFloat(byte[] bytes, int srcPos) {
		return Float.intBitsToFloat(toInt(bytes, srcPos));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static float[] toFloatArray(byte[] bytes) {
		float[] floatArray = new float[bytes.length >> 2];
		int offset = 0;
		for (int i = 0; i < floatArray.length; i++) {
			floatArray[i] = toFloat(bytes, offset);
			offset += 4;
		}
		return floatArray;
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static float[] toFloatArray(byte[] bytes, int srcPos, int count) {
		float[] floatArray = new float[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			floatArray[i] = toFloat(bytes, offset);
			offset += 4;
		}
		return floatArray;
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static float littleToFloat(byte[] bytes) {
		return toFloat(littleToBig(bytes));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static float littleToFloat(byte[] bytes, int srcPos) {
		return Float.intBitsToFloat(littleToInt(bytes, srcPos));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static float[] littleToFlotArray(byte[] bytes, int srcPos, int count) {
		float[] floatArray = new float[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			floatArray[i] = littleToFloat(bytes, offset);
			offset += 4;
		}
		return floatArray;
	}

	/**
	 * 
	 * 
	 * @param d
	 * @return
	 */
	public static byte[] toBytes(double d) {
		return toBytes(Double.doubleToLongBits(d));
	}

	public static byte[] toLittleBytes(double d) {
		return toLittleBytes(Double.doubleToLongBits(d));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static double toDouble(byte[] bytes, int srcPos) {
		return Double.longBitsToDouble(toLong(bytes, srcPos));
	}

	public static double toDouble(byte[] bytes, int srcPos, int len) {
		return Double.longBitsToDouble(toLong(bytes, srcPos, len));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static double[] toDoubleArray(byte[] bytes) {
		double[] doubleArray = new double[bytes.length >> 3];
		int offset = 0;
		for (int i = 0; i < doubleArray.length; i++) {
			doubleArray[i] = toDouble(bytes, offset);
			offset += 8;
		}
		return doubleArray;
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static double[] toDoubleArray(byte[] bytes, int srcPos, int count) {
		double[] doubleArray = new double[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			doubleArray[i] = toDouble(bytes, offset);
			offset += 8;
		}
		return doubleArray;
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @return
	 */
	public static double littleToDouble(byte[] bytes) {
		return toDouble(littleToBig(bytes));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @return
	 */
	public static double littleToDouble(byte[] bytes, int srcPos) {
		return Double.longBitsToDouble(littleToLong(bytes, srcPos));
	}

	/**
	 * 
	 * 
	 * @param bytes
	 * @param srcPos
	 * @param count
	 * @return
	 */
	public static double[] littleToDoubleArray(byte[] bytes, int srcPos, int count) {
		double[] doubleArray = new double[count];
		int offset = srcPos;
		for (int i = 0; i < count; i++) {
			doubleArray[i] = littleToDouble(bytes, offset);
			offset += 8;
		}
		return doubleArray;
	}

	/**
	 * 转16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String toHexString(byte[] src) {
		return toHexString(src, 0, src.length);
	}

	/**
	 * 转16进制字符串
	 * 
	 * @param src
	 * @param offset
	 * @param len
	 * @return
	 */
	public static String toHexString(byte[] src, int offset, int len) {
		return toHexString(src,offset,len,"");
	}
	
	public static String toHexString(byte[] src, int offset, int len,String split) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || len <= 0) {
			return null;
		}
		split = (split==null?"":split);
		for (int i = offset; i < len; i++) {
			stringBuilder.append((i>offset?split:"")+toHexString(src[i]));
		}
		return stringBuilder.toString().trim();
	}

	public static String toHexString(byte data) {
		int v = data & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			return "0" + hv;
		}
		return hv;
	}

	/**
	 * 16进制字符串转字节数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		hexString = hexString.replace(" ", "");
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase(Locale.CHINA);
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String toBinary(byte... bytes) {
		if (bytes == null || bytes.length == 0)
			return null;
		String value = "", tmp = "";
		for (byte item : bytes) {
			tmp = Integer.toBinaryString(item & 0xFF);
			for (int i = tmp.length(); i < 8; i++)
				tmp = "0" + tmp;
			value += tmp;
		}
		return value;
	}

	public static boolean bitIsOne(byte src, int bit) {
		if (bit > 7 || bit < 0)
			return false;
		int x = Double.valueOf(Math.pow(2, bit)).intValue();
		if ((src & x) == x)
			return true;
		return false;
	}

	public static int getUnsignedByte(byte data) { // 将data字节型数据转换为0~255 (0xFF
		// 即BYTE)。
		return data & 0x0FF;
	}

	public static int getUnsignedShort(short data) { // 将data字节型数据转换为0~65535
		// (0xFFFF 即 WORD)。
		return data & 0x0FFFF;
	}

	public static long getUnsignedIntt(int data) { // 将int数据转换为0~4294967295
		// (0xFFFFFFFF即DWORD)。
		return data & 0x0FFFFFFFFl;
	}

	/**
	 * Byte转Bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) 
				+ (byte) ((b >> 6) & 0x1) 
				+ (byte) ((b >> 5) & 0x1)
				+ (byte) ((b >> 4) & 0x1) 
				+ (byte) ((b >> 3) & 0x1) 
				+ (byte) ((b >> 2) & 0x1) 
				+ (byte) ((b >> 1) & 0x1)
				+ (byte) ((b >> 0) & 0x1);
	}
	
	/**
	 * Byte转Bit
	 */
	public static int[] byteToBitArray(byte b) {
		int[] array = new int[8];
		array[7] = ((b >> 7) & 0x1);
		array[6] = ((b >> 6) & 0x1);
		array[5] = ((b >> 5) & 0x1);
		array[4] = ((b >> 4) & 0x1);
		array[3] = ((b >> 3) & 0x1);
		array[2] = ((b >> 2) & 0x1);
		array[1] = ((b >> 1) & 0x1);
		array[0] = ((b >> 0) & 0x1);
		return array;
	}

	/**
	 * Bit转Byte
	 */
	public static byte bitToByte(String byteStr) {
		int re, len;
		if (null == byteStr) {
			return 0;
		}
		len = byteStr.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit处理
			if (byteStr.charAt(0) == '0') {// 正数
				re = Integer.parseInt(byteStr, 2);
			}
			else {// 负数
				re = Integer.parseInt(byteStr, 2) - 256;
			}
		}
		else {// 4 bit处理
			re = Integer.parseInt(byteStr, 2);
		}
		return (byte) re;
	}

	/*
	 * bit 转 字节数组
	 */
	private byte[] bitToBytes(String bin) {
		int value, len, idx;
		len = bin.length() / 8;
		if ((bin.length() % 8) > 0)
			len++;
		byte[] b = new byte[len];
		for (int i = len - 1; i >= 0; i--) {
			if (bin.length() > 8) {
				idx = bin.length() - 8;
				value = Integer.valueOf(bin.substring(idx, idx + 8), 2).intValue();
				b[i] = (byte) (value & 0xFF);
				bin = bin.substring(0, bin.length() - 8);
			}
			else {
				value = Integer.valueOf(bin, 2).intValue();
				b[i] = (byte) (value & 0xFF);
				break;
			}
		}
		return b;
	}

	/**
	 * 字符串转ascii byte数组
	 * 
	 * @return
	 */
	public static byte[] strToAscii(String str) {
		if (str == null)
			return null;
		byte[] bytes = new byte[str.length()];
		char[] chars = str.toCharArray(); // 把字符中转换为字符数组
		for (int i = 0; i < chars.length; i++) {// 输出结果
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}

	public static String asciiToStr(byte[] bytes) {
		if (bytes == null || bytes.length < 0)
			return "";
		StringBuilder sBuilder = new StringBuilder();
		// ascii转String
		for (int j = 0; j < bytes.length; j++) {
			sBuilder.append((char)bytes[j]);
		}
		return sBuilder.toString();
	}
	
	public static String asciiToStrSplit(byte[] bytes) {
        if (bytes == null || bytes.length < 0)
            return "";
        StringBuilder sBuilder = new StringBuilder();
        // ascii转String
        for (int j = 0; j < bytes.length; j++) {
            sBuilder.append(bytes[j]);
            sBuilder.append(".");
        }
        String string = sBuilder.toString();
        return string.substring(0, string.length()-1);
    }
	   /**
     * List<Integer>转为byte[]
     * 
     * @param list
     * @return
     */
    public static byte[] getByteByList(List<Integer> list) {
        int len = list.size();
        byte[] bytes = null;
        if (list != null && len > 0) {
            bytes = new byte[len];
            for (int j = 0; j < list.size(); j++) {
                bytes[j] = ByteArrayUtil.toByte(list.get(j));
            }
        }
        return bytes;
    }
    
    /**
     * 整型转byte
     * 
     * @param i
     * @return
     */
    public static byte toByte(int i) {
        byte bytes;
        bytes = (byte) (i & 0xff);
        return bytes;
    }

	//System.arraycopy()方法
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){
		byte[] bt3 = new byte[bt1.length+bt2.length];
		System.arraycopy(bt1, 0, bt3, 0, bt1.length);
		System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
		return bt3;
	}

}