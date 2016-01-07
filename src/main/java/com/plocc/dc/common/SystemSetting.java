package com.plocc.dc.common;

import java.io.InputStream;
import java.util.Properties;

public class SystemSetting {
	
	private static SystemSetting setting = null;

	public SystemSetting() {

		Properties props = new Properties();
		InputStream configStream = null;
		try {
			configStream = this.getClass().getClassLoader()
					.getResourceAsStream("config.properties");
			if (configStream == null) {
				System.out.println("找不到配置文件:" + "config.properties");
			}
			props.load(configStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (configStream != null) {
				try {
					configStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		this.key = props.getProperty("key");
		this.imageUrl = props.getProperty("imageUrl");
		this.secretKey = props.getProperty("secretKey");

	}

	public static synchronized SystemSetting getInstance() {
		if (setting == null) {
			setting = new SystemSetting();
		}
		return setting;

	}

	
	//签名密钥
	private String key;

	//生成图片url
	private String imageUrl;
	
	//qrcode密钥
	private String secretKey;


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
