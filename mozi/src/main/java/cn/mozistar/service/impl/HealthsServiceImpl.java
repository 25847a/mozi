package cn.mozistar.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mozistar.mapper.HealthsMapper;
import cn.mozistar.pojo.Healths;
import cn.mozistar.service.HealthsService;

@Transactional
@Service
public class HealthsServiceImpl implements HealthsService {

	@Autowired
	private HealthsMapper healthsMapper;

	public int insertSelective(Healths health) {
		return healthsMapper.insertSelective(health);
	}


}
