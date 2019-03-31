package com.fadl.refuseInfo.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.refuseInfo.dao.RefuseInfoMapper;
import com.fadl.refuseInfo.entity.RefuseInfo;
import com.fadl.refuseInfo.service.RefuseInfoService;
import com.fadl.registries.dao.ProviderRegistriesMapper;

/**
 * <p>
 * 拒绝信息发布 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-12
 */
@Service
public class RefuseInfoServiceImpl extends ServiceImpl<RefuseInfoMapper, RefuseInfo> implements RefuseInfoService {

	@Autowired
	public RefuseInfoMapper refuseInfoMapper;
	@Autowired
	public ProviderBaseinfoService providerBaseinfoService;
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	ProviderRegistriesMapper providerRegistriesMapper;
	@Autowired
	ConfigService configService;
	
	/**
	 * 查询体检拒绝信息列表
	 */
	@Override
	public Page<DataRow> queryCheckRefuseInfoList(HashMap<String, String> map, Page<DataRow> page) throws SQLException {
		return page.setRecords(refuseInfoMapper.queryRefuseInfoList(page, map));
	}

	/**
	 * 查询化验拒绝信息列表
	 */
	@Override
	public Page<DataRow> queryAssayRefuseInfoList(HashMap<String, String> map, Page<DataRow> page) throws SQLException {
		return page.setRecords(refuseInfoMapper.queryRefuseInfoList(page, map));
	}

	/**
	 * 更新体检信息
	 * @param refuseInfo
	 * @param messageMap
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateRefuseInfo(RefuseInfo refuseInfo,DataRow messageMap)throws Exception{
		boolean res = true;
		RefuseInfo refuse = this.selectById(refuseInfo.getId());
		//取消发布
		if (refuseInfo.getIsRefuse()==0) {
			refuse.setIsRefuse(0);
			refuse.setOpinion(null);
			refuse.setDay(null);
			refuse.setReleaseId(null);
			res = this.updateAllColumnById(refuse);
		}else{
			//确认发布
			res = this.updateById(refuseInfo);
		}
		if (res) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("providerNo", refuse.getProviderNo()+"");
			//取消发布
			//  `plasmaState` int(1) DEFAULT '0' COMMENT '0.正常，1.暂时拒绝，2.永久拒绝',
			if (refuseInfo.getIsRefuse()==0) {
				map.put("plasmaState", "0");
			}else{
				//确认发布 (  `opinion` int(1) DEFAULT '0' COMMENT '医生意见( 0.暂时拒绝  1.永久淘汰 )', )
				map.put("plasmaState", (refuseInfo.getOpinion()+1)+"");
			}
			int result = providerBaseinfoService.updateProviderBaseinfoState(map);
			if (result>0) {
				//更新血浆信息(血浆报废)
				map = new HashMap<String, String>();
				map.put("providerNo", refuse.getProviderNo()+"");
				map.put("allId", refuse.getAllId()+"");
				map.put("status", refuseInfo.getIsRefuse()==0 ? "0":"1");
				plasmCollectionService.updatePlasmStatus(map);
				messageMap.initSuccess();
				return;
			}
		}
		messageMap.initFial();
		throw new SQLException();
	}

	/**
	 * 生物所反馈
	 */
	@Override
	public Page<DataRow> queryOtherRefuseInfoList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(refuseInfoMapper.queryOtherRefuseInfoList(page, map));
	}
	
	/**
	 * 生物所反馈拒绝信息
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void upateOtherRefuseInfo(RefuseInfo refuseInfo,DataRow messageMap)throws Exception{
		if (null!=refuseInfo.getId()) {
			updateRefuseInfo(refuseInfo, messageMap);
		}else{
			EntityWrapper<ProviderBaseinfo> ews = new EntityWrapper<ProviderBaseinfo>();
			ews.eq("providerNo", refuseInfo.getProviderNo());
			ProviderBaseinfo providerBaseinfo = providerBaseinfoService.selectOne(ews);
			if (null==providerBaseinfo) {
				messageMap.initFial("浆员信息不存在");
			}else{
				providerBaseinfo.setPlasmaState(refuseInfo.getOpinion()+1);
				boolean res = providerBaseinfoService.updateById(providerBaseinfo);
				if (res) {
					refuseInfo.setType(2);
					int a = refuseInfoMapper.insert(refuseInfo);
					if (a>0) {
						messageMap.initSuccess();
					}else{
						messageMap.initFial();
					}
				}
			}
		}
	}
	/**
   	 * 定时任务自动淘汰55-60岁小于两次采浆记录,60岁直接淘汰的记录
   	 * @throws Exception
   	 */
	@Override
   	public void queryEliminateYearOld()throws Exception{
		List<DataRow> list =providerRegistriesMapper.queryEliminateYearOld();
		Config con = configService.getConfig("fixed_config", "maxAge");//最大年龄
		Config config = configService.getConfig("fixed_config","age");//固定浆员最小年龄
		for(DataRow data:list) {
			Map<String,String> map = new HashMap<String,String>();
			if(data.getInt("age")>=Integer.valueOf(config.getValue()) && data.getInt("age")<Integer.valueOf(con.getValue())) {
				map.put("providerNo", data.getString("providerNo"));
				map.put("eliminateReason", "该浆员年龄大于等于"+config.getValue()+"岁,小于"+con.getValue()+"岁,并且半年内少于两次采浆记录");
				map.put("createDate", DateUtil.sf.format(new Date()));
				map.put("updateDate", DateUtil.sf.format(new Date()));
				map.put("creater", "1");
				map.put("updater", "1");
			}else if(data.getInt("age")>=Integer.valueOf(con.getValue())) {
				map.put("providerNo", data.getString("providerNo"));
				map.put("eliminateReason", "该浆员年龄大于等于"+config.getValue()+"岁");
				map.put("createDate", DateUtil.sf.format(new Date()));
				map.put("updateDate", DateUtil.sf.format(new Date()));
				map.put("creater", "1");
				map.put("updater", "1");
			}
			refuseInfoMapper.insertEliminateYearOld(map);
		}
	}
	/**
	 * 查询浆员的最新拒绝信息发布-CJ
	 * @param providerNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public RefuseInfo plasmaRefuseInfo(String providerNo) throws Exception {
		return refuseInfoMapper.plasmaRefuseInfo(providerNo);
	}
	/**
	 * 查询转氨酶暂拒情况 -CJ
	 * @param eliminateReason
	 * @return
	 * @throws Exception
	 */
	@Override
	public RefuseInfo queryTransaminaseInfo(String eliminateReason,String providerNo) throws Exception {
		return refuseInfoMapper.queryTransaminaseInfo(eliminateReason,providerNo);
	}
}
