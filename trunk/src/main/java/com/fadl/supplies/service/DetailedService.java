package com.fadl.supplies.service;
 
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Detailed;
import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 耗材模板明细 服务类
 * </p>
 * @author 啊健
 * @since 2018-04-23
 */
public interface DetailedService extends IService<Detailed> {
	/**
	 * 查询详情页面列表
	 * @param pageing
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public void queryTemplateDatelis(Page<DataRow> pageing,String templateId)throws Exception;
	/**
	 *  批量插入(用)
	 * @param detailed
	 * @throws Exception
	 */
	public void insertDetailed(List<Detailed> detailed)throws Exception;
	/**
     * 采浆模板消耗耗材(CJ)0.失败(耗材不足,扣除不了)   1.成功
     * @param data(apply(用于模块),templateId(模板ID))
     * @throws Exception 
     */
	public DataRow useDetailed(Long templateId,DataRow messageMap)throws Exception;
}
