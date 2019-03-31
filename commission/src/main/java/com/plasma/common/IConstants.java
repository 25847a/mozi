package com.plasma.common;

/**
 * 常量
 * @author wangjing
 */
public class IConstants {
	//后台服务器地址
	public static final String USER_URL_START=ReadProperties.getValue("serverSite");
	
	public static final String SESSION_USER="user";
	/**
	 * 返回状态码
	 */
	public static final String APP_RESULT_ERRPR="error";
	/**
	 * 返回提示消息
	 */
	public static final String APP_RESULT_MSG="msg";
	/**
	 * 返回信息内容
	 */
	public static final String APP_RESULT_DATA="data";
	
	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR="系统错误,请尽快联系管理员";
	/**
	 * 非法请求
	 */
	public static final String ILLEGAL_REQUEST="非法请求";
	/**
	 * pass_key密码密钥
	 */
	public static final String PASS_KEY="pass_key";
	/**
	 * 验证码过期时间
	 */
	public static final int SESSION_SET_TIME=1;
	
	/**
	 * 返回值key
	 */
	public static final String RESULT_KEY="result";
	/**
	 * 业务逻辑成功
	 */
	public static final String RESULT_SUCCESS="操作成功";
	/**
	 * 业务逻辑失败
	 */
	public static final String RESULT_FAILURE = "操作失败";
	/**
	 * 接口调用失败
	 */
	public static final String RESULT_INTERFACE_CODE="-3";
	/**
	 * 操作失败
	 */
	public static final String RESULT_FAIL_CODE="1";
	
	/**
	 * 操作失败状态码
	 */
	public static final String RESULT_CODE_ERROR="0";
	/**
	 * 操作成功状态码
	 */
	public static final String RESULT_CODE_SUCCESS="-1";
	/**
	 * 登录失败
	 */
	public static final String LOGIN_ERROR="登录失败";
	
}