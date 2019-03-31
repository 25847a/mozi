package com.fadl.log.controller;


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

import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.data.MySQLDatabaseBackup;
import com.fadl.log.service.BackupService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wj
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/backup")
public class BackupController extends AbstractController{
	@Autowired
	MySQLDatabaseBackup mysqlDataBaseBackup;
	@Autowired
	BackupService backupService;
	private static Logger logger = LoggerFactory.getLogger(BackupController.class);
	
	/**
	 * 跳转数据备份查询页面
	 * @return
	 */
	@RequestMapping("/backupPage")
	@RequiresPermissions("backup:view")
	public String log() {
		return "/user/database_backup";
	}
	
	/**
	 * 数据备份查询列表
	 * @param log
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryBackUp",method= RequestMethod.POST)
	@InvokeLog(name = "queryBackUp", description = "数据备份查询列表")
	@ResponseBody
	@RequiresPermissions("backup:view")
	public DataRow queryBackUp(@RequestParam Map<String,Object> map, Integer page, Integer limit) {
		Page<DataRow> pageing = null;
        try {
        	pageing = new Page<DataRow>(page,limit);
        	backupService.queryBackUp(map, pageing);
            messageMap.initPage(pageing);
        } catch (Exception e) {
            logger.error("BackupController>>>>>>>>>queryBackUp>>>>>",e);
        }
        return messageMap;
		
	}
	
	/**
	 * 数据库备份
	 */
	@RequestMapping(value="/copyDataBase",method = RequestMethod.POST)
	@ResponseBody
	@InvokeLog(name = "copyDataBase", description = "数据备份")
	@RequiresPermissions("backup:add")
	public DataRow copyDataBase(){
		try {
			boolean flag=mysqlDataBaseBackup.exportDatabaseTool();
			if(flag){
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("BackupController>>>>>>>>>copyDataBase>>>>>>>>>",e);
		}
		return messageMap;
	}
	/**
	 * 删除备份信息
	 */
	@RequestMapping(value="/deleteDataBase",method = RequestMethod.POST)
	@ResponseBody
	@InvokeLog(name = "deleteDataBase", description = "删除备份信息")
	@RequiresPermissions("backup:del")
	public DataRow deleteDataBase(Long ids){
		try {
			boolean flag=mysqlDataBaseBackup.delFile(ids);
			if(flag){
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		} catch (Exception e) {
			logger.error("BackupController>>>>>>>>>deleteDataBase>>>>>>>>>",e);
		}
		return messageMap;
	}
}

