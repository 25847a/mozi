package com.fadl.health.service.impl;

import com.fadl.health.entity.Equipment;
import com.fadl.health.entity.Positionig;
import com.fadl.health.entity.Sensorstatus;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.HttpClientUtil;
import com.fadl.health.dao.EquipmentMapper;
import com.fadl.health.dao.PositionigMapper;
import com.fadl.health.dao.SensorstatusMapper;
import com.fadl.health.service.EquipmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Autowired
	PositionigMapper positionigMapper;
	@Autowired
	SensorstatusMapper sensorstatusMapper;
	
	
	
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
	public DataRow queryEquipmentList(Map<String,Object> map,DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = equipmentMapper.queryEquipmentList(map);
		int total = equipmentMapper.queryEquipmentListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
     * 在线离线
     * @param equipment
     * @return
     */
	@Override
	public DataRow updateBluetooth(Equipment equipment, DataRow messageMap) throws Exception {
		//点击绑定,需要把蓝牙名称更新了
		Equipment e= equipmentMapper.selectById(equipment.getId());
		if(e.getBluetoothList() != null && !e.getBluetoothList().equals("[]") && !e.getBluetoothList().equals("")){
			String bluetooths = e.getBluetoothList().substring(1,e.getBluetoothList().length()-1).replace("\"", "");
			String[] bluetoothList = bluetooths.split(",");
			if(bluetoothList.length==1){
				e.setBluetoothList("[\""+equipment.getBluetoothName()+"\"]");
			}else{
				e.setBluetoothList("[\""+equipment.getBluetoothName()+"\",\""+bluetoothList[1]+"\"]");
			}
			int row =equipmentMapper.updateById(e);
			if(row>0){
				messageMap=HttpClientUtil.connectBluetooth(equipment);
			}else{
				messageMap.initFial("修改蓝牙名称失败");
			}
		}
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
	/**
     * 录入设备到代理商名下
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow inuptEquipmentImeiInfo(Integer id, String[] imeis, DataRow messageMap) throws SQLException {
		Set<String> imeiList = new HashSet<>();
		for(String imei:imeis){
			imeiList.add(imei);
		}
		List<String> list =equipmentMapper.queryImeiList(imeiList);
		if(list!=null && list.size()>0){
			for(String imei:imeiList){
				if(!list.contains(imei)){
					Equipment adde = new Equipment();
					adde.setImei(imei);
					adde.setCreatetime(DateUtil.sf.format(new Date()));
					adde.setUpdatetime(DateUtil.sf.format(new Date()));
					adde.setEqStatus("H:0");
					adde.setBluetoothElectricity(0);
					adde.setBluetoothStatus("0");
					adde.setBluetoothType("0");
					adde.setClock("闹钟");
					adde.setName("设备信息");
					adde.setEqtype("1");
					adde.setLordpower(0);
					adde.setVersion("0.0");
					adde.setSignalxhao("0");
					adde.setBluetoothName("000000000000");
					adde.setBluetoothmac("000000000000");
					adde.setAgentid(id);
					adde.setModel("LN073OV1");
					equipmentMapper.insert(adde);
					Positionig p = new Positionig();
					p.setImei(imei);
					p.setCreatetime(DateUtil.sf.format(new Date()));
					p.setPositioningData("39.9134905988:116.4072638138");
					p.setPositioningS("0");
					positionigMapper.insert(p);
					Sensorstatus s = new Sensorstatus();
					s.setImei(imei);
					s =sensorstatusMapper.selectOne(s);
					if(s==null){
						s = new Sensorstatus();
						s.setImei(imei);
						s.setH("H:1");
						s.setG("G:1");
						s.setAdddate(DateUtil.sf.format(new Date()));
						sensorstatusMapper.insert(s);
					}
				}
			}
		}else{
			for(String imei:imeiList){
				Equipment adde = new Equipment();
				adde.setImei(imei);
				adde.setCreatetime(DateUtil.sf.format(new Date()));
				adde.setUpdatetime(DateUtil.sf.format(new Date()));
				adde.setEqStatus("H:0");
				adde.setBluetoothElectricity(0);
				adde.setBluetoothStatus("0");
				adde.setBluetoothType("0");
				adde.setClock("闹钟");
				adde.setName("设备信息");
				adde.setEqtype("1");
				adde.setLordpower(0);
				adde.setVersion("0.0");
				adde.setSignalxhao("0");
				adde.setBluetoothName("000000000000");
				adde.setBluetoothmac("000000000000");
				adde.setAgentid(id);
				adde.setModel("LN073OV1");
				equipmentMapper.insert(adde);
				Positionig p = new Positionig();
				p.setImei(imei);
				p.setCreatetime(DateUtil.sf.format(new Date()));
				p.setPositioningData("39.9134905988:116.4072638138");
				p.setPositioningS("0");
				positionigMapper.insert(p);
				Sensorstatus s = new Sensorstatus();
				s.setImei(imei);
				s =sensorstatusMapper.selectOne(s);
				if(s==null){
					s = new Sensorstatus();
					s.setImei(imei);
					s.setH("H:1");
					s.setG("G:1");
					s.setAdddate(DateUtil.sf.format(new Date()));
					sensorstatusMapper.insert(s);
				}
			}
		}
		messageMap.initSuccess();
		return messageMap;
	}
}
