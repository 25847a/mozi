package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Return;

/**
 * <p>
  * 耗材退还记录表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface ReturnMapper extends BaseMapper<Return> {
	/**
	 * 查询退还列表 
	 * @param pagination
	 * @param retur
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryReturnList(Pagination pagination,Return retur)throws Exception;
	/**
	 * 查询退还列表 
	 * @param pagination
	 * @param retur
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryReturnDatelis(Pagination pagination,Map<String,String> map)throws Exception;
}