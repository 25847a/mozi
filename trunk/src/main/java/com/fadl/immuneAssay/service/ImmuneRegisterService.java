package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.entity.PrivilegeInjection;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫登记 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-07-18
 */
public interface ImmuneRegisterService extends IService<ImmuneRegister> {
	/**
	 * 普免登记列表(不通过)
	 * @param page
	 * @return
	 */
	public void queryImmuneRegister(Page<DataRow> page,String startTime)throws Exception;
	/**
	 *  特免登记列表(不通过)
	 * @param page
	 * @return
	 */
	public void queryImmuneRegisterSpecial(Page<DataRow> page,String startTime)throws Exception;
	/**
	 * 普免登记列表(已通过)
	 * @param page
	 * @return
	 */
	public void queryImmuneRegisterAdopt(Page<DataRow> page,String startTime) throws Exception;
	/**
	 * 普免登记列表(已通过)
	 * @param page
	 * @return
	 */
	public void queryTeregisterAdopt(Page<DataRow> page,String startTime) throws Exception;
	/**
	 * 插入普免登记信息
	 * @param immuneRegister
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateImmuneRegister(ImmuneRegister immuneRegister,DataRow messageMap)throws Exception;
	/**
	 * 取消普免登记
	 * @param OrdinaryInjection
	 * @param messageMap
	 * @throws Exception
	 */
	public void cancelPuregisterRegister(OrdinaryInjection ord,DataRow messageMap)throws Exception;
	/**
	 * 取消特免登记
	 * @param OrdinaryInjection
	 * @param messageMap
	 * @throws Exception
	 */
	public void canceltergisterRegister(PrivilegeInjection pri,DataRow messageMap)throws Exception;
	/**
	 * 插入特免登记信息
	 * @param immuneRegister
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateteregister(ImmuneRegister immuneRegister,DataRow messageMap)throws Exception;
	/**
	 * 查询普通免疫基本信息查询列表
	 * @param page
	 * @param messageMap
	 * @return
	 */
	public void queryPuregisterInfo(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 查询特殊免疫基本信息查询列表
	 * @param page
	 * @param messageMap
	 * @return
	 */
	public void queryTuregisterInfo(Map<String,String> map,Page<DataRow> page)throws Exception;
	/**
	 * 查询免疫登记表特免未登记的浆员
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public ImmuneRegister queryImmuneRegisterInfo(String providerNo)throws Exception;
}
