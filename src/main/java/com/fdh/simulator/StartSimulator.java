package com.fdh.simulator;

import com.fdh.simulator.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Scanner;


public class StartSimulator {

    private static final Logger logger = LoggerFactory.getLogger(StartSimulator.class);

    public static void main(String[] args) {
        //加载spring配置文件
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-base.xml");
        ctx.start();
        Scanner scanner = new Scanner(System.in);//接收键盘输入的数据
        System.out.println("**************请输入数字1开始测试**************");
//        Simulator simulator = SpringContextUtils.getBean("simulator");
//        simulator.connect();
        while (scanner.hasNext()) {//现在有输入数据
            int data = scanner.nextInt();
            if (data == 1) {
                Simulator simulator = SpringContextUtils.getBean("simulator");
                simulator.connect();
                break;
            } else {
                System.out.println("输入有误！");
            }

        }
    }
}
