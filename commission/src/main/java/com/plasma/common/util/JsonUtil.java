package com.plasma.common.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * josn 工具类，直接获取 objectMapper 对象，转换成 json
 * @author hu
 *
 */
public class JsonUtil {

	public static ObjectMapper objectMapper=null;
	
	public static ObjectMapper getMapper(){
		if (null==objectMapper) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		//getMapper().readValue() 转对象
		System.out.println(getMapper().writeValueAsString(map));
	}
}
