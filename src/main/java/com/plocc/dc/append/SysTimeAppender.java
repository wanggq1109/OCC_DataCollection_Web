package com.plocc.dc.append;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.common.Constants;
import com.plocc.dc.rawdata.RawData;

public class SysTimeAppender extends Appender {
	private final static String SYS_TIME = "sys-time";
	public void append(RawData rawData, JSONObject obj) {
		if (obj != null) {
			Date date = new Date();
			DateFormat sfm = new SimpleDateFormat(Constants.DATEFORMAT);
			obj.put(SYS_TIME, sfm.format(date));
		}
		
		if (getNext() != null && getNext() != this){
			 getNext().append(rawData, obj);
		}
	}

}
