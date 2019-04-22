package com.fadl.health.service;

import com.fadl.health.entity.UserEq;

import java.sql.SQLException;

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
}
