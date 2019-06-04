package com.fadl.account.service;

import com.fadl.account.entity.Role;
import com.fadl.common.DataRow;
import java.sql.SQLException;
import java.util.Map;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 后台页面角色表 服务类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
public interface RoleService extends IService<Role> {

	/**
     * 查询角色列表
     * @return
     */
	public DataRow queryRoleList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	 /**
     * 新增角色信息
     * @return
     */
	public DataRow addRoleInfo(Role role,DataRow messageMap)throws SQLException;
	/**
     * 修改角色信息
     * @return
     */
	public DataRow updateRoleInfo(Role role,DataRow messageMap)throws SQLException;
	/**
     * 删除角色信息
     * @return
     */
	public DataRow deleteRoleInfo(Role role,DataRow messageMap)throws SQLException;
}
