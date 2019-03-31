package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Outstock;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 出库表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-05-28
 */
public interface OutstockService extends IService<Outstock> {
	/**
	 * 出库列表
	 * @param pageing
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void queryOutstockList(Page<DataRow> pageing,Map<String,Object> map)throws Exception;
	/**
	 * 插入出库表
	 * @param map
	 * @param messageMap
	 * @throws Exception
	 */
	public void insertOutstock(Map<String,String> map,DataRow messageMap)throws Exception;
	/**
	 * 提供给免疫查询的列表
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryVaccineList()throws Exception;
	/**
	 * 提供给谢鑫检验查询
	 * @param map
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public void queryTestList(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 提供给谢鑫检验查询2
	 * @param map
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public void queryTestPage(Map<String,String> map,DataRow messageMap)throws Exception;
	
	/**
	 * 根据ID查询信息,包含有库存表的信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DataRow queryById(Long id)throws Exception;
	/**
	 * 查询免疫查询的批号列表
	 * @param map
	 * @throws Exception
	 */
	public void queryVaccineBatchNumber(Map<String,String> map,DataRow messageMap)throws Exception;
}
