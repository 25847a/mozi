package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Depot;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 仓库信息表 服务类
 * </p>
 *
 * @author guokang
 * @since 2018-04-23
 */
public interface DepotService extends IService<Depot> {
	
	
	/**
	 * 新增仓库信息
	 * @param situationConfig
	 * @return
	 */
	public void insertDepot(Depot depot,DataRow messageMap)throws Exception;
	/**
	 * 修改仓库信息
	 * @param situationConfig
	 * @return
	 */
	public void updateDepot(Depot depot,DataRow messageMap)throws Exception;
	/**
	 * 删除仓库信息
	 * @param id
	 * @return
	 */
	public void deleteDepot(Long id,DataRow messageMap) throws Exception;
}
