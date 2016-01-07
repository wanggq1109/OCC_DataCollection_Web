package com.plocc.dc.queue.handler;

/**
 * 队列操作失败处理
 * QueueExceptionHandler
 * 2013-7-2-下午2:41:40
 * @author wuxin
 * @version 1.0.0
 * 
 */
public interface QueueExceptionHandler {
 /**
 * 处理队列操作异常
 * @param obj 
 */
void doHandler(Object obj);
}
