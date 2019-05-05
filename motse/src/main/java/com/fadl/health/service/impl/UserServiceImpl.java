package com.fadl.health.service.impl;

import com.fadl.health.entity.Equipment;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.User;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.SessionUtil;
import com.fadl.health.dao.UserMapper;
import com.fadl.health.service.EquipmentService;
import com.fadl.health.service.PushService;
import com.fadl.health.service.UserEqService;
import com.fadl.health.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	PushService pushService;
	@Autowired
	UserEqService userEqService;
	@Autowired
	EquipmentService equipmentService;
	
	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryUserGender() throws SQLException {
		return userMapper.queryUserGender();
	}
	/**
	 * 查询添加用户的列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdduserList(Map<String, Object> map, DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> list =userMapper.queryAdduserList(map);
		int total = userMapper.queryAdduserListCount(map);
		messageMap.initSuccess(list);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
	 * 查询添加用户的列表总数
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int queryAdduserListCount(Map<String, Object> map) throws SQLException {
		return userMapper.queryAdduserListCount(map);
	}
	/**
     * 查询添加用户页面的信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryaddUserInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		DataRow list =userMapper.queryaddUserInfo(map);
		messageMap.initSuccess(list);
		return messageMap;
	}
	/**
     * 更改重点关爱状态
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateLoveInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.eq("imei", map.get("imei"));
		User user =this.selectOne(ew);
		if(user.getLove()==0){
			user.setLove(1);
			this.update(user, ew);
		}else{
			user.setLove(0);
			this.update(user, ew);
		}
		messageMap.initSuccess(user);
		return messageMap;
	}
	/**
     * 查询添加用户预警设置信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryPushInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		messageMap =userMapper.queryPushInfo(map);
		messageMap.initSuccess();
		return messageMap;
	}
	/**
     * 更改用户预警设置信息
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updatePushInfo(Push push, DataRow messageMap) throws SQLException {
		EntityWrapper<Push> ew = new EntityWrapper<Push>();
		ew.eq("userId", push.getUserId());
		ew.eq("alias", push.getAlias());
		Push p = pushService.selectOne(ew);
		if(p!=null){
			pushService.update(push, ew);
		}else{
			push.setHeartNotifyOn(1);
			push.setBoolPreNotifyOn(1);
			push.setFallNotifyOn(1);
			push.setCreateTime(DateUtil.sf.format(new Date()));
			pushService.insert(push);
		}
		messageMap.initSuccess();
		return messageMap;
	}
	/**
     * 点击查询个人详情的信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryUserEquipmentInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		DataRow row =userMapper.queryUserEquipmentInfo(map);
		List<DataRow> list =userMapper.queryObserver(map);
		messageMap.put("list", list);
		messageMap.initSuccess(row);
		return messageMap;
	}
	/**
     * 通过设备ID查询观察者
     * @param map
     * @return
     */
	@Override
	public List<DataRow> queryObserver(Map<String, String> map) throws SQLException {
		return userMapper.queryObserver(map);
	}
	/**
     * 通过设备查询用户和设备信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryUserEquipment(Map<String, String> map) throws SQLException {
		return userMapper.queryUserEquipment(map);
	}
	/**
     * 点击确认修改个人详情的信息
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateUserInfo(User user, DataRow messageMap) throws SQLException {
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.eq("imei", user.getImei());
		Integer row =userMapper.update(user, ew);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 查询添加用户按钮的用户信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryImeiUserInfo(String imei, DataRow messageMap) throws SQLException {
		EntityWrapper<User> ew= new EntityWrapper<User>();
		ew.eq("imei", imei);
		User user=this.selectOne(ew);
		if(user!=null){
			messageMap.initSuccess(user);
		}else{
			messageMap.initFial("该设备未绑定用户");
		}
		return messageMap;
	}
	/**
     * 点击添加用户确定键添加用户
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow AddUserDetermine(Equipment equipment, DataRow messageMap) throws SQLException {
		Admin admin = SessionUtil.getSessionAdmin();
		EntityWrapper<Equipment> ew = new EntityWrapper<Equipment>();
		ew.eq("agentid", admin.getId());
		ew.eq("imei", equipment.getImei());
		Equipment e =equipmentService.selectOne(ew);
		if(e==null){
			equipment.setAgentid(admin.getId().intValue());
			EntityWrapper<Equipment> eq = new EntityWrapper<Equipment>();
			eq.eq("imei", equipment.getImei());
			boolean row =equipmentService.update(equipment, eq);
			if(row){
				messageMap.initSuccess();
			}else{
				messageMap.initFial("请先通过设备IMEI查询");
			}
		}else{
			messageMap.initFial("该用户已绑定此账号,无需重复绑定");
		}
		return messageMap;
	}
	/**
     * 点击移除用户确定键添加用户
     * @param map
     * @return
     */
	@Override
	public DataRow deleteUserDetermine(User user, DataRow messageMap) throws SQLException {
		EntityWrapper<User> ew =new EntityWrapper<User>();
		ew.eq("imei", user.getImei());
		user = this.selectOne(ew);
		user.setIsDelete(1);
		this.update(user, ew);
		EntityWrapper<Equipment> eq =new EntityWrapper<Equipment>();
		eq.eq("imei", user.getImei());
		Equipment e = equipmentService.selectOne(eq);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("imei", user.getImei());
		map.put("phone", "mozistar"+user.getId());
		map.put("userId", user.getId());
		map.put("account", user.getAccount());
		map.put("equipmentId", e.getId());
		int a =userMapper.deleteUserCount(map);
		System.out.println(a);
		messageMap.initSuccess();
		//jfhealth,waveform,equipment_data,jfhealth_new,user  不删除
		//user_eq,usercode,sensorstatus(清空imei的记录),push,positionig(清空imei的记录),jfhealthdao,chat  可删除
		
		
		return messageMap;
	}
	
}
