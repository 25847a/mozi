package cn.mozistar.service;

import java.util.List;
import java.util.Map;

import cn.mozistar.pojo.Health;
import cn.mozistar.vo.Chart;

public interface HealthService {
	
	
	
	int insertSelective(Health health);
	
	Health getHealthByUserId(Integer userId);

	List<Chart> selecthealth(Map<String, Object> m);
	
	Health selecthealthMax(Map<java.lang.String, Object> map);

	Health selecthealthMin(Map<java.lang.String, Object> map);

	void sendJpush(Health health);
	
}
