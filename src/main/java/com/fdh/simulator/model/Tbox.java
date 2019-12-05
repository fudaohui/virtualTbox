package com.fdh.simulator.model;

/**
 * @author fudh
 * @ClassNmme Tbox
 * @date 2019/1/28 11:28
 * @Description: TODO
 */
public class Tbox {

    private String vin;
    private String iccid;
    private String deviceId;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Tbox(String vin, String iccid, String deviceId) {
        this.vin = vin;
        this.iccid = iccid;
        this.deviceId = deviceId;
    }

    public Tbox() {
        this.vin = vin;
        this.iccid = iccid;
        this.deviceId = deviceId;
    }
}
