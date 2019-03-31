package com.plasma.buss.role.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plasma.buss.role.dao.MenuDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;

/**
 * 菜单管理
 * @author hu
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class MenuService {
	
	@Autowired
	public MenuDao menuDao;
	/**
	 * 查询全部菜单信息
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> menuAllList() throws SQLException{
		return menuDao.menuAllList();
	}
	/**
	 * 查询菜单列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryMenuList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = menuDao.queryMenuList(dr);
		int totalNum = menuDao.queryMenuListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	/**
	 * 查询菜单总数
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int queryMenuListCount(DataRow data) throws SQLException{
		return menuDao.queryMenuListCount(data);
	}
	/**
	 * 查询菜单详情
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryMenuById(Long id) throws SQLException{
		return menuDao.queryMenuById(id);
	}
	/**
	 * 添加菜单信息
	 * @param data
	 * @throws SQLException
	 */
	public void saveMenu(DataRow data) throws SQLException{
		menuDao.saveMenu(data);
	}
	/**
	 * 修改菜单信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateMenuById(DataRow data) throws SQLException{
		return menuDao.updateMenuById(data);
	}
	/**
	 * 删除菜单信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int deleteMenuById(Long id) throws Exception{
		return menuDao.deleteMenuById(id);
	}
	public int deleteRoleMenuById(Long id) throws Exception{
		return menuDao.deleteRoleMenuById(id);
	}
	/**
	 * 删除菜单权限
	 * @param id 菜单ID
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteMenuRole(Long id) throws Exception{
		int res = deleteMenuById(id);
		deleteRoleMenuById(id);
		if (res > 0) {
			return 1;
		}
		return -1;
	}

}
