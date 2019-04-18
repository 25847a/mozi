package com.fadl.health.dao;

import com.fadl.common.DataRow;
import com.fadl.health.entity.User;

import java.sql.SQLException;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 查询使用者男女数量饼状图 
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUserGender()throws SQLException;
}
