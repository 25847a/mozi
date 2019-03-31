package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Destroy;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 耗材销毁信息表 服务类
 * </p>
 *
 * @author 啊健
 * @since 2018-05-03
 */
public interface DestroyService extends IService<Destroy> {
	/**
	 * 耗材销毁首页列表
	 * @param pageing
	 * @param destroy
	 * @throws Exception
	 */
	public void queryDestroyList(Page<DataRow> pageing,Destroy destroy)throws Exception;
	/**
	 * 详情页面
	 * @param pageing
	 * @param map
	 * @throws Exception
	 */
	public void queryDestroyDatelis(Page<DataRow> pageing,Map<String,String> map)throws Exception;
	/**
	 * 获取退订耗材存入退还表
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public void insertDestroy(Map<String,String> map,DataRow messageMap) throws Exception;
	/**
	 * 修改销毁订单信息
	 * @param retur
	 * @return
	 */
	public void updateDestroy(Destroy destroy,DataRow messageMap) throws Exception;
	/**
	 * 修改销毁订单审批状态
	 * @param destroy
	 * @param messageMap
	 */
	public void updateDestroyStatus(Destroy destroy, DataRow messageMap) throws Exception;
}
