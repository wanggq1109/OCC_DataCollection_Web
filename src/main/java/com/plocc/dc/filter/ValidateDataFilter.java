package com.plocc.dc.filter;

import org.apache.commons.lang3.StringUtils;

import com.plocc.dc.rawdata.RawData;

public class ValidateDataFilter extends AbstractDataFilter {

	public boolean accept(RawData data) {
		return true;
	}

	public boolean filter(RawData data) {
		if (data == null || data.getRecordData() == null || data.getRecordData().isEmpty()) {
			return false;
		}
		
		String businessPattern = data.getBusinessPattern();
		if (StringUtils.isBlank(businessPattern)){
			return false;
		}
		
		// 由下一个处理链处理
		if (getNext() != null){
			return getNext().filter(data);
		}
		
		return true;

	}


}
