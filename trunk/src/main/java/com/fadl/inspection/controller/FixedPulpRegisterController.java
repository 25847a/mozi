package com.fadl.inspection.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.SystemSeqService;
import com.fadl.inspection.entity.FixedPulpRegister;
import com.fadl.inspection.service.FixedPulpRegisterService;

/**
 * <p>
 * 固定浆员检验登记 前端控制器
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-16
 */
@Controller
@RequestMapping("/fixedPulpRegister")
public class FixedPulpRegisterController extends AbstractController{

	@Autowired
	private FixedPulpRegisterService fixedPulpRegisterService;
	@Autowired
	private SystemSeqService seqService; 
	private static Logger logger = LoggerFactory.getLogger(FixedPulpRegisterController.class);
	
	/**
	 * 跳转固定浆员登记页面
	 * 
	 * @return
	 */
	@RequestMapping("/initPage")
	@RequiresPermissions("fixedprc:view")
	public String initPage() {
		return "/business/inspection/in_deconfiguration";
	}
	/**
	 * 跳转样本拒收页面
	 * 
	 * @return
	 */
	@RequestMapping("/initRefusePage")
	@RequiresPermissions("fixedprc:update")
	public String initRefusePage() {
		return "/business/inspection/refuse";
	}
	
	/**
	 * 跳转样本拒收打印页面
	 * @param model
	 * @param printDate
	 * @return
	 */
	@RequestMapping("/initRefusePrintPage")
	//TODO @RequiresPermissions("fixedprc:print")
	public String initRefusePrintPage(Model model, String printDate) {
		model.addAttribute("printDate", printDate);
		// 查找拒收的记录
		try {
			List<DataRow> ds = fixedPulpRegisterService.queryRefuseInfosByUpdateDate(printDate);
			model.addAttribute("list", ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/business/inspection/a_aship";
	}
	
	/**
	 * 样本拒收
	 * @param allId
	 * @return
	 */
	@RequestMapping(value = "/updateWithStatus", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithStatus", description = "样本拒收")
	@ResponseBody
	@RequiresPermissions("fixedprc:update")
	public DataRow updateWithStatus(Long allId, Integer refuseRemark) {
		try {
			Wrapper<FixedPulpRegister> ew = new EntityWrapper<FixedPulpRegister>();
			ew.eq("allId", allId);
			FixedPulpRegister fixedPulpRegister = fixedPulpRegisterService.selectOne(ew);
			// 查不到浆员信息就提示没有浆员信息,不拒收
			if(null == fixedPulpRegister) {
				messageMap.initFial("没有该浆员信息");
				return messageMap;
			}
			// 设置拒收
			fixedPulpRegister.setStatus(1);
			// 设置拒收备注
			fixedPulpRegister.setRefuseRemark(refuseRemark);
			if(fixedPulpRegisterService.updateAllColumnById(fixedPulpRegister))
			messageMap.initSuccess();
		} catch (Exception e) {
			messageMap.initFial("没有该浆员信息");
			logger.error("FixedPulpRegisterController>>>>>>>>>updateWithStatus>>>>>", e);
		}
		return messageMap;
	}
	
    /**
     * 获取送样记录,根据更新时间
     * @param sendDate
     * @return
     */
    @RequestMapping(value="/getSendInfoBySendDate" , method=RequestMethod.POST)
    @ResponseBody
    public DataRow getSendInfoBySendDate(String sendDate) {
    	try {
			List<DataRow> ds = fixedPulpRegisterService.querySendInfosByUpdateDate(sendDate);
			messageMap.initSuccess(ds);
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>getSendInfoBySendDate>>>>>", e);
		}
    	return messageMap;
    }
	
    /**
     * 获取拒收记录,根据更新时间
     * @param sendDate
     * @return
     */
    @RequestMapping(value="/getRefuseInfoBySendDate" , method=RequestMethod.POST)
    @ResponseBody
    public DataRow getRefuseInfoBySendDate(String sendDate) {
    	try {
			List<DataRow> ds = fixedPulpRegisterService.queryRefuseInfosByUpdateDate(sendDate);
			messageMap.initSuccess(ds);
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>getSendInfoBySendDate>>>>>", e);
		}
    	return messageMap;
    }
	/**
	 * 根据日期查询采小血的记录 带分页
	 * 
	 * @param blood
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryListByCreateDate", method = RequestMethod.POST)
	@InvokeLog(name = "queryListByCreateDate", description = "根据日期查询采小血的记录 带分页")
	@ResponseBody
	@RequiresPermissions("fixedprc:view")
	public DataRow queryListByCreateDate(FixedPulpRegister fixedPulpRegister, Integer page, Integer limit) {
		try {
			Page<DataRow> dr = new Page<DataRow>(page, limit);
			Page<DataRow> page2 = fixedPulpRegisterService.queryListByCreateDateAndIsAssay(dr, fixedPulpRegister);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>queryListByCreateDate>>>>>", e);
		}
		return messageMap;

	}
	
	/**
	 * 根据id找献浆员送样记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryByID", method = RequestMethod.POST)
	@InvokeLog(name = "queryByID", description = "根据id找献浆员送样记录")
	@ResponseBody
	@RequiresPermissions("fixedprc:view")
	public DataRow queryByID(@NotEmpty Long id) {
		try {
			messageMap.initSuccess(fixedPulpRegisterService.queryFixedPulpRegisterById(id));
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>queryByID>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 根据AllId找献浆员送样记录
	 * 
	 * @param allId 
	 * @return
	 */
	@RequestMapping(value = "/queryByAllId", method = RequestMethod.POST)
	@InvokeLog(name = "queryByAllId", description = "根据AllId找献浆员送样记录")
	@ResponseBody
	@RequiresPermissions("fixedprc:view")
	public DataRow queryByAllId(@NotEmpty Long allId) {
		try {
			FixedPulpRegister entity = new FixedPulpRegister();
			entity.setAllId(allId);
			messageMap.initSuccess(fixedPulpRegisterService.queryFixedPulpRegisterByEntity(entity));
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>queryByID>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 固定浆员检验登记
	 * @param fixedPulpRegister
	 * @return
	 */
	@RequestMapping(value = "/updateWithAssay", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithAssay", description = "固定浆员检验登记")
	@ResponseBody
	@RequiresPermissions("fixedprc:update")
	public DataRow updateWithAssay(FixedPulpRegister fixedPulpRegister) {
		try {
			FixedPulpRegister fixedPulpRegister1 = fixedPulpRegisterService.selectById(fixedPulpRegister.getId());
			if(fixedPulpRegister.getIsAssay()==0) {
				fixedPulpRegister1.setSampleNo(null);
			}else {
				SystemSeq seq =	seqService.queryNewSystemSeqInfo(1);
				fixedPulpRegister1.setSampleNo(seq.getValue());
				fixedPulpRegister1.setReceivePerson(""+fixedPulpRegister1.getUpdater());
			}
			fixedPulpRegister1.setIsAssay(fixedPulpRegister.getIsAssay());
			fixedPulpRegisterService.updateAssayById(fixedPulpRegister1, messageMap);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>>queryListByCreateDate>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 固定浆员检验取消登记
	 * @param fixedPulpRegister
	 * @return
	 */
	@RequestMapping(value = "/updateWithAssay0", method = RequestMethod.POST)
	@InvokeLog(name = "updateWithAssay0", description = "固定浆员检验取消登记")
	@ResponseBody
	@RequiresPermissions("fixedprc:cancel")
	public DataRow updateWithAssay0(FixedPulpRegister fixedPulpRegister) {
		try {
			FixedPulpRegister fixedPulpRegister1 = fixedPulpRegisterService.selectById(fixedPulpRegister.getId());
			if(fixedPulpRegister.getIsAssay()==0) {
				fixedPulpRegister1.setSampleNo(null);
			}else {
				SystemSeq seq =	seqService.queryNewSystemSeqInfo(1);
				fixedPulpRegister1.setSampleNo(seq.getValue());
			}
			fixedPulpRegister1.setIsAssay(fixedPulpRegister.getIsAssay());
			fixedPulpRegisterService.updateAssayById(fixedPulpRegister1, messageMap);
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("FixedPulpRegisterController>>>>>>>>>queryListByCreateDate>>>>>", e);
		}
		return messageMap;
	}
}

