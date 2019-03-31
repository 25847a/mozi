package com.fadl.inspection.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.FixedPulpRegister;

/**
 * <p>
 * 固定浆员检验登记 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-16
 */
public interface FixedPulpRegisterService extends IService<FixedPulpRegister> {
	/**
	 * 获取固定浆员登记记录,带分页和日期查询
	 * @param page
	 * @param collection
	 * @return
	 */
	Page<DataRow> queryListByCreateDateAndIsAssay(Page<DataRow> page, FixedPulpRegister blood)throws Exception;
	/**
	 * 根据ID查找实体的方法,方法会返回浆员的部分个人信息
	 * @param ID
	 * @return
	 */
	DataRow queryFixedPulpRegisterById(Long id)throws Exception;
	/**
	 * 根据AllId查找实体的方法,方法会返回浆员的部分个人信息
	 * @param entity
	 * @return
	 */
	DataRow queryFixedPulpRegisterByEntity(FixedPulpRegister entity)throws Exception;
	/**
	 * 根據ID更新是否生成小样号
	 * @param collection
	 */
	void updateWithCollection(FixedPulpRegister blood)throws Exception;
	
	/**
	 * 根据ID更新固定浆员是否采样
	 * @param fixedPulpRegister
	 * @return
	 */
	boolean updateAssayById(FixedPulpRegister fixedPulpRegister, DataRow messageMap)throws Exception;
	
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate)throws Exception;
	
	/**
	 *  根据修改时间查询拒收的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> queryRefuseInfosByUpdateDate(String updateDate)throws Exception;
}
