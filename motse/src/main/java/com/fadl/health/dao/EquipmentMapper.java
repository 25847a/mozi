package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public DataRow queryEquipmentState(Map<String,Object> map)throws SQLException;
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
	public List<DataRow> queryEquipmentList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询代理商列表总数
	 * @return
	 * @throws SQLException
	 */
	public int queryEquipmentListCount(Map<String,Object> map)throws SQLException;
	/**
	 * 通过设备id查询使用者学习值
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentIdHealthdao(String imei)throws SQLException;
	/**
	 * 查询改该设备是否属于该供应商
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentAgent(Map<String,Object> map)throws SQLException;
	/**
	 * 代理商管理页面根据代理商ID查询设备信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryEquipmentImeiInfo(Map<String,Object> map)throws SQLException;
	/**
	 * 代理商管理页面根据代理商ID查询设备信息总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryEquipmentImeiInfoCount(Map<String,Object> map)throws SQLException;
	/**
	 * 遍历查询设备表存在的设备
	 * @param imeiList
	 * @return
	 * @throws SQLException
	 */
	public List<String> queryImeiList(Set<String> imeiList)throws SQLException;
	
}
