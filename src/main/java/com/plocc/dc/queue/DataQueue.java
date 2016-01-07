package com.plocc.dc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.queue.handler.QueueExceptionHandler;

/**
 * 放格式化后的数据队列
 * DataQueue
 * 2013-7-2-下午3:06:48
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class DataQueue implements Queue<JSONObject> {

	private Logger logger = Logger.getLogger(DataQueue.class);
	
	private QueueExceptionHandler exceptionHandler; 
	private int maxSize = 1000; // 队列长度

	private final ConcurrentLinkedQueue<JSONObject> container = new ConcurrentLinkedQueue<JSONObject>();

	public void setExceptionHandler(QueueExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	public void setMaxSize(int maxSize) {
		if (maxSize >  0){
			this.maxSize = maxSize;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.plocc.dc.queue.Queue#push(java.lang.Object)
	 */
	public synchronized boolean push(JSONObject jsonObject) {
		boolean result = true;
		
		if (container.size() > maxSize || !container.add(jsonObject)){
			logger.warn("加入队列失败:" + jsonObject.toJSONString() + " 队列当前值:" + container.size() + " 最大值:" + maxSize);
			
			result = false;
			exceptionHandler.doHandler(jsonObject.toJSONString());
		}else{
			if (logger.isDebugEnabled()){
				logger.debug("save JSONObject success." + jsonObject.toJSONString());
			}
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.plocc.dc.queue.Queue#pop()
	 */
	public synchronized JSONObject pop() {
		return (JSONObject)container.poll();
	}

}
