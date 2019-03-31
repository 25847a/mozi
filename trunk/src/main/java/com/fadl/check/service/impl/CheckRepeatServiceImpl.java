package com.fadl.check.service.impl;

import com.fadl.check.entity.Check;
import com.fadl.check.entity.CheckRepeat;
import com.fadl.check.dao.CheckMapper;
import com.fadl.check.dao.CheckRepeatMapper;
import com.fadl.check.service.CheckRepeatService;
import com.fadl.common.DataRow;
import com.fadl.common.JsonUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 体检记录表-重检 服务实现类
 * </p>
 *
 * @author hkk
 * @since 2018-10-17
 */
@Service
public class CheckRepeatServiceImpl extends ServiceImpl<CheckRepeatMapper, CheckRepeat> implements CheckRepeatService {

	@Autowired
	public CheckRepeatMapper checkRepeatMapper;
	@Autowired
	public CheckMapper checkMapper;
	
	/**
	 * 重检
	 * @param messageMap
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void saveCheckRepeat(Check check,DataRow messageMap)throws Exception{
		Check ch = checkMapper.selectById(check.getId());
		if (null!=ch && ch.getIsCheck()==1) {
			ch.setId(null);
			String json = JsonUtil.getMapper().writeValueAsString(ch);
			CheckRepeat repeat = JsonUtil.getMapper().readValue(json, CheckRepeat.class);
			//把原来的数据保存到重检表里面
			checkRepeatMapper.insert(repeat);
			checkMapper.deleteById(check.getId());
			check.setAllId(ch.getAllId());
			check.setProviderNo(ch.getProviderNo());
			check.setIsCheck(1);//状态为已体检 
			check.setCheckType(1);//状态为重检
			checkMapper.insert(check);
			messageMap.initSuccess();
		}else{
			messageMap.initFial("该浆员还未体检");
		}
	}
	
}
