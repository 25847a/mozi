package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.Vaccine;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 疫苗设置 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-14
 */
public interface VaccineService extends IService<Vaccine> {
	/**
	 * 疫苗设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	public void vaccineList(Page<DataRow> page,Map<String,String> map)throws Exception;
	/**
	 * 新增疫苗设置
	 * @param vaccine
	 * @return
	 */
	public void insertVaccine(Vaccine vaccine,DataRow messageMap)throws Exception;
	/**
	 * 修改疫苗设置
	 * @param vaccine
	 * @return
	 */
	public void updateVaccine(Vaccine vaccine,DataRow messageMap)throws Exception;
	/**
	 * 删除疫苗设置
	 * @param id
	 * @return
	 */
	public void deleteVaccine(Long id,DataRow messageMap)throws Exception;
}
