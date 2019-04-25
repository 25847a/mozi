package com.fadl.account.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import java.sql.SQLException;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 后台页面用户表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AdminService extends IService<Admin> {
	/**
	 * 根据用户名查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public Admin queryAdminInfo(String account)throws SQLException;
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminList(DataRow messageMap)throws SQLException;
	/**
	 * 查询用户的角色
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminRoleInfo(Long id)throws SQLException;
	/**
	 * 查询用户管理列表
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryAdminInfoList(Map<String,String> map,DataRow messageMap)throws SQLException;
}
