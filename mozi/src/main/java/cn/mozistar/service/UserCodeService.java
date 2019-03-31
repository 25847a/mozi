package cn.mozistar.service;

import cn.mozistar.pojo.UserCode;

public interface UserCodeService {

	boolean checkCode(UserCode userCode);

	void addUsercode(UserCode c);
	
	UserCode selectUserCode(String phone);

	int update(UserCode c);

}
