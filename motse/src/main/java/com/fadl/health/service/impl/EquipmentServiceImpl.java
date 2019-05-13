package com.fadl.health.service.impl;

import com.fadl.health.entity.Equipment;
import com.fadl.common.DataRow;
import com.fadl.common.HttpClientUtil;
import com.fadl.health.dao.EquipmentMapper;
import com.fadl.health.service.EquipmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	public DataRow queryEquipmentState(Map<String,Object> map) throws SQLException {
		return equipmentMapper.queryEquipmentState(map);
	}
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryEquipmentList(DataRow messageMap) throws SQLException {
		List<DataRow> list = equipmentMapper.queryEquipmentList();
		messageMap.initSuccess(list);
		return messageMap;
	}
	/**
     * 在线离线
     * @param equipment
     * @return
     */
	@Override
	public DataRow updateBluetooth(Equipment equipment, DataRow messageMap) throws Exception {
		messageMap=HttpClientUtil.connectBluetooth(equipment);
		return messageMap;
	}
	/**
     * 开始学习
     * @param equipment
     * @return
     */
	@Override
	public DataRow startLearning(Equipment equipment, DataRow messageMap) throws Exception {
			DataRow jsonObject=HttpClientUtil.srtificialLearning(equipment.getImei());
			if(jsonObject.getInt("code")==200){
				DataRow row =equipmentMapper.queryEquipmentIdHealthdao(equipment.getImei());
				if(row!=null){
					messageMap.initSuccess(row);
				}else{
					messageMap.initFial("该使用者查询不到学习值!!");
				}
			}else{
				messageMap.initFial(jsonObject.getString("message"));
			}
		return messageMap;
	}
	/**
	 * 通过设备id查询使用者学习值
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryEquipmentIdHealthdao(String imei) throws SQLException {
		return equipmentMapper.queryEquipmentIdHealthdao(imei);
	}
	/**
	 * 查询改该设备是否属于该供应商
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryEquipmentAgent(Map<String, Object> map) throws SQLException {
		return equipmentMapper.queryEquipmentAgent(map);
	}
	/**
     * 代理商管理页面根据代理商ID查询设备信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryEquipmentImeiInfo(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = equipmentMapper.queryEquipmentImeiInfo(map);
		int total = equipmentMapper.queryEquipmentImeiInfoCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
}
