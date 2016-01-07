package com.plocc.dc.parse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.rawdata.RawData;

public class KeyValueParse implements Parse {

	public JSONObject parse(RawData data) {

		if (data == null || data.getRecordData() == null || data.getRecordData().isEmpty()) {
			return null;
		}

		Map<String, String> jsonMap = new HashMap<String, String>();
		
		// 对于数组参数转化为{a,b}的形式存贮
		Map<String,Object> map = data.getRecordData();
		for (String key : map.keySet()) {
			if (map.get(key).getClass().isArray()){ 
				String[] values = (String[]) map.get(key);
				if (values.length > 1){
					jsonMap.put(key, ArrayUtils.toString(values));
				}else{
					jsonMap.put(key, values[0].trim());
				}
			}else{
				jsonMap.put(key, ((String)map.get(key)).trim());
			}
		}
		
		JSONObject json = ((JSONObject) JSONObject.toJSON(jsonMap));
		return json;
	}

}
