package com.fadl.plasma.dao;

import java.sql.SQLException;
import java.util.HashMap;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.plasma.entity.DetailedInfo;

/**
 * <p>
  * 浆员详细信息表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-04-11
 */
public interface DetailedInfoMapper extends BaseMapper<DetailedInfo> {
    /**
     * 根据baseId查询浆员详细信息
     * @param baseId
     * @return
     * @throws SQLException
     */
    public DetailedInfo queryByBaseId(Long baseId)throws SQLException;
    
    /**
     * 更新浆员详细信息
     * @param detailedInfo
     * @return
     * @throws SQLException
     */
    public int updatePlasmaDetailInfo(HashMap<String, Object> map)throws SQLException;

}