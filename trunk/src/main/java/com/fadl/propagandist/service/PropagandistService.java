package com.fadl.propagandist.service;

import com.fadl.common.DataRow;
import com.fadl.propagandist.entity.Propagandist;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 宣传员 服务类
 * </p>
 *
 * @author guokang
 * @since 2018-05-10
 */
public interface PropagandistService extends IService<Propagandist> {
	/**
	 * 新增宣传员信息
	 * @param propagandist
	 * @param messageMap
	 * @throws Exception
	 */
	public void insertPropagandist(Propagandist propagandist,DataRow messageMap)throws Exception;
	/**
	 * 修改宣传员信息
	 * @param propagandist
	 * @param messageMap
	 * @throws Exception
	 */
	public void updatePropagandist(Propagandist propagandist,DataRow messageMap)throws Exception;
	 /**
     * 查询扩展员、业务员信息 
     * @return
     * @throws SQLException
     */
	 public List<Propagandist> queryPropagandistInfo()throws SQLException;
}
