package com.fadl.account.service.impl;

import com.fadl.account.entity.Admin;
import com.fadl.account.dao.AdminMapper;
import com.fadl.account.service.AdminService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台页面用户表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	/**
	 * 根据用户名查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@Override
	public Admin queryAdminInfo(String account) throws SQLException {
		return adminMapper.queryAdminInfo(account);
	}
	/**
	 * 查询代理商列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdminList(DataRow messageMap) throws SQLException {
		List<DataRow> list =adminMapper.queryAdminList();
		messageMap.initSuccess(list);
		return messageMap;
	}
	/**
	 * 查询用户的角色
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdminRoleInfo(Long id) throws SQLException {
		return adminMapper.queryAdminRoleInfo(id);
	}
	/**
	 * 查询用户管理列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdminInfoList(Map<String,String> map,DataRow messageMap) throws SQLException {
		List<DataRow> list =adminMapper.queryAdminInfoList(map);
		messageMap.initSuccess(list);
		return messageMap;
	}

}
