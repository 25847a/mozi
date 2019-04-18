package com.fadl.health.service.impl;

import com.fadl.health.entity.User;
import com.fadl.common.DataRow;
import com.fadl.health.dao.UserMapper;
import com.fadl.health.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryUserGender() throws SQLException {
		return userMapper.queryUserGender();
	}

}
