package com.plasma.buss.company.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plasma.buss.company.dao.PlasmaCompanyDao;
import com.plasma.buss.site.dao.PlasmaSiteDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.PageBean;

/**
 * 公司管理
 * @author fadl
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PlasmaCompanyService {
	
	@Autowired
	public PlasmaCompanyDao plasmaCompanyDao;
	@Autowired
	public PlasmaSiteDao plasmaSiteDao;

	/**
	 * 查询公司列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryPlasmaCompanyList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = plasmaCompanyDao.queryPlasmaCompanyList(dr);
		int totalNum = plasmaCompanyDao.queryPlasmaCompanyListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	
	public List<DataRow> queryPlasmaCompanyList(DataRow data) throws SQLException{
		return plasmaCompanyDao.queryPlasmaCompanyList(data);
	}

	public int queryPlasmaCompanyListCount(DataRow data) throws SQLException{
		return plasmaCompanyDao.queryPlasmaCompanyListCount(data);
	}

	public DataRow queryPlasmaCompanyById(Long id) throws SQLException{
		return plasmaCompanyDao.queryPlasmaCompanyById(id);
	}

	public void savePlasmaCompany(DataRow data) throws SQLException{
		plasmaCompanyDao.savePlasmaCompany(data);
	}

	public int updatePlasmaCompanyById(DataRow data) throws SQLException{
		return plasmaCompanyDao.updatePlasmaCompanyById(data);
	}
	
	public void deletePlasmaCompanyById(Long id,DataRow messageMap) throws SQLException{
		int count = plasmaSiteDao.queryPlasmaSiteCountByCompanyId(id);
		if (count > 0) {
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "请先解绑浆站");
		}else{
			int res =plasmaCompanyDao.deletePlasmaCompanyById(id);
			if(res>0){
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
				messageMap.put(IConstants.APP_RESULT_MSG,"删除成功");
			}else{
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
				messageMap.put(IConstants.APP_RESULT_MSG, "删除失败");
			}
		}
	}
}
