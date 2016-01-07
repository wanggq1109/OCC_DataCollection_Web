package com.plocc.dc.filter;

/**
 * 数据过滤基类
 * AbstractDataFilter
 * 2013-7-2-上午11:34:54
 * @author wuxin
 * @version 1.0.0
 * 
 */
public abstract class AbstractDataFilter implements DataFilter {
	protected DataFilter next;
	@Override
	public DataFilter getNext() {
		return next;
	}
	
	public void setNext(DataFilter next) {
		this.next = next;
	}
}
