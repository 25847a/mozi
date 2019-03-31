package cn.mozistar.service;

import cn.mozistar.pojo.Healthdao;

public interface HealthdaoService {

	
	public int updateByPrimaryKeySelective(Healthdao healthdao);
	
	int insertSelective(Healthdao healthdao);

	public Healthdao getHealthdaoByUserId(Integer userId);

	public int updateHealthdaoByUserId(Healthdao healthdao);


	
}
