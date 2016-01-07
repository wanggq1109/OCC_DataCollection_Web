/**
 * 宝龙电商
 * org.powerlong.dc.servlet
 * DataCollectionOpenAPI.java
 * 
 * 2013-6-27-下午03:19:39
 *  2013宝龙公司-版权所有
 * 
 */
package com.plocc.dc.collecte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plocc.dc.client.CollectionResultEnum;
import com.plocc.dc.client.DataCollectionOpenAPI;
import com.plocc.dc.filter.DataFilter;
import com.plocc.dc.queue.Queue;
import com.plocc.dc.rawdata.EchoRawDataFactory;
import com.plocc.dc.rawdata.RawData;
import com.plocc.dc.rawdata.RawDataFactory;

/**
 * 
 * DataCollectionOpenAPI
 * 
 * bpLiang bpLiang 2013-6-27-下午03:19:39
 * 
 * @version 1.0.0
 * 
 */
public class DataCollectionOpenAPIImpl implements DataCollectionOpenAPI {
	
	@Autowired
	@Qualifier("rawDataQueue")
	private Queue<RawData> queue;
	
	@Autowired
	private DataFilter dataFilter;


	public CollectionResultEnum collecte(final RawData rawData) {
		
		CollectionResultEnum cr = new DataCollectionTemplate() {

			@Override
			protected RawDataFactory getRawDataFactory() {
				return new EchoRawDataFactory(rawData);
			}

			@Override
			protected DataFilter getDataFilter() {
				return dataFilter;
			}

			@Override
			protected Queue<RawData> getQueue() {
				return queue;
			}

		}.doCollecte();
		return cr;
	}
}
