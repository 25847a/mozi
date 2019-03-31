package com.plasma.common;


/**
 * String操作
 * @author wangjing
 * @time 2014年12月20日 下午12:34:29
 */
public class StringHelper {
	/**
	 * 判断字符串是否为空或者字符串长度==0
	 * 如果返回true为空
	 * @author wangjing
	 * @time 2014年12月20日 下午12:42:33 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||str.trim().length()==0)return true;
		return false;
	}
	
	/**
	 * 将参数转换成数据库操作 ike '%value%'
	 * @author wangjing
	 * @time 2015年2月6日 下午4:39:16 
	 * @param value
	 * @return
	 */
	public static String like(String value){
		return "%"+value+"%";
	}
	
	/**
	 * 将字符串数组，根据指定格式拼接
	 * 如：
	 * format=","
	 * arr=["1","2","3","4"]
	 * 结果：
	 * 1,2,3,4
	 * @author wangjing
	 * @time 2015年2月6日 下午5:41:55 
	 * @param arr
	 * @param format
	 * @return
	 */
	public static String join(String[] arr,String format){
		if(arr==null||arr.length==0)return"";
		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			ids.append(arr[i]).append(format);
		}
		ids.deleteCharAt(ids.length() - 1);
		return ids.toString();
	}

	/**
	 * 将字符串数组，根据指定格式拼接
	 * 如：
	 * 默认：","
	 * arr=["1","2","3","4"]
	 * 结果：
	 * 1,2,3,4
	 * @author wangjing
	 * @time 2015年2月6日 下午5:41:55 
	 * @param arr
	 * @return
	 */
	public static String join(String[] arr){
		return join(arr,",");
	}
	
	/**
	 * 把 object 对象转换成字符串
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj){
		if(null == obj){
			return "";
		}else{
			return obj.toString();
		} 
	}
	
	/**
	 * 字符串转数字
	 * @param obj
	 * @return
	 */
	public static int strToInt(String obj){
		if (StringHelper.isEmpty(obj)) {
			return 0;
		}
		return Integer.valueOf(obj);
	}
}
