package com.fadl.refuseInfo.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.refuseInfo.entity.RefuseInfo;

/**
 * <p>
 * 拒绝信息发布 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
public interface RefuseInfoMapper extends BaseMapper<RefuseInfo> {

	/**
	 * 查询体检拒绝信息列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryRefuseInfoList(Pagination page,HashMap<String, String> map)throws SQLException;
	
	/**
	 * 查询浆员的最新拒绝信息发布-CJ
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public RefuseInfo plasmaRefuseInfo(String providerNo)throws Exception;
	
	/**
	 * 生物所反馈
	 * @param page
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryOtherRefuseInfoList(Pagination page,HashMap<String, String> map)throws SQLException;
	/**
	 * 定时任务插入语句,由于定时任务在项目启动未登陆时启动,使用自带insert报错SecurityManager,手动写
	 * @param refuse
	 * @return
	 * @throws Exception
	 */
	public int insertEliminateYearOld(Map<String,String> map)throws Exception;
	/**
	 * 查询转氨酶暂拒情况 -CJ
	 * @param eliminateReason
	 * @return
	 * @throws Exception
	 */
	public RefuseInfo queryTransaminaseInfo(@Param(value="eliminateReason")String eliminateReason,@Param(value="providerNo")String providerNo)throws Exception;
}
