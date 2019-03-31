package com.fadl.supplies.dao;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Imexport;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 库存进出表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
public interface ImexportMapper extends BaseMapper<Imexport> {
	/**
	 * 查询出入库流水表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<DataRow> queryUsedList(Map<String,Object>map,Pagination pagination)throws Exception;
}
