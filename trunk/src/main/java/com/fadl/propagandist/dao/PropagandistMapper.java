package com.fadl.propagandist.dao;

import com.fadl.propagandist.entity.Propagandist;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * 宣传员 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
public interface PropagandistMapper extends BaseMapper<Propagandist> {
    /**
     * 根据卡号查询业务员、宣传员
     */
    public Propagandist queryByProviderNo(String providerNo)throws SQLException;
    /**
     * 查询扩展员、业务员信息 
     * @return
     * @throws SQLException
     */
    public List<Propagandist> queryPropagandistInfo()throws SQLException;
}
