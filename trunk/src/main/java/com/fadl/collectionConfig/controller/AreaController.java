package com.fadl.collectionConfig.controller;

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
import com.fadl.collectionConfig.entity.Area;
import com.fadl.collectionConfig.service.AreaService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;

/**
 * <p>
 * 区域设置表 前端控制器
 * </p>
 *
 * @author guokang
 * @since 2018-05-24
 */
@Controller
@RequestMapping("/area")
public class AreaController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	AreaService areaService;
	
	/**
	 * 跳转区域设置页面
	 * @return
	 */
	@RequestMapping("/area")
	@RequiresPermissions("area:view")
	public String area() {
		return "/collectionConfig/area";
	}
	
	/**
	 * 跳转区域设置新增页面
	 * @return
	 */
	@RequestMapping("/areaAdd")
	@RequiresPermissions("area:add")
	public String areaAdd() {
		return "/collectionConfig/area_add";
	}
	
	/**
	 * 跳转区域设置修改页面
	 * @return
	 */
	@RequestMapping("/areaDetails")
	@RequiresPermissions("area:update")
	public String areaDetails(String id,Model model ) {
		try {
			Area area = new Area();
			area = areaService.selectById(id);
    		model.addAttribute("area",area);
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>areaDetails>>>>>",e);
		}
		return "/collectionConfig/area_details";
	}
	
	/**
	 * 区域设置列表
	 * @param area
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/queryAreaList",method= RequestMethod.POST)
	@InvokeLog(name = "queryAreaList", description = "区域设置列表")
	@ResponseBody
	@RequiresPermissions("area:view")
	public DataRow queryAreaList(Area area, Integer page, Integer limit) {
		Page<Area> paging = null;
		try {
			EntityWrapper<Area> ew=new EntityWrapper<Area>();
			ew.where("isDelete=0");
	        ew.like("town", area.getTown(),SqlLike.DEFAULT);
	        if(area.getCounty()!=null && area.getCounty()!=""){
	        	ew.eq("county", area.getCounty());
	        }
			paging = new Page<Area>(page, limit);
			paging = areaService.selectPage(paging, ew);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>queryAreaList",e);
		}
		return messageMap;
	}
	/**
	 * 新增区域设置
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/insertArea",method= RequestMethod.POST)
	@InvokeLog(name = "insertArea", description = "新增区域设置")
	@ResponseBody
	@RequiresPermissions("area:add")
	public DataRow insertArea(Area area) {
		try {
			EntityWrapper<Area> ew = new EntityWrapper<Area>();
			ew.eq("town", area.getTown());
			List<Area> a = areaService.selectList(ew);
			if(null==a || a.size()==0){
				area.setIsDelete(0);
				areaService.insertArea(area,messageMap);
			}else {
				messageMap.initFial("此乡镇已添加！");
			}
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>insertArea",e);
		}
		return messageMap;
	}
	/**
	 * 修改区域设置
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/updateArea",method= RequestMethod.POST)
	@InvokeLog(name = "updateArea", description = "修改区域设置")
	@ResponseBody
	@RequiresPermissions("area:update")
	public DataRow updateArea(Area area) {
		try {
			areaService.updateArea(area,messageMap);
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>updateArea",e);
		}
		return messageMap;
	}
	/**
	 * 删除区域设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteArea",method= RequestMethod.POST)
	@InvokeLog(name = "deleteArea", description = "删除区域设置")
	@ResponseBody
	@RequiresPermissions("area:del")
	public DataRow deleteArea(@RequestParam Long ids) {
		try {
			Area area = new Area();
			area.setIsDelete(1);
			area.setId(ids);
			boolean res = areaService.updateById(area);
			if(res){
				messageMap.initSuccess();
			}else {
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>deleteArea",e);
		}
		return messageMap;
	}
	
	/**
	 * 查询县表
	 * @return
	 */
	@RequestMapping(value="/queryAreaByType",method= RequestMethod.POST)
	@InvokeLog(name = "queryAreaByType", description = "查询县表")
	@ResponseBody
	public DataRow queryAreaByType() {
		EntityWrapper<Area> ew = new EntityWrapper<Area>();
		ew.where("isDelete=0" );
		ew.groupBy("county");
		try {
			List<Area> list =areaService.selectList(ew);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>queryAreaByType",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
	 * 根据截取身份证地址判断合法区域
	 * @param address
	 * @return
	 */
	@RequestMapping(value="/judgeArea",method= RequestMethod.POST)
	@InvokeLog(name = "judgeArea", description = "根据截取身份证地址判断合法区域")
	@ResponseBody
	public Area judgeArea(String address){
	   Area area=null;
	   try {
	        area = areaService.judgeArea(address);
	   } catch (Exception e) {
	      logger.error("AreaController>>>>>>>>>>>>>judgeArea",e);
	   }
	   return area;
	}
	/**
	 * 查询区域信息
	 * @return
	 */
	@RequestMapping(value="queryAreaInfo", method= RequestMethod.POST)
	@ResponseBody
	public DataRow queryAreaInfo() {
		try {
			List<Area> list =areaService.selectList(null);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("AreaController>>>>>>>>>>>>>queryAreaInfo",e);
		}
		return messageMap;
	}
}

