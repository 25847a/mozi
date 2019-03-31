package com.fadl.rabatinfo.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.rabatinfo.entity.Rabatinfo;

/**
 * <p>
 * 胸片检查记录表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-08
 */
public interface RabatinfoMapper extends BaseMapper<Rabatinfo> {

	/**
	 * 查询胸片结果列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryRabatinfoListByProviderNo(HashMap<String, String> map,Pagination page)throws SQLException;
	
	/**
	 * 查询浆员最后一次照胸片时间 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryProviderLastTime(Rabatinfo rabatinfo)throws SQLException;
	
	/**
	 * 根据 allid 更新胸片信息
	 * @return
	 * @throws SQLException
	 */
	public int updateRabatInfoByAllId(Long allId)throws SQLException;
}
