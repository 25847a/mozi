package com.plasma.buss.check.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.plasma.buss.check.dao.CheckDao;
import com.plasma.common.DataRow;
import com.plasma.common.base.PageBean;
import com.plasma.common.util.JsonUtil;

/**
 * 体检
 * @author hu
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CheckService {
	
	@Autowired
	public CheckDao checkDao;

	public List<DataRow> queryCheckList(DataRow data) throws SQLException{
		return checkDao.queryCheckList(data);
	}

	public int queryCheckListCount(DataRow data) throws SQLException{
		return checkDao.queryCheckListCount(data);
	}

	public DataRow queryCheckById(Long id) throws SQLException{
		return checkDao.queryCheckById(id);
	}

	public int updateCheckById(DataRow data) throws SQLException{
		return checkDao.updateCheckById(data);
	}

	public int deleteCheckById(Long id) throws SQLException{
		return checkDao.deleteCheckById(id);
	}
	
	/**
	 * 批量保存体检数据
	 * @param info
	 * @throws Exception
	 */
	public void saveCheck(DataRow info) throws Exception{
		List<DataRow> list = JsonUtil.getMapper().readValue(info.getString("data"), new TypeReference<List<DataRow>>(){});
		checkDao.saveCheck(list);
	}
	
	/**
	 * 查询最后体检时间 
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryCheckMaxDate(DataRow info)throws SQLException{
		return checkDao.queryCheckMaxDate(info);
	}
	
	/**
	 * 查询体检数据列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryCheckList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = checkDao.queryCheckList(dr);
		int totalNum = checkDao.queryCheckListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
}
