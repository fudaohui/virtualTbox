package com.fdh.simulator.task;


import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.Simulator;
import com.fdh.simulator.constant.CommandTag;
import com.fdh.simulator.utils.ByteUtils;
import com.fdh.simulator.utils.VechileUtils;
import io.netty.channel.Channel;
import net.jodah.expiringmap.ExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/***
 * 定时任务
 */
public class ScheduleTask extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);


    private int tcpConnections;

    /**
     * 执行调度的次数
     */
    int count = 10;

    /**
     * 每包的过期时间
     */
    int packeExpiredTime = 10;
    int mcount;


    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPackeExpiredTime() {
        return packeExpiredTime;
    }

    public void setPackeExpiredTime(int packeExpiredTime) {
        this.packeExpiredTime = packeExpiredTime;
    }


    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return threadPoolTaskExecutor;
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public int getTcpConnections() {
        return tcpConnections;
    }

    public void setTcpConnections(int tcpConnections) {
        this.tcpConnections = tcpConnections;
    }

    @Override
    public void run() {

        ConcurrentHashMap<String, Channel> loginChannnelMap = NettyChannelManager.getLoginChannnelMap();

        /**
         * 登陆有数据，立即发送数据
         */
        int i = loginChannnelMap.size() * 100 / tcpConnections;
        if (i < 90) {
            return;
        }
//        if (loginChannnelMap.size() <=0) {
//            return;
//        }
        mcount++;
        logger.info("第" + mcount + "数据发送");
        if (count <= 0) {
            Simulator.timer.cancel();
            logger.info("数据发送完成!");
            return;
        }
        try {
            if (loginChannnelMap.size() > 0) {
                Set<Map.Entry<String, Channel>> entries = loginChannnelMap.entrySet();
                for (Map.Entry<String, Channel> entry : entries) {
                    Channel channel = entry.getValue();
                    threadPoolTaskExecutor.execute(new SendDataTask(channel, CommandTag.REALTIME_INFO_REPORT, packeExpiredTime));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据发送异常", e);
        }
        count--;
        VechileUtils.sendCount = count;
    }
}
