package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 免疫登记 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-07-18
 */
public interface ImmuneRegisterMapper extends BaseMapper<ImmuneRegister> {

	/**
	 * 普免特免登记不通过
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryImmuneRegister(Pagination pagination,@Param(value="startTime")String startTime)throws Exception;
	/**
	 * 普免登记不通过(不带查询日期的,主要是为(显示所有浆员)这个功能的)
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryImmuneRegisterWhole(Pagination pagination)throws Exception;
	/**
	 * 普免登记通过(不带查询日期的,主要是为(显示所有浆员)这个功能的)
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryImmuneRegisterAdoptWhole(Pagination pagination)throws Exception;
	/**
	 * 特免登记列表(已通过)(不带查询日期的,主要是为(显示所有浆员)这个功能的)
	 * @param page
	 * @return
	 */
	public List<DataRow> queryTeregisterAdoptWhole(Pagination pagination)throws Exception;
	/**
	 * 普免登记已通过
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryImmuneRegisterAdopt(String startTime,Pagination pagination)throws Exception;
	/**
	 * 特免登记已通过
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryTeregisterAdopt(String startTime,Pagination pagination)throws Exception;
	/**
	 * 查询普通免疫基本信息查询列表
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryPuregisterInfo(Map<String,String> map,Pagination pagination)throws Exception;
	/**
	 * 查询特殊免疫基本信息查询列表
	 * @param map
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryTuregisterInfo(Map<String,String> map,Pagination pagination)throws Exception;
	/**
	 * 查询免疫登记表特免未登记的浆员
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public ImmuneRegister queryImmuneRegisterInfo(String providerNo)throws Exception;
}
