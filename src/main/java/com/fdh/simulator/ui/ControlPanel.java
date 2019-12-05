//package com.fdh.simulator.ui;
//
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.util.Timer;
//
//import javax.swing.BorderFactory;
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//
//import com.fdh.simulator.NettyChannelManager;
//import com.fdh.simulator.utils.PropertiesUtils;
//import com.fdh.simulator.utils.SpringContextUtils;
//import com.fdh.simulator.utils.Utils;
//import com.fdh.simulator.task.ScheduleTask;
//import io.netty.util.internal.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class ControlPanel extends JFrame implements ActionListener {
//    private static Logger logger = LoggerFactory.getLogger(ControlPanel.class);
//    private static final long serialVersionUID = -6131834927905414166L;
//    private JTextField tfsimulatorID, tfIPAdress, tfPort, tfHeatBeat, tcpNum, testTime;
//    private JLabel label0;
//    private JLabel label1;
//    private JLabel label2;
//    private JLabel label3;
//    private JLabel label4, label5, label6;
//    private JRadioButton radioButton1;
//    private JRadioButton radioButton2;
//    private JRadioButton radioButton3;
//    private JRadioButton radioButton4, radioButton5;
//    private JButton btConnect, btDisonnect;
//    private JScrollPane sp1;
//    private JPanel jp1;
//    private Simulator simulator;
//    private String ip = "192.168.0.101";
//    private String port = "7706";
//    private String sendInterval = "1000";
//    private String testAvailableTime = "30";
//    private String tcpConnections = "30";
//    /**
//     * 多选列表
//     */
//    private JList jList;
//
//    /**
//     * 下拉列表
//     */
//    private JComboBox comboBox1;
//
//    /**
//     * 多行文本
//     */
//    private JTextArea textArea1;
//
//    /**
//     * 创建图片对象
//     */
//
//    /**
//     * 初始化窗口
//     */
//    public ControlPanel() {
//        simulator = SpringContextUtils.getBean("simulator");
//        //初始化配置
//        initConfig();
//        //初始化UI
//        init();
//    }
//
//    public void init() {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//        } catch (ClassNotFoundException ex) {
//            // LoggerFactory.getLogger(simulatorSimulator.class.getName()).log(Level.SEVERE,
//            // null, ex);
//        } catch (InstantiationException ex) {
//            // LoggerFactory.getLogger(simulatorSimulator.class.getName()).log(Level.SEVERE,
//            // null, ex);
//        } catch (IllegalAccessException ex) {
//            // LoggerFactory.getLogger(simulatorSimulator.class.getName()).log(Level.SEVERE,
//            // null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            // LoggerFactory.getLogger(simulatorSimulator.class.getName()).log(Level.SEVERE,
//            // null, ex);
//        }
//
//
//        jp1 = new JPanel(null);
//        jp1.setBorder(BorderFactory.createTitledBorder("Config"));
//        jp1.setBounds(15, 10, 330, 490);
//        // 设置容器为空布局，绝对定位
//        this.setLayout(null);
//        // 创建图片对象
//        // 创建字体对象
//        Font font = new Font("微软雅黑", Font.BOLD, 15);
//        Font font1 = new Font("微软雅黑", Font.BOLD, 12);
//        // 创建颜色对象
//
//        //开始X坐标
//        int x = 20;
//        //input的其实位置
//        int x1 = 120;
//        //开始y坐标
//        int y = 30;
//        //垂直高度
//        int height = 30;
//        //垂直偏移量
//        int height1 = 40;
//        //水平宽度
//        int width = 150;
//        int width1 = 180;
//        /********IP***********/
//        // IPAddress
//        label1 = new JLabel("服务地址:");
//        label1.setBounds(x, y, width, height);
//        label1.setFont(font);
//        // simulatorIDText
//        tfIPAdress = new JTextField(ip);
//        tfIPAdress.setBounds(x1, y, width1, height);
//        /********PORT***********/
//        // Port
//        label2 = new JLabel("服务端口:");
//        label2.setBounds(x, y + height1, width, height);
//        label2.setFont(font);
//        // PortText
//        tfPort = new JTextField(port);
//        tfPort.setBounds(x1, y + height1, width1, height);
//        /********发送间隔***********/
//        // interval
//        label3 = new JLabel("发送间隔(ms):");
//        label3.setBounds(x, y + height1 * 2, width, height);
//        label3.setFont(font);
//        // 心跳时间
//        tfHeatBeat = new JTextField();
//        tfHeatBeat.setBounds(x1, y + height1 * 2, width1, height);
//        tfHeatBeat.setText(sendInterval);
//        /********TCP连接数***********/
//        // interval
//        label5 = new JLabel("TCP连接数:");
//        label5.setBounds(x, y + height1 * 3, width, height);
//        label5.setFont(font);
//        //
//        tcpNum = new JTextField();
//        tcpNum.setBounds(x1, y + height1 * 3, width1, height);
//        tcpNum.setText(tcpConnections);
//        /********测试时间***********/
//        // interval
//        label6 = new JLabel("测试时间(mi):");
//        label6.setBounds(x, y + height1 * 4, width, height);
//        label6.setFont(font);
//        // 心跳时间
//        testTime = new JTextField();
//        testTime.setBounds(x1, y + height1 * 4, width1, height);
//        testTime.setText(testAvailableTime);
//        /********终端命令***********/
//        label4 = new JLabel("终端命令:");
//        label4.setBounds(x, y + height1 * 5, width, height);
//        label4.setFont(font);
//        radioButton1 = new JRadioButton("注册");
//        radioButton1.setFont(font);
//        radioButton1.setBounds(x1, y + height1 * 5, width, height);
//        radioButton1.setSelected(true);
//
//        radioButton2 = new JRadioButton("登陆");
//        radioButton2.setFont(font);
//        radioButton2.setBounds(x1, y + height1 * 6, width, height);
//
//        radioButton3 = new JRadioButton("校时");
//        radioButton3.setFont(font);
//        radioButton3.setBounds(x1, y + height1 * 7, width, height);
//
//        radioButton4 = new JRadioButton("实时");
//        radioButton4.setFont(font);
//        radioButton4.setBounds(x1, y + height1 * 8, width, height);
//
//        radioButton5 = new JRadioButton("心跳");
//        radioButton5.setFont(font);
//        radioButton5.setBounds(x1, y + height1 * 9, width, height);
//
//        // 将单选按钮放在按钮组中，实现单选效果
//        ButtonGroup bg = new ButtonGroup();
//        bg.add(radioButton1);
//        bg.add(radioButton2);
//        bg.add(radioButton3);
//        bg.add(radioButton4);
//        bg.add(radioButton5);
//
//        width = 120;
//        height = 45;
//        btConnect = new JButton("Connect");
//        btConnect.setFont(font);
//        btConnect.setBounds(x, y + height1 * 10, width, height);
//
//        btDisonnect = new JButton("Disconnect");
//        btDisonnect.setFont(font);
//        btDisonnect.setBounds(x + 160, y + height1 * 10, width, height);
//
//        textArea1 = new JTextArea();
//        textArea1.setLineWrap(true); // 激活自动换行功能
//        // textArea1.setWrapStyleWord(true); // 激活断行不断字功能
//        textArea1.setFont(font1);
//        // 有滚动条的容器，用来装多行文本框
//        sp1 = new JScrollPane(textArea1);
//        sp1.setBounds(370, 10, 340, 490);
//        sp1.setBorder(BorderFactory.createTitledBorder("Receive Area:"));
//        addContainerAndLisen();
//
//        // 设置标题
//        this.setTitle("T-BOX模拟终端");
//        // 设置窗口的关闭策略
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        // 设置窗口大小
//        this.setSize(750, 550);
//        // 设置窗口居中，放在窗口大小后面，null表示桌面
//        this.setLocationRelativeTo(null);
//        // 将窗口设置为显示,要写在最后一句
//        this.setVisible(true);
//        // 监听事件
//
//        simulator.addPropertyChangerListsener(new PropertyChangeListener() {
//
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                String str = (String) evt.getNewValue();
//                textArea1.append(Utils.getNowDate() + ": ");
//                textArea1.append(str);
//                textArea1.append("\n");
//            }
//        });
//    }
//
//    private void addContainerAndLisen() {
//        // 将组件加入到容器中
////        jp1.add(label0);
////        jp1.add(tfsimulatorID);
//        jp1.add(label1);
//        jp1.add(tfIPAdress);
//        jp1.add(label2);
//        jp1.add(tfPort);
//
//        jp1.add(label3);
//        jp1.add(tfHeatBeat);
//        jp1.add(tcpNum);
//
//        jp1.add(label4);
//        jp1.add(label5);
//        jp1.add(label6);
//        jp1.add(testTime);
//        jp1.add(radioButton1);
//        jp1.add(radioButton2);
//        jp1.add(radioButton3);
//        jp1.add(radioButton4);
//        jp1.add(radioButton5);
//        jp1.add(btConnect);
//        jp1.add(btDisonnect);
//        this.add(jp1);
//        this.add(sp1);
//        btConnect.setEnabled(true);
//        btDisonnect.setEnabled(false);
//        btConnect.addActionListener(this);
//        btDisonnect.addActionListener(this);
//        radioButton1.addActionListener(this);
//        radioButton2.addActionListener(this);
//        radioButton3.addActionListener(this);
//        radioButton4.addActionListener(this);
//
//
//    }
//
//    /***
//     * 初始化配置
//     */
//    private void initConfig() {
//        ip = PropertiesUtils.getProperty("serverip");
//        port = PropertiesUtils.getProperty("serverport");
//        if (StringUtil.isNullOrEmpty(ip) || StringUtil.isNullOrEmpty(port)) {
//            logger.error("ip或者端口为空");
//        }
//        sendInterval = PropertiesUtils.getProperty("send_interval");
//        testAvailableTime = PropertiesUtils.getProperty("test_available_time");
//        tcpConnections = PropertiesUtils.getProperty("tcp_connections");
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if (e.getActionCommand().equals("Connect")) {
//            if (!simulator.isIsconnected()) {
//                //开始连接
//                String ipAdressText = tfIPAdress.getText();
//                int port = Integer.parseInt(tfPort.getText());
//                int tcpcount = Integer.parseInt(tcpNum.getText());
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        simulator.connect(ipAdressText,port ,tcpcount);
//                        //启动定时任务，每隔一定时间调度一次
//                        Integer inteval = Integer.valueOf(tfHeatBeat.getText());
//
//                    }
//                }).start();
//            } else {
//                JOptionPane.showMessageDialog(null, "连接失败");
//            }
//            btConnect.setEnabled(!simulator.isIsconnected());
//            btDisonnect.setEnabled(simulator.isIsconnected());
//        }
//        if (e.getActionCommand().equals("Disconnect")) {
//            simulator.close();
//            JOptionPane.showMessageDialog(null, "断开连接");
//            btDisonnect.setEnabled(simulator.isIsconnected());// 不可被点击
//            btConnect.setEnabled(!simulator.isIsconnected());
//        }
//        if (e.getActionCommand().equals("注册")) {
////             JOptionPane.showMessageDialog(null, "注册");
////            simulator.setsimulatorStatus(simulatorStatus.Normal);
//        }
//        if (e.getActionCommand().equals("登陆")) {
////            simulator.setsimulatorStatus(simulatorStatus.OutOfPaper);
//            // JOptionPane.showMessageDialog(null, "断开连接");
//        }
//        if (e.getActionCommand().equals("校时")) {
////            simulator.setsimulatorStatus(simulatorStatus.Busy);
//            // JOptionPane.showMessageDialog(null, "断开连接");
//        }
//        if (e.getActionCommand().equals("实时")) {
////            simulator.setsimulatorStatus(Utils.simulatorStatus.OtherError);
//            // JOptionPane.showMessageDialog(null, "其他错误");
//        }
//    }
//}