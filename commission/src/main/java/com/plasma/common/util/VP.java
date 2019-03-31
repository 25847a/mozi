package com.plasma.common.util;

/**
 * 验证规则列表（规则、错误消息、错误码）
 * ValidatorPattern
 * @author wangjing
 */
public enum VP {
	// 验证表达式
	NUMBER("[1-9][0-9]+","必须是数字"),
	
	/**
	 * 必须是10位以内的整数
	 */
	NUMBER_MAX_LENGTH11("^\\d{1,11}?$", "必须是10位以内的整数", "100001"),
	NUMBER_MAX_LENGTH10("^\\d{1,10}?$", "必须是10位以内的整数", "100001"),
	/**
	 * 必须是0或1
	 */
	VALUE_0_OR_1("^0|1$", "必须是0或1", "100002"),
	/**
	 * 必须是中文或英文
	 */
	REALNAME("^[\\Α-\\￥]+|[A-Za-z]+$", "必须是中文或英文", "100003"),
	/**
	 * 用户名格式
	 */
	ACCOUNT("^[a-zA-Z][a-zA-Z0-9_]{4,15}$", "用户名不合法（字母开头，允许5-16个字，允许字母数字下划线）",
			"100004"),
	/**
	 * 必须是6~30位以内的整数
	 */
	NUMBER_MAX_LENGTH30("^\\d{6,30}?$", "必须是6~30位以内的整数", "100005"),
	/**
	 * 密码格式
	 */
	PASSWORD("^\\w{6,30}$", "密码必须是长度6~30，由字母、数字、下划线组成！", "100006"),
	/**
	 * 手机号码格式
	 */
	MOBILE("(^((13[0-9])|(14[0-9])|(18[0-9])|(15[0-3|5-9])|(147)|(17[0|6-8])|(145))[0-9]{8}$)", "请输入正确的手机号码", "100007"),
	/**
	 * 邮箱格式
	 */
	EMAIL(
			"^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$",
			"邮箱格式不正确", "100008"),

	/**
	 * 正常请求
	 */
	SUCCESS("", "000000"),

	/**
	 * 非法请求
	 */
	ERROR_REQ("非法请求", "000001"),

	/**
	 * 验证码已失效
	 */
	ERROR_VAL_CODE_LOSE("验证码已失效，请重新获取", "000002"),

	/**
	 * 无任何权限
	 */
	ERROR_NO_ROLE("无访问权限", "000003"),

	/**
	 * 未登录，请先登录！
	 */
	IS_NO_LOGIN("未登录，请先登录！", "000004"),

	/**
	 * 栏目名称已存在
	 */
	EXISTS_CATALOG("栏目名称已存在", "000005"),

	/**
	 * 登录异常，请联系管理员！
	 */
	ERROR_SYSTEM("登录异常，请联系管理员！", "000006"),

	/**
	 * 登录异常，请联系管理员！
	 */
	EXISTS_ROLE_NAME("角色名称已存在！", "000007"),

	/**
	 * 系统异常，请联系管理员！
	 */
	SYSTEM_ERROR("系统异常，请联系管理员！", "000008"),

	/**
	 * 登录账号已存在
	 */
	EXISTS_ACCOUNT("账号已存在", "000009"),

	/**
	 * 手机号已存在
	 */
	EXISTS_MOBILE("手机号已存在", "000010"),

	/**
	 * 邮箱号已存在
	 */
	EXISTS_EMAIL("邮箱号已存在", "000011"),

	/**
	 * 账号或密码错误
	 */
	ERROR_LOGIN_ACC_PWD("账号或密码错误", "000012"),

	/**
	 * 该用户已被禁用
	 */
	ERROR_LOGIN_DISABLE("该用户已被禁用", "000013"),

	/**
	 * 修改成功
	 */
	SUCCESS_UPDATE("修改成功", "000014"),

	/**
	 * 删除成功
	 */
	SUCCESS_DELETE("删除成功", "000015"),

	/**
	 * 添加成功
	 */
	SUCCESS_ADD("添加成功", "000016"),

	/**
	 * 验证码错误
	 */
	ERROR_VAL_CODE("验证码错误", "000017"),

	/**
	 * 还有个{0}子栏目未删除，删除失败！
	 */
	ERROR_CHILEN_COUNT("还有个{0}子栏目未删除，删除失败！", "000018"),

	/**
	 * 操作成功
	 */
	OPERATE_SUCCESS("操作成功！", "000019"),
	
	STRING_4_6("^\\w{4,6}?$", "验证码长度必须4至6位", "000020"),

	;
	private String pattern;
	private String message;
	private String code;

	VP(String message, String code) {
		this.message = message;
		this.code = code;
	}

	VP(String pattern, String message, String code) {
		this.pattern = pattern;
		this.message = message;
		this.code = code;
	}

	public String getPattern() {
		return pattern;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}

	public String getCode() {
		return code;
	}
}
