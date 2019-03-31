package cn.mozistar.util;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {
	private final static String CONTENT_TYPE_TEXT_JSON = "text/json";

	public  static String doPost(String url, String map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数

			StringEntity se = new StringEntity(map);
			// httpPost.setEntity(entity);
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			// ��
			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					//使用EntityUtils 对httpclient 返回的结果进行字符串的转换
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
