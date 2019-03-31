package com.fadl.collectionConfig.service;
import com.fadl.collectionConfig.entity.SituationConfig;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 采浆情况设置表 服务类
 * </p>
 * @author caijian
 * @since 2018-04-21
 */
public interface SituationConfigService extends IService<SituationConfig> {
	/**
	 * 新增采浆情况设置表
	 * @param situationConfig
	 * @return
	 */
	public void insertSituationconfig(SituationConfig situationConfig,DataRow messageMap)throws Exception;
	/**
	 * 修改采浆情况设置表
	 * @param situationConfig
	 * @return
	 */
	public void updateSituationconfig(SituationConfig situationConfig,DataRow messageMap) throws Exception;
}
