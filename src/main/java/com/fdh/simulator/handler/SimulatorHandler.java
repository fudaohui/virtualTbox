package com.fdh.simulator.handler;

import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.Simulator;
import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.task.SendDataTask;
import com.fdh.simulator.utils.BuildPacketService;
import com.fdh.simulator.utils.ByteUtils;
import com.fdh.simulator.utils.SpringContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SimulatorHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private Simulator simulator;

    public SimulatorHandler() {
        threadPoolTaskExecutor = SpringContextUtils.getBean("taskExecutor");
        simulator = SpringContextUtils.getBean("simulator");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        byte[] bytes = (byte[]) msg;
        // TODO: 2020/1/9 待完成
        String vin = "";
//        String vin = BuildPacketService.parseByte2Vin(bytes);
        //解析车辆登入是否成功
        if (bytes[2] == 0x01) {//登陆数据
            if (bytes[3] == 0x01) {
                Channel channel = ctx.channel();
                NettyChannelManager.putLoginChannel(channel);//保存channel
                logger.info("[车辆][" + vin + "][登陆成功]" + ByteUtils.bytesToHexString(bytes));
            } else {
                //移除发登陆使用的连接
                NettyChannelManager.removeChannel(ctx.channel());
                logger.info("[车辆][" + vin + "][登陆失败]" + ByteUtils.bytesToHexString(bytes));
            }
        } else if (bytes[2] == 0x02) {//实时数据
            long packetSerail = ByteUtils.getLong(bytes, 24);
            //解析报文获得报文序列号，计算响应时间
            long receiveTimeMillis = System.currentTimeMillis();
            Long receiveTimeMillis1 = receiveTimeMillis;
            Long sendTimeMillis = PacketAnalyze.sendPacketMap.get(packetSerail);
            if (sendTimeMillis != null) {
                Integer diff = (int) (receiveTimeMillis1 - sendTimeMillis);
                PacketAnalyze.receiveMap.put(packetSerail, diff);
            }
            logger.info("[车辆][" + vin + "][RECE][NO." + packetSerail + "]->" + ByteUtils.bytesToHexString(bytes));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyChannelManager.removeChannel(ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接成功后，绑定channel和设备号
        Channel channel = ctx.channel();
        int i = BuildPacketService.connectCounn.intValue();
        String deviceCode = BuildPacketService.deviceMap.get(i);
        BuildPacketService.connectCounn.incrementAndGet();
        NettyChannelManager.putChannel(channel, deviceCode);//保存建立连接的channel
        //连接一旦建立发送登陆报文
        threadPoolTaskExecutor.execute(new SendDataTask(channel, CommandTagEnum.XBOX_LOGIN_REPORT, 0));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String vin = NettyChannelManager.getVin(ctx.channel());
//        logger.info("[车辆]["+vin+"["+"断开]");
        NettyChannelManager.removeChannel(ctx.channel());
    }
}
