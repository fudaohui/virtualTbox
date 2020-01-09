package com.fdh.simulator.constant;


import com.fdh.simulator.utils.ByteUtils;

import java.util.Arrays;

/**
 * @ClassName: CommandTagEnum
 * @Author: fudaohui
 * @Description: 车联网808上行命令标识，注：枚举类以report结尾的均为设备主动上报的；以resps上报的均为响应平台的应答
 * 有的即是主动上报也是被动上报，不严格区分
 * @Date: 2019/12/16 13:59
 */
public enum CommandTagEnum {

    /**
     * 注册
     */
    XBOX_REGISTER_REPORT("注册", new byte[]{0x01, 0x00}),

    /**
     * 鉴权
     */
    XBOX_LOGIN_REPORT("鉴权", new byte[]{0x01, 0x02}),

    /**
     * 注销
     */
    XBOX_LOGOUT_REPORT("注销", new byte[]{0x00, 0x03}),

    /**
     * 心跳上报
     */
    XBOX_HEARBEAT_REPORT("心跳", new byte[]{0x00, 0x02}),

    /**
     * 上报属性
     */
    XBOX_PROPERTY_REPORT("版本属性信息", new byte[]{0x01, 0x07}),

    /**
     * 实时位置信息
     */
    XBOX_LOCATION_REALTIME_REPORT("实时位置", new byte[]{0x02, 0x00}),

    /**
     * 批量位置信息
     */
    XBOX_LOCATION_BATCH_REPORT("批量位置", new byte[]{0x07, 0x04}),

    /**
     * 通用应答
     */
    XBOX_COMMON_RESP("通用应答", new byte[]{0x00, 0x01}),


    /**
     * 位置查询应答
     */
    XBOX_LOCATION_RESP("位置应答", new byte[]{0x02, 0x01}),

    /**
     * 参数应答
     */
    XBOX_PARAM_RESP("参数应答", new byte[]{0x01, 0x04}),

    /**
     * 多媒体数据应答
     */
    XBOX_MULTIMEDIA_DATA_RESP("多媒体数据应答", new byte[]{0x08, 0x01}),
    /**
     * 多媒体事件应答
     */
    XBOX_MULTIMEDIA_EVENT_RESP("通用应答", new byte[]{0x08, 0x00}),

    /**
     * 音视频资源列表应答--1078指令
     */
    XBOX_AUDIO_VIDEO_RESOURCES_RESP("音视频资源列表应答", new byte[]{0x12, 0x05}),

    /**
     * 文件上传完成应答--1078指令
     */
    XBOX_FILES_UPLOADED_RESP("文件上传完成应答", new byte[]{0x12, 0x06}),

    /**
     * 流媒体自定义播放应答--1078指令
     */
    SRS_CUSTOM_PLAY_RESP("流媒体自定义播放应答", new byte[]{0x09, 0x00}),
    /**
     * 摄像头立即拍摄命令应答--1078指令
     */
    XBOX_CAMERA_SHOOT_RESP("摄像头立即拍摄命令应答", new byte[]{0x08, 0x05}),

    /**
     * 实时事件数据上报，自定义协议《XBox扩展指令.docx》
     */
    XBOX_REALTIME_EVENT_REPORT("实时事件数据上报", new byte[]{(byte) 0xE2}),

    /**
     * 多媒体文件上传FTP应答
     */
    XBOX_MULTFILE_UPLOAD_FTP_RESP("多媒体文件上传FTP应答", new byte[]{0x0f, 0x01}),
    ;

    /**
     * 命令描述
     */
    private String k;

    /**
     * 命令字
     */
    private byte[] commanBytes;


    public String getK() {
        return k;
    }


    public byte[] getCommanBytes() {
        return commanBytes;
    }


    CommandTagEnum(String key, final byte[] commanBytes) {
        this.k = key;
        this.commanBytes = commanBytes;
    }


    public static CommandTagEnum getCommandEnum(byte[] value) {
        for (CommandTagEnum commandTagEnum : CommandTagEnum.values()) {
            byte[] commanBytes = commandTagEnum.getCommanBytes();
            if (Arrays.equals(commanBytes, value)) {
                return commandTagEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CommandTagEnum{" +
                "k='" + k + '\'' +
                ", commanBytes=" + ByteUtils.bytesToHexString(commanBytes) +
                '}';
    }

    public static void main(String[] args) {
//        byte vehicle_loginv = XBOX_AUTHENTICATE.getV();

    }
}
