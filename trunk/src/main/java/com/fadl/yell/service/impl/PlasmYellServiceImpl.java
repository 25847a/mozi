package com.fadl.yell.service.impl;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.collection.entity.PlasmCollection;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.electrophoresis.entity.SerumElectrophoresis;
import com.fadl.electrophoresis.service.SerumElectrophoresisService;
import com.fadl.image.service.PlasmaImageService;
import com.fadl.immuneAssay.service.ImmuneService;
import com.fadl.log.service.LogService;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;
import com.fadl.registries.service.ProviderRegistriesService;
import com.fadl.workflow.common.WorkFlow;
import com.fadl.workflow.dao.WorkflowMapper;
import com.fadl.workflow.entity.Workflow;
import com.fadl.yell.dao.PlasmYellMapper;
import com.fadl.yell.entity.PlasmYell;
import com.fadl.yell.service.PlasmYellService;

/**
 * <p>
 * 采浆叫号 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class PlasmYellServiceImpl extends ServiceImpl<PlasmYellMapper, PlasmYell> implements PlasmYellService {

	@Autowired
	public PlasmYellMapper plasmYellMapper;
	@Autowired
	public PlasmaImageService plasmaImageService;
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	public WorkflowMapper workflowMapper;
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	ConfigService configService;
	@Autowired
	ImmuneService immuneService;
	@Autowired
	public RabatinfoService rabatinfoService;
	@Autowired
	public ProviderRegistriesService providerRegistriesService;
	@Autowired
	public SerumElectrophoresisService serumElectrophoresisService;
	@Autowired
	public LogService logService;
	
	/**
	 * 查询未体检/体检人员
	 */
	@Override
	public Page<DataRow> queryplasmaYellList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(plasmYellMapper.queryplasmaYellList(page, map));
	}

	/**
	 * 验证浆员
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmYellById(HashMap<String, String> map, DataRow messageMap) throws Exception {
		// 查询采浆验证信息
		PlasmYell plasmYell = plasmYellMapper.selectById(Long.valueOf(map.get("id")));
		if (plasmYell.getProviderNo().startsWith("f")) {
			messageMap.initFial("该浆员还未发卡，请发卡后再来采浆");
			return;
		}
		//如果浆员已经采浆验证，只更新数据
		if (plasmYell.getStatus()==1) {
			plasmYell.setType(Integer.valueOf(map.get("type")));
			plasmYell.setIsIdentity(Integer.valueOf(map.get("isIdentity")));
			int res = plasmYellMapper.updateById(plasmYell);
			if (res>0) {
				messageMap.initSuccess();
				return;
			}else{
				messageMap.initFial();
				return;
			}
		}
		Rabatinfo rabatinfo = new Rabatinfo();
		rabatinfo.setProviderNo(plasmYell.getProviderNo());
		DataRow row = rabatinfoService.queryProviderLastTime(rabatinfo);
		if (row.size()==0 || row.getInt("time") >= 365) {
			EntityWrapper<Rabatinfo> rabat = new EntityWrapper<Rabatinfo>();
			rabat.eq("allId", plasmYell.getAllId());
			rabatinfo = rabatinfoService.selectOne(rabat);
			if (null!=rabatinfo && rabatinfo.getIsCheck()==0) {
				messageMap.initFial("该浆员还未做大体检");
				return;
			}else if(rabatinfo.getResult()==1){
				messageMap.initFial("该浆员大体检不合格");
				return;
			}
		}
		//固定浆员做蛋白电泳
		fixedSerum(plasmYell, messageMap);
		
		// plasmYell.setId(Long.valueOf(map.get("id")));
		plasmYell.setStatus(1); // 状态改为 已验证
		plasmYell.setType(Integer.valueOf(map.get("type")));
		plasmYell.setIsIdentity(Integer.valueOf(map.get("isIdentity")));
		int res = plasmYellMapper.updateById(plasmYell);
		if (res > 0) {
			// 插入采浆表
			plasmYell = plasmYellMapper.selectById(plasmYell.getId());
			PlasmCollection plasmCollection = new PlasmCollection();
			plasmCollection.setAllId(plasmYell.getAllId());
			plasmCollection.setProviderNo(plasmYell.getProviderNo());
			boolean result = plasmCollectionService.insert(plasmCollection);
			if (result) {
				//插入操作日志
				logService.insertLog(IConstants.YELL, IConstants.DESC.replace("{0}", "已验证该浆员"),plasmYell.getProviderNo());
				
				// 更新流程表
				Workflow wf = workflowMapper.selectByAllId(plasmYell.getAllId());
				String[] workStep = wf.getWorkStep().split(",");
				char[] work = workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()].toCharArray();
				work[WorkFlow.Collection.VALIDATE.ordinal()] = '2'; // 采浆验证标记为已完成
				work[WorkFlow.Collection.COLLECTION.ordinal()] = '1'; // 采浆流程标记为进行中
				workStep[WorkFlow.WorkFlowEnum.COLLECTION.ordinal()] = new String(work);
				wf.setWorkStep(StringUtils.join(workStep, ","));
				res = workflowMapper.updateById(wf);
				if (res > 0) {
					// 插入现场图片
					plasmaImageService.saveImage(map.get("imageFace"), 3, plasmYell.getId(), messageMap);
				} else {
					messageMap.initFial();
				}
			}
			//immuneRegister(plasmYell.getProviderNo(),messageMap);
		} else {
			messageMap.initFial();
		}
	}
	
	/**
	 * 蛋白电泳
	 * @param plasmYell
	 * @param messageMap
	 * @throws Exception
	 */
	public void fixedSerum(PlasmYell plasmYell,DataRow messageMap) throws Exception{
		//查询浆员是否需要做血清电泳
		serumElectrophoresisService.queryInfoByProviderNo(plasmYell.getProviderNo(), messageMap);
		if (messageMap.getBoolean(IConstants.RESULT_DATA)) {
			SerumElectrophoresis serum = new SerumElectrophoresis();
			serum.setProviderNo(plasmYell.getProviderNo());
			serum.setAllId(plasmYell.getAllId());
			serumElectrophoresisService.insert(serum);
			//页面标识，表示需要打印 蛋白电泳 条码票
			messageMap.put("isSerum", true);
		}else{
			//页面标识，表示需要打印 蛋白电泳 条码票
			messageMap.put("isSerum", false);
		}
	}

	/**
	 * 取消喊号
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void cancelPlasmYell(HashMap<String, String> map, DataRow messageMap) throws Exception {
		Long id = Long.valueOf(map.get("id"));
		PlasmYell plasmYell = plasmYellMapper.selectById(id);
		if (null != plasmYell) {
			EntityWrapper ew = new EntityWrapper();
			ew.setEntity(new PlasmCollection());
			ew.eq("allId", plasmYell.getAllId());
			PlasmCollection plasmCollection = plasmCollectionService.selectOne(ew);
			if (plasmCollection.getIsCollection() == 1) {
				messageMap.initFial("该浆员已经采浆，不能取消喊号");
				return;
			}
			// 删除采浆数据
			boolean result = plasmCollectionService.deleteById(plasmCollection.getId());
			if (result) {
				//取消喊号删除原图片
				plasmaImageService.deletePlasmaImage(plasmCollection.getId(), 3);
				//插入操作日志
				logService.insertLog(IConstants.YELL, IConstants.DESC.replace("{0}", "已取消采浆验证"),plasmYell.getProviderNo());
				// 更新喊号状态
				plasmYell.setStatus(0);// 更新状态为未喊号
				plasmYell.setType(null);
				plasmYell.setIsIdentity(null);
				result = this.updateAllColumnById(plasmYell);
				if (result) {
					messageMap.initSuccess();
				} else {
					messageMap.initFial();
				}
			} else {
				messageMap.initFial();
			}
		} else {
			messageMap.initFial("浆员不存在");
		}
	}

	/**
	 * 采浆验证打印条码
	 */
	@Override
	public HashMap<String, String> queryPlasmYellPrintInfo(String allId) throws SQLException {
		return plasmYellMapper.queryPlasmYellPrintInfo(allId);
	}
	/**
	 * 采浆前验证免疫
	 * @param providerNo
	 * @param messageMap
	 * @throws Exception
	 */
	public void verifyingImmunity(String providerNo,DataRow messageMap)throws Exception{
		Config config =configService.getConfig("open_config", "front");
		if(config.getIsDisable()==0) {
			Config con = configService.queryConfig("immune_type", "1");
			if(con.getIsDisable()==1) {//如果禁用，执行莱士的
				immuneService.immuneRegisterAlone(providerNo, messageMap);
			}else {
				immuneService.immuneRegister(providerNo,messageMap);
			}
		}else {
			messageMap.initSuccess();
		}
	}
	
	
}
