package com.fadl.account.service.impl;

import com.fadl.account.entity.AdminRole;
import com.fadl.account.dao.AdminRoleMapper;
import com.fadl.account.service.AdminRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户---角色关联表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
	
	@Autowired
	AdminRoleMapper adminRoleMapper;
	/**
	 * 查询用户、角色关联信息
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<AdminRole> queryAdminRoleInfo(Long id) throws SQLException {
		return adminRoleMapper.queryAdminRoleInfo(id);
	}

}
