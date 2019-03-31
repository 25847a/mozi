package com.fadl.stock.service.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.account.entity.Admin;
import com.fadl.box.entity.BoxInfo;
import com.fadl.box.service.BoxInfoService;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.entity.ImmuneSetting;
import com.fadl.immuneAssay.service.ImmuneSettingService;
import com.fadl.stock.dao.PlasmaStockMapper;
import com.fadl.stock.entity.PlasmaStock;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 血浆入库表 服务实现类
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
@Service
public class PlasmaStockServiceImpl extends ServiceImpl<PlasmaStockMapper, PlasmaStock> implements PlasmaStockService {

	@Autowired
	public PlasmaStockMapper plasmaStockMapper;
	@Autowired
	public BoxInfoService boxInfoService;
	@Autowired
	public ConfigService configService;
	@Autowired
	public PlasmCollectionService plasmCollectionService;
	@Autowired
	public ImmuneSettingService immuneSettingService;
	
	/**
	 * 查询入库列表
	 */
	@Override
	public Page<DataRow> queryPlasmaStockList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(plasmaStockMapper.queryPlasmaStockList(page, map));
	}

	/**
	 * 根据 id 查询 箱号信息和浆量
	 */
	@Override
	public DataRow queryPlasmaStockById(Long id) throws Exception {
		return plasmaStockMapper.queryPlasmaStockById(id);
	}

	/**
	 * 血浆入库
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void addPlasmaStock(PlasmaStock plasmaStock,DataRow messageMap) throws Exception {
		//查询血浆信息
		DataRow row = plasmaStockMapper.queryPlasmaStockInfoByAllId(plasmaStock);
		//如果血浆状态为 已入库
		if (null==row) {
			messageMap.initFial();
			return;
		}else{
			Config config = configService.getConfig("open_config", "isStock");
			if(null==config){
				messageMap.initFial("请检查系统配置");
				return;
			}
			//如果关闭了 采完浆是否直接入库 配置，就不需要做免疫化验，可以直接入库
			if(config.getIsDisable()==1 && row.getInt("isAssay")==0){
				messageMap.initFial("该血浆还没有化验");
				return;
			}else if(config.getIsDisable()==1 && row.getInt("result")!=0){
				messageMap.initFial("该血浆化验不合格");
				return;
			}else if(row.getInt("isStorage")==1){
				messageMap.initFial("该血浆已入库");
				return;
			}else if (row.getInt("status")==1) {
				messageMap.initFial("该血浆已报废");
				return;
			}else if (config.getIsDisable()==1 && !StringHelper.isEmpty(row.getString("fid")) && StringHelper.isEmpty(row.getString("fresult"))) {
				messageMap.initFial("该血浆还未免疫化验");
				return;
			}else if(StringHelper.isEmpty(row.getString("immuneId"))){
				//如果打开了  采完浆直接入库  配置，默认为普通血浆
				EntityWrapper<ImmuneSetting> immune = new EntityWrapper<ImmuneSetting>();
				immune.eq("immuneName", "普通");
				ImmuneSetting sett = immuneSettingService.selectOne(immune);
				if (null==sett) {
					messageMap.initFial("请在免疫设置---》免疫类别设置 中配置 普通 免疫类型");
					return;
				}
				row.put("immuneId", sett.getId());
				row.put("code", sett.getTypeCode());
			}
		}
		//2.查询今天有没有装箱
		EntityWrapper<BoxInfo> ew=new EntityWrapper<BoxInfo>();
		
		ew.eq("immuneId", row.getString("immuneId"));
		ew.orderBy("createDate", false);
		ew.last("LIMIT 0,1");
		BoxInfo boxInfo = boxInfoService.selectOne(ew);
		
		if(null==boxInfo){
			boxInfo = insertBox(plasmaStock,boxInfo,row);
		}else{
			//如果箱子装满了，插入新的箱子
			if(boxInfo.getsNumber()==(boxInfo.getNumber())){
				boxInfo = insertBox(plasmaStock, boxInfo,row);
			}
		}
		//更新血浆库存状态
		updatePlasmaStock(row, boxInfo, messageMap);
	}
	
	/**
	 * 插入箱号
	 * @param boxInfo
	 * @param time
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public BoxInfo insertBox(PlasmaStock plasmaStock,BoxInfo boxInfo,DataRow row) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String time = cal.get(Calendar.YEAR)+"";
		time = time.substring(2, time.length());
		//查询浆站信息列表
		List<Config> config = configService.getConfig("stock_config");
		String jnum="";//每箱数量
		String jcode=""; //浆站代码
		for (Config con : config) {
			if (con.getLable().equals("num")) {
				jnum = con.getValue();
			}else if(con.getLable().equals("code")){
				jcode = con.getValue();
			}
		}
		//箱号
		String code = jcode+time+row.getString("code")+"00001";
		//如果箱子不是空的，判断是否到了新的一年，如果到了新的一年，则从 第 1 箱开始
		if(null!=boxInfo && boxInfo.getId().startsWith(jcode+time)){
			String c = code.substring(code.length()-5,code.length());
			int add = Integer.valueOf(c)+1;
			code =code.substring(0,code.length()-5) + String.format("%05d", add);
		}
		//如果没有,插入箱号信息
		int num = Integer.valueOf(jnum);
		boxInfo = new BoxInfo();
		boxInfo.setId(code);
		boxInfo.setNumber(num);
		boxInfo.setsNumber(0);
		boxInfo.setImmuneId(row.getLong("immuneId"));
		boxInfo.setCreateDate(DateUtil.getSystemDate(new Date()));
		boxInfo.setVersion(1);
		boxInfoService.insert(boxInfo);
		return boxInfo;
	}
	
	/**
	 * 更新血浆入库状态
	 * @throws SQLException 
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmaStock(DataRow row,BoxInfo boxInfo,DataRow messageMap) throws SQLException{
		int num = boxInfo.getsNumber()+1;
		//更新箱子信息，剩余数量
		EntityWrapper<BoxInfo> ew=new EntityWrapper<BoxInfo>();
		int version = boxInfo.getVersion();
		boxInfo.setVersion(version+1);
		boxInfo.setsNumber(num);
		ew.eq("id", boxInfo.getId());
		ew.eq("version", version);
		boolean result = boxInfoService.update(boxInfo, ew);
		if (result) {
			PlasmaStock plasmaStock = new PlasmaStock();
			plasmaStock.setId(row.getLong("id"));
			//更新血浆库存信息（血浆编号，箱子编号）
			plasmaStock.setPlasmaNo(boxInfo.getId()+String.format("%02d", num));
			plasmaStock.setBoxId(boxInfo.getId());
			plasmaStock.setIsStorage(1);
			result = this.updateById(plasmaStock);
			if (result) {
				if(boxInfo.getNumber() == boxInfo.getsNumber()){
					messageMap.put("finsh", "1");
					messageMap.put("boxId", boxInfo.getId());
				}
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
				throw new SQLException();
			}
		}else{
			messageMap.initFial();
		}
	}

	/**
	 * 入库查询浆员信息并打印条码
	 */
	@Override
	public HashMap<String, String> queryStockInfoAndPrint(String allId) throws SQLException {
		return plasmaStockMapper.queryStockInfoAndPrint(allId);
	}

	/**
	 * 浆库高级查询
	 */
	@Override
	public Page<DataRow> querySeniorStockList(HashMap<String, String> map, Page<DataRow> page) throws Exception {
		return page.setRecords(plasmaStockMapper.querySeniorStockList(page, map));
	}

	/**
	 * 浆库高级查询
	 */
	@Override
	public List<DataRow> querySeniorStockList(HashMap<String, String> map) throws Exception {
		return plasmaStockMapper.querySeniorStockList(map);
	}

	/**
	 * 根据箱号查询血浆详情 
	 */
	@Override
	public List<DataRow> queryPlasmaStockListByBoxId(String boxId) throws Exception {
		return plasmaStockMapper.queryPlasmaStockListByBoxId(boxId);
	}

	/**
	 * 血浆报废
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmaStockScrap(PlasmaStock plasmaStock, DataRow messageMap)throws Exception {
		PlasmaStock stock = plasmaStockMapper.selectById(plasmaStock.getId());
		if (null!=stock) {
			if (stock.getIsStorage()==2) {
				messageMap.initFial("血浆已经出库，不能报废");
			}else{
				Session session= SecurityUtils.getSubject().getSession();
	            Admin admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("allId", stock.getAllId());
				map.put("scrapUserId", admin.getId());
				int res = plasmaStockMapper.updatePlasmaScrapList(map);
				if (res>0) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
				/*if (res) {
					stock.setIsStorage(1);
					int result = plasmaStockMapper.updateById(stock);
					if (result>0) {
						messageMap.initSuccess();
						return;
					}
				}
				messageMap.initFial();
				throw new SQLException();*/
			}
		}else{
			messageMap.initFial("该血浆不存在");
		}
	}

	/**
	 * 取消入库
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmaStockStatus(PlasmaStock plasmaStock,DataRow messageMap) {
		PlasmaStock pl = this.selectById(plasmaStock.getId());
		if (pl.getIsStorage()==0) {
			messageMap.initFial("该血浆还没有入库");
		}else if(pl.getIsStorage()==2){
			messageMap.initFial("该血浆已出库，不能取消");
		}else{
			String boxId = pl.getBoxId();
			pl.setIsStorage(0); //标记为未入库
			pl.setBoxId(null);
			pl.setPlasmaNo(null);
			boolean res = this.updateAllColumnById(pl);
			if (res) {
				//更新箱子信息
				EntityWrapper<BoxInfo> ews = new EntityWrapper<BoxInfo>();
				ews.eq("id", boxId);
				BoxInfo box = boxInfoService.selectOne(ews);
				box.setsNumber(box.getsNumber()-1);
				ews.eq("version", box.getVersion());
				res = boxInfoService.update(box, ews);
				if (res) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}else{
				messageMap.initFial();
			}
		}
	}

	/**
	 * 打印装箱条码
	 */
	@Override
	public HashMap<String, String> queryPlasmaStockByBoxId(String boxId) throws SQLException {
		return plasmaStockMapper.queryPlasmaStockByBoxId(boxId);
	}

	/**
	 * 打印装箱清单（根据箱子编号查询 箱子血浆信息）
	 */
	@Override
	public List<DataRow> queryBoxPlasmaInfo(HashMap<String, String> map) throws Exception {
		return plasmaStockMapper.queryBoxPlasmaInfo(map);
	}
	
	/**
	 * 根据检验配置详情中的检测项目得到具体的试剂信息和检测方法 
	 * @param map key endTime 必填
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> querySuppliesInfoByProjectNameLable(HashMap<String, String> map)throws Exception{
		if(StringUtils.isEmpty(map.get("endTime"))) {
			throw new Exception("参数endTime为空");
		}
		return baseMapper.querySuppliesInfoByProjectNameLable(map);
	}
	
	/**
	 * 浆库高级查询
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Page<DataRow> queryOutPlasmaStock(HashMap<String, String> map, Page<DataRow> page) throws Exception{
		return page.setRecords(plasmaStockMapper.queryOutPlasmaStock(page, map));
	}

	/**
	 * 浆库高级查询
	 */
	@Override
	public List<DataRow> queryOutPlasmaStock(HashMap<String, String> map) throws Exception {
		return plasmaStockMapper.queryOutPlasmaStock(map);
	}

	/**
	 * 查询装箱清单 试剂信息 
	 */
	@Override
	public List<DataRow> queryBoxPlasmaInfoReagents(HashMap<String, String> map) throws Exception {
		return plasmaStockMapper.queryBoxPlasmaInfoReagents(map);
	}

	/**
	 * 批量报废
	 */
	@Override
	public void updatePlasmaScrapList(List<Long> id, DataRow messageMap) throws SQLException {
		Session session= SecurityUtils.getSubject().getSession();
        Admin admin =  (Admin) session.getAttribute(IConstants.SESSION_ADMIN_USER);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ids", id);
		map.put("scrapUserId", admin.getId());
		int res = plasmaStockMapper.updatePlasmaScrapList(map);
		if(res==id.size()){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
	}

	/**
	 * 报废未出库的血浆
	 */
	@Override
	public void queryScrapPlasmaList(Page<DataRow> page,HashMap<String, String> map) throws SQLException {
		page.setRecords(plasmaStockMapper.queryScrapPlasmaList(page, map));
	}

	/**
	 * 固定浆员留样查询
	 */
	@Override
	public void queryReturnSimpleList(HashMap<String, String> map, Page<DataRow> page) throws SQLException {
		page.setRecords(plasmaStockMapper.queryReturnSimpleList(page, map));
	}

	/**
	 * 固定浆员留样查询
	 */
	@Override
	public List<DataRow> queryReturnSimpleList(HashMap<String, String> map) throws SQLException {
		return plasmaStockMapper.queryReturnSimpleList(map);
	}
	
	
	/******************************    导出浆站数据到公司    ***************************************************/
	/**
	 * 导出浆员信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportPlasmaDataInfo(HashMap<String, String> map)throws Exception{
		return plasmaStockMapper.exportPlasmaDataInfo(map);
	}
	
	/**
	 * 导出箱子信息
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportBoxDataInfo(HashMap<String, String> map)throws SQLException{
		return plasmaStockMapper.exportBoxDataInfo(map); 
	}
	
	/**
	 * 采浆记录
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> exportCollectionDataInfo(HashMap<String, String> map)throws SQLException{
		return plasmaStockMapper.exportCollectionDataInfo(map); 
	}
	
	/**
	 * 拒绝信息
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<String, String>> queryRefuseDataInfo(HashMap<String, String> map) throws SQLException{
		return plasmaStockMapper.queryRefuseDataInfo(map); 
	}
	/**
	 * 导出浆员免疫信息表
	 * @param map
	 * @return
	 */
	@Override
	public List<HashMap<String, String>> exportPlasmaStockImmune(HashMap<String, String> map) throws SQLException {
		return plasmaStockMapper.exportPlasmaStockImmune(map);
	}
	/**
	 * 导出浆员免疫化验记录表
	 * @param map
	 * @return
	 */
	public List<HashMap<String, String>> queryTmAssayInfo(HashMap<String,String> map)throws SQLException{
		return plasmaStockMapper.queryTmAssayInfo(map);
	}
	/******************************    导出浆站数据到公司    ***************************************************/

	/**
	 * 血浆装箱单
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaBoxList(HashMap<String, String> map)throws SQLException{
		return plasmaStockMapper.queryPlasmaBoxList(map); 
	}
	
	/**
	 * 血浆检测装运表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaCheckList(HashMap<String, String> map)throws SQLException{
		return plasmaStockMapper.queryPlasmaCheckList(map);
	}
	
	/**
	 * 血浆装箱汇总表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public List<DataRow> queryPlasmaBoxSummaryList(HashMap<String, String> map)throws SQLException{
		return plasmaStockMapper.queryPlasmaBoxSummaryList(map);
	}
	
	/**
	 * 批量送浆
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int updatePulpingList(HashMap<String, Object> map)throws SQLException{
		return plasmaStockMapper.updatePulpingList(map);
	}
	
	/**
	 * 取消送浆
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public int cacalPulpingList(HashMap<String, Object> map)throws SQLException{
		return plasmaStockMapper.cacalPulpingList(map);
	}
}
