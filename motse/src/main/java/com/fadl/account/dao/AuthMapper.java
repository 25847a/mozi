package com.fadl.account.dao;

import com.fadl.account.entity.Auth;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 权限菜单表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AuthMapper extends BaseMapper<Auth> {
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthList() throws SQLException;
}
