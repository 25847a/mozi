package com.fadl.rabatinfo.service;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.rabatinfo.entity.Rabatinfo;

/**
 * <p>
 * 胸片检查记录表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-08
 */
public interface RabatinfoService extends IService<Rabatinfo> {

	/**
	 * 查询胸片结果列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public void queryRabatinfoListByProviderNo(HashMap<String, String> map,Page<DataRow> page)throws SQLException;
	
	/**
	 * 查询浆员最后一次照胸片时间 
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryProviderLastTime(Rabatinfo rabatinfo)throws SQLException;
	
	/**
	 * 录入胸片结果
	 * @param imageList
	 * @param rabatinfo
	 * @param messageMap
	 * @throws SQLException
	 * @throws Exception 
	 */
	public void insertRabatinfo(MultipartFile imageList,Rabatinfo rabatinfo,DataRow messageMap)throws Exception;
	
	/**
	 * 根据 allid 更新胸片信息
	 * @return
	 * @throws SQLException
	 */
	public int updateRabatInfoByAllId(Long allId)throws SQLException;
}
