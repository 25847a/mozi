package com.fadl.health.service.impl;

import com.fadl.health.entity.Equipment;
import com.fadl.common.DataRow;
import com.fadl.health.dao.EquipmentMapper;
import com.fadl.health.service.EquipmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {

	
	@Autowired
	EquipmentMapper equipmentMapper;
	
	/**
	 * 获取设备在线离线数量饼状图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryEquipmentState() throws SQLException {
		return equipmentMapper.queryEquipmentState();
	}
	

}
