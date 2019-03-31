package com.fadl.supplies.dao;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Stream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 流水表(又称临时表) Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
public interface StreamMapper extends BaseMapper<Stream> {
	
	/**
	 * 使用耗材减少
	 * @param map
	 * @throws Exception
	 */
	public Integer updateStream(Map<String,String> map)throws SQLException;
	/**
	 * 查询剩余耗材数量
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int queryStreamNum(Map<String,String> map)throws SQLException;
	/**
	 * 如果流水表存在同等批号和耗材ID的数据，叠加 
	 * @param map
	 * @throws Exception
	 */
	public void AddStreamNum(@Param(value = "sum")Long sum,@Param(value = "batchNumber")String batchNumber,@Param(value = "suppliesId")Long suppliesId)throws SQLException;
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryStreamInfo(Pagination page)throws Exception;
}
