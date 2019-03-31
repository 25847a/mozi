package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Supply;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材供应商设置 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
public interface SupplyService extends IService<Supply> {
	
	/**
	 * 新增供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	public void insertSuppliesSupply(Supply suppliesSupply,DataRow messageMap)throws Exception;
	/**
	 * 修改供应商信息
	 * @param suppliesSupply
	 * @return
	 */
	public void updateSuppliesSupply(Supply suppliesSupply,DataRow messageMap)throws Exception;
	/**
	 * 删除供应商信息
	 * @param id
	 * @return
	 */
	public void deleteSuppliesSupply(Long ids,DataRow messageMap)throws Exception;
}
