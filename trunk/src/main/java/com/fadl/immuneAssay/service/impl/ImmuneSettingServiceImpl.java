package com.fadl.immuneAssay.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.ImmuneSettingMapper;
import com.fadl.immuneAssay.entity.ImmuneSetting;
import com.fadl.immuneAssay.service.ImmuneSettingService;

/**
 * <p>
 * 免疫类别设置 服务实现类
 * </p>
 *
 * @author hkk
 * @since 2018-07-12
 */
@Service
public class ImmuneSettingServiceImpl extends ServiceImpl<ImmuneSettingMapper, ImmuneSetting> implements ImmuneSettingService {
	
	@Autowired
	ImmuneSettingMapper immuneSettingMapper;
	/**
	 * 免疫类别设置列表
	 * @param im
	 * @param page
	 * @return
	 */
	@Override
	public void immuneSettingList(ImmuneSetting im, Page<DataRow> pageing) throws Exception {
		pageing.setRecords(immuneSettingMapper.immuneSettingList(im, pageing));
	}
	/**
	 * 查询免疫设置(获取来世版本的)
	 * @return
	 */
	public List<DataRow> queryAmmuneSetting(Integer type)throws Exception{
		return immuneSettingMapper.queryAmmuneSetting(type);
	}
	/**
	 * 新增免疫类别设置
	 * @param im
	 * @param messageMap
	 * @return
	 */
	@Override
	public void insertImmuneSetting(ImmuneSetting im, DataRow messageMap) throws Exception {
		int row = immuneSettingMapper.insert(im);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}
	/**
	 * 修改免疫类别设置
	 * @param im
	 * @param messageMap
	 */
	@Override
	public void updateImmuneSetting(ImmuneSetting im, DataRow messageMap) throws Exception {
		int row = immuneSettingMapper.updateById(im);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 删除免疫类别设置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteImmuneSetting(Long id, DataRow messageMap) throws Exception {
		int row = immuneSettingMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}
	/**
	 * 查询免疫设置信息
	 * @param id
	 * @return
	 */
	@Override
	public void queryImmuneSettingIn(Long id, DataRow messageMap) throws Exception {
		ImmuneSetting immuneSetting =immuneSettingMapper.selectById(id);
		Long num = immuneSetting.getNum()+1;
		DecimalFormat decimal = new DecimalFormat("000000");
		String typeCode = immuneSetting.getTypeCode()+decimal.format(num);
		messageMap.initSuccess(typeCode);
	}
	
	/**
	 * 获取所有免疫类别设置类型
	 * @return
	 * @throws Exception
	 */
	public List<ImmuneSetting> getAmmuneSetting() throws Exception {
		return immuneSettingMapper.getAmmuneSetting();
	}
}
