package com.fadl.quarantine.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.service.CommonService;
import com.fadl.quarantine.dao.QuarantineMapper;
import com.fadl.quarantine.entity.Quarantine;
import com.fadl.quarantine.service.QuarantineService;

/**
 * <p>
 * 检疫期回访登记 服务实现类
 * </p>
 *
 * @author wj
 * @since 2018-11-11
 */
@Service
public class QuarantineServiceImpl extends ServiceImpl<QuarantineMapper, Quarantine> implements QuarantineService {
    @Autowired
    QuarantineMapper quarantineMapper;
    @Autowired
    CommonService commonService;
    @Autowired
    SocketUtil socketUtil;
    /**
     * 查询检疫期名单列表信息
     * @param page 分页参数
     * @param map 条件参数 参数于数据库对应
     * @return
     */
    public Page<DataRow> queryQuarantine(Page<DataRow> page, HashMap<String,String> map)throws Exception{
        return page.setRecords(quarantineMapper.queryQuarantine(page, map));
    }

    /**
     * 添加检疫期登记信息
     * @param data 标签参数
     * @param ip 地址
     * @param messageMap 引用返回结果
     * @throws Exception
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void quarantineAdd(HashMap<String, String> data, String ip, DataRow messageMap) throws Exception{
        //查询当天是否登记
        DataRow dt =quarantineMapper.queryQuarantineExist(data.get("code"));
        if(dt==null){
            data.put("visitCode",commonService.loadQuarantineNo());//生成回访编号
            socketUtil.readInfo(ip, ReadProperties.getIntValue("port"), "barcodee", data,messageMap);//打印回访标签
            Quarantine quarantine = new Quarantine();
            quarantine.setProviderNo(data.get("code"));
            quarantine.setReturnNo(data.get("visitCode"));
            quarantineMapper.insert(quarantine);//添加回访记录
        }else{
            messageMap.put("message","该献浆员已经登记过");
        }
    }

    /**
     * 查询检疫期登记列表信息
     * @param map 条件参数 参数于数据库对应
     * @return
     */
    public Page<DataRow> queryQuarantineReg(Page<DataRow> page,HashMap<String,String> map)throws Exception{
        return page.setRecords(quarantineMapper.queryQuarantineReg(page, map));
    }
}
