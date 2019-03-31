package com.fadl.registries.dao;

import com.fadl.common.DataRow;
import com.fadl.registries.entity.ProviderRegistries;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 登记记录表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-05-05
 */
public interface ProviderRegistriesMapper extends BaseMapper<ProviderRegistries> {

	/**
	 * 获取浆员登记列表
	 * @param registerDate
	 * @param pagination
	 * @return
	 */
	public List<DataRow>  queryRegistrationList(@Param(value="registerDate")String registerDate,@Param(value="registerNine")String registerNine,Pagination pagination)throws Exception;
	/**
	 * 根据献浆卡号查询浆员登记记录
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public  Map<String,Object> queryRegistriesByProviderNo(Map<String,Object> map)throws Exception;
	/**
	 * 单击列表查询浆员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow queryPlasmaProviderNo(Map<String, Object> map)throws Exception;
	/**
	 * 查询浆员今天是否存在登记号
	 * @param providerRegistries
	 * @return
	 * @throws Exception
	 */
	public Integer queryRegisterNumber(ProviderRegistries providerRegistries)throws Exception;
	/**
	 * 查询登记拒绝情况
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public DataRow queryRefuseSituation(String providerNo)throws Exception;
	/**
	 * 导出下载浆员信息数据
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> downloadRegistries(Map<String,String> data)throws Exception;
	/**
	 * 浆员登记记录高级查询
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public List<DataRow> querySeniorRegistries(Map<String,Object> map)throws Exception;
	/**
	 * 浆员登记记录高级查询总数
	 * @param map
	 * @param rowBounds
	 * @return
	 */
	public int querySeniorRegistriesCount(Map<String,Object> map)throws Exception;
	/**
	 * 小票打印查询浆员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,String> queryTicketInfo(Map<String,String> map)throws Exception;
	/**
	 * 查询全登记号
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public String selectRegisterNumber(String providerNo)throws Exception;
	/**
	 * 年、月总采浆次数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DataRow queryCollectionCount(Map<String,Object> map)throws Exception;
	/**
	 * 查询满56岁前6个月内有两次采浆记录的查询
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	public Integer queryAgeCount(String providerNo)throws Exception;
	/**
   	 * 采浆记录统计人数
   	 * @param map
   	 * @param Pageing
   	 * @throws Exception
   	 */
	public List<DataRow> queryCollectionCountPeople(Map<String,String> map)throws Exception;
	/**
   	 * 采浆记录统计人数总数
   	 * @param int
   	 * @param Pageing
   	 * @throws Exception
   	 */
	public int queryCollectionCountPeopleCount(Map<String,String> map)throws Exception;
	/**
   	 * 采浆记录统计人数详情
   	 * @param providerNo
   	 * @throws Exception
   	 */
   	public List<DataRow> queryCollectionCountDetails(String providerNo)throws Exception;
   	/**
   	 * 今日建档人数,今日登记人数,今日采浆人数,今日采浆重量(首页数据)
   	 * @return
   	 * @throws Exception
   	 */
   	public DataRow queryTodayPeopleInfo()throws Exception;
   	/**
   	 * 建档人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public DataRow queryRecordPeople()throws Exception;
   	/**
   	 * 登记人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public DataRow queryRegisterPeople()throws Exception;
   	/**
   	 * 采浆人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public DataRow queryCollectionPeopleCount()throws Exception;
   	/**
   	 * 采浆量统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public DataRow queryPlasmAmountPeopleCount()throws Exception;
   	/**
   	 * 建档人数(柱状图)
   	 * @param map
   	 * @return
   	 * @throws Exception
   	 */
   	public List<DataRow> queryHistogramPeople(Map<String,String> map)throws Exception;
   	/**
   	 * 登记人数(折线图)
   	 * @param map
   	 * @return
   	 * @throws Exception
   	 */
   	public List<DataRow> queryRegistriesPeople(Map<String,String> map)throws Exception;
   	/**
   	 * 采浆人数(柱状图)
   	 * @param map
   	 * @return
   	 * @throws Exception
   	 */
   	public List<DataRow> queryCollectionPeople(Map<String,String> map)throws Exception;
   	/**
   	 * 采浆重量(折线图)
   	 * @param map
   	 * @return
   	 * @throws Exception
   	 */
   	public List<DataRow> queryPlasmAmountPeople(Map<String,String> map)throws Exception;
   	/**
   	 * 查询当天的登记号 
   	 * @return
   	 * @throws Exception
   	 */
	public List<DataRow> queryBrokenNumber()throws Exception;
	/**
	 * 定时任务自动淘汰55-60岁小于两次采浆记录,60岁直接淘汰的记录
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryEliminateYearOld()throws Exception;
   	
}

