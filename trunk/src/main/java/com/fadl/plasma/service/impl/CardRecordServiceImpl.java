package com.fadl.plasma.service.impl;

import com.fadl.plasma.entity.CardRecord;
import com.fadl.plasma.dao.CardRecordMapper;
import com.fadl.plasma.service.CardRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发卡记录表 服务实现类
 * </p>
 *
 * @author zll
 * @since 2018-07-30
 */
@Service
public class CardRecordServiceImpl extends ServiceImpl<CardRecordMapper, CardRecord> implements CardRecordService {
	@Autowired
	private CardRecordMapper cardRecordMapper;

	/**
	 * 获取浆员发卡记录数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer countCardRecord(String id) throws Exception {
		// TODO Auto-generated method stub
		return cardRecordMapper.countCardRecord(id);
	}

}
