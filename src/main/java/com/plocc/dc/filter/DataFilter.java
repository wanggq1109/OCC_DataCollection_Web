package com.plocc.dc.filter;

import com.plocc.dc.rawdata.RawData;

/**
 * 
 * DataFilter
 * 
 * bpLiang bpLiang 2013-6-28-下午03:25:02
 * 
 * @version 1.0.0
 * 
 */
public interface DataFilter {
	/**
	 * accept(是否系统可以接受的rawdata)
	 * 
	 * @param data
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public boolean accept(RawData data);

	/**
	 * filter(过滤不符合调价你的rawdata)
	 * 
	 * @param data
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public boolean filter(RawData data);
	
	/**
	 * 下一个filter
	 * @return DataFilter
	 */
	public DataFilter getNext();
}
