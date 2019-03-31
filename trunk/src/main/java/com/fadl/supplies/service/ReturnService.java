package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Return;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材退还记录表 服务类
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface ReturnService extends IService<Return> {
	/**
	 * 耗材退还首页列表
	 * @param pageing
	 * @param retur
	 * @throws Exception
	 */
	public void queryReturnList(Page<DataRow> pageing,Return retur)throws Exception;
	/**
	 * 详情页面
	 * @param pageing
	 * @param retur
	 * @throws Exception
	 */
	public void queryReturnDatelis(Page<DataRow> pageing,Map<String,String> map)throws Exception;
	/**
	 * 获取退订耗材存入退还表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public void insertReturn(Map<String,String> map,DataRow messageMap) throws Exception;
	/**
	 * 修改退还订单信息
	 * @param retur
	 * @return
	 */
	public void updateReturn(Return retur,DataRow messageMap)throws Exception;
	/**
	 * 审核批准、库存表耗材改变状态
	 * @param return
	 * @return
	 */
	public void updateReturnStatus(Return retur,DataRow messageMap)throws Exception;

}
