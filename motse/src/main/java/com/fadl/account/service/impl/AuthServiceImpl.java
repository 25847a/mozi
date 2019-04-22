package com.fadl.account.service.impl;

import com.fadl.account.entity.Auth;
import com.fadl.account.dao.AuthMapper;
import com.fadl.account.service.AuthService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限菜单表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {
	
	@Autowired
	AuthMapper authMapper;
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Auth> queryAuthList() throws Exception {
		return authMapper.queryAuthList();
	}
	@Override
	public DataRow queryAuthList(DataRow messageMap) throws Exception {
		List<Auth> list = this.selectList(null);
		messageMap.initSuccess(list);
		return messageMap;
	}

}
