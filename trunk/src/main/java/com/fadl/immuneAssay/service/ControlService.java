package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.Control;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫针次控制设置————控制名称表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-16
 */
public interface ControlService extends IService<Control> {
	/**
	 * 新增控制名称表
	 * @param control
	 * @param messageMap
	 * @return
	 */
	public void insertControl(Control control,DataRow messageMap)throws Exception;
	/**
	 * 修改控制名称表
	 * @param control
	 * @param messageMap
	 */
	public void updateControl(Control control,DataRow messageMap)throws Exception;
	/**
	 * 删除控制名称表
	 * @param id
	 * @return
	 */
	public void deleteControl(Long id,DataRow messageMap)throws Exception;
}
