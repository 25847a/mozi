package com.fadl.rabatUnit.service;

import com.fadl.common.DataRow;
import com.fadl.rabatUnit.entity.RabatUnit;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 胸片检查单位表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
public interface RabatUnitService extends IService<RabatUnit> {

	/**
	 * 添加胸片
	 * @throws Exception
	 */
	public void addRabatUnit(RabatUnit rabatUnit,DataRow messageMap)throws Exception;
	/**
	 * 修改胸片
	 * @param rabatUnit
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateRabatUnit(RabatUnit rabatUnit,DataRow messageMap)throws Exception;
}
