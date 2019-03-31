package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Loss;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材报损记录表 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-24
 */
public interface LossService extends IService<Loss> {
	/**
	 * 耗材退还首页列表
	 * @param pageing
	 * @param retur
	 * @throws Exception
	 */
	public void queryLossList(Page<DataRow> pageing,Loss loss)throws Exception;
	/**
	 * 详情页面
	 * @param pageing
	 * @param retur
	 * @throws Exception
	 */
	public void queryLossDatelis(Page<DataRow> pageing,Map<String,String> map)throws Exception;
	/**
	 * 获取退订耗材存入退还表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public void insertLoss(Map<String,String> map,DataRow messageMap) throws Exception;
	/**
	 * 修改报损订单信息
	 * @param retur
	 * @return
	 */
	public void updateLoss(Loss loss,DataRow messageMap)throws Exception;
	/**
	 * 修改报损订单审批状态
	 * @param updateLossStatus
	 * @return
	 */
	public void updateLossStatus(Loss loss, DataRow messageMap)throws Exception;
}
