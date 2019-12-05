package com.fdh.simulator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fdh.simulator.Simulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Simulator.class);
	public static final int HEAD_LENGHT = 16;

	public static String getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public enum PrinterStatus {
		Normal, OutOfPaper, Busy, OtherError
	}

	/**
	 * 
	 * @Description: TODO
	 * @param
	 * @return String
	 */
	public static String bytesToHexString(byte[] src, int datalength) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < datalength; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv).append(" ");
		}
		return stringBuilder.toString();
	}
	
	public static byte[] getOrialByte(byte[] heartbeartPacket) {
		byte[] test = new byte[heartbeartPacket.length];
		for (int i = 0; i < test.length; i++) {
			test[i] = (byte) (heartbeartPacket[i]^0x53);
		}
		return test;
	}
	
}
