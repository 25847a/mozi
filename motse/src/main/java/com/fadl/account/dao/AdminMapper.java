package com.fadl.account.dao;

import com.fadl.account.entity.Admin;
import java.sql.SQLException;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 后台页面用户表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AdminMapper extends BaseMapper<Admin> {
	/**
	 * 根据用户名查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public Admin queryAdminInfo(String account)throws SQLException;
}
