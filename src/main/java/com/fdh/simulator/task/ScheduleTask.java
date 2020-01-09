package com.fdh.simulator.task;


import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.Simulator;
import com.fdh.simulator.constant.CommandTag;
import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.utils.BuildPacketService;
import io.netty.channel.Channel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


/***
 * 定时任务
 */
@Data
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

    @Override
    public void run() {

        ConcurrentHashMap<String, Channel> loginChannnelMap = NettyChannelManager.getLoginChannnelMap();

        /**
         * 登陆率大于90%后在调度数据
         */
        int i = loginChannnelMap.size() * 100 / tcpConnections;
        if (i < 90) {
            return;
        }
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
                    threadPoolTaskExecutor.execute(new SendDataTask(channel, CommandTagEnum.XBOX_HEARBEAT_REPORT, packeExpiredTime));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据发送异常", e);
        }
        count--;
        BuildPacketService.sendCount = count;
    }
}
