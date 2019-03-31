package com.fadl.collection.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.collection.entity.PlasmCollection;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.cost.service.ICostGrantService;

/**
 * <p>
 * 采浆记录 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@Controller
@RequestMapping("/plasmCollection")
public class PlasmCollectionController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PlasmCollectionController.class);
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	public ICostGrantService iCostGrantService;
	
	/**
	 * 跳转体检页面
	 * @return
	 */
	@RequestMapping("/plasmCollectionList")
	@RequiresPermissions("plasmCollection:view")
	public String plasmCollectionList(){
		return "/plasm_collection/plasm_collection";
	}
	/**
	 * 跳转免疫采浆流程
	 * @return
	 */
	@RequestMapping("/immuneCollection")
	public String immuneCollection(@RequestParam Map<String,String> map,Model model) {
		model.addAttribute("map", map);
		return "/plasm_collection/immune_collection";
	}
	
	/**
	 * 跳转献血浆者采浆记录打印页面
	 * @return
	 */
	@RequestMapping("/printPlasmaCollection")
	public String printPlasmaCollection() {
		return "/plasm_collection/print_plasmaCollection";
	}
	
	
	/**
	 * 查询采浆记录列表
	 * @return
	 */
	@RequestMapping(value="/queryPlasmCollectionList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmCollectionList", description = "查询采浆记录列表")
	@ResponseBody
	@RequiresPermissions("plasmCollection:view")
	public DataRow queryPlasmCollectionList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			plasmCollectionService.queryPlasmCollectionList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryPlasmCollectionList",e);
		}
		return messageMap;
	}
	/**
	 * 获取头部信息(啊健)
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryCollectionHeadInfo")
	@ResponseBody
	public DataRow queryCollectionHeadInfo(PlasmCollection collection) {
		try {
			EntityWrapper<PlasmCollection> ew = new EntityWrapper<PlasmCollection>();
			ew.eq("providerNo", collection.getProviderNo());
			ew.eq("isCollection", collection.getIsCollection());
			ew.eq("allId", collection.getAllId());
			PlasmCollection plasm =plasmCollectionService.selectOne(ew);
			messageMap.initSuccess(plasm);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryCollectionHeadInfo",e);
		}
		return messageMap;
		
	}
	/**
	 * 修改采浆信息
	 * @return
	 */
	@RequestMapping(value="/updatePlasmCollection",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmCollection", description = "修改采浆信息")
	@ResponseBody
	@RequiresPermissions("plasmCollection:update")
	public DataRow updatePlasmCollection(PlasmCollection plasmCollection,@RequestParam BigDecimal money,@RequestParam String detail,@RequestParam String image,@RequestParam Integer bloodGrade){
		try {
			plasmCollectionService.updatePlasmCollection(plasmCollection,detail,money,image,bloodGrade,messageMap);
		} catch (Exception e) {
			if (!StringHelper.isEmpty(messageMap.getString(IConstants.RESULT_MESSAGE))) {
				messageMap.initFial(messageMap.getString(IConstants.RESULT_MESSAGE));
			}
			logger.error("PlasmCollectionController>>>>>>>>>updatePlasmCollection",e);
		}
		return messageMap;
	}
	/**
	 * "验证采浆后是否需要进行免疫流程
	 * @param providerNo
	 * @return
	 */
	@RequestMapping(value="/collectionAfterImmune")
	@InvokeLog(name = "collectionAfterImmune", description = "验证采浆后是否需要进行免疫流程")
	@ResponseBody
	public DataRow collectionAfterImmune(String providerNo) {
		try {
			plasmCollectionService.collectionAfterImmune(providerNo,messageMap);
		} catch (Exception e) {
			logger.error("PlasmYellController>>>>>>>>>collectionAfterImmune",e);
		}
		return messageMap;
	}
	/**
	 * 获取采浆详情
	 * @return
	 */
	@RequestMapping(value="/queryPlasmCollectionById",method = RequestMethod.POST)
	@InvokeLog(name = "queryPlasmCollectionById", description = "获取采浆详情")
	@ResponseBody
	public DataRow queryPlasmCollectionById(@RequestParam Long id){
		try {
			plasmCollectionService.queryPlasmCollectionById(id, messageMap);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryPlasmCollectionById",e);
		}
		return messageMap;
	}
	
	@RequestMapping("/collQueryList")
	public String collQueryList(){
		return "/plasm_collection/coll_query_list";
	}
	
	/**
	 * 采浆高级查询 
	 * @return
	 */
	@RequestMapping(value="/querySeniorQueryPlasmCollectionList",method = RequestMethod.POST)
	@InvokeLog(name = "querySeniorQueryPlasmCollectionList", description = "采浆高级查询 ")
	@ResponseBody
	@RequiresPermissions("seniorPlasmCollection:view")
	public DataRow querySeniorQueryPlasmCollectionList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p = plasmCollectionService.querySeniorQueryPlasmCollectionList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>querySeniorQueryPlasmCollectionList",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询采浆护士列表
	 * @return
	 */
	@RequestMapping(value="/queryCollectionAdminList",method = RequestMethod.POST)
	@InvokeLog(name = "queryCollectionAdminList", description = "查询采浆护士列表")
	@ResponseBody
	public DataRow queryCollectionAdminList(){
		try {
			List<DataRow> list = plasmCollectionService.queryCollectionAdminList();
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryCollectionAdminList",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询采浆活动费用
	 * @return
	 */
	@RequestMapping(value="/queryNextCollectionCost",method = RequestMethod.POST)
	@InvokeLog(name = "queryNextCollectionCost", description = "查询采浆活动费用")
	@ResponseBody
	public DataRow queryNextCollectionCost(@RequestParam Long allId){
		try {
			plasmCollectionService.queryNextCollectionCost(allId,messageMap);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryNextCollectionCost",e);
		}
		return messageMap;
	}
	
	/**
	 * 献血浆者采浆记录表
	 * @return
	 */
	@RequestMapping(value="/queryPrintPlasmaCollectionRecordList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPrintPlasmaCollectionRecordList", description = "献血浆者采浆记录表")
	@ResponseBody
	public DataRow queryPrintPlasmaCollectionRecordList(@RequestParam HashMap<String, String> data){
		try {
			List<DataRow> list = plasmCollectionService.queryPrintPlasmaCollectionRecordList(data);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryPrintPlasmaCollectionRecordList",e);
		}
		return messageMap;
	}
	/**
	 * 获取耗材信息
	 * @return
	 */
	@RequestMapping(value="/queryDetailedCollectionInfo",method = RequestMethod.POST)
	@InvokeLog(name = "queryDetailedCollectionInfo", description = "献血浆者采浆记录表")
	@ResponseBody
	public DataRow queryDetailedCollectionInfo(String data){
		try {
			plasmCollectionService.queryDetailedCollectionInfo(data,messageMap);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryPrintPlasmaCollectionRecordList",e);
		}
		return messageMap;
	}
	/**
	 * 读卡判断是否存在此浆员
	 * @return
	 */
	@RequestMapping(value="/queryDetailedCollectionDu",method = RequestMethod.POST)
	@ResponseBody
	public DataRow queryDetailedCollectionDu(PlasmCollection plasm){
		try {
			EntityWrapper<PlasmCollection> ew = new EntityWrapper<PlasmCollection>();
			ew.eq("providerNo", plasm.getProviderNo());
			ew.eq("DATE_FORMAT(updateDate,'%Y-%m-%d')", plasm.getUpdateDate());
			PlasmCollection p =plasmCollectionService.selectOne(ew);
			messageMap.initSuccess(p);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>queryDetailedCollectionDu",e);
		}
		return messageMap;
	}
	
	/**
	 * 跳转修改血浆重量页面
	 * @return
	 */
	@RequestMapping("/updatePlasmaWeightPage")
	public String updatePlasmaWeightPage(){
		return "/plasmaStock/update_plasmaWeight";
	}
	
	/**
	 * 修改浆量
	 * @return
	 */
	@RequestMapping(value="/updatePlasmaWeight",method = RequestMethod.POST)
	@InvokeLog(name = "updatePlasmaWeight", description = "修改血浆重量")
	@ResponseBody
	public DataRow updatePlasmaWeight(PlasmCollection plasm){
		try {
			plasmCollectionService.updatePlasmaWeight(plasm, messageMap);
		} catch (Exception e) {
			logger.error("PlasmCollectionController>>>>>>>>>updatePlasmaWeight",e);
		}
		return messageMap;
	}
}

