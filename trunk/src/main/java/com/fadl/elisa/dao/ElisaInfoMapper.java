package com.fadl.elisa.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.elisa.entity.ElisaInfo;

/**
 * <p>
 * 酶标板检测信息 Mapper 接口
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaInfoMapper extends BaseMapper<ElisaInfo> {
	/**
	 * 根据创建时间找出当天的最大的序号
	 * @param createDate
	 * @return
	 */
	String getMaxSeqNumberByCreateDate(String createDate);
	/**
	 * 根据条件查询酶标板读板信息
	 * @param page
	 * @param elisaInfo
	 * @return
	 */
	List<DataRow> selectInfoByCondition(Pagination page, ElisaInfo elisaInfo);
	/**
	 *  返回当天的所有的全序号
	 * @return
	 */
	List<String> getAllSequenceNumber();
}
