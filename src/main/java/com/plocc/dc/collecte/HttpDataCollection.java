package com.plocc.dc.collecte;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plocc.dc.client.CollectionResultEnum;
import com.plocc.dc.filter.DataFilter;
import com.plocc.dc.queue.Queue;
import com.plocc.dc.rawdata.HttpRowDataFactory;
import com.plocc.dc.rawdata.RawData;
import com.plocc.dc.rawdata.RawDataFactory;

public class HttpDataCollection {
	
	@Autowired
	@Qualifier("rawDataQueue")
	private Queue<RawData> queue ;
	
	@Autowired
	private DataFilter dataFilter;
	

	/**
	 * 收集HTTP数据
	 * @param req
	 * @param resp
	 * @return 
	 */
	public CollectionResultEnum collecte(final ServletRequest req,
			ServletResponse resp) {
		
		DataCollectionTemplate template = new HttpDataCollectionTemplate((HttpServletRequest)req);
		CollectionResultEnum cr = template.doCollecte();
		return cr;
	}
	
	
	private class HttpDataCollectionTemplate extends DataCollectionTemplate{
		private final HttpServletRequest req;
		private HttpDataCollectionTemplate(HttpServletRequest req){
			this.req = req;
		}
		@Override
		protected RawDataFactory getRawDataFactory() {
			return new HttpRowDataFactory(req);
		}
		@Override
		protected DataFilter getDataFilter() {
			return dataFilter;
		}
		@Override
		protected Queue<RawData> getQueue() {
			return queue;
		}
	}

}
