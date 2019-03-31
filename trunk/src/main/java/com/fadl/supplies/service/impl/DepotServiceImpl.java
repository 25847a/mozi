package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.DepotMapper;
import com.fadl.supplies.entity.Depot;
import com.fadl.supplies.service.DepotService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库信息表 服务实现类
 * </p>
 *
 * @author guokang
 * @since 2018-04-23
 */
@Service
public class DepotServiceImpl extends ServiceImpl<DepotMapper, Depot> implements DepotService {

	
	@Autowired
	DepotMapper depotMapper;
	/**
	 * 新增仓库信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void insertDepot(Depot depot,DataRow messageMap) throws Exception {
		int row = depotMapper.insert(depot);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改仓库信息
	 * @param situationConfig
	 * @return
	 */
	@Override
	public void updateDepot(Depot depot,DataRow messageMap) throws Exception {
		int row = depotMapper.updateById(depot);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 删除仓库信息
	 * @param id
	 * @return
	 */
	@Override
	public void deleteDepot(Long id,DataRow messageMap) throws Exception {
		Depot depot= new Depot();
		depot.setIsDelete(1);
		depot.setId(id);
		int row = depotMapper.updateById(depot);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
