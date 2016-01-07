package com.plocc.dc.queue.handler;

import org.apache.log4j.Logger;

public class LogQueueExceptionHandler implements QueueExceptionHandler {
	private Logger logger = Logger.getLogger(LogQueueExceptionHandler.class);
	
	@Override
	public void doHandler(Object obj) {
		logger.error(obj.toString());
	}

}
