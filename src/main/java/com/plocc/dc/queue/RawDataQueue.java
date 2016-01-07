package com.plocc.dc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.plocc.dc.queue.handler.QueueExceptionHandler;
import com.plocc.dc.rawdata.RawData;

/**
 * 存放接收到的数据
 * RawDataQueue
 * 2013-7-2-下午3:07:13
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class RawDataQueue implements Queue<RawData> {


	private Logger logger = Logger.getLogger(DataQueue.class);
	
	private QueueExceptionHandler exceptionHandler; 
	private int maxSize = 1000; // 队列长度

	private final ConcurrentLinkedQueue<RawData> container = new ConcurrentLinkedQueue<RawData>();

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
	public synchronized boolean push(RawData rawData) {
		boolean result = true;
		
		if (container.size() > maxSize || !container.add(rawData)){
			logger.warn("加入队列失败:" + rawData + " 队列当前值:" + container.size() + " 最大值:" + maxSize);
			
			result = false;
			exceptionHandler.doHandler(rawData.toString());
		}else{
			if (logger.isDebugEnabled()){
				logger.debug("save RawData success." + rawData.toString());
			}
		}
		
		return result;

	}

	/* (non-Javadoc)
	 * @see com.plocc.dc.queue.Queue#pop()
	 */
	public synchronized RawData pop() {
		return (RawData)container.poll();
	}


}
