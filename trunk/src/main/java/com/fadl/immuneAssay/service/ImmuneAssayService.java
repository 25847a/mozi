package com.fadl.immuneAssay.service;

import com.fadl.common.DataRow;
import com.fadl.immuneAssay.entity.ImmuneAssay;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 免疫化验 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
public interface ImmuneAssayService extends IService<ImmuneAssay> {
	/**
	 * 修改免疫流程配置状态
	 * @param type
	 * @param id
	 * @throws Exception
	 */
	public void updateImmuneConfigType(String type,String id,DataRow messageMap)throws Exception;
	/**
	 * 免疫化验   特免化验（未化验）
	 * @param workTime
	 * @return
	 * @throws Exception
	 */
	public void specialNotAssay (String createDate,Page<DataRow> paging) throws Exception;
	
	/**
	 * 免疫化验   特免化验（已化验）
	 * @param workTime
	 * @return
	 * @throws Exception
	 */
	public void specialHaveAssay (String createDate,Page<DataRow> paging) throws Exception;
	/**
	 * 免疫化验   特免化验  --> 发布化验
	 * @param entity
	 * @return
	 */
	public void publishAssay(ImmuneAssay entity,DataRow messageMap)throws Exception;
	/**
	 * 免疫化验   特免化验  --> 取消化验
	 * @param entity
	 * @return
	 */
	public void cancelAssay(ImmuneAssay entity,DataRow messageMap)throws Exception;
	/**
	 * 扫描器自动更新浆员信息
	 * @param allId
	 * @param messageMap
	 * @throws Exception
	 */
	public void updateAssayNumber (String allId,DataRow messageMap) throws Exception;
	/**
	 * 根据时间和卡号查询头部信息(啊健)
	 * @param providerNo
	 * @return
	 */
	public void queryHeadInfo (Map<String,String> map,DataRow messageMap) throws Exception;
	/**
	 * 免疫化验   特免检测查询
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<DataRow> querySpecialImmune (Map<String, String> map,Page<DataRow> page) throws Exception;
	
	/**
	 * 免疫化验   特免检测查询结果导出EXCEL
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> exportSpecialImmune(Map<String, String> map)throws Exception;

}
