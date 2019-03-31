package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Unit;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材单位 服务类
 * </p>
 *
 * @author caijian
 * @since 2018-04-23
 */
public interface UnitService extends IService<Unit> {
	/**
	 * 新增耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	public void insertUnit(Unit unit,DataRow messageMap)throws Exception;
	
	/**
	 * 修改耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	public void updateUnit(Unit unit,DataRow messageMap)throws Exception;
	/**
	 * 删除耗材单位信息
	 * @param id
	 * @return
	 */
	public void deleteUnit(Long id,DataRow messageMap)throws Exception;
}
