package com.plocc.dc.service;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.dao.execpetion.DBErrorException;

public interface MongoService {
	public int save(JSONObject data) throws DBErrorException;
}
