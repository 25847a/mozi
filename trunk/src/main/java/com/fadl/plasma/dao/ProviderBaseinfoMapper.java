package com.fadl.plasma.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.plasma.entity.ProviderBaseinfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 浆员基本信息表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-04-10
 */
public interface ProviderBaseinfoMapper extends BaseMapper<ProviderBaseinfo> {
    /**
     * 查询浆员详情
     * @param map
     * @return
     */
    public DataRow queryProviderBaseInfoDetails(HashMap<String,Object> map)throws SQLException;
    /**
     * 浆员基本信息审核
     * @param ids 审核的浆员id集合
     * @return
     */
    public boolean updateBaseInfoExamine(HashMap<String,Object> map)throws SQLException;
    /**
     * 点击浆员 查询浆员详细信息
     * @param id 浆员id
     * @return 返回浆员详细信息
     */
    public DataRow queryPlasmaInfoDetail(String id)throws SQLException;

    /**
     * 根据浆员卡号查询浆员登记-献浆记录
     * @param page 分页
     * @param providerNo 浆员卡号
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPlasmaInfoDetailList(Pagination page,@Param(value="providerNo") String providerNo)throws SQLException;
    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public List<DataRow> queryWhereBaseInfoOrDetailList(HashMap<String, String> data)throws SQLException;
    /**
     * 查询浆员列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public DataRow queryWhereBaseInfoOrDetailObj(HashMap<String, Object> data)throws SQLException;

    /**
     * 查询浆员首次登记
     * @param startTime 根据登记时间查询
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryRegistriesList(Pagination page,@Param(value="startTime") String startTime)throws SQLException;
    /**
     * 查询浆员信息
     * @param map 根据 姓名、卡号、身份证查询
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryProviderBaseInfoList(Pagination page,Map<String,Object> map)throws SQLException;
    
    /**
     * 办卡查询(未发卡)
     * @param createDate
     * @param page
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryCard(String createDate,Page<DataRow> page) throws SQLException;
    
    /**
     * 办卡查询(未发卡)
     * @param createDate
     * @param page
     * @return
     * @throws SQLException
     */
    public List<DataRow> haveGrantCard(Map<String, String> map,Page<DataRow> page) throws SQLException;
    
    /**
     * 发卡，即给浆员绑定卡号
     * @param map
     * @return
     * @throws SQLException
     */
    public int grantCard (Map<String, String> map)throws SQLException;
    
    /**
     * 取消发卡
     * @param id
     * @return
     * @throws SQLException
     */
    public int cancelCard (String id) throws SQLException;
    
    /**
     * 给检验合格的浆员将临时卡号改为浆员卡号
     * @param map
     * @return
     * @throws SQLException
     */
    public int changeProviderNo(Map<String, String> map)throws SQLException;
    
    /**
     * 写卡(可以根据idNo或者providerNo进行查询分页)
     * @param map
     * @param page
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPunchCard(Map<String, String> map,Page<DataRow> page)throws SQLException;
    
    /**
     * 打印
     * @param map
     * @return
     * @throws SQLException
     */
    public List<DataRow> printPunchCard(Map<String, String> map)throws SQLException;
    
    /**
     * 未审核浆员发送到卫计委系统
     * @return
     * @throws SQLException
     */
    public List<DataRow> sendPlasmaMsg(Map<String, String> map) throws SQLException;
    
    /**
     * 将以发卡浆员审核状态改成已审核
     * @param providerNo
     * @return
     * @throws SQLException
     */
    public int updateStatus(HashMap<String, String> map) throws SQLException;
    

    /**
     * 献浆员高级查询
     * @return
     * @throws SQLException
     */
    //public List<DataRow> queryPlasmaDetailList(Pagination page,HashMap<String, String> map)throws SQLException;
    /**
     * 献浆员高级查询
     * @param map
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPlasmaDetailList(HashMap<String, String> map)throws SQLException;
    /**
     * 献浆员高级查询总数
     * @param map
     * @return
     * @throws SQLException
     */
    public Long queryPlasmaDetailListCount(HashMap<String, String> map)throws SQLException;
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
    public List<DataRow> queryPlasmaExamineList(Pagination page,HashMap<String, String> map)throws SQLException;
    /**
     * 基本信息审核（导出用）
     * @param map
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryPlasmaExamineList(HashMap<String, String> map)throws SQLException;
    
    /**
     * 根据条件更新浆员状态
     * @param map
     * @throws SQLException
     */
    public int updateProviderBaseinfoState(HashMap<String, String> map)throws SQLException;

    /**
     * 查询浆员需要删除的字段id
     * @param id  浆员id
     * @return
     * @throws SQLException
     */
    public DataRow queryPlasmaWorkflow(String id)throws SQLException;
    
    /**
     * 取消发卡
     * @return
     * @throws SQLException
     */
    public int cancelPlassmaCard(String providerNo)throws SQLException;
    
    /**
     * 更新浆员信息 
     * @param map
     * @return
     * @throws SQLException
     */
    public int updatePlasmaInfo(DataRow row)throws SQLException;
    
    /**
     * 根据 id 查询浆员部分信息 
     * @param id
     * @return
     * @throws SQLException
     */
    public DataRow queryPlasmaInfoById(Long id)throws SQLException; 
    
    /**
     * 查询新浆员登记列表
     * @param startTime 根据登记时间查询
     * @return
     * @throws SQLException
     */
    public List<DataRow> queryNewRegistriesList(Pagination page,@Param(value="startTime") String startTime)throws SQLException;
    
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
    
    /**
     * 查询浆员卡号列表，是否需要补卡
     * @return
     * @throws SQLException
     */
    public List<String> queryProviderList(String bloodType)throws SQLException;
}