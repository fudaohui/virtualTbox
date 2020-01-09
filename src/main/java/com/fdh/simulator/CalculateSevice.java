package com.fdh.simulator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ProjectName: virtualtbox
 * @ClassName: CalculateSevice
 * @Author: fudaohui
 * @Description: 时间统计
 * @Date: 2020/1/8 17:06
 */
public class CalculateSevice {
    /**
     * 最小响应时间
     */
    public static Integer minTime;

    /**
     * 最大响应时间
     */
    public static Integer maxTime;

    /**
     * 平均响应时间
     */
    public static Integer averTime;

    /**
     * 总花费时间
     */
    public static Long totalTime;

    /**
     * 总接收包数
     */
    public static Long totalReceivePacketCount;
    /**
     * 总发送包数
     */
    public static AtomicLong totalSendedPacketCount;


    /**
     * 计算最大最小平均总响应时间
     */
    public synchronized  static void calculateTime(int costTime) {

        totalReceivePacketCount++;
        //计算总花费时间
        totalTime += costTime;
        //计算平均响应时间
        averTime = Math.toIntExact(totalTime / totalReceivePacketCount);
        if (totalReceivePacketCount == 1) {
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


    public static  void increaseTotalSendedPacketCount(){
        totalSendedPacketCount.incrementAndGet();
    }
}
