package com.fadl.health.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.account.dao.AgentMapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.Agent;
import com.fadl.common.Base64;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
//import com.fadl.common.GB2312Utils;
import com.fadl.common.HttpClientUtil;
import com.fadl.common.IConstants;
import com.fadl.common.ReadProperties;
import com.fadl.common.SessionUtil;
import com.fadl.health.dao.UserMapper;
import com.fadl.health.entity.Equipment;
import com.fadl.health.entity.EquipmentData;
import com.fadl.health.entity.Health;
import com.fadl.health.entity.HealthNew;
import com.fadl.health.entity.Healthdao;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.User;
import com.fadl.health.entity.UserEq;
import com.fadl.health.service.EquipmentDataService;
import com.fadl.health.service.EquipmentService;
import com.fadl.health.service.HealthNewService;
import com.fadl.health.service.HealthService;
import com.fadl.health.service.HealthdaoService;
import com.fadl.health.service.PushService;
import com.fadl.health.service.UserEqService;
import com.fadl.health.service.UserService;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;



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
	@Autowired
	EquipmentDataService equipmentDataService;
	@Autowired
	HealthService healthService;
	@Autowired
	HealthdaoService healthdaoService;
	@Autowired
	HealthNewService healthNewService;
	@Autowired
	AgentMapper agentMapper;
	
	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryUserGender(Map<String,Object> map) throws SQLException {
		return userMapper.queryUserGender(map);
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
		DataRow row =userMapper.queryaddUserInfo(map);
		if(row.get("bluetooth_list")!=null){
			String[] tooth= row.getString("bluetooth_list").replace("[", "").replace("]", "").replace("\"","").split(",");
			if(tooth.length==1){
				row.set("closeBluetooth1", tooth[0]);
				row.set("closeBluetooth2", "");
			}else{
				row.set("closeBluetooth1", tooth[0]);
				row.set("closeBluetooth2", tooth[1]);
			}
		}
		messageMap.initSuccess(row);
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
     * 上传使用者头像图片
     * @param user
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow uploadUserPicture(User user, DataRow messageMap) throws Exception {
		String filePath = ReadProperties.getValue("imageAddress");
		String time = System.currentTimeMillis()+".jpg";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
        }
		byte[] by= Base64.decode(user.getAvatar());
		OutputStream out = new FileOutputStream(new File(filePath+time));
		out.write(by, 0, by.length);
		out.close();//http://192.168.1.147:8443/avatars/
		user.setAvatar(ReadProperties.getValue("serverUrl")+time);
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.eq("imei", user.getImei());
		int row =userMapper.update(user, ew);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 点击确认修改个人详情的信息
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updateUserInfo(User user,Equipment equipment,DataRow messageMap) throws Exception {
		EntityWrapper<User> ew = new EntityWrapper<User>();
		ew.eq("imei", user.getImei());
		userMapper.update(user, ew);
		EntityWrapper<Equipment> eq = new EntityWrapper<Equipment>();
		eq.eq("imei", equipment.getImei());
		equipmentService.update(equipment, eq);
		messageMap.initSuccess();
		return messageMap;
	}
	/**
     * 查询添加用户按钮的用户信息
     * @param map
     * @return
     */
	@Override
	public DataRow queryImeiUserInfo(String imei, DataRow messageMap) throws Exception {
		EntityWrapper<User> ew= new EntityWrapper<User>();
		ew.eq("imei", imei);
		User user=this.selectOne(ew);
		if(user!=null){
			user.setBorn(DateUtil.sfDay.format(DateUtil.sfDay.parse(user.getBorn())));
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
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("adminId", admin.getId());
		map.put("imei", equipment.getImei());
		DataRow data =equipmentService.queryEquipmentAgent(map);
		if(data==null){
			Agent agent =agentMapper.queryAgentInfo(admin.getId());
			equipment.setAgentid(agent.getId().intValue());
			EntityWrapper<Equipment> eq = new EntityWrapper<Equipment>();
			eq.eq("imei", equipment.getImei());
			boolean row =equipmentService.update(equipment, eq);
			if(row){
				messageMap.initSuccess();
			}else{
				messageMap.initFial("请先通过设备IMEI查询");
			}
		}else{
			messageMap.initFial("该供应商已绑定此账号,无需重复绑定");
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
	/**
     * 添加用户
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addUserInfo(User user,String telephone, DataRow messageMap) throws Exception {
		user.setRole("使用者");
	//	SocketChannel  c = new NioSocketChannel();
		EntityWrapper<Equipment> ew = new EntityWrapper<Equipment>();
		ew.eq("imei", user.getImei());
		Equipment e=equipmentService.selectOne(ew);
		if(e!=null){
			EntityWrapper<User> eq = new EntityWrapper<User>();
			eq.eq("imei", user.getImei());
			User u =this.selectOne(eq);
			if(u==null){
				EntityWrapper<UserEq> ec = new EntityWrapper<UserEq>();
				ec.eq("eq_id", e.getId());
				ec.eq("typeof", 0);
				UserEq uq =userEqService.selectOne(ec);
				if(uq==null){
					user.setJfdataUpdateTime("5");
					user.setHighpressure(0);
					user.setLowpressure(0);
					user.setCreatetime(DateUtil.sf.format(new Date()));
					user.setAtlasttime(DateUtil.sf.format(new Date()));
					this.insert(user);//使用者
					boolean jfstatus = HttpClientUtil.registered(IConstants.channel_id + String.valueOf(user.getId()),"12345", "123456");
					if(jfstatus){
						//设备与监护人的关联关系
						UserEq ue = new UserEq();
						//就是mid
						EntityWrapper<User> ea = new EntityWrapper<User>();
						ea.eq("account", telephone);
						User use =this.selectOne(ea);
						ue.setUserId(use.getId());
						ue.setEqId(e.getId());
						ue.setTypeof(0);
						//设备与使用者的关联关系
						UserEq uue = new UserEq();
						uue.setUserId(user.getId());
						uue.setEqId(e.getId());
						uue.setTypeof(2);
						userEqService.insert(ue);
						userEqService.insert(uue);
						//////////////////////////////////////
						EquipmentData data = new EquipmentData();
						data.setUserId(user.getId());//使用者ID
						data.setHeartrate(0);
						data.setHighpressure(0);
						data.setBottompressure(0);
						data.setBloodpressure(0);
						data.setMocrocirculation(0);
						data.setBreathe(0);
						data.setSleeping(0.0);
						data.setStepWhen(0);
						data.setCarrieroad(0);
						data.setSedentary("0");
						data.setMovementstate(0);
						data.setBodytemp(0);
						data.setHumidity(0);
						data.setCrash(0);
						data.setCreatetime(DateUtil.sf.format(new Date()));
						data.setQxygen(0);
						data.setSleepingS(0);
						data.setRunS(0);
						data.setStepEach(0);
						data.setHrv(0);
						data.setMood(0);
						equipmentDataService.insert(data);
						Health bean = new Health();
						bean.setHrv(0);
						bean.setSbpAve(0);
						bean.setDbpAve(0);
						bean.setHeartrate(0);
						bean.setBloodoxygen(0);
						bean.setMicrocirculation(0);
						bean.setRespirationrate(0);
						bean.setPhone("mozistar"+user.getId());
						bean.setImei(user.getImei());
						bean.setCreatetime(DateUtil.sf.format(new Date()));
						bean.setAmedicalreport("0");
						healthService.insert(bean);
					    Push push = new Push();
					    push.setUserId(user.getId());//使用者ID
					    push.setAlias(use.getId());//监护者ID
					    push.setAllNotifyOn(1);
					    push.setHeartHigThd(100);
					    push.setHeartLowThd(55);
					    push.setHbpstart(80);
					    push.setHbpend(140);
					    push.setLbpstart(60);
					    push.setLbpend(100);
					    pushService.insert(push);
					    Healthdao healthdao = new Healthdao();
						healthdao.setCreatetime(DateUtil.sf.format(new Date()));
						healthdao.setPhone("mozistar"+user.getId());
						healthdao.setImei(user.getImei());
						healthdao.setHeartrate(80);//心率
						healthdao.setBloodoxygen(97);//血氧
						healthdao.setHrv(59);//Hrv
						healthdao.setMicrocirculation(85);//微循环
						healthdao.setRespirationrate(16);//呼吸
						healthdao.setSbpAve(120);//高压
						healthdao.setDbpAve(80);//低压
						healthdaoService.insert(healthdao);
						
						HealthNew healthnew = new HealthNew();
						healthnew.setCreatetime(DateUtil.sf.format(new Date()));
						healthnew.setPhone("mozistar"+user.getId());
						healthnew.setImei(user.getImei());
						healthNewService.insert(healthnew);
					/*	if(c!=null){
							c.writeAndFlush("$R06|"+GB2312Utils.gb2312eecode(user.getName())+":"+GB2312Utils.gb2312eecode(user.getAddress())+"\r\n");
						}*/
						messageMap.initSuccess("添加设备使用者成功！！！");
					}
				}else{
					messageMap.initFial("该设备监护者已经存在！！！");
				}
			}else{
				messageMap.initFial("该设备使用者已经存在！！！");
			}
		}else{
			messageMap.initFial("设备号不存在,请联系经销商！！！");
		}
		return messageMap;
	}
	/**
	 * 根据代理商ID查询使用者总数
	 */
	@Override
	public int queryUserCount(long id) throws Exception {
		return userMapper.queryUserCount(id);
	}
}
