package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneAssaySetting;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫化验效价值设置 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-08-25
 */
public interface ImmuneAssaySettingService extends IService<ImmuneAssaySetting> {
	/**
	 * 效价值列表
	 * @param pageing
	 * @param map
	 * @return
	 */
	public void AssaySettingList(Map<String,String> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 新增效价值设置
	 * @param im
	 * @return
	 */
	public void addAssaySetting(ImmuneAssaySetting immune,DataRow messageMap)throws Exception;
	/**
	 * 修改效价值设置
	 * @param im
	 * @return
	 */
	public void updateAssaySetting(ImmuneAssaySetting immune,DataRow messageMap)throws Exception;
	/**
	 * 删除效价值设置
	 * @param id
	 * @return
	 */
	public void deleteAssaySetting(Long ids,DataRow messageMap)throws Exception;
}
