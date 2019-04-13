package com.fadl.health.service.impl;

import com.fadl.health.entity.Health;
import com.fadl.health.dao.HealthMapper;
import com.fadl.health.service.HealthService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 惊凡给的数据表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {

}
