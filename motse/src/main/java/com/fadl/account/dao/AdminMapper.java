package com.fadl.account.dao;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	/**
	 * 查询代理商的名字
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminAgentInfo(Long id)throws SQLException;
	/**
	 * 查询用户管理列表
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAdminInfoList(Map<String,String> map)throws SQLException;
}
