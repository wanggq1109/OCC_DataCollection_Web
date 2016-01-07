package com.plocc.dc.match;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Match
 * 
 * bpLiang
 * bpLiang
 * 2013-6-28-下午03:19:52
 * 
 * @version 1.0.0
 * 
 */
public interface Match {
	/**
	 * isMatch(根据业务匹配出对应的match对象)
	 * 
	 * @param businessPatten
	 *            boolean
	 * @exception
	 * @since 1.0.0
	 */
	public abstract boolean isMatch(String businessPatten);

	/**
	 * output(输出的方法)
	 * 
	 * @param obj
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void output(JSONObject obj);

	/**
	 * failover(保存失败的处理)
	 * 
	 * @param obj
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void failover(JSONObject obj);
	
	/**
	 * 返回match的名称
	 * @return 
	 */
	public abstract String getName(); 
}
