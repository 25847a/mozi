package com.fadl.account.service;

import com.fadl.account.entity.Admin;
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
	public Admin queryAdminInfo(String account)throws Exception;
}
