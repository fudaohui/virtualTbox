package com.fdh.simulator;


import com.fdh.simulator.listenner.PacketLisenner;
import net.jodah.expiringmap.ExpiringMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/***
 * 数据包性能统计
 */
public class PacketAnalyze {

    public static AtomicLong atomicLong = new AtomicLong(0);

    /**
     * key packetserialNum
     * value send packet timestamp
     */
    public static ExpiringMap<Long, Long> sendPacketMap = ExpiringMap.builder()
            .variableExpiration()
            .expirationListener(new PacketLisenner())
            .build();



    /***
     * key packetserialNum
     * value send and recevie time
     */
    public static ConcurrentHashMap<Long, Integer> receiveMap = new ConcurrentHashMap<Long, Integer>();

    public static long getPacketSerialNum() {
        return atomicLong.incrementAndGet();
    }

    /**
     * 分析报文性能
     */
    public static List<Integer> analyze() {
        List<Integer> responseDiff = (List<Integer>) receiveMap.values();
        Collections.sort(responseDiff);
        return responseDiff;
    }

    /**
     * 计算平均响应时间
     *
     * @return
     */
    public static String average(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 0 + "";
        }
        OptionalDouble average = list.stream().mapToInt(Integer::intValue).average();
        return String.format("%.2f", average.getAsDouble());//保留两位小数
    }

    /***
     * 计算最大响应时间
     * @param list
     * @return
     */
    public static String max(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 0 + "";
        }
        OptionalInt OptionalMax = list.stream().mapToInt(Integer::intValue).max();
        Integer max = OptionalMax.getAsInt();
        return max + "";
    }

    /***
     * 计算最小响应时间
     * @param list
     * @return
     */
    public static String min(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return 0 + "";
        }
        OptionalInt OptionalMin = list.stream().mapToInt(Integer::intValue).min();
        Integer min = OptionalMin.getAsInt();
        return min + "";
    }

    /***
     * 计算丢包率
     * @param
     * @return
     */
    public static String getSupplementary(List<Integer> sends, List<Integer> receivces) {
        boolean suppleList = sends.remove(receivces);
        return sends.size() + "";

    }


    public static void main(String[] args) throws InterruptedException {
//        long currentTimeMillis = System.currentTimeMillis();
//        List<Integer> a = new ArrayList<>();
//        for (int i = 0; i < 10000000; i++) {
//            a.add(i);
//        }
//        String average = average(a);
//        String max = max(a);
//        String min = min(a);
//        long currentTimeMillis1 = System.currentTimeMillis();
//        System.out.println(average);
//        System.out.println(max);
//        System.out.println(min);
//        System.out.println(currentTimeMillis1 - currentTimeMillis);
//
//        for (int i = 0; i < 10; i++) {
//            sendPacketMap.put(i, (long) i,1, TimeUnit.SECONDS);
//        }
//
//
//        for (int i = 0; i < 1000000; i++) {
//
//            Thread.sleep(1000);
//        }

    }
}
