package com.plasma.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SocketUtil {
	private static Logger logger = LoggerFactory.getLogger(SocketUtil.class);
	/**
	 * 硬件发送请求并获取返回数据 (图片需要加密)
	 * @param host 请求 ip
	 * @param port 请求端口
	 * @param type 请求类型 (idCard：身份证,idCardm 身份证识别仪 )
	 * @param map 请求参数 {"type":"idCard",data:参数 json 对象}
	 * @return {"recvCode":"0","recvMsg":"读身份证信息失败。","type":"idCard","data":{}}
	 * recvCode:成功1 失败0 ，recvMsg 失败信息，data 返回的数据，type 请求的类型
	 * @throws Exception
	 */
	public void readInfo(String host,int port,String type,HashMap<String, String> map,DataRow messageMap) {
		// 1、创建客户端Socket，指定服务器地址和端口
		//Socket socket = new Socket("192.168.1.116", 51003);
		OutputStream out = null;
		BufferedReader in = null;
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			//socket.setSoTimeout(5000);//5s 超时时间
			// 2、获取输出流，向服务器端发送信息
/*************************** 传输数据开始 **************************************************************/
			out = socket.getOutputStream();
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("type", type);
			hashMap.put("data", map);

			ObjectMapper mapper = new ObjectMapper();
			out.write(mapper.writeValueAsBytes(hashMap));
/*************************** 传输数据结束 **************************************************************/
			// 由Socket对象得到输出流，并构造PrintWriter对象
			// 3、获取输入流，并读取服务器端的响应信息
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
//			System.out.println(in.readLine());


			JSONObject json = JSONObject.fromObject(in.readLine());
			logger.info(json.toString() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<");
			messageMap.put("data", json.get("data"));
			messageMap.put("msg", json.getString("recvMsg"));
			messageMap.put("error", json.getString("recvCode").equals("1") ? "-1" : IConstants.RESULT_INTERFACE_CODE);
		} catch (Exception e) {
			logger.error("SocketUtil>>>>>>>>>>>>>>>>readInfo>>>>>>>>>>>>>>", e);
			messageMap.put("data", null);
			messageMap.put("msg", "建立驱动连接失败,请确定驱动是否启动");
			messageMap.put("error", IConstants.RESULT_FAIL_CODE);
		} finally {
			// 4、关闭资源
			try {
				if (out != null) {
					out.close(); // 关闭Socket输出流
				}
				if (in != null) {
					in.close(); // 关闭Socket输入流
				}
				if (socket != null) {
					socket.close(); // 关闭Socket
				}
			} catch (Exception e) {
				messageMap.put("data", null);
				messageMap.put("msg", "关闭连接异常");
				messageMap.put("error", IConstants.RESULT_FAIL_CODE);
			}
		}
	}
}
