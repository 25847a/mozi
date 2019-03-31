package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.UnitMapper;
import com.fadl.supplies.entity.Unit;
import com.fadl.supplies.service.UnitService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 耗材单位 服务实现类
 * </p>
 * @author caijian
 * @since 2018-04-23
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {

	@Autowired
	UnitMapper unitMapper;
	/**
	 * 新增耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertUnit(Unit unit,DataRow messageMap) throws Exception {
		int row = unitMapper.insert(unit);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initFial("新增失败");
		}
	}

	/**
	 * 修改耗材单位信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateUnit(Unit unit,DataRow messageMap) throws Exception {
		int row = unitMapper.updateById(unit);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initFial("修改失败");
		}
	}
	/**
	 * 删除耗材单位信息
	 * @param id
	 * @return
	 */
	@Override
	public void deleteUnit(Long id,DataRow messageMap) throws Exception {
		Unit unit= new Unit();
		unit.setIsDelete(1);
		unit.setId(id);
		int row = unitMapper.updateById(unit);
		if(row>0){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initFial("删除失败");
		}
	}
}
