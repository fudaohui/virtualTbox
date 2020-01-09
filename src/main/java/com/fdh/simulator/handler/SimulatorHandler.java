package com.fdh.simulator.handler;

import com.fdh.simulator.CalculateSevice;
import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.Simulator;
import com.fdh.simulator.constant.CommandTagEnum;
import com.fdh.simulator.model.Tbox;
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
    private CalculateSevice calculateSevice;
    private Simulator simulator;

    public SimulatorHandler() {
        threadPoolTaskExecutor = SpringContextUtils.getBean("taskExecutor");
        simulator = SpringContextUtils.getBean("simulator");
        calculateSevice = SpringContextUtils.getBean("calculateSevice");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        byte[] bytes = (byte[]) msg;
        Tbox tbox = BuildPacketService.parsePacket(bytes);
        String deviceCode = tbox.getDeviceCode();
        //解析车辆登入是否成功
        if (tbox.getCommandTagEnum() == CommandTagEnum.XBOX_COMMON_RESP) {//登陆数据
            if (tbox.getRet() == 0) {
                Channel channel = ctx.channel();
                NettyChannelManager.putLoginChannel(channel);//保存channel
                logger.info("[车辆][" + deviceCode + "][登陆成功]" + ByteUtils.bytesToHexString(bytes));
            } else {
                //移除发登陆使用的连接
                NettyChannelManager.removeChannel(ctx.channel());
                logger.info("[车辆][" + deviceCode + "][登陆失败]" + ByteUtils.bytesToHexString(bytes));
            }
        } else if (tbox.getRet() == 0) {//实时数据
            Short serialNum = tbox.getSerialNum();
            //解析报文获得报文序列号，计算响应时间
            long receiveTimeMillis = System.currentTimeMillis();
            Long receiveTimeMillis1 = receiveTimeMillis;
            Long sendTimeMillis = PacketAnalyze.sendPacketMap.get(deviceCode + serialNum);
            if (sendTimeMillis != null) {
                Integer diff = (int) (receiveTimeMillis1 - sendTimeMillis);
                calculateSevice.calculateTime(diff);
            }
            logger.info("[车辆][" + deviceCode + "][RECE][NO." + serialNum + "]->" + ByteUtils.bytesToHexString(bytes));
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
