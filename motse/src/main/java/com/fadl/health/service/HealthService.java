package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Health;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 惊凡给的数据表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface HealthService extends IService<Health> {
	/**
	 * 获取养老院页面的数据
	 * @param messageMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public DataRow queryBeadhouseList(DataRow messageMap,Map<String,Object> map)throws Exception;
	/**
	 * 查询首页健康数据列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryHealthList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询首页健康数据列表总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryHealthListCount(Map<String,Object> map)throws SQLException;
	/**
	 * 查询重点关爱的使用者
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryHealthListLove(Map<String,Object> map)throws SQLException;
	/**
	 * 首页当天心率统计图
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryHeartrateCount()throws SQLException;
	/**
	 * 首页当天血压统计图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBloodCount()throws SQLException;
	/**
	 * 首页当天微循环统计图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryMicrocirculationCount()throws SQLException;
	/**
	 * 首页当天血氧统计图
	 * @return
	 * @throws SQLException
	 */
	public Object[] queryBloodoxygenCount()throws SQLException;
	/**
	 * 首页当天呼吸统计图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryRespirationrateCount()throws SQLException;
	/**
	 * 查询历史健康数据
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryHistoryList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
	 * 查询健康数据管理列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryHealthInfoList(Map<String,Object> map,DataRow messageMap)throws SQLException;
}
