/*
 * 文件名： ReportObjEncoder.java
 * 
 * 创建日期： 2016年11月29日
 *
 * Copyright(C) 2016, by <a href="mailto:liws@xingyuanauto.com">liws</a>.
 *
 * 原始作者: liws
 *
 */
package com.fdh.simulator.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * 上报消息 编码器
 *
 * @author <a href="mailto:liws@xingyuanauto.com">liws</a>
 *
 * @version $Revision$
 *
 * @since 2016年11月29日
 */
public class StreamByteEncoder extends MessageToByteEncoder<byte[]> {

	private static final Logger logger = LoggerFactory.getLogger(StreamByteEncoder.class);
	 /**
	 *
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] obj, ByteBuf bytebuf) {
		try{
			bytebuf.writeBytes(obj);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally {
//			ReferenceCountUtil.release(bytebuf);
		}
	}



}
