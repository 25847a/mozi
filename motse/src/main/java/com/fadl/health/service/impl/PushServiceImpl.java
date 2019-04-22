package com.fadl.health.service.impl;

import com.fadl.health.entity.Push;
import com.fadl.health.dao.PushMapper;
import com.fadl.health.service.PushService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 极光推送表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-18
 */
@Service
public class PushServiceImpl extends ServiceImpl<PushMapper, Push> implements PushService {

}
