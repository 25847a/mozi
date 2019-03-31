package com.plasma.buss.plasma.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plasma.common.DataRow;

/**
 * 浆员信息管理
 * @author fadl
 *
 */
public interface ProviderBaseinfoDao {

	/**
	 * 查询未审核浆员列表
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryProviderBaseinfoList(DataRow data) throws SQLException;
	/**
	 * 查询未审核浆员总数
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public Integer queryProviderBaseinfoListCount(DataRow data)throws SQLException;
	
	/**
	 * 批量更新浆员信息
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public Integer examinePlasma(DataRow data)throws SQLException;
	
	/**
	 * 查询浆员详细信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryDetail(Long id)throws SQLException;
	
	/**
	 * 修改浆员状态为取消发卡
	 * @return
	 * @throws SQLException
	 */
	public int changeStatus(DataRow data)throws SQLException;
	/**
	 * 插入浆员基本信息
	 * @param data
	 * @throws SQLException
	 */
	public void insertBaseinfo(DataRow data)throws SQLException;
	/**
	 * 插入浆员详细信息
	 * @param data
	 * @throws SQLException
	 */
	public void insertDetail(DataRow data)throws SQLException;
	/**
	 * 根据身份证号码查询浆员是否存在
	 * @param idNo
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryProviderBaseinfoByIdNo(String idNo)throws SQLException;
	/**
	 * 对写卡浆员进行验证
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public DataRow verification(String providerNo)throws SQLException;
	
	/**
	 * 根据ID修改详细信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateDetailedInfoById(DataRow data)throws SQLException;
	
	/**
	 * 根据ID修改基本信息
	 * @return
	 * @throws SQLException
	 */
	public int updateProviderBaseinfoById(DataRow data)throws SQLException;
	
	/**
	 * 批量取消发卡
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int cancelExaminePlasma(DataRow data)throws SQLException;
	
	/**
	 * 查询浆员详细信息
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryBaseInfo(DataRow data)throws SQLException;
	
	/**
	 * 根据浆员卡号修改信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updatePlasmaByProvider(DataRow data)throws SQLException;
	
	/**
	 * 批量查询浆员卡号
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public String queryProviderByIds(String ids)throws SQLException;
	
	/**
	 * 更新浆员信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateProviderBaseinfoByProvider(DataRow data)throws SQLException;
	/**
	 * 更新浆员详细信息
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public int updateDetailedInfoByBaseId(DataRow data)throws SQLException;
	
	/**
	 * 查询浆员审核状态
	 * @return
	 * @throws SQLException
	 */
	public String queryProviderStatus(DataRow data)throws SQLException;
	/**
	 * 删除浆员信息
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public int deleteProviderBaseInfoByProvider(DataRow data)throws SQLException;
	/**
	 * 删除浆员详情
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public int deleteDetailInfoByProvider(DataRow data)throws SQLException;
	/**
	 * 根据id集合 查询该批浆员是否为当前加密狗中的id一致
	 */
	public int queryBaseInfoCount(@Param("plasmaId")String plasmaId ,@Param("templist")List<String> templist)throws SQLException;
}
