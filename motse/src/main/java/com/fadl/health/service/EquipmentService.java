package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.Equipment;

import java.sql.SQLException;
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
	public DataRow queryEquipmentState()throws SQLException;
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryEquipmentList(DataRow messageMap)throws SQLException;
	
}
