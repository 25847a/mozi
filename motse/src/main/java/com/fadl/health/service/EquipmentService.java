package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface EquipmentService extends IService<Equipment> {

	/**
	 * 获取设备在线离线数量饼状图
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentState(Map<String,Object> map)throws SQLException;
	/**
	 * 查询设备数据列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
     * 在线离线
     * @param equipment
     * @return
     */
	public DataRow updateBluetooth(Equipment equipment,DataRow messageMap)throws Exception;
	/**
     * 开始学习
     * @param equipment
     * @return
     */
	public DataRow startLearning(Equipment equipment,DataRow messageMap)throws Exception;
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
     */
	public DataRow queryEquipmentImeiInfo(Map<String,Object> map,DataRow messageMap)throws SQLException;
	/**
     * 录入设备到代理商名下
     * @param map
     * @return
     */
	public DataRow inuptEquipmentImeiInfo(Integer id,String[] imeis,DataRow messageMap)throws SQLException;
	
}
