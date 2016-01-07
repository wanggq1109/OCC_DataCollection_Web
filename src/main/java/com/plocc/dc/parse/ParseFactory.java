package com.plocc.dc.parse;

import com.plocc.dc.rawdata.FormatEnum;

public class ParseFactory {
	//init a Parse entity by format
	public static Parse getParase(FormatEnum format) {
		Parse parse = null;
		if (FormatEnum.JSON == format) {
			parse = new JsonParse();
		} else {
			parse = new KeyValueParse();
		}
		return parse;
	}
}
