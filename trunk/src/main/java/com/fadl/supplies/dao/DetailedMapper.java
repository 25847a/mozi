package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Detailed;

/**
 * <p>
  * 耗材模板明细 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
public interface DetailedMapper extends BaseMapper<Detailed> {
	/**
	 * 查询详情页面列表
	 * @param rowBounds
	 * @param map
	 * @throws Exception
	 */
	public List<DataRow> queryTemplateDatelis(Pagination pagination,String templateId)throws Exception;
	/**
	 * 通过查询耗材明细减掉流水表的数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryDetailed(Long templateId)throws Exception;
}