package com.fadl.upload.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpUploadImg {

	public static void main(String[] args) {
		CloseableHttpResponse response=null;
		try{  
		// 1. 创建上传需要的元素类型  
        // 1.1 装载本地上传图片的文件  
        File imageFile = new File("D:/image/upload/idCard/sh.jpg");  
        FileBody imageFileBody = new FileBody(imageFile);  
        // 1.2 装载经过base64编码的图片的数据  
        // 1.3 装载上传字符串的对象  
        StringBody name = new StringBody("18888888888",org.apache.http.entity.ContentType.TEXT_PLAIN);
        System.out.println("装载数据完成");  
        // 2. 将所有需要上传元素打包成HttpEntity对象  
        HttpEntity reqEntity = MultipartEntityBuilder.create()  
                .addPart("phone", name)  
                .addPart("image",imageFileBody).build();  
        System.out.println("打包数据完成");  
        // 3. 创建HttpPost对象，用于包含信息发送post消息  
        HttpPost httpPost = new HttpPost("http://192.168.1.114:8080/commission/client/queryaaa");  
        httpPost.setEntity(reqEntity);  
        System.out.println("创建post请求并装载好打包数据");  
        // 4. 创建HttpClient对象，传入httpPost执行发送网络请求的动作  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        response = httpClient.execute(httpPost);  
        System.out.println("发送post请求并获取结果");  
        // 5. 获取返回的实体内容对象并解析内容  
        HttpEntity resultEntity = response.getEntity();
        String responseMessage = "";  
            System.out.println("开始解析结果");  
            if(resultEntity!=null){  
                InputStream is = resultEntity.getContent();  
                BufferedReader br = new BufferedReader(new InputStreamReader(is));  
                StringBuffer sb = new StringBuffer();  
                String line = "";  
                while((line = br.readLine()) != null){  
                    sb.append(line);  
                }  
                responseMessage = sb.toString();  
                System.out.println("解析完成，解析内容为"+ responseMessage);  
            }  
            EntityUtils.consume(resultEntity);  
        }catch(Exception e){
        	e.printStackTrace();
        }finally{  
            if (null != response){  
                try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }  
	}
}
