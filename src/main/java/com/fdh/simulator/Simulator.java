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


    public ThreadPoolTaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void setTimer(Timer timer) {
        Simulator.timer = timer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }

    public int getTcpConnections() {
        return tcpConnections;
    }

    public void setTcpConnections(int tcpConnections) {
        this.tcpConnections = tcpConnections;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public static Logger getLogger() {
        return logger;
    }
}
