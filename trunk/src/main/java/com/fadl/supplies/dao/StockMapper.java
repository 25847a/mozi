package com.fadl.supplies.dao;
 
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.supplies.entity.Stock;

/**
 * <p>
  * 耗材库存记录 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface StockMapper extends BaseMapper<Stock> {
	/**
	 * 获取订单入库可用状态下的拥有订单号的耗材(适用报损、退还、销毁、出库)
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<DataRow> querySuppliesStatus(Pagination page)throws Exception;
	/**
	 * 查询库房详情列表
	 * @param batchNumber
	 * @param pagination
	 * @return
	 */
	public List<DataRow> queryStockDatelis(String id,Pagination pagination)throws Exception;
	/**
	 * 退换、销毁、报废 使用耗材减少
	 * @param map
	 * @throws Exception
	 */
	public void updateStockNum(@Param(value = "num")Integer num,@Param(value = "id")String id,@Param(value = "suppliesId")Long suppliesId)throws SQLException;
	/**
	 * 库存报警列表 
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryPoliceList(Map<String,String> map,Pagination pagination)throws Exception;
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
	 * 盘点查询
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryPointList(Map<String,String> map,Pagination pagination)throws Exception;
	/**
	 * 谢鑫使用的SQL语句
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryOutNumberInfo(Pagination page)throws Exception;
	/**
	 * 根据ID查询耗材信息，包含其它表内容信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public DataRow queryById(Long id)throws Exception;
	
	/**
	 * 根据条件查找质控品的批号
	 * @param elisaQc
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryBatchNumberByQC(ElisaQc elisaQc)throws Exception;
}