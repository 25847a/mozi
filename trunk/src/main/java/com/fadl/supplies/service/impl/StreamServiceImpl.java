package com.fadl.supplies.service.impl;
 
import com.fadl.supplies.entity.Stream;
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.service.StreamService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流水表(又称临时表) 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
@Service
public class StreamServiceImpl extends ServiceImpl<StreamMapper, Stream> implements StreamService {
	@Autowired
	StreamMapper streamMapper;
	/**
	 * 插入流水表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertStream(List<Stream> stream) throws Exception {
		this.insertBatch(stream);
	}
	/**
	 * 流水表(又称临时表)获取已经出库正在使用的耗材(适用退还,报损,摧毁)
	 * @return
	 * @throws Exception
	 */
	@Override
	public void queryStreamInfo(Page<DataRow> pageing) throws Exception {
		pageing.setRecords(streamMapper.queryStreamInfo(pageing));
	}
}
