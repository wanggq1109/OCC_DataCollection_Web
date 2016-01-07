package com.plocc.dc.match;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.dao.execpetion.DBErrorException;
import com.plocc.dc.service.MongoService;

public class MongodbMatch extends AbstractConfigedMatch  implements InitializingBean {
	private Logger logger = Logger.getLogger(MongodbMatch.class);

	private MongoService mongoService;

	public void setMongoService(MongoService mongoService) {
		this.mongoService = mongoService;
	}

	@Override
	public void output(JSONObject json) {
		if (mongoService != null)
			try {
				mongoService.save(json);
			} catch (DBErrorException e) {
				logger.error("保存错误 " + json.toJSONString());
			}
	}


	@Override
	public void failover(JSONObject json) {
		logger.error("保存错误 " + json.toJSONString());
	}

	public void afterPropertiesSet() throws Exception {
		if(mongoService==null){
			logger.error("mongoService is null,please check it;");
			throw new Exception("mongoService is null");
		}
	}

	@Override
	public String getName() {
		return "mongo";
	}

}
