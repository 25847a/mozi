package com.fadl.inspection.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.inspection.entity.SpecimenCollection;
import com.fadl.workflow.common.WorkFlow;


/**
 * <p>
 * 检验-标本采集 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-07
 */
public interface SpecimenCollectionService extends IService<SpecimenCollection> {
	
	/**
	 * 获取血浆采集记录,带分页和日期查询
	 * @param page
	 * @param collection
	 * @return
	 */
	Page<DataRow> queryListByCreateDateAndIsCollection(Page<DataRow> page, SpecimenCollection collection) throws Exception;
	/**
	 * 根据ID查找实体的方法,方法会返回浆员的部分个人信息
	 * @param ID
	 * @return
	 */
	DataRow querySpecimenCollectionByEntity(Long id);
	
	/**
	 * 根據ID更新是否取小樣
	 * @param collection
	 */
	void updateWithCollection(SpecimenCollection collection);
	
	/**
	 * 根据ID更新标本采集属性
	 */
	boolean updateEntityById(SpecimenCollection collection, DataRow dataRow) throws Exception;
	
	/**
	 * 根据实体集合更新标本采集属性
	 *
	 * @param collections
	 * @param dataRow
	 * @return
	 * @throws Exception
	 */
	boolean updateBatchEntityByIds(List<SpecimenCollection> collections, DataRow dataRow) throws Exception;
	
	
	/**
	 * 根据allId更新下一步操作
	 * @param allId
	 * @param dataRow
	 * @param int  子流程
	 * @return boolean
	 */
	boolean nextWorkFlow(Long allId, DataRow dataRow,int wflow) throws Exception;
	/**
	 * 根据allId撤回下一步操作
	 * @param allId
	 * @param WorkFlow
	 * @param dataRow
	 * @return boolean
	 */
	boolean retractWorkFlow(Long allId, WorkFlow wf, DataRow dataRow) throws Exception;
	
	/**
	 * 浆员采小血打印小票
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	HashMap<String, String> printSpecimenCollection(Long id, Long isSpecimen)throws Exception;
	
	/**
	 * 根据小样号前面日期得到所有被使用的小样号集合
	 * @param dateStr
	 * @return
	 */
	List<String> selectAllSampleNoByDateStr(String dateStr)throws Exception;
	
	/**
	 *  根据修改时间查询送样的报表记录
	 * @param updateDate
	 * @return
	 */
	List<DataRow> querySendInfosByUpdateDate(String updateDate)throws Exception;
}
