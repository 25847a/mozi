package com.fadl.plasma.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.entity.ProviderBaseinfo;
/**
 * <p>
 * 浆员基本信息表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-10
 */
public interface ProviderBaseinfoService extends IService<ProviderBaseinfo> {
    /**
     * 查询浆员详情
     * @param providerNo
     * @param startTime
     * @return
     */
    public DataRow queryProviderBaseInfoDetails(String providerNo,String startTime) throws Exception;

    /**
     * 修改浆员信息
     * @param file
     * @param providerBaseinfo 浆员基本信息
     * @param detailedInfo 浆员详细信息
     * @return
     */
    public void updateProviderBaseInfo(MultipartFile[] file, HashMap<String, String> map, DataRow messageMap) throws Exception;
    /**
     * 浆员基本信息审核
     * @param ids 审核的浆员id集合
     * @return
     */
    public void updateBaseInfoExamine(HashMap<String,Object> map,DataRow messageMap)throws Exception;
    /**
     * 新增浆员信息
     * @param file 文件流
     * @param providerBaseinfo  浆员基本信息
     * @param detailedInfo 浆员详细信息
     * @param validateType 是否验证 0人脸识别
     * @return
     */
    public void insertBaseInfo(MultipartFile[] file, @Valid ProviderBaseinfo providerBaseinfo, DetailedInfo detailedInfo, Integer validateType, Integer roadFeeType, BigDecimal roadFee,String group,DataRow messageMap)throws Exception;
    /**
     * 验证浆员信息是否合规合法
     * @return
     */
    public void queryPlasmaLegal(String birthday,String address,String idNo,String validDate,DataRow messageMap)throws Exception;
    /**
     * 查询浆员首次建档登记
     * @param page 分页参数
     * @param startTime 根据登记时间查询
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryRegistriesList(Page<DataRow> page, String startTime)throws Exception;
    /**
     * 查询浆员信息
     * @param map 根据 姓名、卡号、身份证查询
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryProviderBaseInfoList(Page<DataRow> page, Map<String,Object> map)throws Exception;
    /**
     * 点击浆员 查询浆员详细信息
     * @param id 浆员id
     * @return 返回浆员详细信息
     */
    public DataRow queryPlasmaInfoDetail(String id,DataRow messageMap)throws Exception;
    /**
     * 根据浆员卡号查询浆员登记-献浆记录
     * @param page 分页
     * @param providerNo 浆员卡号
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryPlasmaInfoDetailList(Page<DataRow> page, String providerNo)throws Exception;
    
    
    /**
     * 给检验合格的浆员将临时卡号改为浆员卡号
     * @param providerNo
     * @param bloodType
     * @return
     * @throws Exception
     */
    public DataRow changeProviderNo(Map<String, String> map,DataRow messageMap)throws Exception;
    
    /**
     * 办卡查询(未发卡)
     * @param createDate
     * @param page
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryCard(String createDate,Page<DataRow> page) throws SQLException;
    
    /**
     * 办卡查询(已发卡)
     * @param createDate
     * @param page
     * @return
     * @throws SQLException
     */
    public Page<DataRow> haveGrantCard(Map<String, String> map,Page<DataRow> page) throws SQLException;
    
    /** 
     * 发卡，即给浆员绑定卡号
     * @param map
     * @return
     * @throws Exception
     */
    public void grantCard(Map<String, String> map,DataRow messageMap)throws Exception;
    
    /**
     * 取消发卡
     * @param id
     * @return
     * @throws Exception
     */
    public int cancelCard (String id) throws Exception;
    
   /**
    * 写卡(可以根据idNo或者providerNo进行查询)
    * @param map
    * @param page
    * @return
    * @throws Exception
    */
    public Page<DataRow> queryPunchCard(Map<String, String> map,Page<DataRow> page)throws Exception;
    
    /**
     * 将以发卡浆员审核状态改成已审核
     * @param providerNo
     * @return
     * @throws Exception
     */
    public int updateStatus(HashMap<String, String> map) throws Exception;
    
    /**
     * 献浆员高级查询
     * @param page 分页参数
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryPlasmaDetailList(Page<DataRow> page, HashMap<String, String> map)throws Exception;
    /**
	 * 献浆员高级查询（导出查询所有）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryPlasmaDetailList(HashMap<String, String> map) throws Exception;
	/**
     * 献浆员数据导出
     * @param map
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPlasmaDetailExcel(HashMap<String, String> map)throws SQLException;
	/**
     * 基本信息审核
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryPlasmaExamineList(Page<DataRow> page, HashMap<String, String> map)throws Exception;
    /**
     * 基本信息审核（导出用）
     * @param map
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPlasmaExamineList(HashMap<String, String> map)throws Exception;
    
    /**
     * 根据条件更新浆员状态
     * @param map
     * @throws SQLException
     */
    public int updateProviderBaseinfoState(HashMap<String, String> map)throws Exception;

    /**
     * 删除浆员信息
     * @param id
     * @return
     */
    public void deleteBaseInfo(String id,DataRow messageMap)throws Exception;
    
    /**
     * 取消发卡
     * @param providerNo
     * @param messageMap
     * @throws Exception
     */
    public void cancelPlassmaCard(String providerNo,DataRow messageMap)throws Exception;
    
    /**
     * 发送取消发卡请求到卫计委
     * @param providerNo
     * @param messageMap
     * @throws SQLException
     */
    public void sendCancelCard(HashMap<String, String> map,DataRow messageMap)throws Exception;
    
    /**
	 * 浆员信息修改后，提交卫计委系统审核
	 * @throws Exception
	 */
	public void submitPlasmaUpdate(Map<String, String> map,DataRow messageMap)throws Exception;
	
	/**
	 * 卫计委审核通过后，更新浆员信息
	 * @param providerNo
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePlasmaInfo(String providerNo,DataRow messageMap)throws Exception;
	
	/**
     * 查询新浆员登记列表
     * @param startTime 根据登记时间查询
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryNewRegistriesList(Page<DataRow> page, String startTime)throws SQLException;
    
    /**
     * 查询未发卡人员 
     * @return
     * @throws SQLException
     */
    public DataRow queryNotGrantCard()throws SQLException;
    
    /**
     * 更新浆员审核状态
     * @param providerNo
     * @return
     * @throws SQLException
     */
    public int updatePrividerStatus(DataRow row)throws SQLException;
}
