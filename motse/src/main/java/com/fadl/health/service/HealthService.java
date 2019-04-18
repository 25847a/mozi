package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Health;

import java.sql.SQLException;
import java.util.List;

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
	 * 查询首页健康数据列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryHealthList(Integer pageNum,Integer pageSize)throws SQLException;
	/**
	 * 查询首页健康数据列表总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryHealthListCount()throws SQLException;
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
	public List<DataRow> queryBloodoxygenCount()throws SQLException;
	/**
	 * 首页当天呼吸统计图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryRespirationrateCount()throws SQLException;
	
	public DataRow queryBeadhouseList(DataRow messageMap,String page)throws Exception;
}