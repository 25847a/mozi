package com.fadl.quarantine.service;

import java.util.HashMap;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.quarantine.entity.Quarantine;

/**
 * <p>
 * 检疫期回访登记 服务类
 * </p>
 *
 * @author wj
 * @since 2018-11-11
 */
public interface QuarantineService extends IService<Quarantine> {
    /**
     * 查询检疫期名单列表信息
     * @param page 分页参数
     * @param map 条件参数 参数于数据库对应
     * @return
     */
    public Page<DataRow> queryQuarantine(Page<DataRow> page,HashMap<String,String> map)throws Exception;

    /**
     * 添加检疫期登记信息
     * @param data 标签参数
     * @param ip
     * @return
     * @throws Exception
     */
    public void quarantineAdd(HashMap<String, String> data, String ip, DataRow messageMap) throws Exception;


    /**
     * 查询检疫期登记列表信息
     * @param map 条件参数 参数于数据库对应
     * @return
     */
    public Page<DataRow> queryQuarantineReg(Page<DataRow> page,HashMap<String,String> map)throws Exception;
}
