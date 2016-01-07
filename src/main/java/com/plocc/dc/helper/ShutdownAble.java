package com.plocc.dc.helper;

/**
 * 
 * 开关类
 * 
 * @author wuxin
 * @version 1.0.0
 * 
 */
public interface ShutdownAble {
	/**
	 * 关闭
	 */
	public void shutDown();

	/**
	 * 开启
	 */
	public void turnOn();

	/**
	 * 是否关闭状态
	 * 
	 * @return
	 */
	public boolean isTurnOn();
}
