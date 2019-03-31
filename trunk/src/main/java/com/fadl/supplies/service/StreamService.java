package com.fadl.supplies.service;

import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Stream;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 流水表(又称临时表) 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
public interface StreamService extends IService<Stream> {
	/**
	 * 插入流水表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public void insertStream(List<Stream> stream)throws Exception;
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @return
	 * @throws Exception
	 */
	public void queryStreamInfo(Page<DataRow> pageing)throws Exception;
}
