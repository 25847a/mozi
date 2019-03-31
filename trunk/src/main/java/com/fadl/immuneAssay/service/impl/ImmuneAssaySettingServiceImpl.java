package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.ImmuneAssaySetting;
import com.fadl.common.DataRow;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.dao.ImmuneAssaySettingMapper;
import com.fadl.immuneAssay.service.ImmuneAssaySettingService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 免疫化验效价值设置 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-08-25
 */
@Service
public class ImmuneAssaySettingServiceImpl extends ServiceImpl<ImmuneAssaySettingMapper, ImmuneAssaySetting> implements ImmuneAssaySettingService {

	@Autowired
	ImmuneAssaySettingMapper immuneAssaySettingMapper;
	@Autowired
	ConfigService configService;
	
	/**
	 * 效价值列表
	 * @param pageing
	 * @param map
	 * @return
	 */
	@Override
	public void AssaySettingList(Map<String, String> map, Page<DataRow> pageing) throws Exception {
		Config config = configService.queryConfig("immune_type","1");
		if(config.getIsDisable()==1) {//被禁用了
			map.put("type", "1");
		}
		pageing.setRecords(immuneAssaySettingMapper.queryAssaySettingList(map, pageing));
	}
	/**
	 * 新增效价值设置
	 * @param im
	 * @return
	 */
	@Override
	public void addAssaySetting(ImmuneAssaySetting immune, DataRow messageMap) throws Exception {
		int result =immuneAssaySettingMapper.insert(immune);
		if(result>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改效价值设置
	 * @param im
	 * @return
	 */
	@Override
	public void updateAssaySetting(ImmuneAssaySetting immune, DataRow messageMap) throws Exception {
		int result =immuneAssaySettingMapper.updateById(immune);
		if(result>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除效价值设置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteAssaySetting(Long ids, DataRow messageMap) throws Exception {
		ImmuneAssaySetting immune = new ImmuneAssaySetting();
		immune.setId(ids);
		immune.setIsDelete(1);
		int result =immuneAssaySettingMapper.updateById(immune);
		if(result>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}

}
