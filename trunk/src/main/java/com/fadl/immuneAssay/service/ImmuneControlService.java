package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneControl;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫针次控制设置 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-17
 */
public interface ImmuneControlService extends IService<ImmuneControl> {
	/**
	 * 免疫针次控制设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public void immuneControlList(Page<DataRow> page,Map<String,String> map)throws Exception;
	/**
	 * 新增免疫针次控制设置
	 * @param vaccine
	 * @return
	 */
	public void insertimmuneControl(ImmuneControl immune,DataRow messageMap)throws Exception;
	/**
	 * 修改免疫针次控制设置
	 * @param im
	 * @return
	 */
	public void updateimmuneControl(ImmuneControl immune,DataRow messageMap)throws Exception;
	/**
	 * 删除免疫针次控制设置
	 * @param id
	 * @return
	 */
	public void deleteimmuneControl(Long id,DataRow messageMap)throws Exception;
}
