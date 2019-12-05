/*
 * 文件名： Command.java
 * 
 * 创建日期： 2016年11月23日
 *
 * Copyright(C) 2016, by <a href="mailto:liws@xingyuanauto.com">liws</a>.
 *
 * 原始作者: liws
 *
 */
package com.fdh.simulator.constant;


/**
 * 车联网通信协议  命令标识
 *
 * @author <a href="mailto:liws@xingyuanauto.com">liws</a>
 *
 * @version $Revision$
 *
 * @since 2016年11月23日
 */
public enum CommandTag implements IDictsEnumTag {
	
	/*上行命令*/
	/**
	 * 车辆登入
	 */
	VEHICLE_LOGIN("车辆登入", (byte) 0x01),
	/**
	 * 实时信息上报
	 */
	REALTIME_INFO_REPORT("实时信息上报", (byte) 0x02), 
	/**
	 * 停车充电
	 */
	RESEND_INFO_REPORT("补发信息上报",(byte) 0x03),
	/**
	 * 车辆登出
	 */
	VEHICLE_LOOUT("车辆登出",(byte) 0x04), 
	/**
	 * 平台登入
	 */
	PLATFORM_LOGIN("平台登入", (byte) 0x05), 
	/**
	 * 平台登出
	 */
	PLATFORM_LOOUT("平台登出", (byte) 0x06), 
	/**
	 * 心跳
	 */
	HEARTBEAT("心跳", (byte) 0x07),
	
	/**
	 * 终端校时
	 */
	TERMINAL_TIME_CHECK("终端校时", (byte) 0x08),

	/*下行命令*/
	/**
	 * 查询命令
	 */
	QUERY_CMD("查询命令", (byte) 0x80),
	/**
	 * 设置命令
	 */
	SETTING_CMD("设置命令", (byte) 0x81),
	/**
     * 车载终端控制命令
     */
    TERMINAL_CONTROL_CMD("车载终端控制命令", (byte) 0x82),
	/**
	 * 车辆登入回应
	 */
	VEHICLE_LOGIN_RESP("车辆登入回应", (byte) 0xBD),
	/**
	 * 车辆登出回应
	 */
	VEHICLE_LOOUT_RESP("车辆登出回应", (byte) 0xBE), 
	
	/**
	 * 车辆注册
	 */
	VEHICLE_REGISTER("车辆注册", (byte) 0xDB),

	/**-----------------------------------------------下行指令响应-----------------------------------------------------*/

	/**
	 * 远程车辆控制命令应答
	 */
	REMOTE_CONTROL_RESPONSE("远程车辆控制命令应答", (byte)0xD1),

	/**
	 * 远程车辆控制命令结果应答
	 */
	REMOTE_CONTROL_RESULT_RESPONSE("远程车辆控制命令结果应答", (byte)0xD2),

	/**
	 * 恒领远程升级命令应答
	 */
	REMOTE_UPGRADE_RESPONSE("恒领远程升级命令应答", (byte)0xD4),

	/**
	 * 终端参数查询结果上报应
	 */
	TERMINAL_PARAMETER_QUERY_RESPONSE("终端参数查询结果上报应答", (byte)0xD8),

	/**
	 * 终端参数设置结果应答
	 */
	TERMINAL_PARAMETER_SETTING_RESPONSE("终端参数设置结果应答", (byte)0xD9),

	/**
	 * 终端控制应答
	 */
	TERMINAL_CONTROL_RESPONSE("终端控制应答", (byte) 0xDD),

	/**
	 * 请求车辆实时信息上报应答
	 */
	REMOTE_ROLL_CALL_RESPONSE("请求车辆实时信息上报", (byte)0xDF);

	CommandTag(){
		
	}
	CommandTag(String key, byte value) {
		this.k = key;
		this.v = value;
	}
	private String k;

	private byte v;

	public CommandTag valueOf(byte value) {
		switch (value) {
		case 0x01:
			return VEHICLE_LOGIN;
		case 0x02:
			return REALTIME_INFO_REPORT;
		case 0x03:
			return RESEND_INFO_REPORT;
		case 0x04:
			return VEHICLE_LOOUT;
		case 0x05:
			return PLATFORM_LOGIN;
		case 0x06:
			return PLATFORM_LOOUT;
		case 0x07:
			return HEARTBEAT;
		case 0x08:
            return TERMINAL_TIME_CHECK;
		case (byte)0x80:
		    return QUERY_CMD;
		case (byte)0x81:
		    return SETTING_CMD;
		case (byte)0x82:
		    return TERMINAL_CONTROL_CMD;
		case (byte)0xBD:
		    return VEHICLE_LOGIN_RESP;
		case (byte)0xBE:
		    return VEHICLE_LOOUT_RESP;
		case (byte)0xDB:
			return VEHICLE_REGISTER;
		case (byte)0xD1:
			return REMOTE_CONTROL_RESPONSE;
		case (byte)0xD2:
			return REMOTE_CONTROL_RESULT_RESPONSE;
		case (byte)0xD4:
			return REMOTE_UPGRADE_RESPONSE;
		case (byte)0xD8:
			return TERMINAL_PARAMETER_QUERY_RESPONSE;
		case (byte)0xD9:
			return TERMINAL_PARAMETER_SETTING_RESPONSE;
		case (byte)0xDD:
			return TERMINAL_CONTROL_RESPONSE;
		case (byte)0xDF:
			return REMOTE_ROLL_CALL_RESPONSE;
		default:
			return null;
		}
	}
	
	public static CommandTag valOf(byte value) {
		switch (value) {
		case 0x01:
			return VEHICLE_LOGIN;
		case 0x02:
			return REALTIME_INFO_REPORT;
		case 0x03:
			return RESEND_INFO_REPORT;
		case 0x04:
			return VEHICLE_LOOUT;
		case 0x05:
			return PLATFORM_LOGIN;
		case 0x06:
			return PLATFORM_LOOUT;
		case 0x07:
			return HEARTBEAT;
		case 0x08:
            return TERMINAL_TIME_CHECK;
		case (byte)0x80:
            return QUERY_CMD;
        case (byte)0x81:
            return SETTING_CMD;
        case (byte)0x82:
            return TERMINAL_CONTROL_CMD;
        case (byte)0xBD:
            return VEHICLE_LOGIN_RESP;
        case (byte)0xBE:
            return VEHICLE_LOOUT_RESP;
        case (byte)0xDB:
			return VEHICLE_REGISTER;
		case (byte)0xD1:
			return REMOTE_CONTROL_RESPONSE;
		case (byte)0xD2:
			return REMOTE_CONTROL_RESULT_RESPONSE;
		case (byte)0xD4:
			return REMOTE_UPGRADE_RESPONSE;
		case (byte)0xD8:
			return TERMINAL_PARAMETER_QUERY_RESPONSE;
		case (byte)0xD9:
			return TERMINAL_PARAMETER_SETTING_RESPONSE;
		case (byte)0xDD:
			return TERMINAL_CONTROL_RESPONSE;
		case (byte)0xDF:
				return REMOTE_ROLL_CALL_RESPONSE;
		default:
			return null;
		}
	}
	public static String getKey(byte value) {
		for (CommandTag c : CommandTag.values()) {
			if (c.getV() == value) {
				return c.k;
			}
		}
		return null;
	}

	@Override
	public String getK() {
		return k;
	}

	@Override
	public byte getV() {
		return v;
	}
	
	public static void main(String[] args) {
		System.out.println("##"+CommandTag.valOf((byte)0x0));
	}
}
