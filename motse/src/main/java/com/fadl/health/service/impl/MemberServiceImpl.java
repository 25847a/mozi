package com.fadl.health.service.impl;

import com.fadl.health.entity.Member;
import com.fadl.health.dao.MemberMapper;
import com.fadl.health.service.MemberService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员制度 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-07-29
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
