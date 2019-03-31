package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.SupplyMapper;
import com.fadl.supplies.entity.Supply;
import com.fadl.supplies.service.SupplyService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 耗材供应商设置 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {

	@Autowired
	SupplyMapper suppliesSupplyMapper;
	/**
	 * 新增供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	@Override
	public void insertSuppliesSupply(Supply suppliesSupply,DataRow messageMap) throws Exception {
		int row = suppliesSupplyMapper.insert(suppliesSupply);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	@Override
	public void updateSuppliesSupply(Supply suppliesSupply,DataRow messageMap) throws Exception {
		int row = suppliesSupplyMapper.updateById(suppliesSupply);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除供应商信息
	 * @param id
	 * @return
	 */
	@Override
	public void deleteSuppliesSupply(Long ids,DataRow messageMap) throws Exception {
		EntityWrapper<Supply> ew = new EntityWrapper<Supply>();
		Supply supply= new Supply();
		supply.setIsDelete(1);
		ew.eq("id", ids);
		int row = suppliesSupplyMapper.update(supply,ew);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	
}
