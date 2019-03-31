package com.fadl.registries.service.impl;

import com.fadl.registries.entity.ProviderAbout;
import com.fadl.common.DataRow;
import com.fadl.registries.dao.ProviderAboutMapper;
import com.fadl.registries.service.ProviderAboutService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预约时间表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-09-11
 */
@Service
public class ProviderAboutServiceImpl extends ServiceImpl<ProviderAboutMapper, ProviderAbout> implements ProviderAboutService {

	@Autowired
	ProviderAboutMapper providerAboutMapper;
	/**
   	 * 预约人数查询
   	 * @param aboutDate
   	 * @param future
   	 * @param Pageing
   	 * @throws Exception
   	 */
	@Override
	public void queryAboutPeople(Map<String,String> map,Page<DataRow> pageing) throws Exception {
			pageing.setRecords(providerAboutMapper.queryAboutPeople(map,pageing));
	}

}
