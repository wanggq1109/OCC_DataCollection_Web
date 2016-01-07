package com.plocc.dc.consumer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.common.Constants;
import com.plocc.dc.match.Match;
import com.plocc.dc.match.MatchManager;

public class DataQueueConsumer extends Consumer<JSONObject> {
	private Logger logger = Logger.getLogger(FormatConsumer.class);

	@Autowired
	private MatchManager matchManager;

	@Override
	public void consumer(JSONObject jsonObject) {
		List<Match> matchList = null;
		if (jsonObject != null) {
			// get bussinePatten
			String bussinePatten = (String) jsonObject.get(Constants.BUSINESS_PATTERN);
			if (bussinePatten != null) {
				Match matchRef = null;
				try {
					// find a match
					matchList = matchManager.findMatch(bussinePatten);
					for (Match match : matchList) {
						matchRef = match; // for failover
						if (match != null) {
							// to save jsonObject
							match.output(jsonObject);
						} else {
							logger.error("save error :"+ jsonObject.toJSONString());
						}
					}
				} catch (Throwable e) {
					logger.error(e);

					// to save fail
					if (matchRef != null) {
						matchRef.failover(jsonObject);
					}
				}

			} else {
				logger.warn("businessPattern 为空:" + jsonObject);
			}
		}

	}

}
