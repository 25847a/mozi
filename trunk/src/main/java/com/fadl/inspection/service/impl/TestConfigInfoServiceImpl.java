package com.fadl.inspection.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.inspection.dao.TestConfigInfoMapper;
import com.fadl.inspection.entity.TestConfigInfo;
import com.fadl.inspection.service.TestConfigInfoService;
import com.fadl.log.service.LogService;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 检验配置记录表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-05
 */
@Service
public class TestConfigInfoServiceImpl extends ServiceImpl<TestConfigInfoMapper, TestConfigInfo>
		implements TestConfigInfoService {

	@Autowired
	private ConfigService cService;
	@Autowired
	public LogService logService;
	@Autowired
	private PlasmaStockService psService;
	/**
	 * 根据ID查找info信息,包含对象信息
	 * 
	 * @param id
	 * @return
	 */
	public TestConfigInfo queryById(Long id) {
		return baseMapper.queryById(id);
	};

	/**
	 * 根据检测配置ID获取info列表
	 * 
	 * @param tcId
	 * @return
	 */
	public Page<DataRow> queryByTCId(Page<DataRow> page, Long tcId) {
		return page.setRecords(baseMapper.queryByTCId(page, tcId));
	};

	/**
	 * 根据ID进行逻辑删除
	 * 
	 * @param id
	 */
	public void deleteByIdWithDelFlag(Long id) {
		baseMapper.deleteByIdWithDelFlag(id);
		try {
			logService.insertLog(IConstants.MODULE_TCI, IConstants.DESC.replace("{0}", "删除了检验配置详情,ID为"+id),"null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据检验配置详情中的检测项目得到具体的试剂信息和检测方法 
	 * @param map key endTime 必填
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> querySuppliesInfoByProjectNameLable(HashMap<String, String> map)throws Exception{
		if(StringUtils.isEmpty(map.get("endTime"))) {
			throw new Exception("参数endTime为空");
		}
		return psService.querySuppliesInfoByProjectNameLable(map);
	}
	/**
	 * 查询检验项目配置列表
	 */
	@Override
	public Page<DataRow> queryTestConfigInfoListByTcid(Page<DataRow> page, Long tcid) {
		return page.setRecords(baseMapper.queryTestConfigInfoListByTcid(page, tcid));
	}

	@Override
	public List<DataRow> queryByEtId(Long etId) {
		return baseMapper.queryByEtId(etId);
	}

	@Override
	public TestConfigInfo getByLableAndType(String projectLable, String methodLable) throws Exception {

		// 检查检验配置中硫酸铜检验配置是否有效
		Config config = cService.getConfig("check_method", "比重法");
		Config config1 = cService.getConfig("elisa_check_project", "全血比重");
		if (config == null) {
			throw new Exception("没有配置检验方法");
		}
		if (config1 == null) {
			throw new Exception("没有配置检验项目");
		}
		// 科学的方式 找出来数据字典中定义的和血红蛋白含量有关的信息
		Wrapper<TestConfigInfo> ew = new EntityWrapper<TestConfigInfo>();
		ew.eq("projectName", config1.getValue());
		ew.eq("testingMethodid", config.getValue());
		ew.le("startDate", DateUtil.getSystemDate(null));
		ew.ge("endTime", DateUtil.getSystemDate(null));
		// 设置项目为血红蛋白含量
		TestConfigInfo tci = this.selectOne(ew);
		if (tci == null) {
			throw new Exception("没有检验配置信息");
		}
		return tci;
	}
	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param tci
	 * @return
	 */
	@Override
	public List<DataRow> querySuppliesListByProjectId(TestConfigInfo tci) {
		return baseMapper.querySuppliesListByProjectId(tci);
	}
}
