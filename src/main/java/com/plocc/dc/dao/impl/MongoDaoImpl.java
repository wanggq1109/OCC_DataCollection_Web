/**
 * 宝龙电商
 * org.powerlong.dc.dao.impl
 * TestDaoImpl.java
 * 
 * 2013-6-19-下午05:42:13
 *  2013宝龙公司-版权所有
 * 
 */
package com.plocc.dc.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.plocc.dc.common.Constants;
import com.plocc.dc.dao.MongoDao;
import com.plocc.dc.dao.execpetion.DBErrorException;
import com.plocc.dc.mongo.MongoRule;

/***
 * 
 * 
 * MongoDaoImpl
 * 
 * bpLiang
 * bpLiang
 * 2013-6-24-下午01:50:55
 * 
 * @version 1.0.0
 *
 */
public class MongoDaoImpl implements MongoDao{

	private Datastore ds;
	public MongoDaoImpl(Datastore ds) {
		this.ds = ds;
	}
	
	/* (non-Javadoc)
	 * @see org.powerlong.dc.dao.MongoDao#save(com.alibaba.fastjson.JSONObject)
	 */
	public int save(JSONObject data) throws DBErrorException {

		int affect = 0;

		if (data == null) {
			return affect;
		}

		DBAndCollectionNamePair namePair = new DBAndCollectionNamePair(data);

		// 创建DB
		DB db = ds.getMongo().getDB(MongoRule.dbName(namePair.getDbName()));
		if (db == null) {
			throw new DBErrorException("获取Mongo.DB异常");
		}

		// 创建COLLECTION
		DBCollection collection = db.getCollection(MongoRule.collectionName(namePair.getCollectionName()));
		if (collection == null) {
			throw new DBErrorException("获取Mongo.Collection异常");
		}

		Map map = jsonToMap(data);
		if (map == null) {
			return 0;
		}

		// 创建数据
		DBObject dataObj = new BasicDBObject(map);

		WriteResult rs;
		try {
			// 保存数据
			rs = collection.insert(dataObj, WriteConcern.SAFE);

			if (rs != null) {
				affect = rs.getN();
			}
		} catch (MongoException e) {
			throw new DBErrorException("保存数据到Mongo异常:" + e.getMessage()
					+ " 原始数据：" + data.toString());
		}

		return affect;
	}
	

	private Map jsonToMap(JSONObject data) throws DBErrorException {
		if (data == null || data.isEmpty()){
			return null;
		}
		
		Map map = new HashMap();
		
		Iterator it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			
			try {
				map.put(MongoRule.key(key), String.valueOf(data.get(key)));
			} catch (JSONException e) {
				throw new DBErrorException("JSON转换成MAP出错" + e.getMessage() + " 原始数据：" + data.toString()); 
			}
		}
		
		return map;
	}
	
	private final class DBAndCollectionNamePair {
		private String dbName = null;
		private String collectionName = null;

		private DBAndCollectionNamePair(JSONObject json) {
			if (json == null) {
				return;
			}
			String tag = String.valueOf(json.remove(Constants.BUSINESS_PATTERN));

			String[] dbCollectionName = StringUtils.split(tag, ".");
			if (dbCollectionName == null || dbCollectionName.length == 0) {
				return;
			}

			dbName = dbCollectionName[0];

			if (dbCollectionName.length > 1) {
				collectionName = dbCollectionName[1];
			}

		}

		public String getDbName() {
			return dbName;
		}

		public String getCollectionName() {
			return collectionName;
		}
	}
}
