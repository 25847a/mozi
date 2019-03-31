package com.fadl.common;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


/**
 * 配置文件读取
 * 
 * @author 胡康康
 * 
 */
@Service("readProperties")
public class ReadProperties {
	private static Properties prop = new Properties();
	private static Properties propApplication = new Properties();
	public static Log log = LogFactory.getLog(ReadProperties.class);
	static {
		InputStream in = ReadProperties.class.getResourceAsStream("/config.properties");
					//in=ReadProperties.class.getResourceAsStream("/application.properties");
		try {
			prop.load(in);
			propApplication.load(in);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static String getValue(String key) {
		return prop.get(key).toString().trim();
	}

	public static int getIntValue(String key) {
		Object obj = prop.get(key);
		return Integer.valueOf(obj.toString().trim());
	}
	public static String getAppValue(String key) {
		return propApplication.get(key).toString().trim();
	}

	public static int getAppIntValue(String key) {
		Object obj = propApplication.get(key);
		return Integer.valueOf(obj.toString().trim());
	}
	
}
