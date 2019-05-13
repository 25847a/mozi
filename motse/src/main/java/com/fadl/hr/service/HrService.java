package com.fadl.hr.service;

import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.hr.entity.Hr;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jian
 * @since 2019-05-10
 */
public interface HrService extends IService<Hr> {
	/**
	 * 查询机构管理页面下工作人员的信息列表
	 * @return
	 * @throws Exception
	 */
	public DataRow queryHrList(Map<String,Object> map,DataRow messageMap)throws Exception;
	/**
	 * 删除供应商的个人信息
	 * @return
	 * @throws Exception
	 */
	public DataRow deleteHrInfo(Hr hr,DataRow messageMap)throws Exception;
}
