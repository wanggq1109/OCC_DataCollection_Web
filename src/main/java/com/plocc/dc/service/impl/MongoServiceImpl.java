package com.plocc.dc.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.dao.MongoDao;
import com.plocc.dc.dao.execpetion.DBErrorException;
import com.plocc.dc.service.MongoService;

/***
 * 
 * 
 * MongoServiceImpl
 * 
 * bpLiang bpLiang 2013-6-24-下午01:36:22
 * 
 * @version 1.0.0
 * 
 */
public class MongoServiceImpl implements MongoService {

	private MongoDao mongoDao;

	public void setMongoDao(MongoDao mongoDao) {
		this.mongoDao = mongoDao;
	}


	public int save(JSONObject data) throws DBErrorException {
		return mongoDao.save(data);
	}

}
