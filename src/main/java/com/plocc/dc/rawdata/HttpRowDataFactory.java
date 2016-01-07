package com.plocc.dc.rawdata;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.plocc.dc.common.CommonUtils;

public class HttpRowDataFactory implements RawDataFactory {
	private ServletRequest req;

	public HttpRowDataFactory() {
	}

	public HttpRowDataFactory(ServletRequest req) {
		this.req = req;
	}

	public RawData create() {
		RawData rawData = null;
		
		HttpServletRequest request = (HttpServletRequest) req;
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && !map.isEmpty()) {
			rawData = new RawData();
			Map<String, String[]> paramterMap = new HashMap<String, String[]>();
			rawData.setRecordData(paramterMap);
			
			// 请求参数
			paramterMap.putAll(map);
			
			// 暂存将HTTP头信息
			fillExtendMap(rawData, request);
			
			// 设置客户端IP
			rawData.setClientIP(CommonUtils.getIpAddr(request));

			// 设置业务正则信息
			fillBussinessPattern(rawData, request);
		}
		return rawData;
	}

	/**
	 * 暂存将HTTP头信息
	 * @param rawData
	 * @param request 
	 */
	private void fillExtendMap(RawData rawData, HttpServletRequest request) {
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			rawData.getExtendData().put(headerName, request.getHeader(headerName));
		}
	}

	/**
	 * 
	 * @param rawData
	 * @param request 
	 */
	private void fillBussinessPattern(RawData rawData,
			HttpServletRequest request) {
		String pattern = request.getRequestURI();
		if (StringUtils.isNotBlank(pattern) ){
			if (pattern.contains("/")){
				rawData.setBusinessPattern(pattern.substring(pattern.lastIndexOf("/")  + 1));
			}
		}
	}
}
