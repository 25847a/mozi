package com.fadl.health.service.impl;

import com.fadl.health.entity.Health;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.UserEq;
import com.fadl.common.ArrayUtil;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.health.dao.HealthMapper;
import com.fadl.health.service.EquipmentService;
import com.fadl.health.service.HealthService;
import com.fadl.health.service.PushService;
import com.fadl.health.service.UserEqService;
import com.fadl.health.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 惊凡给的数据表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {

	
	@Autowired
	HealthMapper healthMapper;
	@Autowired
	EquipmentService equipmentService;
	@Autowired
	UserService userService;
	@Autowired
	PushService pushService;
	@Autowired
	UserEqService userEqService;
	@Autowired
	HealthService healthService;
	/**
	 * 获取养老院页面的数据
	 * @param messageMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryBeadhouseList(DataRow messageMap,Map<String,Object> map) throws Exception {
		DataRow equipment = equipmentService.queryEquipmentState(map);//设备
		if(equipment.getInt("count")!=0){
			messageMap.put("equipment", equipment);
			DataRow userGender  = userService.queryUserGender(map);//男女
			messageMap.put("userGender", userGender);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>:::::"+map.get("page"));
			List<DataRow> tableData = healthService.queryHealthList(map);//列表数据
			int count = healthMapper.queryHealthListCount(map);
			messageMap.put("count", count);
			List<DataRow> heartrate = healthService.queryHeartrateCount(map);//首页当天心率统计图      数组
			for(DataRow dataRow: heartrate){
				int gender = dataRow.getInt("gender");
				Object[] result=ArrayUtil.listArray(dataRow,"gender");
				if(gender==0){
					messageMap.put("heartrateBoy", result);
				}else if(gender==1){
					messageMap.put("heartrateGirl", result);
				}else if(gender==2){
					messageMap.put("heartrateOther", result);
				}
			}
			DataRow blood = healthMapper.queryBloodCount(map);//首页当天血压统计图
			messageMap.put("blood", blood);
			DataRow microcirculation = healthMapper.queryMicrocirculationCount(map);//首页当天微循环统计图
			messageMap.put("microcirculation", microcirculation);
			Object[] result = healthService.queryBloodoxygenCount(map);//首页当天血氧统计图     数组
			messageMap.put("bloodoxygen", result);
			DataRow respirationrate = healthMapper.queryRespirationrateCount(map);//首页当天呼吸统计图
			messageMap.put("respirationrate", respirationrate);
			messageMap.customValue(tableData,IConstants.SUCCESS,IConstants.RESULT_BEADHOUSE);
		}else{
			messageMap.initFial("无使用者数据");
		}
		
		return messageMap;
	}
	/**
	 * 查询首页健康数据列表
	 * @param map
	 * @returnsss
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHealthList(Map<String,Object> map) throws SQLException {
		int page = Integer.valueOf((String) map.get("page"));
		map.put("pageNum", ((Integer.valueOf(page) - 1) * 7));
		map.put("pageSize", 7);
		List<DataRow> love = healthService.queryHealthListLove(map);//((Integer.valueOf(page) - 1) * 7),7
		for(int i=0;i<love.size();i++){
			love.get(i).set("count", DateUtil.getConversionDate(love.get(i).getInt("count")));
			UserEq userEq = userEqService.queryUserEqInfo(love.get(i).getInt("phone"));
			if(userEq!=null){
				EntityWrapper<Push> ew = new EntityWrapper<Push>();
				ew.eq("userId", love.get(i).getInt("phone"));//使用者的ID
				ew.eq("alias", userEq.getUserId());
				Push push =pushService.selectOne(ew);
				if(push!=null){
					if(push.getAllNotifyOn()==1){
						if(love.get(i).getInt("Heartrate")>push.getHeartHigThd() || love.get(i).getInt("Heartrate")<push.getHeartLowThd()){
							love.get(i).set("Heartrate", love.get(i).getString("Heartrate")+"A");
						}
						if(love.get(i).getInt("sbp_ave")>push.getHbpend()|| love.get(i).getInt("sbp_ave")<push.getHbpstart()){
							love.get(i).set("sbp_ave", love.get(i).getString("sbp_ave")+"A");
						}
						if(love.get(i).getInt("dbp_ave")>push.getLbpend()|| love.get(i).getInt("dbp_ave")<push.getLbpstart()){
							love.get(i).set("dbp_ave", love.get(i).getString("dbp_ave")+"A");
						}
					}
				}
				
			}
		};
	//	if(love.size()<=7){}
		
		
		
		map.put("pageNum", ((Integer.valueOf(page) - 1) * 14));
		map.put("pageSize", 14);
		List<DataRow> tableData =healthMapper.queryHealthList(map);//((Integer.valueOf(page) - 1) * 14),14
		for(int i=0;i<tableData.size();i++){
			tableData.get(i).set("count", DateUtil.getConversionDate(tableData.get(i).getInt("count")));
			UserEq userEq = userEqService.queryUserEqInfo(tableData.get(i).getInt("phone"));
			if(userEq!=null){
				EntityWrapper<Push> ew = new EntityWrapper<Push>();
				ew.eq("userId", tableData.get(i).getInt("phone"));//使用者的ID
				ew.eq("alias", userEq.getUserId());
				Push push =pushService.selectOne(ew);
				if(push!=null){
					if(push.getAllNotifyOn()==1){
						if(tableData.get(i).getInt("Heartrate")>push.getHeartHigThd() || tableData.get(i).getInt("Heartrate")<push.getHeartLowThd()){
							tableData.get(i).set("Heartrate", tableData.get(i).getString("Heartrate")+"A");
						}
						if(tableData.get(i).getInt("sbp_ave")>push.getHbpend()|| tableData.get(i).getInt("sbp_ave")<push.getHbpstart()){
							tableData.get(i).set("sbp_ave", tableData.get(i).getString("sbp_ave")+"A");
						}
						if(tableData.get(i).getInt("dbp_ave")>push.getLbpend()|| tableData.get(i).getInt("dbp_ave")<push.getLbpstart()){
							tableData.get(i).set("dbp_ave", tableData.get(i).getString("dbp_ave")+"A");
						}
					}
				}
				
			}
		}
		
		if(love.size()<=7){
			if(love.size()==7){
				if(tableData.size()<7){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<7;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==6){
				if(tableData.size()<8){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<8;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==5){
				if(tableData.size()<9){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<9;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==4){
				if(tableData.size()<10){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<10;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==3){
				if(tableData.size()<11){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<11;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==2){
				if(tableData.size()<12){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<12;i++){
						love.add(tableData.get(i));
					}	
				}
			}else if(love.size()==1){
				if(tableData.size()<13){
					for(int i=0;i<tableData.size();i++){
						love.add(tableData.get(i));
					}
				}else{
					for(int i=0;i<13;i++){
						love.add(tableData.get(i));
					}
				}
			}else if(love.size()==0){
				for(int i=0;i<tableData.size();i++){
					love.add(tableData.get(i));
				}
			}
		}
		return love;
	}
	/**
	 * 查询重点关爱的使用者
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHealthListLove(Map<String,Object> map) throws SQLException {
		return healthMapper.queryHealthListLove(map);
	}
	/**
	 * 查询首页健康数据列表总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int queryHealthListCount(Map<String,Object> map) throws SQLException {
		return healthMapper.queryHealthListCount(map);
	}
	/**
	 * 首页当天心率统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHeartrateCount(Map<String,Object> map) throws SQLException {
		return healthMapper.queryHeartrateCount(map);
	}
	/**
	 * 首页当天血压统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryBloodCount(Map<String,Object> map) throws SQLException {
		return healthMapper.queryBloodCount(map);
	}
	/**
	 * 首页当天微循环统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryMicrocirculationCount(Map<String,Object> map) throws SQLException {
		return healthMapper.queryMicrocirculationCount(map);
	}
	/**
	 * 首页当天血氧统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object[] queryBloodoxygenCount(Map<String,Object> map) throws SQLException {
		List<DataRow> bloodoxygen =healthMapper.queryBloodoxygenCount(map);
		Object[] result={0,0,0,0,0};
		//Object[] result={0,"",0,"",0,0,""};
		for(DataRow dataRow: bloodoxygen){
			int gender = dataRow.getInt("gender");
			if(gender==0){//男
				result[0]=dataRow.getInt("bloodoxygen1");//'<94男性'
				result[1]=dataRow.getInt("bloodoxygen2");//'94-99男性
			}else if(gender==1){//女
				result[2]=dataRow.getInt("bloodoxygen1");//'<94女性'
				result[3]=dataRow.getInt("bloodoxygen2");//'94-99女性'
			}else if(gender==2){
			//	messageMap.put("bloodoxygenOther", result);
			}
		}
		
		return result;
	}
	/**
	 * 首页当天呼吸统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryRespirationrateCount(Map<String,Object> map) throws SQLException {
		return healthMapper.queryRespirationrateCount(map);
	}
	/**
	 * 查询历史健康数据
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryHistoryList(Map<String,Object> map,DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = healthMapper.queryHistoryList(map);
		int total = healthMapper.queryHistoryListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
	/**
	 * 查询健康数据管理列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryHealthInfoList(Map<String,Object> map,DataRow messageMap) throws SQLException {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		List<DataRow> result = healthMapper.queryHealthInfoList(map);
		int total = healthMapper.queryHealthInfoListCount(map);
		messageMap.initSuccess(result);
		messageMap.put("total", total);
		return messageMap;
	}
}
