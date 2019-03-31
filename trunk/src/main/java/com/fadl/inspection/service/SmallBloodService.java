package com.fadl.inspection.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.SmallBlood;

/**
 * <p>
 * 检验-采小血 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface SmallBloodService extends IService<SmallBlood> {
	/**
	 * 获取血浆采集记录,带分页和日期查询
	 * @param page
	 * @param collection
	 * @return
	 */
	Page<DataRow> queryListByCreateDateAndIsCollection(Page<DataRow> page, SmallBlood blood) throws Exception;
	/**
	 * 根据日期,浆员卡号查询查询采小血的id,采浆次数(啊健)
	 * @param blood
	 * @return
	 */
	public void querySmallBloodHeadInfo(SmallBlood blood,DataRow messageMap);
	/**
	 * 根据ID查找实体的方法,方法会返回浆员的部分个人信息
	 * @param ID
	 * @return
	 */
	DataRow querySmallBloodByEntity(Long id);
	
	/**
	 * 根據ID更新是否采小血
	 * @param collection
	 */
	void updateWithCollection(SmallBlood blood,DataRow messageMap)throws Exception;
	/**
	 * 取消小采
	 * @param blood
	 * @param messageMap
	 * @throws Exception
	 */
	void cacalSmallBlood(SmallBlood blood,DataRow messageMap) throws Exception;
	
	/**
	 * 根据实体集合更新采小血属性
	 *
	 * @param smallBloods
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	boolean updateBatchEntityByIds(List<SmallBlood> smallBloods, DataRow dataRow) throws Exception;
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate)throws Exception;
}
