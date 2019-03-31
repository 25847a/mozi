package com.fadl.supplies.dao;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Outstock;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 出库表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-05-28
 */
public interface OutstockMapper extends BaseMapper<Outstock> {
	/**
	 * 出库列表
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<DataRow> queryoutstockList(Pagination pagination,Map<String,Object> map)throws Exception;
	/**
	 * 提供给免疫查询的列表
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryVaccineList()throws Exception;
	/**
	 * 提供给谢鑫检验查询 
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryTestList(Map<String,String> map,Pagination pagination)throws Exception;
	/**
	 * 提供给谢鑫检验查询2
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryTestPage(Map<String,String> map)throws Exception;
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
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryVaccineBatchNumber(Map<String,String> map)throws Exception;
}
