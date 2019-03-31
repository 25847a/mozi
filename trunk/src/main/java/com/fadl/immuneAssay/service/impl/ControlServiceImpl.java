package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.Control;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.ControlMapper;
import com.fadl.immuneAssay.service.ControlService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 免疫针次控制设置————控制名称表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-16
 */
@Service
public class ControlServiceImpl extends ServiceImpl<ControlMapper, Control> implements ControlService {

	@Autowired
	ControlMapper controlMapper;
	@Override
	public void insertControl(Control control, DataRow messageMap) throws Exception {
		int row = controlMapper.insert(control);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}

	@Override
	public void updateControl(Control control, DataRow messageMap) throws Exception {
		int row = controlMapper.updateById(control);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}

	@Override
	public void deleteControl(Long id, DataRow messageMap) throws Exception {
		int row = controlMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}
}
