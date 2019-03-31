package com.fadl.account.service.impl;

import com.fadl.account.entity.Admin;
import com.fadl.account.dao.AdminMapper;
import com.fadl.account.service.AdminService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
	public Admin queryAdminInfo(String account) throws Exception {
		return adminMapper.queryAdminInfo(account);
	}

}
