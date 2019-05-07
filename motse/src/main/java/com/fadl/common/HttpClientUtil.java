package com.fadl.common;


import com.fadl.comfig.HttpClientConfig;
import com.fadl.health.entity.Equipment;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class HttpClientUtil {
	private static String charset = "utf-8";
	/**
	 * 注册
	 * 
	 * @param validate_number
	 *            验证码
	 * @param phone_num
	 *            手机号码
	 * @param password
	 *            要设置的密码
	 * @param channel_id
	 *            惊凡提供
	 * @param channel_secret
	 *            惊凡提供
	 * @return
	 */
	public static boolean registered(String phone_num, String validate_number, String password) {
		String url = "https://api.jingfantech.com/V1.02/register_step_2";
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("validate_number", validate_number);
		createMap.put("phone_num", phone_num);
		createMap.put("password", password);
		createMap.put("channel_id", IConstants.channel_id);
		createMap.put("channel_secret", IConstants.channel_secret);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientConfig.doPost(httpOrgCreateTest, json, charset,"text/json");
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		if (jsonObject.get("message").equals("Registered successfully.")
				|| jsonObject.get("message").equals("The user already exists and failed to register.")) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 在线离线
	 * @param id 设备ID
	 * @return
	 */
	public static DataRow connectBluetooth(Equipment equipment) {
		String url = ReadProperties.getValue("connectBluetooth");
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("id", equipment.getId());
		createMap.put("bluetoothName", equipment.getBluetoothName());
		createMap.put("bluetoothmac", equipment.getBluetoothmac());
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientConfig.doPost(httpOrgCreateTest, json, charset,"application/json");
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		DataRow row = new DataRow();
		row.put("message", jsonObject.get("message"));
		row.put("code", jsonObject.get("code"));
		return row;
	}
	/**
	 * 开始学习
	 * @param imei 设备imei
	 * @return
	 */
	public static DataRow srtificialLearning(String imei) {
		String url = ReadProperties.getValue("srtificialLearning");
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("imei", imei);
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientConfig.doPost(httpOrgCreateTest, json, charset,"application/json");
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		System.out.println(jsonObject);
		DataRow row = new DataRow();
		row.put("message", jsonObject.get("message"));
		row.put("code", jsonObject.get("code"));
		return row;
	}
	
	
	public static void main(String[] args) {
		String url = ReadProperties.getValue("srtificialLearning");
		String httpOrgCreateTest = url;
		JSONObject createMap = new JSONObject();
		createMap.put("imei", "862237045560906");
		Gson gson = new Gson();
		String json = gson.toJson(createMap);
		String httpOrgCreateTestRtn = HttpClientConfig.doPost(httpOrgCreateTest, json, charset,"application/json");
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		DataRow row = new DataRow();
		row.put("message", jsonObject.get("message"));
		row.put("code", jsonObject.get("code"));
	}
}
