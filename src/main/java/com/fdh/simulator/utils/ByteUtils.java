package com.fdh.simulator.utils;

/**
 * @author fudh
 * @ClassNmme ByteUtils
 * @date 2019/1/19 15:24
 * @Description: TODO
 */
public class ByteUtils {
    /**
     * bcc 异或校验返回byte
     *
     * @param bytes
     * @return
     */
    public static byte bccEncode(byte[] bytes) {
        byte retByte = 0;
        if (bytes == null || bytes.length <= 0) {
            return retByte;
        }
        retByte = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            retByte ^= bytes[i];
        }
        return retByte;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }




    /**
     * 转换short为byte
     *
     * @param dst
     * @param src   需要转换的short
     * @param index
     */
    public static void putShort(byte[] dst, short src, int index) {
        dst[index] = (byte) (src >> 8);
        dst[index + 1] = (byte) (src >> 0);
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index 第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
    }

    /**
     * 转换int为byte数组
     *
     * @param x
     */
    public static byte[] getIntegerByte(Integer x) {
        byte[] bb = new byte[4];
        bb[0] = (byte) (x >> 24);
        bb[1] = (byte) (x >> 16);
        bb[2] = (byte) (x >> 8);
        bb[3] = (byte) (x >> 0);
        return bb;
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index 第几位开始
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 0] & 0xff) << 24)
                | ((bb[index + 1] & 0xff) << 16)
                | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
    }

//    public static void main(String[] args) {
//    232307FE 4C4E42534342334658595A313030303031 01 0004 00 00 00 02 C2
//        int anInt = getInt(new byte[]{0x07, 0x5B, (byte) 0xCD, 0x15},0);
//        System.out.println(anInt);
//    }


//    public  static  int parseSerialNum(byte[] packet){
//


//    }

    /**
     * 转换long型为byte数组
     *
     * @param x
     */
    public static byte[] putLong(long x) {
        byte[] bb = new byte[8];
        bb[0] = (byte) (x >> 56);
        bb[1] = (byte) (x >> 48);
        bb[2] = (byte) (x >> 40);
        bb[3] = (byte) (x >> 32);
        bb[4] = (byte) (x >> 24);
        bb[5] = (byte) (x >> 16);
        bb[6] = (byte) (x >> 8);
        bb[7] = (byte) (x >> 0);
        return bb;
    }
    /**
     * 通过byte数组取到long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 0] & 0xff) << 56)
                | (((long) bb[index + 1] & 0xff) << 48)
                | (((long) bb[index + 2] & 0xff) << 40)
                | (((long) bb[index + 3] & 0xff) << 32)
                | (((long) bb[index + 4] & 0xff) << 24)
                | (((long) bb[index + 5] & 0xff) << 16)
                | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
    }

    public static void main(String[] args) {
//        byte[] aa = {0x23, 0x23, 0x07, (byte) 0xFE, 0x4C, 0x4E, 0x42, 0x53, 0x43, 0x42, 0x33, 0x46, 0x58, 0x59, 0x5A, 0x31, 0x30, 0x30, 0x30, 0x30, 0x31, 0x01, 0x00, 0x04, 0x00, 0x00, 0x00, 0x02, (byte) 0xC2};
//        int bb = getInt(aa, 24);
//        byte[] bytes = putLong(System.currentTimeMillis());
//        System.out.println(ByteUtils.bytesToHexString(bytes));
//        byte[] a = {0x23,0x23, (byte) 0xDC,0x01,0x36,0x36,0x31,0x44,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x32,0x01,0x00,0x0C,
//                0x13,0x01,0x19,0x0E,0x1D,0x09,0x00,0x01,0x00,0x00,0x00,0x00, (byte) 0x87};
//        String vin = parseByte2Vin(a);
//        System.out.println(vin);
    }
}
