package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Imexport;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 库存进出表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
public interface ImexportService extends IService<Imexport> {
	/**
	 * 查询出入库流水表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	public void queryUsedList(Map<String,Object> map,Page<DataRow> pageing)throws Exception;
	/**
	 * 插入库存进出表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public void insertImexport(List<Imexport> t)throws Exception;
}
