package com.fdh.simulator.task;


import com.fdh.simulator.CalculateSevice;
import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.utils.BuildPacketService;
import com.fdh.simulator.utils.ByteUtils;
import io.netty.channel.Channel;
import net.jodah.expiringmap.ExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private String ddeviceCode;

    public SendDataTask(Channel channel, CommandTagEnum commandTag, int packeExpiredTime) {
        this.channel = channel;
        this.commandTag = commandTag;
        this.packeExpiredTime = packeExpiredTime;
    }

    @Override
    public void run() {
        try {
            long packetSerialNum = 0;
            byte[] packet = new byte[0];
            String logstr = "";
            String toHexString = "";
            ddeviceCode = NettyChannelManager.getVin(channel);
            if (commandTag == CommandTagEnum.XBOX_LOGIN_REPORT) {//登陆
                packetSerialNum = PacketAnalyze.getPacketSerialNum();
                //记录发送的流水号
//                PacketAnalyze.sendPacketMap.put(ddeviceCode + packetSerialNum, System.currentTimeMillis(), ExpirationPolicy.CREATED, packeExpiredTime, TimeUnit.SECONDS);
                logstr = "[NO." + ddeviceCode + packetSerialNum + "]=>";
                packet = BuildPacketService.buildLoginPacket(ddeviceCode);
                toHexString = ByteUtils.bytesToHexString(packet);
            }else if(commandTag == CommandTagEnum.XBOX_HEARBEAT_REPORT){

                //累加
                CalculateSevice.increaseTotalSendedPacketCount();
            }
            logger.info("[车辆]" + "[" + ddeviceCode + "][SENDED]" + logstr + toHexString);
            channel.writeAndFlush(packet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送报文失败,ddeviceCode:" + ddeviceCode, e);
        }
    }
}
