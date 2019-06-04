package com.fadl.account.dao;

import com.fadl.account.entity.Auth;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	 * 查询菜单列表
	 * @param map
	 * @return
	 * @throws SQLExceptionL
	 */
	public List<DataRow> queryAuthList(Map<String,Object> map)throws SQLException;
	/**
	 * 查询菜单列表总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryAuthListCount(Map<String,Object> map)throws SQLException;
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthList() throws SQLException;
	/**
	 * 查询角色全部信息
	 * @return
	 * @throws SQLException
	 */
	public List<Auth>queryAuthInfo() throws SQLException;
}
