package com.fdh.simulator;

import org.springframework.stereotype.Service;

/**
 * @ProjectName: virtualtbox
 * @ClassName: CalculateSevice
 * @Author: fudaohui
 * @Description: 时间统计
 * @Date: 2020/1/8 17:06
 */
@Service
public class CalculateSevice {
    /**
     * 最小响应时间
     */
    private Integer minTime;

    /**
     * 最大响应时间
     */
    private Integer maxTime;

    /**
     * 平均响应时间
     */
    private Integer averTime;

    /**
     * 总花费时间
     */
    private Long totalTime;


    /**
     * 计算最大最小平均总响应时间
     */
    public synchronized void calculateTime(int costTime, int receivePackeetCount) {

        //计算总花费时间
        totalTime += costTime;
        //计算平均响应时间
        averTime = Math.toIntExact(totalTime / receivePackeetCount);
        if (receivePackeetCount == 1) {
            maxTime = minTime = costTime;
            return;
        }
        if (costTime > maxTime) {
            maxTime = costTime;
            return;
        }
        if (costTime < minTime) {
            minTime = costTime;
        }
    }
}
