package cn.mozistar.util;

import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


/**
 * 配置文件读取
 * 
 * @author 黄才健
 * 
 */
@Service
public class ReadProperties {
	private static Properties prop = new Properties();
	public static Log log = LogFactory.getLog(ReadProperties.class);
	static {
		try {
			InputStreamReader in = new InputStreamReader(ReadProperties.class.getResourceAsStream("/systemConfig.properties"),"UTF-8");
			prop.load(in);
			
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
	public static Float getFloatValue(String key){
		Object obj = prop.get(key);
		return Float.valueOf(obj.toString().trim());
	}
}
