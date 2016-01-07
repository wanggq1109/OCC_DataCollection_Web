/**
 * 宝龙电商
 * org.powerlong.dc.servlet
 * HessianServlet.java
 * 
 * 2013-7-1-下午05:30:45
 *  2013宝龙公司-版权所有
 * 
 */
package com.plocc.dc.servlet;

import com.caucho.hessian.server.HessianServlet;
import com.plocc.dc.client.DataCollectionOpenAPI;
import com.plocc.framework.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;

/**
 * 
 * HessianServlet
 * 
 * bpLiang
 * bpLiang
 * 2013-7-1-下午05:30:45
 * 
 * @version 1.0.0
 * 
 */
public class HessianProxyServlet extends HessianServlet {

	private static final long serialVersionUID = -4961524756244217509L;
	private static final String INIT_PARAM_NAME = "targetBeanName";

	private String targetBeanName;

	// 核心处理类
	private DataCollectionOpenAPI dataCollection;

	@Override
	public void init() throws ServletException {
		super.init();
		this.targetBeanName = getInitParameter(INIT_PARAM_NAME);
		if (StringUtils.isEmpty(targetBeanName)) {
			this.targetBeanName = getServletName();
		}

		// 初始化被代理的bean
		setTargetBean();
		setService(dataCollection);
	}

	private void setTargetBean() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.dataCollection = (DataCollectionOpenAPI) wac.getBean(targetBeanName);

	}



}
