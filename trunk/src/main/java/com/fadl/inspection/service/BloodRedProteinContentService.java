package com.fadl.inspection.service;

import com.fadl.common.DataRow;
import com.fadl.inspection.entity.BloodRedProteinContent;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 检验-血红蛋白含量表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface BloodRedProteinContentService extends IService<BloodRedProteinContent> {

	/**
	 * 根据更新时间查找所有的血红蛋白含量检测的记录带分页
	 * @param page
	 * @param bloodRedProteinContent
	 * @return
	 */
	Page<DataRow> queryListByUpdateDate(Page<DataRow> page, BloodRedProteinContent bloodRedProteinContent)throws Exception;
	
	
	/**
	 * 根据ID查找对应的血红蛋白含量检测信息,带浆员个人信息
	 * @param id
	 * @return
	 */
	DataRow queryBloodRedProteinContentById(Long id);
	
	/**
	 * 根据ID更新血红蛋白含量检测信息.会在血红蛋白检测表中添加一条记录
	 * (如果存在则更新记录,存在多条则报错.)
	 * @param content
	 * @param dataRow
	 */
	void updateInfoById(BloodRedProteinContent content, DataRow dataRow) throws Exception; 
	
	/**
	 * 根据选择的日期获取当天做了检测的所有的浆员信息,供打印使用
	 * @param chooseDate
	 * @return
	 */
	List<DataRow> queryInfosByChooseDate(String chooseDate);
}
