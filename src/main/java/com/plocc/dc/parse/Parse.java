package com.plocc.dc.parse;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.rawdata.RawData;

public interface Parse {
	/**
	 * parse(根据用户给出的把对象转换成jsonobject对象)
	 * (根据rawdata中的format的值来判断rawdata的格式)
	 * @param data
	 * @return 
	 *JSONObject
	 * @exception 
	 * @since  1.0.0
	*/
	public JSONObject parse(RawData data) throws Exception;
}
