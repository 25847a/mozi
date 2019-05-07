package com.fadl.common;

import java.util.LinkedHashMap;

import com.baomidou.mybatisplus.plugins.Page;
/**
 * 通用bean 接收参数进行jdbc操作
 * 
 * @author jian
 * @time 2019年03月27日
 */
public class DataRow  extends LinkedHashMap<String, Object>{
	private static final long serialVersionUID = -5997498541153398009L;

	public DataRow() {

	}
	public void initError(){
		put(IConstants.RESULT_DATA,null);
		put(IConstants.RESULT_MESSAGE,IConstants.ERROR);
		put(IConstants.RESULT_CODE,IConstants.RESULT_ERROR_CODE);
	}
	/**
	 * 失败
	 */
	public  void initFial(String message){
		put(IConstants.RESULT_DATA,null);
		put(IConstants.RESULT_MESSAGE,message);
		put(IConstants.RESULT_CODE,IConstants.RESULT_FAIL_CODE);
	}
	/**
	 * 失败
	 */
	public  void initFial(){
		 initFial(IConstants.Fail);
	}
	/**
	 * 成功 带对象和提示信息
	 */
	public  void initSuccessObject(String message,Object obj){
		put(IConstants.RESULT_DATA,obj);
		put(IConstants.RESULT_MESSAGE,message);
		put(IConstants.RESULT_CODE,IConstants.RESULT_SUCCESS_CODE);
	}
	/**
	 * 成功 带提示
	 */
	public  void initSuccess(String message){
		 initSuccessObject(message,null);
	}
	/**
	 * 成功 带对象
	 */
	public  void initSuccess(Object obj){
		 initSuccessObject(IConstants.SUCCESS,obj);
	}
	/**
	 * 成功
	 */
	public  void initSuccess(){
		 initSuccess(IConstants.SUCCESS);
	}
	/**
	 * 成功分页
	 */
	public  void initPage(String message, Page page){
		put("count",page.getTotal());
		put(IConstants.RESULT_DATA,page.getRecords());
		put(IConstants.RESULT_MESSAGE,message);
		put(IConstants.RESULT_CODE,IConstants.RESULT_SUCCESS_CODE);
	}

	/**
	 * 自定义返回
	 * @param obj 对象
	 * @param message 提示信息
	 * @param code 返回状态
	 */
	public  void customValue(Object obj,String message,String code){
		put(IConstants.RESULT_DATA,obj);
		put(IConstants.RESULT_MESSAGE,message);
		put(IConstants.RESULT_CODE,code);
	}
	/**
	 * 成功分页
	 */
	public  void initPage(Page page){
		 initPage(IConstants.SUCCESS,page);
	}

	/**
	 * 无权限
	 */
	public void initNoPermission(){
		put(IConstants.RESULT_DATA,null);
		put(IConstants.RESULT_MESSAGE,IConstants.NO_PERMISSION_MESSAGE);
		put(IConstants.RESULT_CODE,IConstants.NO_PERMISSION_CODE);
	}
	/**
	 * 无页面
	 */
	public void initNoPage(){
		put(IConstants.RESULT_DATA,null);
		put(IConstants.RESULT_MESSAGE,IConstants.NO_PAGE_MESSAGE);
		put(IConstants.RESULT_CODE,IConstants.NO_PAGE_CODE);
	}

	/**
	 * 页面提交上来的dataRow对象,如果接受数据的是一个Map对象， 则struts总是会把参数值设为字串数组，所以在此需要把只有一个
	 * 数值的对象转换为非数组形式。
	 */
	public Object put(String key, Object value) {
		if (this.size() >= 50)
			return null;// 如果MAP中的值超过30禁止在增加
		if (value instanceof String[]) {
			String[] arr = (String[]) value;
			if (arr.length == 1) {
				return super.put(key, cleanXss(arr[0]));
			}else{
				for (int i = 0; i < arr.length; i++) {
					String val = cleanXss(arr[i]);
					arr[i]=val;
				}
			}
		}
		return super.put(key, value);
	}

