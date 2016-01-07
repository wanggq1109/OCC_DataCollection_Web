package com.plocc.dc.consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.plocc.dc.helper.ShutdownAble;
import com.plocc.dc.queue.Queue;

public abstract class Consumer<T> extends Thread  implements ShutdownAble,InitializingBean  {

	private static Logger logger = Logger.getLogger(Consumer.class);
	
	private int threadWaitTime = 5*1000; // sleep 5000s
	
	private Queue targetQueue;
	
	private volatile boolean isRun = true;
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while (isTurnOn()) {
			try {
				T t = (T)targetQueue.pop();
				if (t != null) {
					consumer(t);
				} else {
					Thread.sleep(threadWaitTime);
				}
			} catch (Throwable e) {
				logger.error(e);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.plocc.dc.helper.ShutdownAble#shutDown()
	 */
	@Override
	public void shutDown() {
		this.isRun = false;
	}

	/* (non-Javadoc)
	 * @see com.plocc.dc.helper.ShutdownAble#turnOn()
	 */
	@Override
	public void turnOn() {
		this.isRun = true;
	}
	
	/* (non-Javadoc)
	 * @see com.plocc.dc.helper.ShutdownAble#isTurnOn()
	 */
	@Override
	public boolean isTurnOn() {
		return isRun;
	}
	
	public void setThreadWaitTime(int threadWaitTime) {
		this.threadWaitTime = threadWaitTime;
	}
	
	public void setTargetQueue(Queue targetQueue) {
		this.targetQueue = targetQueue;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (targetQueue == null){
			throw new Exception("消费目标queue不能为空");
		}
	}
	/**
	 * 
	 * @param t 
	 */
	public abstract void consumer(T t);
	

	
}
