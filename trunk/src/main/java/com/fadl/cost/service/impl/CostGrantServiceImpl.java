package com.fadl.cost.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.cost.dao.ICostGrantMapper;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.immuneAssay.service.ImmuneService;
import com.fadl.log.service.LogService;
import com.fadl.registries.dao.ProviderRegistriesMapper;

/**
 * <p>费用发放记录service实现类</p>
 * 
 * @author zhanll
 * @since 2018-5-5
 * */
@Service
public class CostGrantServiceImpl extends ServiceImpl<ICostGrantMapper, CostGrant> implements ICostGrantService{
	@Autowired
	private ICostGrantMapper iCostGrantMapper; 
	
	@Autowired
	public PlasmaImageService plasmaImageService;
	@Autowired
	ProviderRegistriesMapper providerRegistriesMapper;
	@Autowired
	ImmuneService immuneService;
	@Autowired
	ConfigService configService;
	@Autowired
	LogService logService;
	
	/**
	 * 查询费用发放记录（分页）
	 */
	public List<CostGrant> getCostGrant(Integer page,Integer limit) throws Exception {
		EntityWrapper<CostGrant> wrapper = new EntityWrapper<CostGrant>();
		Page<CostGrant> paging = new Page<>(page,limit);
		List<CostGrant> dataList = iCostGrantMapper.selectPage(paging, wrapper);
		return dataList;
	}
	
	/**
	 * 浆员费用信息列表（未发放）
	 */
	public Page<DataRow> queryNotGrantList(CostGrant cost,Page<DataRow> page) throws Exception {
		/*RowBounds rowBounds = new RowBounds((page.getCurrent()-1)*page.getSize(),page.getSize());
		List<HashMap<String, Object>> list = iCostGrantMapper.queryPlasmaCostList(createDate,rowBounds);
		page.setRecords(list);
		int count = this.queryPlasmaCostListCount(createDate);
		page.setTotal(count);*/
		return page.setRecords(iCostGrantMapper.queryNotGrantList(cost,page));
	}
	
	/**
	 * 浆员费用信息列表（已发放）
	 * */
	public Page<DataRow> queryGrantList(String createDate,Page<DataRow> page) throws Exception {
		return page.setRecords(iCostGrantMapper.queryGrantList(createDate,page));
	}
	
	/**
	 * 根据providerNo相关条件查询费用发放信息
	 * */
	public List<DataRow> getCostGrantDetailList(String param) throws Exception {
		
		return iCostGrantMapper.getCostGrantDetailList(param);
	}
	
	/**
	 * 费用发放首页
	 * */
	public Map<String, Object> queryCostHead(Map<String, Object> map) throws Exception {
		map.put("year",DateUtil.sdy.format(new Date()));
		map.put("month",DateUtil.sdm.format(new Date()));
		DataRow data = providerRegistriesMapper.queryCollectionCount(map);
		Map<String, Object> result = iCostGrantMapper.queryCostHead(map);
		if(null!=result){
			result.put("year", data.getString("year"));
			result.put("month", data.getString("month"));
			return result;
		}
		return null;
	}
	
	/**
	 * 费用详情查询
	 * */
	public List<DataRow> costDetail(Long id) throws Exception {
		return iCostGrantMapper.costDetail(id);
	}
	
	/**
	 * 修改发放状态(取消发放--发放)
	 * */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public int updateStatusTo1(CostGrant costGrant,String image,DataRow messageMap) throws Exception {
		//因为是属于费用发放，所以类型为4
		costGrant.setIsGrant(1);
		if(!StringHelper.isEmpty(image)){
			plasmaImageService.saveImage(image, 4, costGrant.getId(), messageMap);
		}
		//插入操作日志
		logService.insertLog(IConstants.GRANT_COST, IConstants.DESC.replace("{0}", "已发放该浆员费用"+costGrant.getMoney()),costGrant.getProviderNo());
		return iCostGrantMapper.updateGrandCostStatus(costGrant);
	}
	/**
	 * 费用发放验证是否已经进行免疫流程
	 * @param providerNo
	 * @return
	 */
	public void costImmunity(String providerNo,DataRow messageMap)throws Exception{
		Config con = configService.queryConfig("immune_type", "1");
		if(con.getIsDisable()==1){//如果禁用，执行莱士的
			immuneService.immuneRegisterAlone(providerNo, messageMap);
		}else{
			immuneService.immuneRegister(providerNo,messageMap);
		}
	}
	/**
	 * 修改发放状态(发放--取消发放)
	 * */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public int updateStatusTo0(CostGrant costGrant,DataRow messageMap) throws Exception {
		plasmaImageService.deletePlasmaImage(costGrant.getId(), 4);
		return iCostGrantMapper.updateCostGrantById(costGrant.getId());
	}
	
	/**
	 * 费用发放查询
	 * */
	public Page<DataRow> queryCostGrantList(Map<String, String> map,Page<DataRow> page) throws Exception {
		map.put("pageNum", ((page.getCurrent() - 1) * page.getSize())+"");
		map.put("pageSize", page.getSize()+"");
		return page.setRecords(iCostGrantMapper.queryCostGrantList(map,page));
	}
	
	/**
	 * 导出数据Excel
	 * */
	public List<DataRow> exportCostGrant(Map<String, String> map) throws Exception {
		
		return iCostGrantMapper.queryCostGrantList(map);
	}

	/**
	 * 批量插入费用中间表 
	 */
	@Override
	public void insertActivityCostDetail(List<DataRow> list) throws Exception {
		iCostGrantMapper.insertActivityCostDetail(list);
	}

	/**
	 * 打印费用详情小票 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> printGrantCost(Long id) throws Exception {
		// TODO Auto-generated method stub
		return iCostGrantMapper.printGrantCost(id);
	}

}
