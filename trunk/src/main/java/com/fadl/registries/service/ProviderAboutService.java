package com.fadl.registries.service;

import com.fadl.common.DataRow;
import com.fadl.registries.entity.ProviderAbout;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 预约时间表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-09-11
 */
public interface ProviderAboutService extends IService<ProviderAbout> {

	/**
   	 * 预约人数查询
   	 * @param aboutDate
   	 * @param Pageing
   	 * @throws Exception
   	 */
   	public void queryAboutPeople(Map<String,String> map,Page<DataRow> pageing)throws Exception;
}
