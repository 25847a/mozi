package com.fadl.account.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.dao.SysAdminRoleMapper;
import com.fadl.account.dao.SysRolePermissionMapper;
import com.fadl.account.entity.SysAdminRole;
import com.fadl.account.entity.SysRole;
import com.fadl.account.dao.SysRoleMapper;
import com.fadl.account.entity.SysRolePermission;
import com.fadl.account.service.SysRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
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
 * @since 2018-04-17
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    SysAdminRoleMapper sysAdminRoleMapper;
    /**
     * 新增角色
     * @param sysRole 角色对象
     * @param messageMap 信息返回
     * @throws SQLException
     */
    public void insertSysRole(SysRole sysRole,DataRow messageMap) throws Exception{
        SysRole s = sysRoleMapper.queryByName(sysRole.getName());
        if(s==null){
            int flag =sysRoleMapper.insert(sysRole);
            if(flag>0){
                messageMap.initSuccess();
            }else{
                messageMap.initFial();
            }
        }else{
            messageMap.initFial(IConstants.ROLE_EXIST);
        }
    }
    /**
     * 修改角色信息
     * @param sysRole 角色信息
     * @throws SQLException
     */
    public void updateByIdSysRole(SysRole sysRole,DataRow messageMap) throws Exception{
        SysRole sysRole1 = sysRoleMapper.selectById(sysRole.getId());
        if(sysRole1==null){
            messageMap.initFial(IConstants.ROLE_NOT);
        }else {
            SysRole s = sysRoleMapper.queryByName(sysRole.getName());
            if (s != null && !sysRole.getName().equals(s.getName())) {
                messageMap.initFial(IConstants.ROLE_EXIST);
            } else {
                int flag = sysRoleMapper.updateById(sysRole);
                if (flag > 0) {
                    messageMap.initSuccess();
                } else {
                    messageMap.initFial();
                }
            }
        }
    }
    /**
     * 删除角色信息
     * @param ids 角色ID
     * @param messageMap
     * @throws Exception
     */
    public void deleteRole(List<Long> ids, DataRow messageMap) throws Exception{
        List<String> sysRolePermissionsList=sysRolePermissionMapper.queryRolePermissionList(ids);//查询角色是否已绑定权限
        if(sysRolePermissionsList.size()>0){
            messageMap.initFial(IConstants.ROLE_BINDING_PERMISSION);
        }else{
            List<SysAdminRole> sysAdminRolesList= sysAdminRoleMapper.queryAdminRoleList(ids);//查询角色是否绑定用户
            if(sysAdminRolesList.size()>0){
                messageMap.initFial(IConstants.ROLE_BINDING_ADMIN);
            }else{
                boolean flag = deleteBatchIds(ids);
                if (flag) {
                    messageMap.initSuccess();
                } else {
                    messageMap.initFial();
                }
            }
        }
    }
}
