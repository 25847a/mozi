package com.fadl.log.dao;

import java.util.List;
import java.util.Map;

import com.fadl.common.DataRow;
import com.fadl.log.entity.Log;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wj
 * @since 2018-07-20
 */
public interface LogMapper extends BaseMapper<Log> {
	
	/**
	 * 查询用户操作日志
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryLog(Map<String,Object>map,Pagination pagination)throws Exception;
}
