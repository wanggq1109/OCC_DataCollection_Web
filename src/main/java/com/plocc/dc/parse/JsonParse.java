package com.plocc.dc.parse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.common.Constants;
import com.plocc.dc.rawdata.RawData;

public class JsonParse implements Parse {
	private Logger logger = Logger.getLogger(JsonParse.class);
	
	public JSONObject parse(RawData data) throws UnsupportedEncodingException {

		if (data == null || data.getRecordData() == null || data.getRecordData().isEmpty()) {
			return null;
		}

		
		Map map = data.getRecordData();
		Object objJson = map.get(Constants.REQJSON);
		if (objJson == null){
			return null;
		}
		
		String json = null;
		if (objJson.getClass().isArray()){
			String[] jsonStr = (String[]) map.get(Constants.REQJSON);
			if (jsonStr == null || jsonStr.length == 0) {
				return null;
			}
			
			json = jsonStr[0]; 
		}else{
			json =  (String)map.get(Constants.REQJSON);
		}
		
		if (json == null){
			return null;
		}

		json = URLDecoder.decode(json, Constants.ENCODING);
		
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) JSONObject.parse(json);
		} catch (Exception e) {
			logger.error("将rawdata转为了json出错:"+ data, e);
		}
		
		if (jsonObject == null){
			return null;
		}
		
		return jsonObject;
	}

}
