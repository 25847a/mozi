package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.TestConfigInfo;

/**
 * <p>
 * 检验配置记录表 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
public interface TestConfigInfoMapper extends BaseMapper<TestConfigInfo> {
	
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
	List<DataRow> queryByTCId(Pagination page, Long tcId);
	
	/**
	 * 根据ID逻辑删除
	 * @param id
	 */
	void deleteByIdWithDelFlag(Long id);
	/**
	 * 查询检验项目配置列表 
	 * @return
	 */
	List<DataRow> queryTestConfigInfoListByTcid(Pagination page, Long tcid);
	
	/**
	 * 根据模板Id查询对应的配置详情信息
	 * @param etId
	 * @return
	 */
	List<DataRow> queryByEtId (Long etId);
	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param info
	 * @return
	 */
	List<DataRow> querySuppliesListByProjectId(TestConfigInfo info);
}
