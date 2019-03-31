package com.fadl.check.service;

import com.fadl.check.entity.Check;
import com.fadl.check.entity.CheckRepeat;
import com.fadl.common.DataRow;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 体检记录表-重检 服务类
 * </p>
 *
 * @author hkk
 * @since 2018-10-17
 */
public interface CheckRepeatService extends IService<CheckRepeat> {

	public void saveCheckRepeat(Check check,DataRow messageMap)throws Exception;
}
