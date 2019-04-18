package com.fadl.health.service;

import com.fadl.common.DataRow;
import com.fadl.health.entity.User;

import java.sql.SQLException;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface UserService extends IService<User> {

	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUserGender()throws SQLException;
}
