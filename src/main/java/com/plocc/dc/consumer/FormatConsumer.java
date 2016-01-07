package com.plocc.dc.consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.append.Appender;
import com.plocc.dc.parse.Parse;
import com.plocc.dc.parse.ParseFactory;
import com.plocc.dc.queue.Queue;
import com.plocc.dc.rawdata.RawData;

public class FormatConsumer extends Consumer<RawData> implements InitializingBean {

	private static Logger logger = Logger.getLogger(FormatConsumer.class);
	
	private Queue jsonDataQueue;
	
	@Autowired
	private Appender appender;

	/**
	 * 解析
	 * @param rawData
	 * @return 
	 */
	private JSONObject parse(RawData rawData) {
		if (rawData == null) {
			return null;
		}

		JSONObject json = null;
		Parse parse = ParseFactory.getParase(rawData.getFormat());
		try {
			json = parse.parse(rawData);
		} catch (Exception e) {
			logger.error(" 消费rawData出错： " + rawData, e);
		}

		return json;
	}

	/* (non-Javadoc)
	 * @see com.plocc.dc.consumer.Consumer#consumer(java.lang.Object)
	 */
	public void consumer(RawData rawData) {
		if (rawData == null){
			return;
		}
		
		try {
			JSONObject json = parse(rawData);
			if (json == null){
				throw new Exception("解析后的json为空");
			}
			
			append(rawData,json);// to append a date
			
			jsonDataQueue.push(json);// push json dataqueue
		} catch (Exception e) {
			logger.error("消费rawData出错:" + rawData,e);
		}
	}

	private void append(RawData rawData,JSONObject json) {
		if (appender != null){
			appender.append(rawData, json);
		}
	}


	public void setJsonDataQueue(Queue jsonDataQueue) {
		this.jsonDataQueue = jsonDataQueue;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (jsonDataQueue == null) {
			throw new Exception("jsonDataQueue is null!");
		}
	}

}
