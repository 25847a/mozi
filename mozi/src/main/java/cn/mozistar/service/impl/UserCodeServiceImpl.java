package cn.mozistar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mozistar.mapper.UserCodeMapper;
import cn.mozistar.pojo.UserCode;
import cn.mozistar.service.UserCodeService;

@Transactional
@Service
public class UserCodeServiceImpl implements UserCodeService{
	
	@Autowired 
	private UserCodeMapper userCodeMapper;

	public boolean checkCode(UserCode userCode) {
		UserCode usercode = userCodeMapper.selectUserCode(userCode.getPhone());
		boolean b = false;
		if(usercode!=null&&usercode.getCode().equals(userCode.getCode())){
			b = true;
		}
		return b;
	}

	public void addUsercode(UserCode c) {
		userCodeMapper.insert(c);
	}

	@Override
	public UserCode selectUserCode(String phone) {
		return userCodeMapper.selectUserCode(phone);
	}
	
	public int update(UserCode c){
		return userCodeMapper.update(c);
	}
}
