package com.fadl.account.service;

import com.fadl.account.entity.Auth;
import com.fadl.common.DataRow;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
     * 查询菜单管理列表
     * @return
     */
	public DataRow queryAuthInfoList(Map<String,Object> map,DataRow messageMap)throws SQLException;
	 /**
     * 新增菜单信息
     * @return
     */
	public DataRow addAuthInfo(Auth auth,DataRow messageMap)throws SQLException;
	/**
     * 修改菜单信息
     * @return
     */
	public DataRow updateAuthInfo(Auth auth,DataRow messageMap)throws SQLException;
	/**
     * 删除菜单信息
     * @return
     */
	public DataRow deleteAuthInfo(Auth auth,DataRow messageMap)throws SQLException;
	/**
	 * 查询权限菜单集合
	 * @return
	 * @throws Exception
	 */
	public List<Auth> queryAuthList()throws Exception;
	/**
	 * 查询角色全部信息
	 * @return
	 * @throws SQLException
	 */
	public List<Auth>queryAuthInfo() throws SQLException;
}
