package com.fadl.immuneAssay.service.impl;

import com.fadl.immuneAssay.entity.Vaccine;
import com.fadl.common.DataRow;
import com.fadl.immuneAssay.dao.VaccineMapper;
import com.fadl.immuneAssay.service.VaccineService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 疫苗设置 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-07-14
 */
@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {

	@Autowired
	VaccineMapper vaccineMapper;
	/**
	 * 疫苗设置列表
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public void vaccineList(Page<DataRow> page, Map<String, String> map) throws Exception {
		page.setRecords(vaccineMapper.vaccineList(map, page));
	}
	/**
	 * 新增疫苗设置
	 * @param vaccine
	 * @return
	 */
	@Override
	public void insertVaccine(Vaccine vaccine, DataRow messageMap) throws Exception {
			int row = vaccineMapper.insert(vaccine);
			if(row>0){
				messageMap.initSuccess("新增成功");
			}else {
				messageMap.initFial("新增失败");
			}
	}
	/**
	 * 修改疫苗设置
	 * @param vaccine
	 * @return
	 */
	@Override
	public void updateVaccine(Vaccine vaccine, DataRow messageMap) throws Exception {
		int row = vaccineMapper.updateById(vaccine);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 删除疫苗设置
	 * @param id
	 * @return
	 */
	@Override
	public void deleteVaccine(Long id, DataRow messageMap) throws Exception {
		int row = vaccineMapper.deleteById(id);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}

}
