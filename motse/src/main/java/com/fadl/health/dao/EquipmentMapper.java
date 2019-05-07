package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {
	/**
	 * 获取设备在线离线数量饼状图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentState()throws SQLException;
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
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryEquipmentList()throws SQLException;
	/**
	 * 通过设备id查询使用者学习值
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentIdHealthdao(String imei)throws SQLException;
}
