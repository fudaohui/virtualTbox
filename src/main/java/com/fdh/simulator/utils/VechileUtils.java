package com.fdh.simulator.utils;


import com.fdh.simulator.constant.CommandTag;
import com.fdh.simulator.model.Tbox;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fudh
 * @ClassNmme VechileUtils
 * @date 2019/1/21 8:55
 * @Description: TODO
 */
public class VechileUtils {


    public static String prefix = "LNBSCB3FXYZ";

    public static int intSuffix ;
    public static volatile AtomicInteger suffix;

//    public static List<Tbox> mlist = ExcelUtils.getTBoxDataFromExcel();
    public static AtomicInteger index = new AtomicInteger(0);
    public static AtomicInteger mcounn = new AtomicInteger(0);
    public static AtomicInteger connectCounn = new AtomicInteger(0);

    public  static  Integer sendCount = 0;

    public static ConcurrentHashMap<Integer, String> vinMap = new ConcurrentHashMap<>();
    /***
     * iccid的后缀
     */
    public static AtomicInteger iccidSuffix = suffix;

    /**
     * 终端序列号，7位
     */
    public static AtomicInteger terminalSerialNo = new AtomicInteger(7000000 + intSuffix);

    private static byte[] vechileData;

    /**
     * 数据单元有效数据的个数
     */
    private static int count = 0;

    /**
     * 只生成一次假的实时数据
     */
    static {
        count = 400;
        vechileData = new byte[count];//填充假的实时数据
        for (int i = 0; i < count; i++) {
            vechileData[i] = (byte) i;
        }
    }

    /**
     * @param commandTag
     * @param vin
     * @param packetSerialNum
     * @param iccid           注册报文必填
     * @param deviceid        注册报文必填
     * @return
     */
    public static byte[] getPacket(CommandTag commandTag, String vin, long packetSerialNum, String iccid, String deviceid) {

        byte[] fixPrefixPacket;
        //数据包序号，8个字节，实际业务数据是数据采集时间和终端流水号
        byte[] packetSerialNo = ByteUtils.putLong(packetSerialNum);
        byte[] middle;
        if (commandTag == CommandTag.REALTIME_INFO_REPORT) {
            count = 400;
            //实时数据处理在包序号后面追假的实时数据
        } else if (commandTag == CommandTag.VEHICLE_LOGIN) {
            count = 0;
            packetSerialNo = new byte[0];
        } else if (commandTag == CommandTag.VEHICLE_REGISTER) {
            vechileData = geRegisterData(vin);
//            vechileData = geRegisterData(vin,iccid,deviceid);
            count = vechileData.length;
        }
        fixPrefixPacket = fixPrefixPacket(commandTag, vin, (short) (packetSerialNo.length + count));
        middle = new byte[fixPrefixPacket.length + packetSerialNo.length + count];
        System.arraycopy(fixPrefixPacket, 0, middle, 0, fixPrefixPacket.length);
        System.arraycopy(packetSerialNo, 0, middle, fixPrefixPacket.length, packetSerialNo.length);
        System.arraycopy(vechileData, 0, middle, fixPrefixPacket.length + packetSerialNo.length, count);

        byte bccEncode = ByteUtils.bccEncode(middle);
        byte[] ret = new byte[2 + middle.length + 1];
        ret[0] = 0x23;
        ret[1] = 0x23;
        System.arraycopy(middle, 0, ret, 2, middle.length);
        ret[middle.length + 2] = bccEncode;
        return ret;
    }

    public static byte[] geRegisterData(String vin) {
        byte[] registerbyte = new byte[17 + 20 + 1 + 7];//17位vin,20位iccid,终端序列号长度1位，7位终端序列号
        byte[] vinBytes = vin.getBytes();
        System.arraycopy(vinBytes, 0, registerbyte, 0, vinBytes.length);//vin
        String iccidPrefix = "898602B4071630";
        int incrementAndGet = iccidSuffix.incrementAndGet();
        iccidPrefix += incrementAndGet;
        byte[] iccid = iccidPrefix.getBytes();
        String serialNo = terminalSerialNo.incrementAndGet() + "";
        byte[] serialNoBytes = serialNo.getBytes();

        System.arraycopy(iccid, 0, registerbyte, vinBytes.length, iccid.length);
        registerbyte[vinBytes.length + iccid.length] = 0x07;
        System.arraycopy(serialNoBytes, 0, registerbyte, vinBytes.length + iccid.length + 1, serialNoBytes.length);
        return registerbyte;
    }

