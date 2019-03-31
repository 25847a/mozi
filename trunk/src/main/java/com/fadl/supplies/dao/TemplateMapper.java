package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Template;

/**
 * <p>
  * 耗材模板配置 Mapper 接口
 * </p>
 * @author 啊健
 * @since 2018-04-23
 */
public interface TemplateMapper extends BaseMapper<Template> {
	/**
	 * 查询耗材模板配置列表
	 * @param rowBounds
	 * @param map
	 * @throws Exception
	 */
	public List<DataRow> queryTemplateList(Pagination pagination,Map<String,String> map)throws Exception;
	/**
	 * 添加耗材模板的耗材列表
	 * @param rowBounds
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryTemplateSuppliesInfo(Pagination pagination,Map<String,Object> map)throws Exception;
	/**
	 * 采浆打印需要查询耗材编号
	 * @param suppliesId
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryDetailedCollection(Integer suppliesId)throws Exception;
}