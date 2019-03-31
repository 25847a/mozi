package com.fadl.check.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.check.entity.Check;
import com.fadl.common.DataRow;

/**
 * <p>
  * 体检记录表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2017-02-13
 */
public interface CheckMapper extends BaseMapper<Check> {

	/**
	 * 查询未体检/体检人员
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckList(Pagination page,HashMap<String, String> map)throws SQLException;
	/**
	 * 查询体检头部信息 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow queryCheckHeadInfo(Map<String,String> map)throws Exception;
	
	/**
	 * 献浆员体检高级查询 
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckQueryList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 献浆员体检高级查询 
	 * @param map
	 * @param rowBounds
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckQueryList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询上次体检记录
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPrevCheckInfo(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 同步数据到叫号系统
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCallDataList(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 打印体检记录
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryCheckRecordList(HashMap<String, String> map)throws SQLException;
}