package com.fadl.inspection.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaQc;
import com.fadl.inspection.entity.TestConfigInfo;

/**
 * <p>
 * 检验配置记录表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
public interface TestConfigInfoService extends IService<TestConfigInfo> {

	/**
	 * 根据ID查找info信息,包含对象信息
	 * @param id
	 * @return
	 */
	TestConfigInfo queryById(Long id);
	
	/**根据检测配置ID获取info列表 
	 * 
	 * @param tcId
	 * @return
	 */
	Page<DataRow> queryByTCId(Page<DataRow> page, Long tcId);
	
	/**
	 * 根据ID进行逻辑删除
	 * @param id
	 */
	public void deleteByIdWithDelFlag(Long id);
	
	/**
	 * 查询检验项目配置列表 
	 * @return
	 */
	Page<DataRow> queryTestConfigInfoListByTcid(Page<DataRow> page, Long tcid);
	
	/**
	 * 根据模板Id查询对应的配置详情信息
	 * @param etId
	 * @return
	 */
	List<DataRow> queryByEtId (Long etId);
	/**
	 * 根据检验配置详情中的检测项目得到具体的试剂信息和检测方法 
	 * @param map key endTime 必填
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> querySuppliesInfoByProjectNameLable(HashMap<String, String> map)throws Exception;
	
	/**
	 * 根据Config中的type和Lable查找合适的记录,并返回第一条
	 * @param projectLable
	 * @param methodLable
	 * @return
	 */
	TestConfigInfo getByLableAndType(String projectLable, String methodLable) throws Exception;
	
	
	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param tci
	 * @return
	 */
	List<DataRow> querySuppliesListByProjectId(TestConfigInfo tci);
}
