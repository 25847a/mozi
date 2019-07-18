package cn.mozistar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mozistar.mapper.PushMapper;
import cn.mozistar.pojo.Push;
import cn.mozistar.service.PushService;


@Transactional
@Service
public class PushServiceImpl implements PushService {

	@Autowired
	private PushMapper pushMapper;
	
	public int insertSelective(Push push){
		return pushMapper.insertSelective(push);
	}

	@Override
	public int updatePush(Push push) {
		return pushMapper.updatePush(push);
	}

	@Override
	public int deletePush(Push push) {
		return pushMapper.deletePush(push);
	}

	@Override
	public Push selectPushByAliasAndUserId(Push push) {
		return pushMapper.selectPushByAliasAndUserId(push);
	}

}
