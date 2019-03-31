package com.fadl.box.service.impl;

import com.fadl.box.entity.BoxInfo;
import com.fadl.box.dao.BoxInfoMapper;
import com.fadl.box.service.BoxInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 箱号信息 服务实现类
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
@Service
public class BoxInfoServiceImpl extends ServiceImpl<BoxInfoMapper, BoxInfo> implements BoxInfoService {

}
