package com.plasma.buss.plasma.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plasma.buss.lock.service.PasswordLockService;
import com.plasma.buss.plasma.dao.ProviderBaseinfoDao;
import com.plasma.buss.plasma.dao.ProviderBaseinfoTempDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.SocketUtil;
import com.plasma.common.StringHelper;
import com.plasma.common.base.PageBean;

/**
 * 浆员信息管理
 * @author fadl
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProviderBaseinfoService {
	
	@Autowired
	public ProviderBaseinfoDao providerBaseinfoDao;
	@Autowired
	public ProviderBaseinfoTempDao providerBaseinfoTempDao;
	@Autowired
	public PasswordLockService passwordLockService;
	@Autowired
	public SocketUtil socketUtil;

	/**
	 * 查询未审核浆员列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryProviderBaseinfoList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = providerBaseinfoDao.queryProviderBaseinfoList(dr);
		int totalNum = providerBaseinfoDao.queryProviderBaseinfoListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	
	/**
	 * 批量更新浆员信息
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor=Exception.class)
	public void examinePlasma(DataRow data,DataRow messageMap)throws Exception{
		//检测是否插入加密狗
		String hid= passwordLockService.returnLock(data.getString("ip"), data.getInt("port"), messageMap);
		if(StringHelper.isEmpty(hid)){
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "请接入加密锁");
			return;
		}
		//根据hid找到对应的加密狗详细信息
		DataRow lockInfo=passwordLockService.queryHidExits(hid);
		if(lockInfo!=null){
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("checkData", lockInfo.getString("inputData"));
			map.put("UID", lockInfo.getString("uid"));
			map.put("HID", lockInfo.getString("hid"));
			//查看加密狗是否匹配
			socketUtil.readInfo(data.getString("ip"),data.getInt("port"),"softdogr",map,messageMap);
			if("-1".equals(messageMap.getString("error"))){
				String[] str = data.getString("id").split(",");
				List<String> list = Arrays.asList(str);
				int count=providerBaseinfoDao.queryBaseInfoCount(lockInfo.getString("plasmaId"), list);
				if(count>0){
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
					messageMap.put(IConstants.APP_RESULT_MSG, "该加密锁不允许审核此数据");
				}else{
					data.put("ids", list);
					data.put("plasmaId", lockInfo.get("plasmaId"));//只允许审核对应浆站的数据
					providerBaseinfoDao.examinePlasma(data);
					messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
					messageMap.put(IConstants.APP_RESULT_MSG,"审核成功");
				}
			}else{
				return;
			}
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "加密锁未在系统进行登记");
		}
		
	} 
	

	/**
	 * 查询浆员详细信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryDetail(Long id)throws SQLException{
		return providerBaseinfoDao.queryDetail(id);
	}
	
	/**
	 * 修改浆员状态为取消发卡
	 * @return
	 * @throws SQLException
	 */
	public int changeStatus(DataRow data)throws SQLException{
		return providerBaseinfoDao.changeStatus(data);
	}
	
	/**
	 * 批量取消发卡
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	@Transactional(rollbackFor=Exception.class)
	public int cancelExaminePlasma(DataRow data)throws SQLException{
		data.put("status", 0);
		int res = providerBaseinfoDao.updateProviderBaseinfoById(data);
		return res;
	}
	
	/**
	 * 查询浆员详细信息
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBaseInfo(DataRow data)throws SQLException{
		return providerBaseinfoDao.queryBaseInfo(data);
	}
	
	/**
	 * 查询浆员审核状态
	 * @return
	 * @throws SQLException
	 */
	public String queryProviderStatus(DataRow data)throws SQLException{
		return providerBaseinfoDao.queryProviderStatus(data);
	}
	
	/**
	 * 删除浆员信息
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deleteProviderInfo(DataRow data)throws SQLException{
		int res1 = providerBaseinfoDao.deleteDetailInfoByProvider(data);
		int res = providerBaseinfoDao.deleteProviderBaseInfoByProvider(data);
		if (res>0 && res1>0) {
			return 1;
		}else{
			throw new SQLException();
		}
	}
}
