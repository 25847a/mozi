package com.fadl.health.service.impl;

import com.fadl.health.entity.UserEq;
import com.fadl.health.dao.UserEqMapper;
import com.fadl.health.service.UserEqService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	/**
	 * 查询使用者关联的监护者ID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserEq queryUserEqInfo(Integer userId) throws SQLException {
		// TODO Auto-generated method stub
		return userEqMapper.queryUserEqInfo(userId);
	}

}
