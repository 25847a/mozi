package com.fadl.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.dao.SystemSeqMapper;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;

/**
 * <p>
 * 序号 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-18
 */
@Service
public class SystemSeqServiceImpl extends ServiceImpl<SystemSeqMapper, SystemSeq> implements SystemSeqService {
    @Autowired
    SystemSeqMapper systemSeqMapper;
    SystemSeq systemSeq;
    /**
     * 查询并生成新的登记号、小样号、订单号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     * @throws Exception
     */
    public SystemSeq queryNewSystemSeqInfo(Integer type)throws Exception{
        systemSeq =systemSeqMapper.querySystemSeqInfo(type);
            systemSeqMapper.updateSystemSeqInfo(systemSeq);
            systemSeq.setValue(systemSeq.getValue()+systemSeq.getIncrement());
           return systemSeq;
    }
    /**
     * 查询新的登记号、小样号、订单号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     * @throws Exception
     */
    public SystemSeq querySystemSeqInfo(Integer type)throws Exception{
        SystemSeq systemSeq =systemSeqMapper.querySystemSeqInfo(type);
            systemSeq.setValue(systemSeq.getValue()+systemSeq.getIncrement());
            return systemSeq;
    }
    
    /**
     * 初始化序列号
     */
	@Override
	public int updateSystemById(SystemSeq seq) throws Exception {
		return systemSeqMapper.updateSystemById(seq);
	}
}
