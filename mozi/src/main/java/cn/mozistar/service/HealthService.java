package cn.mozistar.service;

import java.util.List;
import java.util.Map;

import cn.mozistar.pojo.Health;
import cn.mozistar.vo.Chart;

public interface HealthService {
	
	
	
	int insertSelective(Health health);
	
    Health getHealthByUserId(Integer userId);

	List<Chart> selecthealth(Map<String, Object> m);
	/**
	 * 查询心率的各项数值
	 * @param m
	 * @return
	 */
	Map<String,String> selectHeartRateInfo(Map<String, Object> m);
	/**
	 * 查询步数MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	Map<String,String> selectStepWhenInfo(Map<String, Object> m);
	/**
	 * 查询血压MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	Map<String,String> selectBloodpressureInfo(Map<String, Object> m);
	/**
	 * 查询HRV MAX,MIN,AVG,COUNT
	 * @param m
	 * @return
	 */
	Map<String,String> selectHrvInfo(Map<String, Object> m);

	void sendJpush(Health health);
	
}
