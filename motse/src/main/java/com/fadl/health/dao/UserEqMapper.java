package com.fadl.health.dao;

import com.fadl.health.entity.UserEq;

import java.sql.SQLException;

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
}
