package com.fadl.log.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.log.entity.Log;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wj
 * @since 2018-07-20
 */
public interface LogService extends IService<Log> {
	
	/**
	 * 查询用户操作日志
	 * @param map
	 * @param pageing
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> queryLog(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 添加操作日志
	 * @param method 方法或模块
	 * @param describe 描述
	 * @param providerNo 浆员卡号
	 * @return
	 * @throws Exception
	 */
	public Integer insertLog(String method,String describe,String providerNo)throws Exception;
}
