//package com.fdh.simulator;
//
//import io.netty.channel.Channel;
//import net.jodah.expiringmap.ExpirationListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * @author fudh
// * @ClassNmme ExpirieListenner
// * @date 2019/1/19 16:05
// * @Description: TODO
// */
//public class ExpirieListenner implements ExpirationListener<String, Channel> {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(ExpirieListenner.class);
//
//    @Override
//    public void expired(String channelId, Channel channel) {
//
//        //过期时间到所有的entry
//        try {
//            //取消调度任务
////            if (Simulator.bisRuning) {
////                logger.info("测试时间到");
////                Simulator.timer.cancel();
////                ReportUtils.report();
////                Simulator.bisRuning = false;
////            }
//            NettyChannelManager.removeChannel(channel);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//
//        } finally {
////            long activeChannelSize = NettyChannelManager.getActiveChannelSize();
////            if (activeChannelSize <= 0) {
////                if (PacketAnalyze.packetMap.size() == PacketAnalyze.atomicLong.get()) {
////
////                } else {
////
////                    //发送报文为收到完全，等待5秒钟
////                    int count = 10;
////                    for (int i = 0; i < count; i++) {
////                        try {
////                            Thread.sleep(1000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                    logger.info("**********************测试时间到**********************");
////                    Simulator.timer.cancel();
////                    ReportUtils.report();
////                }
//
////            }
//        }
//    }
//}
