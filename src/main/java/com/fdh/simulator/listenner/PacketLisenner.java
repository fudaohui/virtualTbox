package com.fdh.simulator.listenner;

import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.PacketAnalyze;
import com.fdh.simulator.Simulator;
import com.fdh.simulator.utils.ReportUtils;
import com.fdh.simulator.utils.VechileUtils;
import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpiringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fudh
 * @ClassNmme PacketLisenner
 * @date 2019/1/22 11:48
 * @Description: 发送的数据包过期策略
 */
public class PacketLisenner implements ExpirationListener<Long, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PacketLisenner.class);

    @Override
    public void expired(Long packetSerailNum, Long timestamp) {
        ExpiringMap<Long, Long> sendPacketMap = PacketAnalyze.sendPacketMap;
        if ( VechileUtils.sendCount == 0 && sendPacketMap.size() == 0) {
            logger.error("***********************************************************测试完成***********************************************************");
            Simulator.timer.cancel();
            //断开所有的连接不在接收报文
            logger.error("开始移除所有的连接");
            NettyChannelManager.removeAll();
            logger.error("断开所有的连接");
            ReportUtils.report();
            ConcurrentHashMap<Long, Integer> receiveMap = PacketAnalyze.receiveMap;
            Set<Map.Entry<Long, Integer>> entries = receiveMap.entrySet();
            for (Map.Entry<Long, Integer> entry : entries) {
                Long key = entry.getKey();
                Integer value = entry.getValue();
                logger.error("数据包："+key+"过期了，累计耗时："+value);
            }

        }

    }
}
