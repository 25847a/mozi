package com.fadl.plasma.service;

import com.fadl.plasma.entity.CardRecord;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 发卡记录表 服务类
 * </p>
 *
 * @author zll
 * @since 2018-07-30
 */
public interface CardRecordService extends IService<CardRecord> {

	/**
	 * 获取浆员发卡记录数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer countCardRecord(String id) throws Exception;
}
