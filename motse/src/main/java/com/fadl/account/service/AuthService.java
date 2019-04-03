package com.fadl.account.service;

import com.fadl.account.entity.Auth;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 权限菜单表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface AuthService extends IService<Auth> {
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthList()throws Exception;
}