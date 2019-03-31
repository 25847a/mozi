package com.fadl.immuneAssay.dao;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneAssay;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

/**
 * <p>
 * 免疫化验 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface ImmuneAssayMapper extends BaseMapper<ImmuneAssay> {

	
	/**
	 * 免疫化验   特免化验（未化验）
	 * @param workTime
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> specialNotAssay (String createDate,Pagination pagination) throws SQLException;
	
	/**
	 * 免疫化验   特免化验（已化验）
	 * @param workTime
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> specialHaveAssay (String createDate,Pagination pagination) throws SQLException;
	
	/**
	 * 免疫化验(根据浆员全登记号查询当天已献浆的浆员信息)(啊健)
	 * @param providerNo
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryImmuneTodayCollection(String allId)throws SQLException;
	/**
	 * 扫描器自动更新浆员信息
	 * @param number
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public int updateAssayNumber(@Param(value="number")String number,@Param(value="allId")String allId)throws SQLException;
	/**
	 * 判断是否扫面过
	 * @param allId
	 * @return
	 * @throws SQLException
	 */
	public ImmuneAssay queryScanningNumber(String allId)throws SQLException;
	/**
	 * 根据时间和卡号查询头部信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryHeadInfo(Map<String,String> map)throws SQLException;
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 免疫化验   特免检测查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySpecialImmune (Map<String, String> map,Page<DataRow> page) throws SQLException;
	/**
	 * 免疫化验   特免检测查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> querySpecialImmune (Map<String, String> map) throws SQLException;
}
