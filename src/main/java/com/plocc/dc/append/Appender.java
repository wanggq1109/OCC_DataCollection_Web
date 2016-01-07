package com.plocc.dc.append;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.rawdata.RawData;

public abstract class Appender {
	private Appender next;
	/**
	 * 往obj中添加数据
	 * @param rawData TODO
	 * @param obj 
	 */
	public abstract void append(RawData rawData, JSONObject obj);
	
	/**
	 * 返回一下处理者
	 * @return 
	 */
	public Appender getNext() {
		return next;
	}
	
	public void setNext(Appender next) {
		this.next = next;
	}
	
}
