package com.plasma.buss.collection.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plasma.buss.collection.dao.PlasmaCollectionDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;
import com.plasma.common.util.JsonUtil;

/**
 * 采浆
 * @author hu
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PlasmaCollectionService {
	
	@Autowired
	public PlasmaCollectionDao plasmaCollectionDao;

	public List<DataRow> queryPlasmCollectionList(DataRow data) throws SQLException{
		return plasmaCollectionDao.queryPlasmCollectionList(data);
	}

	public int queryPlasmCollectionListCount(DataRow data) throws SQLException{
		return plasmaCollectionDao.queryPlasmCollectionListCount(data);
	}

	public DataRow queryPlasmCollectionById(Long id) throws SQLException{
		return plasmaCollectionDao.queryPlasmCollectionById(id);
	}

	public int updatePlasmCollectionById(DataRow data) throws SQLException{
		return plasmaCollectionDao.updatePlasmCollectionById(data);
	}

	public int deletePlasmCollectionById(Long id) throws SQLException{
		return plasmaCollectionDao.deletePlasmCollectionById(id);
	}
	
	/**
	 * 查询最后同步时间
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaCollectionMaxDate(DataRow info)throws SQLException{
		return plasmaCollectionDao.queryPlasmaCollectionMaxDate(info);
	}
	
	public void savePlasmCollection(DataRow info) throws Exception{
		List<DataRow> list = JsonUtil.getMapper().readValue(info.getString("data"), new TypeReference<List<DataRow>>(){});
		plasmaCollectionDao.savePlasmCollection(list);
	}

	/**
	 * 查询体检数据列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryPlasmCollectionList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = plasmaCollectionDao.queryPlasmCollectionList(dr);
		int totalNum = plasmaCollectionDao.queryPlasmCollectionListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
}
