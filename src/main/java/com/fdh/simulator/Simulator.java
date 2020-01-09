package com.fdh.simulator;

import java.util.Timer;
import java.util.TimerTask;

import com.fdh.simulator.utils.BuildPacketService;
import com.fdh.simulator.task.ConnectTask;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Data
public class Simulator {

    private ThreadPoolTaskExecutor taskExecutor;
    public static Timer timer;
    private String address;
    private int port;
    private int sendInterval;
    private int tcpConnections;
    private TimerTask timerTask;


    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);


    public Simulator() {
    }

    public void connect() {

        logger.info("设置的连接数为:" + tcpConnections);
        logger.info("初始化设备号中。。。。");
        for (int i = 0; i < tcpConnections; i++) {
            BuildPacketService.deviceMap.put(i, BuildPacketService.buildDeviceCode());
        }

        EventLoopGroup workgroup = new NioEventLoopGroup(60);
        for (int i = 0; i < tcpConnections; i++) {
            new Thread(new ConnectTask(address, port, i, workgroup)).start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 处理实时报文发送，等待登陆完成立马发送数据
         */
        timer = new Timer();
        timer.schedule(timerTask, 0, sendInterval);
    }


}
