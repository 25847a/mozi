package com.fadl.immuneAssay.service;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneSetting;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫类别设置 服务类
 * </p>
 *
 * @author hkk
 * @since 2018-07-12
 */
public interface ImmuneSettingService extends IService<ImmuneSetting> {
	/**
	 * 免疫类别设置列表
	 * @param im
	 * @param page
	 * @return
	 */
	public void immuneSettingList(ImmuneSetting im,Page<DataRow> pageing)throws Exception;
	/**
	 * 查询免疫设置(获取来世版本的)
	 * @return
	 */
	public List<DataRow> queryAmmuneSetting(Integer type)throws Exception;
	/**
	 * 新增免疫类别设置
	 * @param im
	 * @param messageMap
	 * @return
	 */
	public void insertImmuneSetting(ImmuneSetting im,DataRow messageMap)throws Exception;
	/**
	 * 修改免疫类别设置
	 * @param im
	 * @param messageMap
	 */
	public void updateImmuneSetting(ImmuneSetting im,DataRow messageMap)throws Exception;
	/**
	 * 删除免疫类别设置
	 * @param id
	 * @return
	 */
	public void deleteImmuneSetting(Long id,DataRow messageMap)throws Exception;
	/**
	 * 查询免疫设置信息
	 * @param id
	 * @return
	 */
	public void queryImmuneSettingIn(Long id,DataRow messageMap)throws Exception;
	
	/**
	 * 获取所有免疫类别设置类型
	 * @return
	 * @throws SQLException
	 */
	public List<ImmuneSetting> getAmmuneSetting() throws Exception;
}
