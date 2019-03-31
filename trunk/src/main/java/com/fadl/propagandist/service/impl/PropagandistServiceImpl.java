package com.fadl.propagandist.service.impl;

import com.fadl.common.DataRow;
import com.fadl.propagandist.entity.Propagandist;
import com.fadl.propagandist.dao.PropagandistMapper;
import com.fadl.propagandist.service.PropagandistService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宣传员 服务实现类
 * </p>
 *
 * @author guokang
 * @since 2018-05-10
 */
@Service
public class PropagandistServiceImpl extends ServiceImpl<PropagandistMapper, Propagandist> implements PropagandistService {
	
	
	@Autowired
	PropagandistMapper propagandistMapper;
	/**
	 * 新增宣传员
	 */
	@Override
	public void insertPropagandist(Propagandist propagandist,DataRow messageMap) throws Exception {
		int row = propagandistMapper.insert(propagandist);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	/**
	 * 修改宣传员
	 */
	@Override
	public void updatePropagandist(Propagandist propagandist,DataRow messageMap)throws Exception {
		int row = propagandistMapper.updateById(propagandist);
		if(row>0){
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
	 /**
     * 查询扩展员、业务员信息 
     * @return
     * @throws SQLException
     */
	@Override
	public List<Propagandist> queryPropagandistInfo() throws SQLException {
		return propagandistMapper.queryPropagandistInfo();
	}
}
