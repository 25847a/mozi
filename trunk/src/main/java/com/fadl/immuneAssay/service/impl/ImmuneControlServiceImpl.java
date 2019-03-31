package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.ImmuneControl;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.ImmuneControlMapper;
import com.fadl.immuneAssay.service.ImmuneControlService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 免疫针次控制设置 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-17
 */
@Service
public class ImmuneControlServiceImpl extends ServiceImpl<ImmuneControlMapper, ImmuneControl> implements ImmuneControlService {

	@Autowired
	ImmuneControlMapper immuneControlMapper;
	/**
	 * 免疫针次控制设置列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public void immuneControlList(Page<DataRow> page, Map<String, String> map) throws Exception {
		page.setRecords(immuneControlMapper.immuneControlList(map, page));
	}
	/**
	 * 新增免疫针次控制设置
	 * @param vaccine
	 * @return
	 */
	@Override
	public void insertimmuneControl(ImmuneControl immune, DataRow messageMap) throws Exception {
		int row = immuneControlMapper.insert(immune);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}
	/**
	 * 修改免疫针次控制设置
	 * @param im
	 * @return
	 */
	@Override
	public void updateimmuneControl(ImmuneControl immune, DataRow messageMap) throws Exception {
		int row = immuneControlMapper.updateById(immune);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 删除免疫针次控制设置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteimmuneControl(Long id, DataRow messageMap) throws Exception {
		int row = immuneControlMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}

}
