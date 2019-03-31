package com.plasma.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;

/**
 * 参数验证类
 * @author wangjing
 *
 */
public class Validator {
	/**
	 * 需要验证的字段集合
	 */
	private List<DataRow> val = new ArrayList<DataRow>();
	/**
	 * 需要验证的数据
	 */
	private Map<String, Object> dr;

	private DataRow message;

	public Validator() {
	}

	public Validator(DataRow data) {
		init(data);
	}

	public Validator(Map<String, Object> data, DataRow messages) {
		this.message = messages;
		if(data!=null&&data.size()>0){
			init(data);
		}else{
			messages.put(IConstants.APP_RESULT_MSG, "非法请求");
		}
		
	}
	
	public void init(Map<String, Object> data){
		this.dr = data;
		if (dr == null || dr.size() == 0) {
			throw new NullPointerException();
		}
	}

	/**
	 * 不允许为空
	 * 
	 * @author wangjing
	 * @time 2016年8月4日 下午3:49:20
	 * @param field
	 * @param vp
	 */
	public void addParamCheck(String field) {
		this.addParamCheck(field, true, VP.ERROR_REQ);
	}

	/**
	 * 不允许为空
	 * 
	 * @author wangjing
	 * @time 2016年8月4日 下午3:49:20
	 * @param field
	 * @param vp
	 */
	public void addParamCheck(String field, VP vp) {
		this.addParamCheck(field, true, vp);
	}

	/**
	 * @author wangjing
	 * @time 2016年8月4日 下午3:49:20
	 * @param field
	 *            需要验证的字段
	 * @param notNull
	 *            是否非空
	 * @param vp
	 *            枚举：包含信息验证规则，错误提示，错误编号
	 */
	public void addParamCheck(String field, boolean notNull, VP vp) {
		if (notNull) {// 该字段不能为空
			add(field, vp);
		} else {
			if (!StringHelper.isEmpty(dr.get(field).toString())) {
				add(field, vp);
			}
		}
	}

	/**
	 * 添加验证
	 * 
	 * @author wangjing
	 * @time 2016年8月4日 下午3:49:20
	 * @param field
	 * @param vp
	 */
	protected void add(String field, VP vp) {
		DataRow row = new DataRow();
		row.put("key", field);
		row.put("value", dr==null?"":dr.get(field));
		if (vp != null) {
			row.put("pattern", vp.getPattern());
			row.put(IConstants.APP_RESULT_MSG, vp.getMessage());
		}
		val.add(row);
	}

	/**
	 * 验证表单是否通过！ false:不通过 true:通过
	 * 
	 * @author wangjing
	 * @time 2016年8月4日 下午3:49:20
	 * @return
	 */
	public boolean validate() {
		for (DataRow d : val) {
			String value = d.getString("value");
			if (StringHelper.isEmpty(value)) {
				//this.message.put(IConstants.RESULT_KEY, d.getString("key")+ "不能为空！");
				this.message.put(IConstants.APP_RESULT_MSG, "非法请求");
				return false;
			}
			if (!StringHelper.isEmpty(d.getString("pattern"))&& !Pattern.compile(d.getString("pattern")).matcher(value).matches()) {
				this.message.put(IConstants.APP_RESULT_MSG,"非法请求");
				return false;
			}
		}
		return true;
	}
}
