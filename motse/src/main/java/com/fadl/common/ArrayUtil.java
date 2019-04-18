package com.fadl.common;

import java.util.Map.Entry;

public class ArrayUtil {

	
	public static Object[] listArray(DataRow data,String name){
		Object[] result = null;
				data.remove(name);
				 int count = 0;
				result = new Object[data.size()];
				for(Entry<String, Object> entry:data.entrySet()){
					result[count]=entry.getValue();
					count++;
				}
		return result;
	}
	
}
