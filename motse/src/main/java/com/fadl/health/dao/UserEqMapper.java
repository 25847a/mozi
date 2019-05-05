package com.fadl.health.dao;

import com.fadl.health.entity.UserEq;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户设备关联表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
public interface UserEqMapper extends BaseMapper<UserEq> {

	/**
	 * 查询使用者关联的监护者ID
	 * @return
	 * @throws SQLException
	 */
	public UserEq queryUserEqInfo(Integer userId)throws SQLException;
	/**
	 * 通过手机号码和设备号查询是否绑定观察者
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public UserEq queryFollowInfo(Map<String,String> map)throws SQLException;
	/**
	 * 通过手机号码和设备号查询是否绑定监护者
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public UserEq queryCustodyInfo(Map<String,String> map)throws SQLException;
	
}
