package cn.mozistar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.mozistar.mapper.UserCodeMapper;
import cn.mozistar.mapper.UserMapper;
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
