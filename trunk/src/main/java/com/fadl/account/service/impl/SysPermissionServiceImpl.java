package com.fadl.account.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.account.entity.SysPermission;
import com.fadl.account.dao.SysPermissionMapper;
import com.fadl.account.entity.SysRolePermission;
import com.fadl.account.service.SysPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    /**
     * 查询权限URL
     * @return
     */
    public List<SysPermission> querySysPermissionList()throws Exception{
        return sysPermissionMapper.querySysPermissionList();
    }
    /**
     * 新增菜单
     * @param sysPermission 菜单信息
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void insertMenu(SysPermission sysPermission, DataRow messageMap)throws Exception{
        List<SysPermission> s = sysPermissionMapper.queryByName(sysPermission);
        if(s.size()<1){
            int flag =sysPermissionMapper.insert(sysPermission);
            if(flag>0){
                messageMap.initSuccess();
            }else{
                messageMap.initFial();
            }
        }else{
            messageMap.initFial(IConstants.MENU_EXIST);
        }
    }
    /**
     * 修改菜单
     * @param sysPermission 菜单信息
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void updateByIdSysRole(SysPermission sysPermission, DataRow messageMap)throws Exception{
        SysPermission sysPermission1 = sysPermissionMapper.selectById(sysPermission.getId());
        if(sysPermission1==null){
            messageMap.initFial(IConstants.MENU_NOT);
        }else {
            List<SysPermission> s = sysPermissionMapper.queryByName(sysPermission);
            if (s.size()>0&&!sysPermission1.getPermission().equals(sysPermission.getPermission())) {
                messageMap.initFial(IConstants.MENU_EXIST);
            } else {
                int flag = sysPermissionMapper.updateById(sysPermission);
                if (flag > 0) {
                    messageMap.initSuccess();
                } else {
                    messageMap.initFial();
                }
            }
        }
    }
    /**
     * 删除菜单
     * @param id 菜单id
     * @param messageMap 返回提示信息
     * @throws SQLException
     */
    public void deleteMenu(Long id, DataRow messageMap)throws Exception{
        //查询菜单是否有子级菜单
       List<SysPermission> sysPermissionList= sysPermissionMapper.queryByIdMenuList(id);
        if(sysPermissionList.size()>0){
            messageMap.initFial(IConstants.MENU_BINDING_PARENT);
        }else {
            boolean flag = deleteById(id);
            if (flag) {
                messageMap.initSuccess();
            } else {
                messageMap.initFial();
            }
        }
    }
    /**
     * 查询所有查单及按钮 用以授权 并返回选中角色已有菜单及权限
     */
    public List<Map<String,Object>> queryPermissionList(Long id)throws Exception{
        return sysPermissionMapper.queryPermissionList(id);
    }
}
