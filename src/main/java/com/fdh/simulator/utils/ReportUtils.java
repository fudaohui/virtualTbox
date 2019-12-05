package com.fdh.simulator.utils;

import com.fdh.simulator.PacketAnalyze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);

    /**
     * 输出性能分析报告
     */
    public static void report() {
        logger.info("正在生成测试报告");
        String fileName = Utils.getNowDate();
        String report_path = PropertiesUtils.getProperty("client.report.path");
        File path = new File(report_path);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(report_path + fileName + ".log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                return;
            }
        }
        //创建FileOutInputStream的对象
        FileOutputStream fos = null;
        try {
            String tile = "|发送总报文数|\t\t|接收总报文数|\t\t|丢失率|\t\t|最大响应时间(ms)|\t\t|最小响应时间(ms)|\t\t|平均响应时间(ms)|\n";
            double sendCount = PacketAnalyze.atomicLong.get();//总发送量
            double receiveCount = PacketAnalyze.receiveMap.size();//总接收送量
            double lostPercent = 0;
            if (sendCount != 0) {
                lostPercent = (sendCount - receiveCount) * 100 / sendCount;
            }
            Collection<Integer> values = PacketAnalyze.receiveMap.values();

            List<Integer> receviceList = new ArrayList<Integer>(values);
            String max = PacketAnalyze.max(receviceList);
            String min = PacketAnalyze.min(receviceList);
            String average = PacketAnalyze.average(receviceList);
            String blank = "\t\t\t\t\t";
            StringBuffer sbf = new StringBuffer("");
            sbf.append((int) sendCount)//总发送
                    .append(blank)
                    .append((int) receiveCount)//总接收
                    .append(blank)
                    .append(String.format("%.2f", lostPercent))//丢包率
                    .append(blank)
                    .append(max)//最大响应时间
                    .append(blank)
                    .append(min)//最小响应时间
                    .append(blank)
                    .append(average)//最小响应时间
                    .append(blank);
            fos = new FileOutputStream(file, true);
            fos.write(tile.getBytes("utf-8"));
            fos.write(sbf.toString().getBytes("utf-8"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        logger.info("测试报告生成完毕");
    }

//    public static void main(String[] args) {
//        report();
//    }
}
