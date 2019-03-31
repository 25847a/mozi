package com.fadl.check.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.check.entity.Check;
import com.fadl.check.service.CheckService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 体检记录表 前端控制器 (老浆员)
 * </p>
 *
 * @author wangjing
 * @since 2017-03-12
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@RequestMapping("/check")
public class CheckController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CheckController.class);
	 
	@Autowired
	public CheckService checkService;
	
	/**
	 * 跳转体检页面
	 * @return
	 */
	@RequestMapping("/checkList")
	@RequiresPermissions("check:view")
	public String checkList(){
		return "/check/check_list";
	}
	
	/**
	 * 跳转新献浆员体检页面
	 * @return
	 */
	@RequestMapping("/newCheckList")
	@RequiresPermissions("newCheck:view")
	public String newCheckList(){
		return "/check/new_check_list";
	}
	
	/**
	 * 打印体格表（正面）
	 * @return
	 */
	@RequestMapping("/checkPrint")
	@InvokeLog(name = "checkPrint", description = "打印体格表正面")
	public String checkPrint(){
		return "/check/check_print";
	}
	
	/**
	 * 打印体格表（反面）
	 * @return
	 */
	@RequestMapping("/checkPrintF")
	@InvokeLog(name = "checkPrintF", description = "打印体格表反面")
	public String checkPrintF(){
		return "/check/check_print_f";
	}
	
	/**
	 * 跳转新献浆员体检 高级查询页面
	 * @return
	 */
	@RequestMapping("/checkQueryList")
	public String checkQueryList(){
		return "/check/check_query_list";
	}
	
	/**
	 * 打印献血浆者体检记录表
	 * @return
	 */
	@RequestMapping("/printCheck")
	public String printCheck(){
		return "/check/print_check";
	}
	
	/*********************     老浆员     ******************/
	/**
	 * 查询已体检人员 
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryCheckList",method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckList", description = "查询已体检人员")
	@ResponseBody
	@RequiresPermissions("check:view")
	public DataRow queryCheckList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p = checkService.queryCheckList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckList",e);
		}
		return messageMap;
	}
	
	/*********************     老浆员     ******************/
	
	/*********************     新浆员     ******************/
	/**
	 * 查询已体检人员 
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryNewCheckList",method = RequestMethod.POST)
	@InvokeLog(name = "queryNewCheckList", description = "查询已体检人员 ")
	@ResponseBody
	@RequiresPermissions("newCheck:view")
	public DataRow queryNewCheckList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			p = checkService.queryCheckList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryNewCheckList",e);
		}
		return messageMap;
	}
	
	/*********************     新浆员     ******************/
	
	/**
	 * 根据浆员编号查询浆员上次体检详情
	 * @return
	 */
	@RequestMapping(value="/queryNotCheckPrevDetail",method = RequestMethod.POST)
	@InvokeLog(name = "queryNotCheckPrevDetail", description = "根据浆员编号查询浆员上次体检详情")
	@ResponseBody
	public DataRow queryNotCheckPrevDetail(@RequestParam String providerNo){
		try {
			EntityWrapper ew=new EntityWrapper();
			ew.eq("providerNo", providerNo);
			ew.orderBy("createDate", false);
			ew.last("LIMIT 0,1");
			Check check = checkService.selectOne(ew);
			messageMap.initSuccess(check);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryNotCheckPrevDetail",e);
		}
		return messageMap;
	}
	/**
	 * 读卡机获取部分浆员数据
	 * @param map
	 * @return
	 */
	@RequestMapping("/queryCheckHeadInfo")
	@ResponseBody
	public DataRow queryCheckHeadInfo(@RequestParam Map<String,String> map) {
		try {
			checkService.queryCheckHeadInfo(map,messageMap);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckHeadInfo",e);
		}
		return messageMap;
		
	}
 	/**
	 * 根据 id 查询体检详情
	 * @return
	 */
	@RequestMapping(value="/queryCheckById",method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckById", description = "根据 id 查询体检详情")
	@ResponseBody
	public DataRow queryCheckById(@RequestParam HashMap<String, String> map){
		try {
			if(map.get("isCheck").equals("0")){
				EntityWrapper<Check> ew = new EntityWrapper<Check>();
				ew.eq("providerNo", map.get("providerNo"));
				ew.lt("createDate", DateUtil.formatDate(new Date(),DateUtil.yyyy_MM_dd_EN));
				ew.orderBy("createDate", false);
				ew.last("LIMIT 0,1");
				Check check = checkService.selectOne(ew);
				messageMap.initSuccess(check);
			}else{
				Check check = checkService.selectById(map.get("id"));
				messageMap.initSuccess(check);
			}
			
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckById",e);
		}
		return messageMap;
	}
	
	/**
	 * 保存或更新体检信息
	 * @return
	 */
	@RequestMapping(value="/updateChcekInfo",method = RequestMethod.POST)
	@InvokeLog(name = "updateChcekInfo", description = "保存或更新体检信息")
	@ResponseBody
	@RequiresPermissions("check:update")
	public DataRow updateChcekInfo(Check check,@RequestParam Integer isRoadFee){
		try {
			//修改状态为已体检
			check.setIsCheck(1);
			checkService.updateCheck(check,isRoadFee,messageMap);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>updateChcekInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 取消体检信息
	 * @return
	 */
	@RequestMapping(value="/cacalCheckInfo",method = RequestMethod.POST)
	@InvokeLog(name = "cacalCheckInfo", description = "取消体检信息")
	@ResponseBody
	@RequiresPermissions("check:cacal")
	public DataRow cacalCheckInfo(@RequestParam Long id){
		try {
			checkService.cacalCheckInfo(id, messageMap);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>cacalCheckInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 献浆员体检高级查询
	 * @return
	 */
	@RequestMapping(value="/queryCheckQueryList",method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckQueryList", description = "献浆员体检高级查询")
	@ResponseBody
	@RequiresPermissions("check:query")
	public DataRow queryCheckQueryList(@RequestParam HashMap<String, String> data,Integer page, Integer limit){
		try {
			Page<DataRow> p = new Page<DataRow>(page,limit);
			checkService.queryCheckQueryList(data, p);
			messageMap.initPage(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryNewCheckQueryList",e);
		}
		return messageMap;
	}
	
	/**
	 * 重检
	 * @return
	 */
	@RequestMapping(value="/restCheck",method = RequestMethod.POST)
	@InvokeLog(name = "restCheck", description = "献浆员体检高级查询")
	@ResponseBody
	public DataRow restCheck(Check check){
		try {
			checkService.restCheck(check,messageMap);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>restCheck",e);
		}
		return messageMap;
	}
	
	/**
	 * 打印体检记录
	 * @return
	 */
	@RequestMapping(value="/queryCheckRecordList",method = RequestMethod.POST)
	@InvokeLog(name = "queryCheckRecordList", description = "打印体检记录")
	@ResponseBody
	public DataRow queryCheckRecordList(@RequestParam HashMap<String, String> data){
		try {
			List<DataRow> list = checkService.queryCheckRecordList(data);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckRecordList",e);
		}
		return messageMap;
	}
	/**
	 * 读卡判断是否存在此浆员
	 * @return
	 */
	@RequestMapping(value="/queryCheckRecordDu",method = RequestMethod.POST)
	@ResponseBody
	public DataRow queryCheckRecordDu(Check check){
		try {
			EntityWrapper<Check> ew = new EntityWrapper<Check>();
			ew.eq("providerNo", check.getProviderNo());
			ew.eq("DATE_FORMAT(updateDate,'%Y-%m-%d')", check.getUpdateDate());
			Check p =checkService.selectOne(ew);
			messageMap.initSuccess(p);
		} catch (Exception e) {
			logger.error("CheckController>>>>>>>>>queryCheckRecordDu",e);
		}
		return messageMap;
	}
}
