/*
 * 文件名： IDictsEnumTag.java
 * 
 * 创建日期： 2016年11月28日
 *
 * Copyright(C) 2016, by <a href="mailto:liws@xingyuanauto.com">liws</a>.
 *
 * 原始作者: liws
 *
 */
package com.fdh.simulator.constant;

import java.io.Serializable;

/**
 * 单字节 枚举 字典类型 接口
 *
 * @author <a href="mailto:liws@xingyuanauto.com">liws</a>
 *
 * @version $Revision$
 *
 * @since 2016年11月28日
 */
public interface IDictsEnumTag extends Serializable{
	
	String getK();
	
	byte getV();
	
	IDictsEnumTag valueOf(byte pByte);
	
}
