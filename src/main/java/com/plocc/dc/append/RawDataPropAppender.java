package com.plocc.dc.append;

import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.common.Constants;
import com.plocc.dc.rawdata.RawData;

/**
 * 将rowdata中的其它属性写入记录
 * 2013-7-4-上午10:34:17
 * @author wuxin
 * @version 1.0.0
 * 
 */
public class RawDataPropAppender extends Appender {
   private final static String CLIENT_TIME = "client-time";
   private final static String USER_IP = "user-ip";
   private final static String BUSINESS_PATTERN = Constants.BUSINESS_PATTERN;
   
   private String defaultBusinessPattern;
   public void setDefaultBusinessPattern(String defaultBusinessPattern) {
	this.defaultBusinessPattern = defaultBusinessPattern;
}
   
   
	@Override
	public void append(RawData rawData, JSONObject obj) {
		appendDate(rawData, obj);
		appendClientIP(rawData, obj);
		appendBussinePattern(rawData, obj);
		
		if (getNext() != null && getNext() != this){
			 getNext().append(rawData, obj);
		}
	}


	private void appendDate(RawData rawData, JSONObject obj) {
		if (rawData != null && obj != null && rawData.getDate() != null){
				obj.put(CLIENT_TIME, new SimpleDateFormat(Constants.DATEFORMAT).format(rawData.getDate()));
		}
	}
	
	private void appendClientIP(RawData rawData, JSONObject obj) {
		if (rawData != null && obj != null && rawData.getClientIP() != null){
				obj.put(USER_IP, rawData.getClientIP());
		}
	}
	
	private void appendBussinePattern(RawData rawData, JSONObject obj) {
		if (rawData != null && obj != null ){
				obj.put(BUSINESS_PATTERN, rawData.getBusinessPattern() == null? defaultBusinessPattern : rawData.getBusinessPattern() );
		}
	}


}
