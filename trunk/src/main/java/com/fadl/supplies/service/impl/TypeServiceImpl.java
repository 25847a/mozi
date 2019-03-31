package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.TypeMapper;
import com.fadl.supplies.entity.Type;
import com.fadl.supplies.service.TypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 耗材类别信息 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
	
	@Autowired
	TypeMapper suppliesTypeMapper;
	
	/**
	 * 新增耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertType(Type type,DataRow messageMap) throws Exception {
		int row = suppliesTypeMapper.insert(type);
		if(row>0){
			messageMap.initSuccess("新增成功");
		}else {
			messageMap.initSuccess("新增失败");
		}
	}
	/**
	 * 修改耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateType(Type type,DataRow messageMap) throws Exception {
		int row = suppliesTypeMapper.updateById(type);
		if(row>0){
			messageMap.initSuccess("修改成功");
		}else {
			messageMap.initSuccess("新增失败");
		}
	}
	/**
	 * 删除耗材类别信息
	 * @param id
	 * @return
	 */
	@Override
	public void deleteType(Long ids,DataRow messageMap) throws Exception {
		Type type= new Type();
		type.setIsDelete(1);
		type.setId(ids);
		boolean row = this.updateById(type);;
		if(row){
			messageMap.initSuccess("删除成功");
		}else {
			messageMap.initSuccess("删除失败");
		}
	}
}
