package com.fdh.simulator.utils;

import java.io.ByteArrayOutputStream;
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


    /**
     * BCD字节数组===>String
     *
     * @param bytes
     * @return 十进制字符串
     */
    public static String bcd2String(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            // 高四位
            temp.append((bytes[i] & 0xf0) >>> 4);
            // 低四位
            temp.append(bytes[i] & 0x0f);
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * @param
     * @return String
     * @Description: TODO
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

    /**
     * 从第start开始到end，包含end计算bcc校验位
     * @param data
     * @param start
     * @param end
     * @return
     */
    public static byte getBCCByteFromStart2End(byte[] data, int start, int end) {
        byte bcc = 0;
        if (start >= end) {
            return bcc;
        }
        for (int i = start; i <= end; i++) {
            bcc = (byte) (bcc ^ data[i]);
        }
        return bcc;
    }

    /**
     * 接收消息时转义<br>
     *
     * <pre>
     * 0x7d01 <====> 0x7d
     * 0x7d02 <====> 0x7e
     * </pre>
     *
     * @param bs    要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     * @throws Exception
     */
    public static byte[] doEscape4Receive(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("doEscape4Receive error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < start; i++) {
                baos.write(bs[i]);
            }
            for (int i = start; i < end; i++) {
                if (bs[i] == 0x7d && bs[i + 1] == 0x01) {
                    baos.write(0x7d);
                    i++;
                    continue;
                } else if (bs[i] == 0x7d && bs[i + 1] == 0x02) {
                    baos.write(0x7e);
                    i++;
                    continue;
                } else {
                    baos.write(bs[i]);
                    continue;
                }
            }
            for (int i = end; i < bs.length; i++) {
                baos.write(bs[i]);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    /**
     * 字符串==>BCD字节数组
     *
     * @param str
     * @return BCD字节数组
     */
    public static byte[] string2Bcd(String str) {
        // 奇数,前补零
        if ((str.length() & 0x1) == 1) {
            str = "0" + str;
        }

        byte ret[] = new byte[str.length() / 2];
        byte bs[] = str.getBytes();
        for (int i = 0; i < ret.length; i++) {

            byte high = ascII2Bcd(bs[2 * i]);
            byte low = ascII2Bcd(bs[2 * i + 1]);

            // TODO 只遮罩BCD低四位?
            ret[i] = (byte) ((high << 4) | low);
        }
        return ret;
    }

    private static byte ascII2Bcd(byte asc) {
        if ((asc >= '0') && (asc <= '9'))
            return (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            return (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            return (byte) (asc - 'a' + 10);
        else
            return (byte) (asc - 48);
    }


    /**
     *
     * 发送消息时转义<br>
     *
     * <pre>
     *  0x7e <====> 0x7d02
     * </pre>
     *
     * @param bs
     *            要转义的字节数组
     * @param start
     *            起始索引
     * @param end
     *            结束索引
     * @return 转义后的字节数组
     * @throws Exception
     */
    public static byte[] doEscape4Send(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("doEscape4Send error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < start; i++) {
                baos.write(bs[i]);
            }

            for (int i = start; i < end; i++) {
                if(bs[i] == 0x7d){
                    baos.write(0x7d);
                    baos.write(0x01);
                    continue;
                }else if (bs[i] == 0x7e) {
                    baos.write(0x7d);
                    baos.write(0x02);
                    continue;
                } else {
                    baos.write(bs[i]);
                    continue;
                }
            }
            for (int i = end; i < bs.length; i++) {
                baos.write(bs[i]);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }
}
