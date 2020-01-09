package com.fdh.simulator.task;

import com.fdh.simulator.codec.CustomerDelimiterBasedFrameDecoder;
import com.fdh.simulator.codec.StreamByteDecoder;
import com.fdh.simulator.codec.StreamByteEncoder;
import com.fdh.simulator.handler.SimulatorHandler;
import com.fdh.simulator.NettyChannelManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fudh
 * @ClassNmme ConnectTask
 * @date 2019/1/15 15:43
 * @Description: TODO
 */
public class ConnectTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConnectTask.class);
    private String address;
    private int port;
    private Integer taskId;
    private EventLoopGroup workgroup;

    //报文分隔符
    ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{0x7E});

    public ConnectTask(String address, int port, Integer taskId, EventLoopGroup workgroup) {
        this.address = address;
        this.port = port;
        this.taskId = taskId;
        this.workgroup = workgroup;
    }

    @Override
    public void run() {


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workgroup);
        // 指定连接通道类型
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);// TCP 连接保活机制，2小时监测一次
        // 接收缓冲区,最小32直接，初始是1500字节，最大65535字节
        bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(32, 1500, 65536));
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,15000);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StreamByteEncoder());//解码
                ch.pipeline().addLast(new CustomerDelimiterBasedFrameDecoder(2048, false, delimiter));//解码
                ch.pipeline().addLast(new SimulatorHandler());
            }
        });
        // 异步等待连接成功
        ChannelFuture connectFuture = null;
        try {
            // Start the client.
            connectFuture = bootstrap.connect(address, port).sync();
            connectFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {

            e.printStackTrace();
            logger.error("连接失败!" ,e);
        }catch (Exception e){
            logger.error("连接或关闭出现异常" ,e);
        }
    }

}
