package com.plasma.buss.plasma.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plasma.buss.plasma.dao.ProviderBaseinfoDao;
import com.plasma.buss.plasma.dao.ProviderBaseinfoTempDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.base.PageBean;
import com.plasma.common.util.JsonUtil;

/**
 * 浆员临时表
 * @author fadl
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProviderBaseinfoTempService {

	@Autowired
	public ProviderBaseinfoTempDao providerBaseinfoTempDao;
	@Autowired
	public ProviderBaseinfoDao providerBaseinfoDao;
	
	/**
	 * 接收取消发卡的请求,并插入到临时表中
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void receiveCancelCard(DataRow data,DataRow messageMap)throws Exception{
		DataRow row = providerBaseinfoDao.queryBaseInfo(data);
		if (row.getInt("status")==1) {
			String id = row.getString("id");
			/*row.remove("id");
			row.put("type", data.getInt("type"));
			row.put("status", 0);
			// 保存记录到临时表
			providerBaseinfoTempDao.saveProviderBaseinfoTemp(row);*/
			data = new DataRow();
			data.put("id", id);
			data.put("status", "0");
			data.put("type", "1");
			//更新浆员状态
			providerBaseinfoDao.updateProviderBaseinfoById(data);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG,"浆员审核中");
		}
	}
	
	/**
	 * 取消发卡列表
	 */
	public PageBean queryCancelCardList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = providerBaseinfoTempDao.queryProviderBaseinfoTempList(dr);
		int totalNum = providerBaseinfoTempDao.queryProviderBaseinfoTempListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	
	/**
	 * 根据条件查询浆员信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaTempInfo(DataRow data)throws SQLException{
		return providerBaseinfoTempDao.queryPlasmaTempInfo(data);
	}
	
	/**
	 * 查询临时表浆员详情
	 * @return
	 * @throws Exception
	 */
	public DataRow queryBaseTempInfo(DataRow data)throws SQLException{
		return providerBaseinfoTempDao.queryBaseTempInfo(data);
	}
	
	/**
	 * 接收信息变更申请
	 * @param data
	 * @param messageMap
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void receivePlasmaUpdate(DataRow data,DataRow messageMap)throws Exception{
		DataRow temp = JsonUtil.getMapper().readValue(data.getString("info"), DataRow.class);
		temp.put("id", null);
		temp.put("status", "0");
		DataRow row = this.queryBaseTempInfo(temp);
		if (null==row || row.size()==0) {
			DataRow up = new DataRow();
			up.put("providerNo", temp.get("providerNo"));
			up.put("status", 0);
			up.put("type", 2);
			up.put("plasmaId", temp.get("plasmaId"));
			providerBaseinfoDao.updatePlasmaByProvider(up);
			providerBaseinfoTempDao.saveProviderBaseinfoTemp(temp);
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG,"浆员审核中");
		}
	}
	
	/**
	 * 批量审核浆员
	 * @return
	 * @throws SQLException
	 */
	public int updateTempByProvider(DataRow data)throws SQLException{
		return providerBaseinfoTempDao.updateTempByProvider(data);
	}
	
	/**
	 * 浆员信息变更
	 * @return
	 * @throws SQLException 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void examinePlasmaUpdate(DataRow data) throws SQLException{
		String[] str = data.getString("id").split(",");
		List<String> list = Arrays.asList(str);
		data.put("ids", list);
		int res = providerBaseinfoTempDao.updateTempByProvider(data);
		String plasmaId = data.getString("plasmaId");
		if (res>0) {
			for (String string : list) {
				data = new DataRow();
				data.put("id", string);
				data.put("plasmaId", plasmaId);
				DataRow row = providerBaseinfoTempDao.queryBaseTempInfo(data);
				res = providerBaseinfoDao.updateProviderBaseinfoByProvider(row);
				int res1 =providerBaseinfoDao.updateDetailedInfoByBaseId(row);
				if(res<1 || res1< 1){
					throw new SQLException("信息变更失败");
				}
			}
		}
	}
}