	/**
	 * 防止 xss 攻击,sql注入
	 * @param val
	 * @return
	 */
	public String cleanXss(String val){
		val = val.replaceAll("<iframe[^>]*?>.*?</iframe>", "");
		val = val.replaceAll("<frame[^>]*?>.*?</frame>", "");
		val = val.replaceAll("<script[^>]*?>.*?</script>", "");
		val = val.replaceAll("<head[^>]*?>.*?</head>", "");
		val = val.replaceAll("<title[^>]*?>.*?</title>", "");
		val = val.replaceAll("<meta[^>]*?>.*?</meta>", "");
		val = val.replaceAll("<link[^>]*?>.*?</link>", "");
		val = val.replaceAll("alert(.*)", "");
		val = val.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		val = val.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		val = val.replaceAll("'", "&#39;");
		val = val.replaceAll("eval\\((.*)\\)", "");
		val = val.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		return val;
	}
	
	/**
	 * put String
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:42:35
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		if (StringHelper.isEmpty(key))
			return;
		if (value == null)
			value = "";
		super.put(key, value);
	}

	/**
	 * put int
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:42:47
	 * @param key
	 * @param value
	 */
	public void set(String key, int value) {
		super.put(key, new Integer(value));
	}

	/**
	 * put boolean
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:43:02
	 * @param key
	 * @param value
	 */
	public void set(String key, boolean value) {
		super.put(key, new Boolean(value));
	}

	/**
	 * put long
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:43:10
	 * @param key
	 * @param value
	 */
	public void set(String key, long value) {
		super.put(key, new Long(value));
	}

	/**
	 * put float
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:45:56
	 * @param key
	 * @param value
	 */
	public void set(String key, float value) {
		super.put(key, new Float(value));
	}

	/**
	 * put double
	 * 
	 * @author wangjing
	 * @time 2014年12月29日 下午9:46:04
	 * @param key
	 * @param value
	 */
	public void set(String key, double value) {
		super.put(key, new Double(value));
	}

	public String getString(String key) {
		if (StringHelper.isEmpty(key))
			return "";
		Object value = get(key);
		return value == null ? "" : value.toString();
	}

	public int getInt(String key) {
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = get(key);
				if (obj != null) {
					int value = 0;
					if (obj instanceof Integer) {
						value = ((Integer) obj).intValue();
					} else {
						try {
							value = Integer.parseInt(obj.toString());
						} catch (Exception e) {
							value = 0;
						}
					}
					obj = null;
					return value;
				}
			}
		}
		return 0;
	}

	public Long getLong(String key) {
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = get(key);
				if (obj != null) {
					long value = 0;
					if (obj instanceof Long) {
						value = ((Long) obj).longValue();
					} else {
						try {
							value = Long.parseLong(obj.toString());
						} catch (Exception e) {
							value = 0;
						}
					}
					obj = null;
					return value;
				}
			}
		}
		return null;
	}

	public float getFloat(String key) {
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = get(key);
				if (obj != null) {
					float value = 0;
					if (obj instanceof Float) {
						value = ((Float) obj).floatValue();
					} else {
						try {
							value = Float.parseFloat(obj.toString());
						} catch (Exception e) {
							value = 0;
						}
					}
					obj = null;
					return value;
				}
			}
		}
		return 0;
	}

	public double getDouble(String key) {
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = get(key);
				if (obj != null) {
					double value = 0;
					if (obj instanceof Double) {
						value = ((Double) obj).doubleValue();
					} else {
						try {
							value = Double.parseDouble(obj.toString());
						} catch (Exception e) {
							value = 0;
						}
					}
					obj = null;
					return value;
				}
			}
		}
		return 0;
	}

	public boolean getBoolean(String key) {
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = this.get(key);
				if (obj != null) {
					boolean value = false;
					if (obj instanceof Boolean) {
						return ((Boolean) obj).booleanValue();
					}
					value = Boolean.valueOf(obj.toString()).booleanValue();
					obj = null;
					return value;
				}
			}
		}
		return false;
	}

	/**
	 * 获得字符串数组
	 * 
	 * @author wangjing
	 * @time 2015年2月6日 下午5:36:17
	 * @param key
	 * @return
	 */
	public String[] getArrayString(String key) {
		String[] temp = {};
		if (!StringHelper.isEmpty(key)) {
			if (containsKey(key)) {
				Object obj = get(key);
				if (obj != null) {
					if (obj instanceof String[]) {
						return (String[]) obj;
					} else if (obj instanceof String) {
						try {
							return new String[] { obj.toString() };
						} catch (Exception e) {
						}
					}
					obj = null;
				}
			}
		}
		return temp;
	}
}
