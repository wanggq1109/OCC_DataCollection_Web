package com.plocc.dc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.plocc.dc.client.CollectionResultEnum;
import com.plocc.dc.collecte.HttpDataCollection;

public class DataCollectionProxyServlet extends GenericServlet {

	private static final long serialVersionUID = -4961524756840217509L;
	private static final String INIT_PARAM_NAME = "targetBeanName";

	private String targetBeanName;

	// 核心处理类
	private HttpDataCollection dataCollection;

	@Override
	public void init() throws ServletException {
		super.init();
		this.targetBeanName = getInitParameter(INIT_PARAM_NAME);
		if (StringUtils.isEmpty(targetBeanName)) {
			this.targetBeanName = getServletName();
		}

		// 初始化被代理的bean
		setTargetBean();

	}

	private void setTargetBean() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.dataCollection = (HttpDataCollection) wac.getBean(targetBeanName);

	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();

		JSONObject jsons = new JSONObject();

		CollectionResultEnum result = dataCollection.collecte(req, resp);
		String code = result.getCode();
		String msg = result.getMsg();
		if (code.equals("1")) {
			jsons.put("code", "0");
			jsons.put("msg", msg);
		} else {

			jsons.put("code", "1");
			jsons.put("msg", msg);
		}

		out.print(jsons.toJSONString());
		out.flush();
		out.close();
	}

}
