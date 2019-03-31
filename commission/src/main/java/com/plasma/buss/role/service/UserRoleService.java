package com.plasma.buss.role.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plasma.buss.role.dao.RoleMenuDao;
import com.plasma.buss.role.dao.UserRoleDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.PageBean;

/**
 * 角色管理
 * @author hu
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class UserRoleService {
	
	@Autowired
	public UserRoleDao userRoleDao;
	@Autowired
	public RoleMenuDao roleMenuDao;
	/**
	 * 分页查询角色列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryRoleList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = userRoleDao.queryRoleList(dr);
		int totalNum = userRoleDao.queryRoleListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	/**
	 * 查询全部角色列表
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryAllRoleList(DataRow dr) throws SQLException{
		List<DataRow> list = userRoleDao.queryAllRoleList(dr);
		return list;
	}
	/**
	 * 查询角色列表总数
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int queryRoleListCount(DataRow data) throws SQLException{
		return userRoleDao.queryRoleListCount(data);
	}
	/**
	 * 查询角色详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryRoleById(Long id) throws SQLException{
		return userRoleDao.queryRoleById(id);
	}
	/**
	 * 添加角色
	 * @param data
	 * @throws SQLException
	 */
	public void saveRole(DataRow data) throws SQLException{
		userRoleDao.saveRole(data);
	}
	/**
	 * 修改角色信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateRoleById(DataRow data) throws SQLException{
		return userRoleDao.updateRoleById(data);
	}

	/**
	 * 删除角色，删除角色下面的菜单
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteRoleById(Long id,DataRow messageMap) throws SQLException{
		int count = roleMenuDao.queryRoleMenuCount(id);
		if (count>0) {
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "请先取消该角色的授权");
			return;
		}
		int res = userRoleDao.deleteRoleById(id);
		int res1 = roleMenuDao.deleteRoleMenuByRoleId(id);
		if (res>0 && res1>0) {
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
		}
		throw new SQLException();
	}
}
