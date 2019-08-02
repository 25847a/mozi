package cn.mozistar.service;

import java.util.List;
import java.util.Map;

import cn.mozistar.pojo.Health;
import cn.mozistar.util.DataRow;
import cn.mozistar.vo.Chart;

public interface HealthService {
	
	
	
	int insertSelective(Health health);
	/**
	 * 查询最新的一条数据
	 * @param userId
	 * @return
	 */
    Health getHealthByUserId(Integer userId);
    /**
     * 除了步数卡路里的健康数据
     * @param m
     * @return
     */
	List<Chart> selecthealth(Map<String, Object> m);
	 /**
     * 步数卡路里的健康数据
     * @param m
     * @return
     */
	List<DataRow> queryHealthstep(Map<String, Object> m);
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
