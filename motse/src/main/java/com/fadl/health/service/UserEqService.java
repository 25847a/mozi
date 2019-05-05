package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.UserEq;

import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户设备关联表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
public interface UserEqService extends IService<UserEq> {

	/**
	 * 查询使用者关联的监护者ID
	 * @return
	 * @throws SQLException
	 */
	public UserEq queryUserEqInfo(Integer userId)throws SQLException;
	/**
     * 输入手机号码添加观察者
     * @param map
     * @return
     */
	public DataRow addFollowInfo(Map<String,String> map,DataRow messageMap)throws SQLException;
	/**
	 * 通过手机号码和设备号查询是否绑定
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
