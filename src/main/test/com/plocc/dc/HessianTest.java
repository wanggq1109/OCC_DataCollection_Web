package com.plocc.dc;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.plocc.dc.client.DataCollectionOpenAPI;
import com.plocc.dc.rawdata.FormatEnum;
import com.plocc.dc.rawdata.RawData;

public class HessianTest {
	 public static String url = "http://localhost:8080/openapi";
	    public static void  main(String[] args){
	        HessianProxyFactory factory = new HessianProxyFactory();
	        try {

				DataCollectionOpenAPI iHello = (DataCollectionOpenAPI) factory.create(DataCollectionOpenAPI.class, url);
	            RawData rd = new RawData();
	            rd.setBusinessPattern("test.wx");
	            rd.setFormat(FormatEnum.JSON);
	            Map map = new HashMap();
	            map.put("json", "{'a':9,'b':10}");
	            rd.setRecordData(map );
	        	System.out.println(iHello.collecte(rd).getCode());
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}
