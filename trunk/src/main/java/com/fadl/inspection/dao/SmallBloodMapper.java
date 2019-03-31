package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.SmallBlood;

/**
 * <p>
 * 检验-采小血 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface SmallBloodMapper extends BaseMapper<SmallBlood> {

	
	List<DataRow> queryListByCreateDateAndIsCollection(Pagination page, SmallBlood blood);
	/**
	 * 根据日期,浆员卡号查询查询采小血的id,采浆次数(啊健)
	 * @param blood
	 * @return
	 */
	DataRow querySmallBloodHeadInfo(SmallBlood blood);
	
	DataRow querySmallBloodByEntity(Long id);
	
	void updateWithCollection(SmallBlood blood);
	
	
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate);
}
