package com.plasma.buss.role.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plasma.buss.role.dao.MenuDao;
import com.plasma.buss.role.dao.RoleMenuDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;

/**
 * 角色菜单关联
 * 
 * @author hu
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class RoleMenuService {

	@Autowired
	public RoleMenuDao roleMenuDao;
	@Autowired	
	public MenuDao menuDao;
	
	public PageBean queryRoleMenuList(DataRow dr,PageBean pageBean) throws SQLException{
		
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = roleMenuDao.queryRoleMenuList(dr);
		int totalNum = roleMenuDao.queryRoleMenuListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}

	public int queryRoleMenuListCount(DataRow data) throws SQLException{
		return roleMenuDao.queryRoleMenuListCount(data);
	}

	public DataRow queryRoleMenuById(Long id) throws SQLException{
		return roleMenuDao.queryRoleMenuById(id);
	}

	/**
	 * 保存角色菜单
	 * @param data
	 * @throws SQLException
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveRoleMenu(DataRow data) throws SQLException{
		List<DataRow> list = new ArrayList<DataRow>();
		//先删除该角色下所关联的菜单
		roleMenuDao.deleteRoleMenuByRoleId(data.getLong("roleId"));
		//保存该角色菜单权限
		String[] str = data.getString("menuId").split(",");
		DataRow row = null;
		for (String string : str) {
			row = new DataRow();
			row.put("roleId", data.get("roleId"));
			row.put("menuId", string);
			list.add(row);
		}
		roleMenuDao.saveRoleMenu(list);
	}

	public int updateRoleMenuById(DataRow data) throws SQLException{
		return roleMenuDao.updateRoleMenuById(data);
	}

	public int deleteRoleMenuById(Long id) throws SQLException{
		return roleMenuDao.deleteRoleMenuById(id);
	}
	
	
	/**
	 * 查询某个角色的权限 
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryRoleMenuListByRoleId(Long roleId)throws SQLException{
		return roleMenuDao.queryRoleMenuListByRoleId(roleId);
	}
	/**
	 * 查询全部菜单并返回选中角色已有菜单
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryUserRoleMenu(DataRow dr)throws SQLException{
		List<DataRow> menuList = menuDao.menuAllList();
		List<DataRow> roleList=queryRoleMenuListByRoleId(dr.getLong("roleId"));
		for (int i=0;i<menuList.size();i++) {
			for (DataRow roleRow : roleList) {
				if(menuList.get(i).getString("id").equals(roleRow.getString("menuId"))){//如果这个角色有这个菜单  就勾选
					menuList.get(i).put("checked", true);
					menuList.get(i).put("open", true);
				}
			}
		}
		return menuList;
	}
}
