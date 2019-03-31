package com.fadl.account.service.impl;

import com.fadl.account.entity.RoleAuth;
import com.fadl.account.dao.RoleAuthMapper;
import com.fadl.account.service.RoleAuthService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth> implements RoleAuthService {

}