    public static byte[] geRegisterData(String vin, String iccid, String serialNo) {
        byte[] registerbyte = new byte[17 + 20 + 1 + 7];//17位vin,20位iccid,终端序列号长度1位，7位终端序列号
        byte[] vinBytes = vin.getBytes();
        System.arraycopy(vinBytes, 0, registerbyte, 0, vinBytes.length);//vin
        byte[] iccidBytes = iccid.getBytes();
        byte[] serialNoBytes = serialNo.getBytes();
        System.arraycopy(iccidBytes, 0, registerbyte, vinBytes.length, iccidBytes.length);
        registerbyte[vinBytes.length + iccidBytes.length] = 0x07;
        System.arraycopy(serialNoBytes, 0, registerbyte, vinBytes.length + iccidBytes.length + 1, serialNoBytes.length);
        return registerbyte;
    }


//    public static Tbox getTbox() {
//        Tbox tbox = mlist.get(index.get());
//        index.incrementAndGet();
//        return tbox;
//    }

    /**
     * 生成报文头，从命令标识到数据单元长度字节
     *
     * @param commandTag 命令标识
     * @param vin        vin
     * @return
     */
    public static byte[] fixPrefixPacket(CommandTag commandTag, String vin, short dataLength) {
        byte[] preffixBytes = new byte[22];
        preffixBytes[0] = commandTag.getV();
        preffixBytes[1] = (byte) 0xFE;
        byte[] vinBytes = vin.getBytes();
        System.arraycopy(vinBytes, 0, preffixBytes, 2, vinBytes.length);
        preffixBytes[19] = 0x01;
        byte[] length = new byte[2];
        ByteUtils.putShort(length, dataLength, 0);
        preffixBytes[20] = length[0];
        preffixBytes[21] = length[1];
        //包序列号
        return preffixBytes;
    }

    /**
     * @param
     * @param
     * @return 获取注册报文
     */
//    public static byte[] getRegisterPacket(String vin, int packetSerialNum) {
//
//
//    }
    public static String getVin() {
        String vin = prefix + suffix.intValue();
        suffix.incrementAndGet();
        return vin;
    }

    /**
     * 从原始报文中解析出Vin
     *
     * @param receives
     * @return
     */
    public static String parseByte2Vin(byte[] receives) {

        if (receives == null || receives.length <= 15) {
            return null;
        } else {
            byte[] vinBytes = new byte[17];
            System.arraycopy(receives, 4, vinBytes, 0, 17);
            return new String(vinBytes);
        }
    }


//    public static void main(String[] args) {
//        ExpiringMap<String,Integer> expiringMap = ExpiringMap.builder()
//                .variableExpiration()
//                .expirationListener(new ExpirationListener<String, Integer>() {
//                    @Override
//                    public void expired(String s, Integer integer) {
//                        System.out.println(s+"--->"+integer);
//                    }
//                })
//                .build();
//
//
//        for (int i = 0; i < 10; i++) {
//            expiringMap.put(i+"d",i, ExpirationPolicy.CREATED, 10, TimeUnit.SECONDS);
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (int i = 0; i < 100000000; i++) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    public static void main(String[] args) {
//        String vin = getVin();
//        byte[] bytes = fixPrefixPacket(CommandTag.HEARTBEAT, vin, (short) 4);
//        byte[] timingPacket = getTimingPacket(getVin(), 1);
//        String vin1 = getVin();
//        byte[] timingPacket = getPacket(CommandTag.REALTIME_INFO_REPORT, vin, 1);
//        System.out.println(ByteUtils.bytesToHexString(timingPacket));
//        vechileData = geRegisterData(vin);
//        byte[] packet = getPacket(CommandTag.REALTIME_INFO_REPORT, vin, 1L);
//        System.out.println(ByteUtils.bytesToHexString(packet));


//        byte[] bytes = geRegisterData("LNBSCB3F4JW183979", "898602B4071730066650", "6854194");
//        byte[] bytes =   getPacket(CommandTag.VEHICLE_REGISTER,"LNBSCB3F4JW183979",1L,"898602B4071730066650", "6854194");
//        System.out.println(ByteUtils.bytesToHexString(bytes));

//        Tbox tbox = getTbox();
//        System.out.println(tbox.getVin());

//        byte[] bytes = geRegisterData("LNBSCB3F0JW184854");
//        System.out.println(ByteUtils.bytesToHexString(bytes));

        byte[] packet = VechileUtils.getPacket(CommandTag.VEHICLE_LOGIN, "LNBSCB3FXYZ101686", 1, null, null);
        System.out.println(ByteUtils.bytesToHexString(packet));
    }


}
