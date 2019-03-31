package cn.mozistar.configurer;

import net.sf.json.JSONObject;

public class Sssss {

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("data", "1SJZeyP");
		System.out.println(json);
		String data = json.getString("data");
		System.out.println(data);
		if(json.containsKey("status")){
			System.out.println(1);
		}else{
			System.out.println(2);
		}

	}

}
