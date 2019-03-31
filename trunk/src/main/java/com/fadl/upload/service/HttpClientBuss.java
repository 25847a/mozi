package com.fadl.upload.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientBuss {
	
	/**
	 * post提交
	 * 
	 * @param 请求前缀
	 * @param 请求Controller
	 * @param 请求参数
	 * @return
	 */
	public String doPost(String url,Map<String,String> map){  
      //  HttpClient httpClient = HttpClientBuilder.create().build();  
		//HttpClient httpClient = null;
		HttpPost httpPost = null;  
        String result = null;  
        try{  
           // httpClient = new SSLClient();  
        	HttpClient httpClient=new DefaultHttpClient();

            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                @SuppressWarnings("unchecked")
				Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");  
                httpPost.setEntity(entity);  
            }  
            System.out.println(httpPost+"=======================");
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
}
