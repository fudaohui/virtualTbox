/*
 * 文件名： ByteToObjDecoder.java
 *
 * 创建日期： 2016年11月29日
 *
 * Copyright(C) 2016, by <a href="mailto:liws@xingyuanauto.com">liws</a>.
 *
 * 原始作者: liws
 *
 */
package com.fdh.simulator.codec;

import com.fdh.simulator.NettyChannelManager;
import com.fdh.simulator.utils.ByteArrayUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 消息解码器 进行拆包解包
 *
 * @author <a href="mailto:liws@xingyuanauto.com">liws</a>
 * @version $Revision$
 * @since 2016年11月29日
 */
public class StreamByteDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(StreamByteEncoder.class);

    //消息头部起始标记
    private static final ByteBuf START_TAG_BYTEBUF = Unpooled.copiedBuffer("##".getBytes());

    private static final byte[] START_TAG_BYTES = "##".getBytes();
    //起始符 字节长度
    private static final int START_TAG_BYTES_LENGTH = START_TAG_BYTES.length;
    //报文 数据单元长度索引
    private static final byte DATA_LENGTH_INDEX = 22;

    //报文 数据单元长度 所占字节数 2字节
    private static final byte DATA_LENGTH_SIZE = 2;

    //报文 校验位所占字节数 1字节
    private static final byte BCC_DATA_SIZE = 1;

    /**
     * 解决接收数据粘包问题 进行拆包操作,采用先读 前2字节,判断为"##"则继续读取index=22数据单元长度
     * 根据 起始字节长度 + 数据单元索引 + 数据单元长度 + BCC校验位长度 = 报文总体长度
     * 注意：此方法会被调用多次知道没有数据可以度，所有千万不要一次读取数据不够就判定数据不对
     *
     * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, List)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        try {
            int readerSize = buf.readableBytes();
            if (readerSize < (DATA_LENGTH_INDEX + DATA_LENGTH_SIZE))
                return;
            //先读取前两个字节
            byte[] startTagBytes = new byte[START_TAG_BYTES_LENGTH];
            //标记读索引
            buf.markReaderIndex();
            buf.readBytes(startTagBytes, 0, START_TAG_BYTES_LENGTH);
            //buf.resetReaderIndex();
            //判断两个是否为##起始符
            if (Arrays.equals(START_TAG_BYTES, startTagBytes)) {
                //跳到数组单元长度字节
                buf.skipBytes(DATA_LENGTH_INDEX - START_TAG_BYTES_LENGTH);
                //字节长度 第22位字节 2字节长度
                byte[] dataLenBytes = new byte[DATA_LENGTH_SIZE];
                buf.readBytes(dataLenBytes, 0, DATA_LENGTH_SIZE);
                buf.resetReaderIndex();
                //数据单元包长度
                int dataLen = ByteArrayUtil.toInt(dataLenBytes);
                //所有报文包总体长度
                int objBytesLen = START_TAG_BYTES_LENGTH + DATA_LENGTH_INDEX + dataLen + BCC_DATA_SIZE;
                //再次校验 buf容量是否容纳 报文长度 未达到报文定义长度,跳出
                if (readerSize < objBytesLen) {
//					logger.error("[StreamByteDecoder][原始报文有误##][长度有误]");
//                    ctx.channel().close();
                    return;
                }
                byte[] objBytes = new byte[objBytesLen];
                buf.readBytes(objBytes, 0, objBytesLen);
                out.add(objBytes);
            } else {
                buf.resetReaderIndex();
                byte[] objBytes = new byte[readerSize];
                buf.readBytes(objBytes, 0, readerSize);
                logger.error("[StreamByteDecoder][拆包时出现头两个字节不是##][dataMessage=" + ByteArrayUtil.toHexString(objBytes) + "]");
                buf.resetReaderIndex();
                buf.readBytes(1);
                NettyChannelManager.removeChannel(ctx.channel());//接收数据不正确断开连接，防止长时间占用连接
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            byte[] bytes = new byte[buf.writerIndex() - buf.readerIndex()];
            buf.readBytes(bytes);
            logger.error(e.getMessage() + "错误[Bytes]->" + ByteArrayUtil.toHexString(bytes), e);
            ctx.channel().close();//接收数据不正确断开连接，防止长时间占用连接
        }
    }
}
