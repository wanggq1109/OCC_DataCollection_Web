package com.plocc.dc.collecte;

import org.apache.log4j.Logger;

import com.plocc.dc.client.CollectionResultEnum;
import com.plocc.dc.common.Constants;
import com.plocc.dc.filter.DataFilter;
import com.plocc.dc.queue.Queue;
import com.plocc.dc.rawdata.FormatEnum;
import com.plocc.dc.rawdata.RawData;
import com.plocc.dc.rawdata.RawDataFactory;

public abstract class DataCollectionTemplate {
	private Logger logger = Logger.getLogger(DataCollectionTemplate.class);

	/**
	 * 构建RawData
	 * @param factory
	 * @return 
	 */
	private RawData buildRawData(RawDataFactory factory) {
		RawData data = factory.create();
		
		// 设置数据格式
		if (data != null && data.getFormat() == null && data.getRecordData() != null){
			String[] dfArray = (String[]) data.getRecordData().get(Constants.DATA_FORMAT);
			String df = null;
			if (dfArray != null && dfArray.length > 0) {
				df = dfArray[0];
			}
			
			data.setFormat(FormatEnum.convert(df,FormatEnum.RAW));
		}
		
		return data;
	}

	/**
	 * 过滤数据
	 * @param data
	 * @return 
	 */
	private boolean filter(RawData data) {
		if (data == null){
			logger.warn("RawData is null!");
			return false;
		}
		
		DataFilter dataFilter = getDataFilter();
		if (dataFilter == null){
			logger.warn("dataFilter is null!");
			return false;
		}
		
		return dataFilter.accept(data) && dataFilter.filter(data);
		
	}

	/**
	 * 将数据放入缓存
	 * @param data
	 * @return 
	 */
	private boolean cache(RawData data) {
		if (data == null){
			logger.warn("RawData is null!");
			return false;
		}
		
		Queue<RawData> queue = this.getQueue();
		if (queue == null){
			logger.warn("queue is null!");
			
			return false;
		}
		
		return queue.push(data);
	}

	/**
	 * 执行收集
	 * @return 
	 */
	public CollectionResultEnum doCollecte() {
		// create RawDataFactory
		RawDataFactory factory = getRawDataFactory();
		if (factory == null){
			logger.error("RawDataFactory不能为空!");
			return CollectionResultEnum.system_inner_error;
		}
		
		try {
			// build RawData
			RawData data = buildRawData(factory);
			// fiter rawdata
			if (filter(data) && cache(data)) {
				return CollectionResultEnum.success;
			} else {
				return CollectionResultEnum.paramter_error;
			}
		} catch (Exception e) {
			logger.error(e);
			return CollectionResultEnum.paramter_error;
		}
	}

	/**
	 * 返回RawData工厂类
	 * @return 
	 */
	protected abstract RawDataFactory getRawDataFactory();

	/**
	 * 返回过滤器
	 * @return 
	 */
	protected abstract DataFilter getDataFilter();

	/**
	 * 返回缓存队列
	 * @return 
	 */
	protected abstract Queue<RawData> getQueue();

}
