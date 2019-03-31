package com.fadl.elisa.dao;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaTemplateSupplies;
import com.fadl.supplies.entity.Stream;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 酶标板模板使用耗材 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaTemplateSuppliesMapper extends BaseMapper<ElisaTemplateSupplies> {

	/**
	 * 根据条件查询可使用的耗材记录 
	 * @param stream
	 * @return
	 */
	DataRow querySuppliesStreamByCondition(Stream stream);
}
