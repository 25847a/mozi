package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Type;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材类别信息 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-04-23
 */
public interface TypeService extends IService<Type> {
	
	/**
	 * 新增耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	public void insertType(Type type,DataRow messageMap)throws Exception;
	/**
	 * 修改耗材类别信息
	 * @param situationConfig
	 * @return
	 */
	public void updateType(Type type,DataRow messageMap)throws Exception;
	/**
	 * 删除耗材类别信息
	 * @param id
	 * @return
	 */
	public void deleteType(Long ids,DataRow messageMap)throws Exception;
}
