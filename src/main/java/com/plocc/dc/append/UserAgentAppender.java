package com.plocc.dc.append;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.rawdata.RawData;

/**
 * 
 * 增加user-agent
 * 2013-7-4-上午10:34:17
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class UserAgentAppender extends Appender {
   private final static String USER_AGENT = "user-agent";
   
	@Override
	public void append(RawData rawData, JSONObject obj) {
		// 如果没有传user-agent使用默认
		if (rawData != null && obj != null && rawData.getExtendData() != null && !obj.containsKey(USER_AGENT)){
			String userAgent = (String)rawData.getExtendData().get(USER_AGENT);
			if (StringUtils.isNotBlank(userAgent)){
				obj.put(USER_AGENT, userAgent);
			}
		}
		
		if (getNext() != null && getNext() != this){
			 getNext().append(rawData, obj);
		}
	}


}
