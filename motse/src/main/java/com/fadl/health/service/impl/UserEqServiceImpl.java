package com.fadl.health.service.impl;

import com.fadl.health.entity.Push;
import com.fadl.health.entity.User;
import com.fadl.health.entity.UserEq;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.health.dao.UserEqMapper;
import com.fadl.health.service.PushService;
import com.fadl.health.service.UserEqService;
import com.fadl.health.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户设备关联表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
@Service
public class UserEqServiceImpl extends ServiceImpl<UserEqMapper, UserEq> implements UserEqService {
	
	@Autowired
	UserEqMapper userEqMapper;
	@Autowired
	UserService userService;
	@Autowired
	PushService pushService;
	
	/**
	 * 查询使用者关联的监护者ID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserEq queryUserEqInfo(Integer userId) throws SQLException {
		return userEqMapper.queryUserEqInfo(userId);
	}
	/**
     * 输入手机号码添加观察者
     * @param map
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow addFollowInfo(Map<String, String> map, DataRow messageMap) throws SQLException {
		UserEq userEq = userEqMapper.queryCustodyInfo(map);
		if(userEq==null){
			UserEq u = userEqMapper.queryFollowInfo(map);
			if(u==null){
				DataRow row =userService.queryUserEquipment(map);
				if(row!=null){
					EntityWrapper<User> eq = new EntityWrapper<User>();
					eq.eq("account", map.get("account"));
					User user =userService.selectOne(eq);
					u= new UserEq();
					u.setEqId(row.getInt("equipmentId"));
					u.setUserId(user.getId());
					u.setTypeof(1);
					u.setAuthorized("已授权");
					userEqMapper.insert(u);
					Push push = new Push();
				    push.setUserId(row.getInt("userId"));
				    push.setAlias(user.getId());
				    push.setAllNotifyOn(1);
				    push.setHeartHigThd(100);
				    push.setHeartLowThd(60);
				    push.setHbpstart(90);
				    push.setHbpend(120);
				    push.setLbpstart(60);
				    push.setLbpend(80);
				    push.setCreateTime(DateUtil.sf.format(new Date()));
				    pushService.insert(push);
				    map.put("equipmentId", row.getString("equipmentId"));
				    List<DataRow> list =userService.queryObserver(map);
				    messageMap.initSuccess(list);
				}else{
					messageMap.initFial("设备未绑定,请先绑定设备");
				}
			}else{
				messageMap.initFial("已经关注,不可重复关注");
			}
		}else{
			messageMap.initFial("该用户已绑定此设备,不可成为观察者");
		}
		return messageMap;
	}
	/**
	 * 通过手机号码和设备号查询是否绑定观察者
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserEq queryFollowInfo(Map<String, String> map) throws SQLException {
		return userEqMapper.queryFollowInfo(map);
	}
	/**
	 * 通过手机号码和设备号查询是否绑定监护者
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserEq queryCustodyInfo(Map<String, String> map) throws SQLException {
		return userEqMapper.queryCustodyInfo(map);
	}

}
