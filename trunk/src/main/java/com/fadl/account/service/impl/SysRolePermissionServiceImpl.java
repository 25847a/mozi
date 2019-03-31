package com.fadl.account.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.dao.SysAdminRoleMapper;
import com.fadl.account.entity.SysRolePermission;
import com.fadl.account.dao.SysRolePermissionMapper;
import com.fadl.account.service.SysRolePermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.config.shiro.MyShiroRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
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
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    SysAdminRoleMapper sysAdminRoleMapper;
    @Autowired
    MyShiroRealm myShiroRealm;
    /**
     * 根据role查询权限
     */
    public List<String> queryRolePermissionList(List<Long> list)throws Exception{
        return sysRolePermissionMapper.queryRolePermissionList(list);
    }
    /**
     * 角色权限关联变更
     * @param permissionId 权限编号
     * @param roleId 角色编号
     * @throws Exception
     */

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void insertRolePermission(List<Long> permissionId, Long roleId)throws Exception{
            EntityWrapper ew = new EntityWrapper();
            //TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            ew.eq("roleId",roleId);
            sysRolePermissionMapper.delete(ew);//删除原有的权限关联
            List<SysRolePermission> list = new ArrayList<SysRolePermission>();
            SysRolePermission sysRolePermission=null;
            for (Long id: permissionId) {
                sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setPermissionId(id);
                list.add(sysRolePermission);
            }
            if(list.size()>0)
                sysRolePermissionMapper.insertRolePermission(list);
                List<Long> adminList =sysAdminRoleMapper.queryAdminRoleByRoleIdList(roleId);
                myShiroRealm.clearAdminAuthByUserId(adminList);
    }
}
