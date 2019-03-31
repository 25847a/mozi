package com.fadl.quarantine.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.quarantine.entity.Quarantine;

/**
 * <p>
 * 检疫期回访登记 Mapper 接口
 * </p>
 *
 * @author wj
 * @since 2018-11-11
 */
public interface QuarantineMapper extends BaseMapper<Quarantine> {
    /**
     * 查询检疫期名单列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public List<DataRow> queryQuarantine(Pagination page, Map<String,String> data)throws SQLException;

    /**
     * 查询检疫期指定献浆员当天是否登记
     * @param providerNo 献浆员
     * @return
     */
    public DataRow queryQuarantineExist(String providerNo)throws SQLException;

    /**
     * 查询检疫期登记列表信息
     * @param data 条件参数 参数于数据库对应
     * @return
     */
    public List<DataRow> queryQuarantineReg(Pagination page, Map<String,String> data)throws SQLException;

}
