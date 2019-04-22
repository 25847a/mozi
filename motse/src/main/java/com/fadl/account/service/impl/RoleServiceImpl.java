package com.fadl.account.service.impl;

import com.fadl.account.entity.Role;
import com.fadl.account.dao.RoleMapper;
import com.fadl.account.service.RoleService;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台页面角色表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
	
	@Autowired
	RoleMapper roleMapper;
	
	/**
	 * 查询角色管理列表
	 * @param map
	 * @return
	 */
	@Override
	public DataRow queryRoleList(DataRow messageMap) throws SQLException {
		List<Role> list =this.selectList(null);
		messageMap.initSuccess(list);
		return messageMap;
	}

}
