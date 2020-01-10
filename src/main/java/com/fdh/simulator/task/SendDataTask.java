package com.fdh.simulator.task;


import com.fdh.simulator.Calculate;
import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.utils.BuildPacketService;
import com.fdh.simulator.utils.ByteUtils;
import io.netty.channel.Channel;
import net.jodah.expiringmap.ExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 * @author fudh
 * @ClassNmme SendDataTask
 * @date 2019/1/25 15:35
 * @Description: TODO
 */
public class SendDataTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SendDataTask.class);
    private Channel channel;
    private CommandTagEnum commandTag;
    private int packeExpiredTime;
    private String deviceCode;

    public SendDataTask(Channel channel, CommandTagEnum commandTag, int packeExpiredTime) {
        this.channel = channel;
        this.commandTag = commandTag;
        this.packeExpiredTime = packeExpiredTime;
    }

    @Override
    public void run() {
        try {
            byte[] packet = new byte[0];
            String logstr = "";
            String toHexString = "";
            int packetSerialNum = PacketAnalyze.getPacketSerialNum();
            deviceCode = NettyChannelManager.getVin(channel);
            if (commandTag == CommandTagEnum.XBOX_LOGIN_REPORT) {//登陆
                packet = BuildPacketService.buildLoginPacket(deviceCode, packetSerialNum);
                toHexString = ByteUtils.bytesToHexString(packet);
            } else if (commandTag == CommandTagEnum.XBOX_HEARBEAT_REPORT) {
                //累加
                Calculate.increaseTotalSendedPacketCount();
                packet = BuildPacketService.buildHeartBeatPacket(deviceCode, packetSerialNum);
                //记录发送的流水号
                PacketAnalyze.sendPacketMap.put(deviceCode + packetSerialNum, System.currentTimeMillis(), ExpirationPolicy.CREATED, packeExpiredTime, TimeUnit.SECONDS);
                toHexString = ByteUtils.bytesToHexString(packet);
            }
            logger.info("[车辆]" + "[" + deviceCode + "][SENDED]" + logstr + toHexString);
            channel.writeAndFlush(packet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送报文失败,deviceCode:" + deviceCode, e);
        }
    }
}
