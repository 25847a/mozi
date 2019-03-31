package com.fadl.registries.service;

import com.fadl.common.DataRow;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.registries.entity.ProviderRegistries;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 登记记录表 服务类
 * </p>
 *
 * @author gkang
 * @since 2018-05-05
 */
public interface ProviderRegistriesService extends IService<ProviderRegistries> {

	/**
	 * 获取浆员登记列表
	 * @param registerDate
	 * @param page
	 * @return
	 */
	public Page<DataRow> queryRegistrationList(String registerDate,String registerNine,Page<DataRow> page)throws Exception;
	/**
	 *  获取登记断档号
	 * @param messageMap
	 * @throws Exception
	 */
	public void queryBrokenNumber(DataRow messageMap)throws Exception;
	/**
	 * 单击列表查询浆员信息
	 * @param map
	 * @param messageMap
	 * @return
	 */
	public void queryPlasmaProviderNo(Map<String,Object> map,DataRow messageMap)throws Exception;
	/**
	 * 根据献浆卡号查询浆员登记记录(读卡器读取,可以直接传值providerNo获取浆员信息)
	 * (写socketUtil方法与设备连接,返回值是messageMap,所以传值是通过messageMap)
	 * @param messageMap
	 * @return
	 */
	public void readPlasmaProviderNo(DataRow messageMap) throws Exception;
	/**
	 * 验证登记流程
	 * @param provider
	 * @param messageMap
	 * @throws Exception
	 */
	public DataRow verificationRegister(ProviderRegistries provider,DataRow messageMap)throws Exception;
	
	/**
     * 新增浆员登记记录
     * @param providerRegistries
     * @param messageMap
     * @param img
     * @return
     */
    public DataRow insertRegistries(ProviderRegistries providerRegistries,Integer roadFeeType,BigDecimal roadFee,DataRow messageMap,String img)throws Exception;
    /**
     * 删除登记记录
     * @param ids
     * @param messageMap
     * @throws Exception
     */
    public void delectRegistration(String ids,DataRow messageMap)throws Exception;
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
   	 * @param page
   	 * @return
   	 */
   	public void querySeniorRegistries(Map<String,Object> map,Page<DataRow> page)throws Exception;
   	/**
	 * 小票打印查询浆员信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
   	public HashMap<String,String> queryTicketInfo(HashMap<String,String> map)throws Exception;
   	/**
   	 * 修改手机号码
   	 * @param detailedInfo
   	 * @param messageMap
   	 * @throws Exception
   	 */
   	public void updatePhoneNumber(DetailedInfo detailedInfo,DataRow messageMap)throws Exception;
   	/**
   	 * 采浆记录统计人数
   	 * @param map
   	 * @param Pageing
   	 * @throws Exception
   	 */
   	public void queryCollectionCountPeople(Page<DataRow> pageing,Map<String,String> map)throws Exception;
   	/**
   	 * 采浆记录统计人数详情
   	 * @param providerNo
   	 * @throws Exception
   	 */
   	public List<DataRow> queryCollectionCountDetails(String providerNo)throws Exception;
   	/**
   	 * 今日建档人数,今日登记人数,今日采浆人数,今日采浆重量(首页数据)
   	 * @param messageMap
   	 * @throws Exception
   	 */
   	public void queryTodayPeopleInfo(DataRow messageMap)throws Exception;
   	/**
   	 * 建档人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryRecordPeople(DataRow messageMap)throws Exception;
   	/**
   	 * 登记人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryRegisterPeople(DataRow messageMap)throws Exception;
   	/**
   	 * 采浆人数统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryCollectionPeopleCount(DataRow messageMap)throws Exception;
   	/**
   	 * 采浆量统计(扇形图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryPlasmAmountPeopleCount(DataRow messageMap)throws Exception;
   	/**
   	 * 建档人数(柱状图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryHistogramPeople(Map<String,String> map,DataRow messageMap)throws Exception;
   	/**
   	 * 登记人数(折线图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryRegistriesPeople(Map<String,String> map,DataRow messageMap)throws Exception;
   	/**
   	 * 采浆人数(柱状图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryCollectionPeople(Map<String,String> map,DataRow messageMap)throws Exception;
   	/**
   	 * 采浆重量(折线图)
   	 * @return
   	 * @throws Exception
   	 */
   	public void queryPlasmAmountPeople(Map<String,String> map,DataRow messageMap)throws Exception;
}

