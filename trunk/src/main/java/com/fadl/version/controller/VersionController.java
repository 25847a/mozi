package com.fadl.version.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.version.entity.Version;
import com.fadl.version.service.VersionService;

/**
 * <p>
 * 系统版本表 前端控制器
 * </p>
 *
 * @author CJ
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/version")
public class VersionController extends AbstractController{
	
		private static Logger logger  = LoggerFactory.getLogger(VersionController.class);
		
		@Autowired
		VersionService versionService;
		/**
		 * 跳转版本页面
		 * @return
		 */
		@RequestMapping("/versionPage")
		public String versionPage(){
			return "/collectionConfig/version";
		}
		 /**
	     * 跳转版本添加页面
	     */
	    @RequestMapping("/versionAdd")
	    public String versionAdd() {
	        return "/collectionConfig/version_add";
	    }
	    /**
	     * 查询版本列表
	     * @return
	     */
	    @RequestMapping("/queryVersionList")
	    @ResponseBody
	    public DataRow queryVersionList() {
	    	try {
	    		List<Version> v=versionService.selectList(null);
	    		messageMap.initSuccess(v);
			} catch (Exception e) {
				logger.error("VersionController>>>>>>>>queryVersionList>>>>>>>>>>>>",e);
			}
			return messageMap;
	    }
	    /**
	     * 获取当前版本号
	     * @return
	     */
	    @RequestMapping("/queryVersionOne")
	    @ResponseBody
	    public DataRow queryVersionOne(Version version) {
	    	try {
	    		EntityWrapper<Version> ew = new EntityWrapper<Version>();
	    		if(null!=version.getId()) {
	    			ew.eq("id", version.getId());
	    		}
	    		ew.last("ORDER BY createDate DESC LIMIT 1");
	    		Version v=versionService.selectOne(ew);
	    		messageMap.initSuccess(v);
			} catch (Exception e) {
				logger.error("VersionController>>>>>>>>queryVersionOne>>>>>>>>>>>>",e);
			}
			return messageMap;
	    }
	    /**
	     * 新增版本
	     * @param v
	     * @return
	     */
	    @RequestMapping("/insertVersion")
	    @ResponseBody
	    public DataRow insertVersion(Version v) {
	    	try {
	    		versionService.insertVersion(v,messageMap);
			} catch (Exception e) {
				logger.error("VersionController>>>>>>>>insertVersion>>>>>>>>>>>>",e);
			}
			return messageMap;
	    }
}

