package com.plasma.buss.assay.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plasma.buss.assay.dao.NewCardDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;
import com.plasma.common.util.JsonUtil;

/**
 * 新老卡化验
 * @author hu
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class NewCardService {

	@Autowired
	public NewCardDao newCardDao;
	
	/**
	 * 查询化验记录列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryNewCardList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = newCardDao.queryNewCardList(dr);
		int totalNum = newCardDao.queryNewCardListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	
	public List<DataRow> queryNewCardList(DataRow data) throws SQLException{
		return newCardDao.queryNewCardList(data);
	}

	public int queryNewCardListCount(DataRow data) throws SQLException{
		return newCardDao.queryNewCardListCount(data);
	}

	public DataRow queryNewCardById(Long id) throws SQLException{
		return newCardDao.queryNewCardById(id);
	}

	public int updateNewCardById(DataRow data) throws SQLException{
		return newCardDao.updateNewCardById(data);
	}

	public int deleteNewCardById(Long id) throws SQLException{
		return newCardDao.deleteNewCardById(id);
	}
	
	/**
	 * 批量保存化验数据
	 * @param info
	 * @throws Exception
	 */
	public void saveNewCard(DataRow info) throws Exception{
		List<DataRow> list = JsonUtil.getMapper().readValue(info.getString("data"), new TypeReference<List<DataRow>>(){});
		newCardDao.saveNewCard(list);
	}
	
	/**
	 * 查询最后同步时间
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryNewCardMaxDate(DataRow info)throws SQLException{
		return newCardDao.queryNewCardMaxDate(info);
	}
	
}
