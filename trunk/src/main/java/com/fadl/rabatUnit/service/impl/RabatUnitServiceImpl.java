package com.fadl.rabatUnit.service.impl;

import com.fadl.rabatUnit.entity.RabatUnit;
import com.fadl.common.DataRow;
import com.fadl.rabatUnit.dao.RabatUnitMapper;
import com.fadl.rabatUnit.service.RabatUnitService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 胸片检查单位表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-10
 */
@Service
public class RabatUnitServiceImpl extends ServiceImpl<RabatUnitMapper, RabatUnit> implements RabatUnitService {
	
	@Autowired
	public RabatUnitMapper rabatUnitMapper;
	
	/**
	 * 添加胸片
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void addRabatUnit(RabatUnit rabatUnit, DataRow messageMap) throws Exception {
		//如果新加的默认为选中，改变数据库中其他的值为不选中
		if (rabatUnit.getIsName()==1) {
			int res = rabatUnitMapper.updateRabatUnitStatus();
			if (res>0) {
				boolean result = this.insert(rabatUnit);
				if (result) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}
		}else{
			boolean res = this.insert(rabatUnit);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}
	}

	/**
	 * 修改胸片
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateRabatUnit(RabatUnit rabatUnit, DataRow messageMap) throws Exception {
		//如果新加的默认为选中，改变数据库中其他的值为不选中
		if (rabatUnit.getIsName()==1) {
			int res = rabatUnitMapper.updateRabatUnitStatus();
			if (res>0) {
				boolean result = this.updateById(rabatUnit);
				if (result) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}
		}else{
			boolean res = this.updateById(rabatUnit);
			if (res) {
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}
	}

}
