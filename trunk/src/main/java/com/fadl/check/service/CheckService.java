package com.fadl.check.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.check.entity.Check;
import com.fadl.common.DataRow;
import com.fadl.registries.entity.ProviderRegistries;

/**
 * <p>
 * 体检记录表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2017-02-13
 */
public interface CheckService extends IService<Check> {
	
	/**
	 * 查询未体检/体检人员
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryCheckList(HashMap<String, String> map, Page<DataRow> page) throws Exception;
	/**
	 * 查询体检头部信息 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void queryCheckHeadInfo(Map<String,String> map,DataRow messageMap)throws Exception;
	
	/**
	 * 更新体检信息并插入标本采集或血红蛋白含量
	 * @param check
	 * @return
	 * @throws SQLException
	 */
	public void updateCheck(Check check,Integer isRoadFee,DataRow messageMap)throws Exception;
	
	/**
	 * 献浆员体检高级查询 
	 * @param map
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryCheckQueryList(HashMap<String, String> map, Page<DataRow> page)throws Exception;
	
	/**
	 * 献浆员体检高级查询 
	 * @param map
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckQueryList(HashMap<String, String> map)throws Exception;
	
	/**
	 * 取消体检
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void cacalCheckInfo(Long id,DataRow messageMap)throws Exception;
	
	/**
	 * 查询上次体检记录
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPrevCheckInfo(HashMap<String, String> map)throws Exception;
	/**
	 * 根据浆员类型判断浆员走哪个流程
	 * @param c
	 * @param providerRegistries
	 * @param messageMap
	 * @throws Exception
	 */
	public void nextProcedure(Check c,ProviderRegistries providerRegistries,DataRow messageMap) throws Exception;
	
	/**
	 * 同步数据到叫号系统
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCallDataList(HashMap<String, String> map)throws Exception;
	
	/**
	 * 重检
	 */
	public void restCheck(Check check,DataRow messageMap)throws Exception;
	/**
	 * 打印体检记录
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckRecordList(HashMap<String, String> map)throws SQLException;
}
