package com.fadl.electrophoresis.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.electrophoresis.dao.SerumElectrophoresisMapper;
import com.fadl.electrophoresis.entity.SerumElectrophoresis;
import com.fadl.electrophoresis.service.SerumElectrophoresisService;

/**
 * <p>
 *  血清电泳 服务实现类
 * </p>
 *
 * @author hu
 * @since 2018-06-06
 */
@Service
public class SerumElectrophoresisServiceImpl extends ServiceImpl<SerumElectrophoresisMapper, SerumElectrophoresis> implements SerumElectrophoresisService {

	@Autowired
	public SerumElectrophoresisMapper serumElectrophoresisMapper;
	@Autowired
	public ConfigService configService;
	
	/**
	 * 查询未体检/体检人员
	 */
	@Override
	public Page<DataRow> querySerumElectrophoresisList(HashMap<String, String> map, Page<DataRow> page)
			throws Exception {
		return page.setRecords(serumElectrophoresisMapper.querySerumElectrophoresisList(page, map));
	}

	/**
	 * 根据浆员卡号查询该浆员是否要做血清蛋白电泳检验 目前是超过1年就要做检验,新浆员也要化验
	 * @param providerNo
	 * @param messageMap
	 * @throws Exception
	 */
	public void queryInfoByProviderNo(String providerNo,DataRow messageMap)throws Exception{
		Wrapper<SerumElectrophoresis> ew =  new EntityWrapper<SerumElectrophoresis>();
		Calendar ca = Calendar.getInstance();
		Config config = configService.getConfig("electrophoresis_config", "after_days");
		int days = 14;
		if(StringUtils.isNotBlank(config.getValue())) {
			days = Integer.parseInt(config.getValue());
		}
		ca.add(Calendar.DATE, -days);
		ew.eq("providerNo", providerNo).gt("updateDate", ca.getTime());
		int count = this.selectCount(ew);
		if(count>=1) { // 大于1 表示血清电泳还有效，不用再做.
			messageMap.initSuccess(false);
		}else {
			messageMap.initSuccess(true);
		}
	}

	/**
	 * 添加蛋白电泳记录
	 * @param entity
	 * @return boolean
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean insertEntity(SerumElectrophoresis entity) throws Exception {
		// 第一步 先获取蛋白电泳批号
		Config config = configService.getConfig("electrophoresis_config","batch");
		Config config1 = configService.getConfig("electrophoresis_config","serial");
		entity.setBatchNO(config.getValue());
		entity.setSerialNO(config1.getValue());
		config1.setValue(""+(Integer.valueOf(config1.getValue())+1));
		configService.updateById(config1);
		return insert(entity);
	}

	/**
	 * 根据更新时间查找蛋白电泳记录,包含浆员基础信息
	 * @param electrophoresis
	 * @return
	 */
	@Override
	public List<DataRow> selectByUpdateDate(SerumElectrophoresis electrophoresis) {
		return baseMapper.selectByUpdateDate(electrophoresis);
	}
	
}
