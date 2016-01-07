package com.plocc.dc.assemble;


/**
 * 装配系统组件异常
 * @author wuxin
 *
 */
public class AssembleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1763027145994751456L;
	
	public AssembleException(String msg) {
		super(msg);
	}
	public AssembleException(Throwable t) {
		super(t);
	}

}
