package com.fadl.inspection.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.DetectionProteins;

/**
 * <p>
 * 检验-血红蛋白检测 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface DetectionProteinsService extends IService<DetectionProteins> {

	/**
	 * 根据更新时间查找所有的血红蛋白含量检测的记录带分页
	 * @param page
	 * @param proteins
	 * @return
	 */
	Page<DataRow> queryListByUpdateDate(Page<DataRow> page, DetectionProteins proteins);
	
	
	/**
	 * 根据ID查找对应的血红蛋白检测信息,带浆员个人信息
	 * @param id
	 * @return
	 */
	DataRow queryDetectionProteinsById(Long id);
	
	
	/**
	 * 根据ID更新血红蛋白检测信息.会在蛋白含量表中添加一条记录
	 * (如果存在则更新记录,存在多条则报错.)
	 * @param proteins
	 * @param dataRow
	 */
	void updateInfoById(DetectionProteins proteins,  DataRow dataRow) throws Exception; 
	
	/**
	 * 根据选择的日期获取当天做了检测的所有的浆员信息,供打印使用
	 * @param chooseDate
	 * @return
	 */
	List<DataRow> queryInfosByChooseDate(String chooseDate);
}
