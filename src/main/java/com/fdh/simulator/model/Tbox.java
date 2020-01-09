package com.fdh.simulator.model;

import com.fdh.simulator.constant.CommandTagEnum;
import lombok.Data;

/**
 * @author fudh
 * @ClassNmme Tbox
 * @date 2019/1/28 11:28
 * @Description: 解析808平台通用应答
 */

@Data
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
}
