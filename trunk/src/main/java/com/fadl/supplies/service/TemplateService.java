package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Template;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材模板配置 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
public interface TemplateService extends IService<Template> {
	/**
	 * 查询耗材模板配置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<DataRow> queryTemplateList(Page<DataRow> pageing,Map<String,String> map)throws Exception;
	/**
	 * 添加耗材模板的耗材列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public void queryTemplateSuppliesInfo(Map<String, Object> map, Page<DataRow> pageing) throws Exception;
	/**
	 * 新增耗材模板配置
	 * @param Template
	 * @return
	 */
	public void insertTemplate(Map<String,String> map,DataRow messageMap)throws Exception;
	/**
	 * 修改耗材模板配置
	 * @param id
	 * @return
	 */
	public void updateTemplate(Template template,DataRow messageMap)throws Exception;
	/**
	 * 删除耗材模板配置
	 * @param id
	 * @return
	 */
	public void deleteTemplate(Template template,DataRow messageMap)throws Exception;
	/**
	 * 采浆打印需要查询耗材编号
	 * @param suppliesId
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryDetailedCollection(Integer suppliesId)throws Exception;
}
