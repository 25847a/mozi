package com.fadl.inspection.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.FixedPulpRegister;

/**
 * <p>
 * 固定浆员检验登记 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-16
 */
public interface FixedPulpRegisterMapper extends BaseMapper<FixedPulpRegister> {

	
	List<DataRow> queryListByCreateDateAndIsAssay(Pagination page, FixedPulpRegister pulpRegister);
	
	DataRow queryFixedPulpRegisterByEntity(FixedPulpRegister pulpRegister);
	
	void updateWithCollection(FixedPulpRegister pulpRegister);
	
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate);
	/**
	 *  根据修改时间查询拒收的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> queryRefuseInfosByUpdateDate(String updateDate);
	
}
