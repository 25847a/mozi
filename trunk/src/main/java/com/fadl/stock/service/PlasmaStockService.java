package com.fadl.stock.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.stock.entity.PlasmaStock;

/**
 * <p>
 * 血浆入库表 服务类
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
public interface PlasmaStockService extends IService<PlasmaStock> {

	/**
	 * 查询入库列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryPlasmaStockList(HashMap<String, String> map, Page<DataRow> page) throws Exception;
	
	/**
	 * 根据 id 查询 箱号信息和浆量
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaStockById(Long id)throws Exception;
	
	/**
	 * 血浆入库
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void addPlasmaStock(PlasmaStock slasmaStock,DataRow messageMap)throws Exception;
	
	/**
	 * 入库查询浆员信息并打印条码
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, String> queryStockInfoAndPrint(String allId)throws Exception;
	/**
	 * 浆库高级查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> querySeniorStockList(HashMap<String, String> map, Page<DataRow> page) throws Exception;
	/**
	 * 浆库高级查询
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> querySeniorStockList(HashMap<String, String> map)throws Exception;
	
	/**
	 * 根据箱号查询血浆详情 
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaStockListByBoxId(String boxId)throws Exception;
	/**
	 * 血浆报废
	 * @param id
	 * @param messageMap
	 */
	public void updatePlasmaStockScrap(PlasmaStock plasmaStock, DataRow messageMap)throws Exception;
	/**
	 * 取消入库
	 * @param plasmaStock
	 * @return
	 */
	public void updatePlasmaStockStatus(PlasmaStock plasmaStock,DataRow messageMap)throws Exception;
	
	/**
	 * 打印装箱条码
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String,String> queryPlasmaStockByBoxId(String boxId)throws Exception;
	
	/**
	 * 打印装箱清单（根据箱子编号查询 箱子血浆信息）
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryBoxPlasmaInfo(HashMap<String, String> map)throws Exception;
	
	/**
	 * 根据检验配置详情中的检测项目得到具体的试剂信息和检测方法 
	 * @param map key endTime 必填
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> querySuppliesInfoByProjectNameLable(HashMap<String, String> map)throws Exception;
	
	/**
	 * 浆库高级查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryOutPlasmaStock(HashMap<String, String> map, Page<DataRow> page) throws Exception;
	
	/**
	 * 浆库高级查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryOutPlasmaStock(HashMap<String, String> map) throws Exception;
	
	/**
	 * 查询装箱清单 试剂信息 
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryBoxPlasmaInfoReagents(HashMap<String, String> map)throws Exception;
	
	
	/**
	 * 报废未出库的血浆
	 * @return
	 * @throws SQLException
	 */
	public void queryScrapPlasmaList(Page<DataRow> page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 批量报废
	 * @param id
	 * @param messageMap
	 * @return
	 * @throws SQLException
	 */
	public void updatePlasmaScrapList(List<Long> id,DataRow messageMap)throws SQLException;
	
	/**
	 * 固定浆员留样查询
	 * @param map
	 * @param page
	 * @throws SQLException
	 */
	public void queryReturnSimpleList(HashMap<String, String> map, Page<DataRow> page)throws SQLException;
	
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
	public List<HashMap<String, String>> exportPlasmaDataInfo(HashMap<String, String> map)throws Exception;
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
