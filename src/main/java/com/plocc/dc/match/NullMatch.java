package com.plocc.dc.match;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class NullMatch extends AbstractConfigedMatch {
	
	private static Logger logger = Logger.getLogger(NullMatch.class);
	@Override
	public void output(JSONObject json) {
		if (json != null){
		 logger.warn("use null match:" + json);
		}
	}
	
	@Override
	public void failover(JSONObject obj) {
		// do nothing
	}
	
	@Override
	public String getName() {
		return "null";
	}
}
