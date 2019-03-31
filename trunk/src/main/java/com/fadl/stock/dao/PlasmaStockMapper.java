package com.fadl.stock.dao;

import com.fadl.common.DataRow;
import com.fadl.stock.entity.PlasmaStock;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 血浆入库表 Mapper 接口
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
public interface PlasmaStockMapper extends BaseMapper<PlasmaStock> {

	/**
	 * 查询入库列表
	 * @param page
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaStockList(Pagination page,HashMap<String, String> map) throws SQLException;
	/**
	 * 根据 id 查询 箱号信息和浆量
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaStockById(Long id)throws SQLException;
	/**
	 * 入库查询浆员信息并打印条码
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> queryStockInfoAndPrint(String allId)throws SQLException;
	/**
	 * 浆库高级查询
	 * @param page
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySeniorStockList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 浆库高级查询
	 * @param page
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySeniorStockList(HashMap<String, String> map)throws SQLException;
	/**
	 * 根据箱号查询血浆详情 
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaStockListByBoxId(String boxId)throws SQLException;
	/**
	 * 打印装箱条码
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String,String> queryPlasmaStockByBoxId(String boxId)throws SQLException;
	/**
	 * 打印装箱清单（根据箱子编号查询 箱子血浆信息）
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryBoxPlasmaInfo(HashMap<String, String> map)throws SQLException;
	/**
	 * 查询装箱清单 试剂信息 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryBoxPlasmaInfoReagents(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 根据检验配置详情中的检测项目得到具体的试剂信息和检测方法 
	 * @param map key endTime 必填
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySuppliesInfoByProjectNameLable(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询血浆信息
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaStockInfoByAllId(PlasmaStock plasmaStock)throws SQLException;
	
	/**
	 * 查询出库信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryOutPlasmaStock(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询出库信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryOutPlasmaStock(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 批量送浆
	 * @return
	 * @throws SQLException
	 */
	public int updateOutPlasmaStockAllStatus(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 报废未出库的血浆
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryScrapPlasmaList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 批量报废血浆
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updatePlasmaScrapList(HashMap<String, Object> map)throws SQLException;
	
	/**
	 * 固定浆员留样查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryReturnSimpleList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 固定浆员留样查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryReturnSimpleList(HashMap<String, String> map)throws SQLException;
	
	
	
	/******************************    导出浆站数据到公司    ***************************************************/
	/**
	 * 导出浆员信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportPlasmaDataInfo(HashMap<String, String> map)throws SQLException;
	/**
	 * 导出箱子信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportBoxDataInfo(HashMap<String, String> map)throws SQLException;
	/**
	 * 采浆记录
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportCollectionDataInfo(HashMap<String, String> map)throws SQLException;
	/**
	 * 拒绝信息
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> queryRefuseDataInfo(HashMap<String, String> map) throws SQLException;
	/**
	 * 导出浆员免疫信息表
	 * @param map
	 * @return
	 */
	public List<HashMap<String, String>> exportPlasmaStockImmune(HashMap<String,String> map)throws SQLException;
	/**
	 * 导出浆员免疫化验记录表
	 * @param map
	 * @return
	 */
	public List<HashMap<String, String>> queryTmAssayInfo(HashMap<String,String> map)throws SQLException;
	/******************************    导出浆站数据到公司    ***************************************************/

	/**
	 * 血浆装箱单
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaBoxList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 血浆检测装运表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaCheckList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 血浆装箱汇总表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaBoxSummaryList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 批量送浆
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updatePulpingList(HashMap<String, Object> map)throws SQLException;
	
	/**
	 * 取消送浆
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int cacalPulpingList(HashMap<String, Object> map)throws SQLException;
}
