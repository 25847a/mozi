package com.fadl.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用ConcurrentHashMap作为项目的缓存
 * @author Administrator
 *
 */
public class CacheMap {

	public static Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
	
	public static void addMap(String key,Integer value){
		map.put(key,value);
	}
	public static Integer getMap(String key){
		return map.get(key);
	}
	public  static void deletesb(String key){
		map.remove(key);
	}
	
}
