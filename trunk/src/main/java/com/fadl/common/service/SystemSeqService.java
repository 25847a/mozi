package com.fadl.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.entity.SystemSeq;

/**
 * <p>
 * 序号 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-18
 */
public interface SystemSeqService extends IService<SystemSeq> {

    /**
     * 查询并生成新的登记号、小样号、订单号
     * @param type 序列类型  0、登记号 1、小样号  2、订单 10、回访编号
     * @return
     * @throws Exception
     */
    public SystemSeq queryNewSystemSeqInfo(Integer type)throws Exception;
    /**
     * 查询新的登记号、小样号、订单号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     * @throws Exception
     */
    public SystemSeq querySystemSeqInfo(Integer type)throws Exception;
    
    /**
     * 更新序列号
     * @throws Exception
     */
    public int updateSystemById(SystemSeq seq)throws Exception;
}
