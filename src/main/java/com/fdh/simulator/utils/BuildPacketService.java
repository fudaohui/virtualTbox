package com.fdh.simulator.utils;


import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.model.Tbox;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fudh
 * @ClassNmme BuildPacketService
 * @date 2019/1/21 8:55
 * @Description: TODO
 */
public class BuildPacketService {


    /**
     * 线程号连接号对应的设备号
     */
    public static ConcurrentHashMap<Integer, String> deviceMap = new ConcurrentHashMap();

    /**
     * 发送次数，递减，为0，数据全部发送完成
     */
    public static int sendCount;
    /**
     * 设备号前缀
     */
    private static String preffix;

    /**
     * 设备号后缀
     */
    private static Integer suffix;

    /**
     * 流水号
     */
    private static AtomicInteger serialNumer;

    private static final byte[] LOGIN_COMMAND = {0x01, 0x02};
    private static final byte[] HEART_BEAT_COMMAND = {0x00, 0x02};


    public static synchronized String buildDeviceCode() {
        return preffix + suffix++;
    }


    /**
     * @return
     */
    public static byte[] buildLoginPacket(String deviceCode, int serialNum) {

        byte[] body = "123456".getBytes();
        return encodeMsg(LOGIN_COMMAND, serialNum, deviceCode, body);

    }

    /**
     * 创建心跳报文
     *
     * @param deviceCode
     * @return
     */
    public static byte[] buildHeartBeatPacket(String deviceCode, int serialNum) {

        byte[] body = new byte[0];
        return encodeMsg(HEART_BEAT_COMMAND, serialNum, deviceCode, body);

    }


    /**
     * 封装消息
     *
     * @param msgid
     * @param serialnumber
     * @param phoneNumber
     * @param body         消息体
     * @return
     */
    public static byte[] encodeMsg(byte[] msgid, int serialnumber, String phoneNumber, byte[] body) {
        short msgBodyProperty = 0;
        ByteList byteList = new ByteList();
        //1、包开始标识
        byteList.add((byte) 0x7e);
        //2、消息id
        byteList.addArray(msgid);
        //3、消息体属性
        if (body == null || body.length <= 0) {
            msgBodyProperty = 0;
        } else {
            msgBodyProperty = (short) body.length;
        }
        byte[] bodyPropertyBytes = ByteUtils.getShortBytes(msgBodyProperty);
        byteList.addArray(bodyPropertyBytes);
        //4、bcd 手机号
        byte[] phoneBytes = Utils.string2Bcd(phoneNumber);
        byteList.addArray(phoneBytes);
        //5、消息流水号
        byte[] serialBytes = ByteUtils.getShortBytes((short) serialnumber);
        byteList.addArray(serialBytes);
        //6、分包
        // TODO: 2019/7/25  注意该方法尚未实现消息分包，因此没有追加消息包分包项
        //7、消息体
        byteList.addArray(body);
        //8、计算bcc校验位
        byte[] byteListArray = byteList.getArray();
        byte bccByte = Utils.getBCCByteFromStart2End(byteListArray, 1, byteListArray.length - 1);
        //9、bcc校验位
        byteList.add(bccByte);
        //10、结束符
        byteList.add((byte) 0x7e);
        byteListArray = byteList.getArray();
        byte[] bytes = new byte[0];
        try {
            bytes = Utils.doEscape4Send(byteListArray, 1, byteListArray.length - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }


    /**
     * 解码消息
     *
     * @param msg
     * @return
     */
    public static Tbox parsePacket(byte[] msg) {

        Tbox tbox = new Tbox();
        //消息体长度
        short aShort = ByteUtils.getShort(msg, 3);
        int bodyLength = 0x03ff & aShort;
        //设备号
        byte[] deviceBytes = ByteUtils.subBytes(msg, 5, 6 + 6);
        String deviceCode = Utils.bcd2String(deviceBytes);
        tbox.setDeviceCode(deviceCode);
        //具体数据
        byte[] body = ByteUtils.subBytes(msg, 13, 13 + bodyLength);
        //应答流水号
        short reSerialNum = ByteUtils.getShort(body, 0);
        tbox.setSerialNum(reSerialNum);
        byte[] bytes = ByteUtils.subBytes(body, 2, 2 + 2);
        //命令字
        CommandTagEnum commandEnum = CommandTagEnum.getCommandEnum(bytes);
        tbox.setCommandTagEnum(commandEnum);
        //应答结果
        tbox.setRet(body[4]);
        return tbox;
    }

    public static void main(String[] args) {
        String hex = "7E8001000501311963266617F90001010200627E";
        byte[] bytes = ByteUtils.hexToByteArray(hex);
        Tbox tbox = BuildPacketService.parsePacket(bytes);
        System.out.println(tbox.toString());
    }

    public static ConcurrentHashMap<Integer, String> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(ConcurrentHashMap<Integer, String> deviceMap) {
        BuildPacketService.deviceMap = deviceMap;
    }

    public String getPreffix() {
        return preffix;
    }

    public void setPreffix(String preffix) {
        BuildPacketService.preffix = preffix;
    }

    public Integer getSuffix() {
        return suffix;
    }

    public void setSuffix(Integer suffix) {
        BuildPacketService.suffix = suffix;
    }

    public AtomicInteger getSerialNumer() {
        return serialNumer;
    }

    public void setSerialNumer(AtomicInteger serialNumer) {
        BuildPacketService.serialNumer = serialNumer;
    }


}
