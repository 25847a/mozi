package cn.mozistar.util;

public class ResultBase {
	//private Boolean success; // 成功与否
	private String message; // 消息
	private Integer  code;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

	
}
