/**
 * 宝龙电商
 * org.powerlong.dc.dao
 * TestDao.java
 * 
 * 2013-6-19-下午05:41:46
 *  2013宝龙公司-版权所有
 * 
 */
package com.plocc.dc.dao;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.dao.execpetion.DBErrorException;

/**
 * TestDao
 * @see
 * Kira Sun
 * 2013-6-19-下午05:41:46
 * @version 1.0.0
 */
public interface MongoDao {
	/**
	 * 固化data
	 * @param data
	 * @return
	 * @throws DBErrorException
	 */
	public int save(JSONObject data) throws DBErrorException;
}
