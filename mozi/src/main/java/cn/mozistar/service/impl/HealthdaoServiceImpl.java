package cn.mozistar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.HealthdaoMapper;
import cn.mozistar.pojo.Healthdao;
import cn.mozistar.pojo.Push;
import cn.mozistar.service.HealthdaoService;


@Transactional
@Service
public class HealthdaoServiceImpl implements HealthdaoService {

	@Autowired
	private HealthdaoMapper healthdaoMapper;
	
	
	public int updateByPrimaryKeySelective(Healthdao healthdao){
		return healthdaoMapper.updateByPrimaryKeySelective(healthdao);
	}
	
	public int insertSelective(Healthdao healthdao) {
		return healthdaoMapper.insertSelective(healthdao);
	}

	public Healthdao getHealthdaoByUserId(Integer userId) {
		return healthdaoMapper.getHealthdaoByUserId(userId);
	}

	@Override
	public int updateHealthdaoByUserId(Healthdao healthdao) {
		return healthdaoMapper.updateHealthdaoByUserId(healthdao);
	}


}
