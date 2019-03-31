package com.fadl.propagandist.controller;

import java.util.List;

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

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.propagandist.entity.Propagandist;
import com.fadl.propagandist.service.PropagandistService;

/**
 * <p>
 * 宣传员 前端控制器
 * </p>
 *
 * @author hu&guokang
 * @since 2018-05-10
 */
@Controller
@RequestMapping("/propagandist")
public class PropagandistController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PropagandistController.class);
	
	@Autowired
	public PropagandistService propagandistService;
	@Autowired
    SystemSeqService systemSeqService;
	
	/**
	 * 跳转宣传员页面
	 * @return
	 */
	@RequestMapping("/propagandist")
	@RequiresPermissions("propagandist:view")
	public String propagandist() {
		return "/collectionConfig/propagandist";
	}
	
	/**
	 * 跳转宣传员新增页面
	 * @return
	 */
	@RequestMapping("/propagandistAdd")
	@RequiresPermissions("propagandist:add")
	public String propagandistAdd() {
		return "/collectionConfig/propagandist_add";
	}
	
	/**
	 * 跳转宣传员修改页面
	 * @return
	 */
	@RequestMapping("/propagandistDetails")
	public String propagandistDetails(String id,Model model ) {
		try {
			Propagandist propagandist = new Propagandist();
			propagandist = propagandistService.selectById(id);
    		model.addAttribute("propagandist",propagandist);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>propagandistDetails>>>>>",e);
		}
		return "/collectionConfig/propagandist_details";
	}
	
	/**
	 * 宣传员列表
	 * @param propagandist
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryPropagandist",method= RequestMethod.POST)
	@InvokeLog(name = "queryPropagandist", description = "宣传员列表")
	@ResponseBody
	@RequiresPermissions("propagandist:view")
	public DataRow queryPropagandist(Propagandist propagandist, Integer page, Integer limit) {
		Page<Propagandist> paging = null;
		try {
			EntityWrapper<Propagandist> ew=new EntityWrapper<Propagandist>();
	        ew.where("isDisable=0");
	        ew.like("name", propagandist.getName(),SqlLike.DEFAULT);
	        ew.like("IdNo", propagandist.getIdNo(),SqlLike.DEFAULT);
	        ew.like("tel", propagandist.getTel(),SqlLike.DEFAULT);
	        if(propagandist.getType()!=null && !"".equals(propagandist.getType())){
	        	ew.eq("type", propagandist.getType());
	        } 
	        if(propagandist.getSex()!=null && !"".equals(propagandist.getSex())){
	        	ew.eq("sex", propagandist.getSex());
	        }
	        if(propagandist.getLevel()!=null && !"".equals(propagandist.getLevel())){
	        	ew.eq("level", propagandist.getLevel());
			}
			paging = new Page<Propagandist>(page, limit);
			paging = propagandistService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>>>>>queryPropagandist",e);
		}
		return messageMap;
	}
	/**
	 * 新增宣传员
	 * @param propagandist
	 * @return
	 */
	@RequestMapping(value = "/insertPropagandist",method= RequestMethod.POST)
	@InvokeLog(name = "insertPropagandist", description = "新增宣传员")
	@ResponseBody
	@RequiresPermissions("propagandist:add")
	public DataRow insertPropagandist(Propagandist propagandist) {
		try {
			//新增自动生成工号
			SystemSeq providerNo = systemSeqService.queryNewSystemSeqInfo(7);
			propagandist.setProviderNo(String.valueOf(providerNo.getValue()));
			propagandist.setIsDisable(0);
			propagandistService.insertPropagandist(propagandist,messageMap);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>>>>>insertPropagandist",e);
		}
		return messageMap;
	}
	/**
	 * 修改宣传员
	 * @param propagandist
	 * @return
	 */
	@RequestMapping(value = "/updatePropagandist",method= RequestMethod.POST)
	@InvokeLog(name = "updatePropagandist", description = "修改宣传员")
	@ResponseBody
	@RequiresPermissions("propagandist:update")
	public DataRow updatePropagandist(Propagandist propagandist) {
		try {
			propagandistService.updatePropagandist(propagandist,messageMap);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>>>>>updatePropagandist",e);
		}
		return messageMap;
	}
	/**
	 * 删除宣传员
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deletePropagandist",method= RequestMethod.POST)
	@InvokeLog(name = "deletePropagandist", description = "删除宣传员")
	@ResponseBody
	@RequiresPermissions("propagandist:del")
	public DataRow deletePropagandist(@RequestParam Long ids) {
		try {
			Propagandist propagandist = new Propagandist();
			propagandist.setIsDisable(1);
			propagandist.setId(ids);
			boolean res = propagandistService.updateById(propagandist);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>>>>>deletePropagandist",e);
		}
		return messageMap;
	}
	/**
	 * 查询等级列表
	 * @return
	 */
	@RequestMapping(value="/queryPropagandistLevel",method= RequestMethod.POST)
	@InvokeLog(name = "queryPropagandistLevel", description = "查询等级列表")
	@ResponseBody
	public DataRow queryPropagandistLevel() {
		try {
			EntityWrapper<Propagandist> ew = new EntityWrapper<Propagandist>();
			ew.where("1=1");
			ew.groupBy("level");
			List<Propagandist> list =propagandistService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>>>>>queryPropagandistLevel",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
	 * 查询体检医生列表
	 * @return
	 */
	@RequestMapping(value="/queryPropagandistList",method = RequestMethod.POST)
	@InvokeLog(name = "queryPropagandistList", description = "查询体检医生列表")
	@ResponseBody
	public DataRow queryPropagandistList(){
		try {
			EntityWrapper<Propagandist> ew = new EntityWrapper<Propagandist>();
            ew.eq("type",1);
			List<Propagandist> list = propagandistService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>queryPropagandistList",e);
		}
		return messageMap;
	}
	/**
	 * 查询扩展员、业务员信息 
	 * @return
	 */
	@RequestMapping(value="/queryPropagandistInfo",method = RequestMethod.POST)
	@InvokeLog(name = "queryPropagandistInfo", description = "查询扩展员、业务员信息")
	@ResponseBody
	public DataRow queryPropagandistInfo(){
		try {
			List<Propagandist> list = propagandistService.queryPropagandistInfo();
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PropagandistController>>>>>>>>>queryPropagandistInfo",e);
		}
		return messageMap;
	}
}

