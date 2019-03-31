package com.fadl.elisa.service;

import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaTemplate;
import com.fadl.inspection.entity.TestConfigInfo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 酶标板检测信息 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaInfoService extends IService<ElisaInfo> {
	
	/**
	 * 根据创建时间找到最大序号
	 * @param createDate
	 * @return
	 */
	String getMaxSeqNumberByCreateDate(String createDate);
	/**
	 * 根据条件查询酶标板读板信息
	 * @param page
	 * @param elisaInfo
	 * @return
	 */
	Page<DataRow> selectInfoByCondition(Page<DataRow> page, ElisaInfo elisaInfo);
	
	/**
	 *  返回当天的所有的全序号
	 * @return
	 */
	List<String> getAllSequenceNumber();
	/**
	 * 根据ID删除记录,包含孔位信息的删除
	 * @param id
	 * @return
	 */
	boolean delById(Long id) throws Exception;
	/**
	 * 保存酶标仪检测记录
	 * @param elisaInfo
	 * @param doInsertQC 是否保存质控记录
	 * @return
	 * @throws Exception
	 */
	boolean insertAll(ElisaInfo elisaInfo, Boolean doInsertQC, Boolean isElisa) throws Exception;
	
	/**
	 * 保存酶标读板信息 
	 * @param map
	 * @param tci
	 * @param template
	 * @param values
	 * @param messageMap
	 * @throws Exception
	 */
	void doInsertEi(Map<String,String> map, TestConfigInfo tci, ElisaTemplate template, String[] values, DataRow messageMap) throws Exception;
	
}
