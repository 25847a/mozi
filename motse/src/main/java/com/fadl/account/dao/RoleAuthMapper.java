package com.fadl.account.dao;

import com.fadl.account.entity.RoleAuth;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {
		/**
		 * 查询角色菜单关联集合
		 * @return
		 * @throws SQLException
		 */
		public List<String> queryRoleAuthList()throws SQLException;
}
