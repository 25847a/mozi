package cn.mozistar.service;

import cn.mozistar.pojo.Push;

public interface PushService {
	
	public int updatePush(Push push);
	public int insertSelective(Push push);
	public int deletePush(Push push);
	public Push selectPushByAliasAndUserId(Push push);
}
