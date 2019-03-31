package com.fadl.account.service.impl;

import com.fadl.account.entity.Role;
import com.fadl.account.dao.RoleMapper;
import com.fadl.account.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台页面角色表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
