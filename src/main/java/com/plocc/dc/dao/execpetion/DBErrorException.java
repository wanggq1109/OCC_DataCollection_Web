package com.plocc.dc.dao.execpetion;


/**
 * 
 * 数据库操作异常
 * 2013-6-26-上午10:29:37
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class DBErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3748577497194467894L;
	
	public DBErrorException() {
	}
	
	public DBErrorException(String msg) {
		super(msg);
	}

}
