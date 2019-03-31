package com.fadl.account.service.impl;

import com.fadl.account.entity.SysAdminRole;
import com.fadl.account.dao.SysAdminRoleMapper;
import com.fadl.account.service.SysAdminRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-23
 */
@Service
public class SysAdminRoleServiceImpl extends ServiceImpl<SysAdminRoleMapper, SysAdminRole> implements SysAdminRoleService {
    private static Logger logger = LoggerFactory.getLogger(SysAdminRoleServiceImpl.class);
    @Autowired
    SysAdminRoleMapper sysAdminRoleMapper;
    /**
     * 查询用户角色关联表
     * @param list
     * @return
     * @throws Exception
     */
    public List<SysAdminRole> queryAdminRoleList(List<Long> list)throws Exception{
        return sysAdminRoleMapper.queryAdminRoleList(list);
    }
}
