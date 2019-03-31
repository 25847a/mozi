package com.fadl.elisa.service;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaTemplate;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 酶标板模板表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaTemplateService extends IService<ElisaTemplate> {

	/**
	 * 保存酶标板模板信息,级联保存其它关联信息
	 * @param elisaTemplate
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	boolean insertTemplate(ElisaTemplate elisaTemplate, DataRow dataRow) throws Exception;
	
	/**
	 * 修改酶标板模板信息,级联保存其它关联信息
	 * @param elisaTemplate
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	boolean updateTemplate(ElisaTemplate elisaTemplate, DataRow dataRow) throws Exception;
	
	/**
	 * 根据ID查找酶标仪模板设置信息,包含API设置
	 * @param id
	 * @return
	 */
	ElisaTemplate selectInfoAndApiById(Long id);
}
