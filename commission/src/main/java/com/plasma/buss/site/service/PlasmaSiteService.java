package com.plasma.buss.site.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plasma.buss.site.dao.PlasmaSiteDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;

/**
 * 浆站管理
 * 
 * @author fadl
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PlasmaSiteService {

	@Autowired
	public PlasmaSiteDao plasmaSiteDao;

	/**
	 * 查询未审核浆员列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryPlasmaSiteList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = plasmaSiteDao.queryPlasmaSiteList(dr);
		int totalNum = plasmaSiteDao.queryPlasmaSiteListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}

	public DataRow queryPlasmaSiteById(Long id) throws SQLException{
		return plasmaSiteDao.queryPlasmaSiteById(id);
	}

	public void savePlasmaSite(DataRow data) throws SQLException{
		plasmaSiteDao.savePlasmaSite(data);
	}

	public int updatePlasmaSiteById(DataRow data) throws SQLException{
		return plasmaSiteDao.updatePlasmaSiteById(data);
	}

	public int deletePlasmaSiteById(Long id) throws SQLException{
		return plasmaSiteDao.deletePlasmaSiteById(id);
	}
	
	/**
	 * 查询公司下面的浆站数量 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int queryPlasmaSiteCountByCompanyId(Long companyId)throws SQLException{
		return plasmaSiteDao.queryPlasmaSiteCountByCompanyId(companyId);
	}
	/**
	 * 查询浆站列表
	 */
	public List<DataRow> queryPlasmaSiteNoPageList()throws SQLException{
		return plasmaSiteDao.queryPlasmaSiteNoPageList();
	}
}
