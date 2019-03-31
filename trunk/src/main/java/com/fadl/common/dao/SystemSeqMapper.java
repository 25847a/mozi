package com.fadl.common.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.entity.SystemSeq;

/**
 * <p>
 * 序号 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-18
 */
public interface SystemSeqMapper extends BaseMapper<SystemSeq> {
    /**
     * 查询登记号、小样号、订单号
     * @param type 序列类型  0、登记号 1、小样号  2、订单
     * @return
     * @throws Exception
     */
    public SystemSeq querySystemSeqInfo(int type)throws SQLException;

    /**
     * 更新序列号
     * @param systemSeq
     * @throws SQLException
     */
    public void updateSystemSeqInfo(@Param("systemSeq") SystemSeq systemSeq)throws SQLException;
    
    /**
     * 初始化序列号
     * @throws Exception
     */
    public int updateSystemById(@Param("systemSeq") SystemSeq systemSeq)throws SQLException;
}
