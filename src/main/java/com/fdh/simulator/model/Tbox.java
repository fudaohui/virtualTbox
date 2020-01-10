package com.fdh.simulator.model;

import com.fdh.simulator.constant.CommandTagEnum;
import lombok.Data;

/**
 * @author fudh
 * @ClassNmme Tbox
 * @date 2019/1/28 11:28
 * @Description: 解析808平台通用应答
 */

public class Tbox {

    /**
     * 设备号
     */
    private String deviceCode;

    /**
     * 应答命令字
     */
    private CommandTagEnum commandTagEnum;
    /**
     * 应答流水号
     */
    private Short serialNum;

    /**
     * 应答结果
     */
    private byte ret;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public CommandTagEnum getCommandTagEnum() {
        return commandTagEnum;
    }

    public void setCommandTagEnum(CommandTagEnum commandTagEnum) {
        this.commandTagEnum = commandTagEnum;
    }

    public Short getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Short serialNum) {
        this.serialNum = serialNum;
    }

    public byte getRet() {
        return ret;
    }

    public void setRet(byte ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "Tbox{" +
                "deviceCode='" + deviceCode + '\'' +
                ", commandTagEnum=" + commandTagEnum +
                ", serialNum=" + serialNum +
                ", ret=" + ret +
                '}';
    }
}
