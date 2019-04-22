package com.fadl.health.service.impl;

import com.fadl.health.entity.Health;
import com.fadl.health.entity.Push;
import com.fadl.health.entity.UserEq;
import com.fadl.common.ArrayUtil;
import com.fadl.common.DataRow;
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
	public DataRow queryBeadhouseList(DataRow messageMap,String page) throws Exception {
		DataRow equipment = equipmentService.queryEquipmentState();//设备
		messageMap.put("equipment", equipment);
		DataRow userGender  = userService.queryUserGender();//男女
		messageMap.put("userGender", userGender);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>:::::"+page);
		List<DataRow> tableData = healthService.queryHealthList(Integer.valueOf(page));//列表数据
		int count = healthMapper.queryHealthListCount();
		messageMap.put("count", count);
		List<DataRow> heartrate = healthService.queryHeartrateCount();//首页当天心率统计图      数组
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
		DataRow blood = healthMapper.queryBloodCount();//首页当天血压统计图
		messageMap.put("blood", blood);
		DataRow microcirculation = healthMapper.queryMicrocirculationCount();//首页当天微循环统计图
		messageMap.put("microcirculation", microcirculation);
		Object[] result = healthService.queryBloodoxygenCount();//首页当天血氧统计图     数组
		messageMap.put("bloodoxygen", result);
		DataRow respirationrate = healthMapper.queryRespirationrateCount();//首页当天呼吸统计图
		messageMap.put("respirationrate", respirationrate);
		messageMap.customValue(tableData,IConstants.SUCCESS,IConstants.RESULT_BEADHOUSE);
	//	messageMap.customValue("",IConstants.SUCCESS,IConstants.RESULT_BEADHOUSE);
		return messageMap;
	}
	/**
	 * 查询首页健康数据列表
	 * @param map
	 * @returnsss
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHealthList(Integer page) throws SQLException {
		List<DataRow> love = healthService.queryHealthListLove(((Integer.valueOf(page) - 1) * 7),7);
		List<DataRow> tableData =healthMapper.queryHealthList(((Integer.valueOf(page) - 1) * 14),14);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>:::::"+tableData);
		for(int i=0;i<tableData.size();i++){
			UserEq userEq = userEqService.queryUserEqInfo(tableData.get(i).getInt("phone"));
			if(userEq!=null){
				EntityWrapper<Push> ew = new EntityWrapper<Push>();
				ew.eq("userId", tableData.get(i).getInt("phone"));//使用者的ID
				ew.eq("alias", userEq.getUserId());
				Push push =pushService.selectOne(ew);
				if(push!=null){
					if(tableData.get(i).getInt("Heartrate")>push.getHeartHigThd() || tableData.get(i).getInt("Heartrate")<push.getHeartLowThd()){
						tableData.get(i).set("Heartrate", tableData.get(i).getString("Heartrate")+"A");
					}
					if(tableData.get(i).getInt("sbp_ave")>push.getHbpend()|| tableData.get(i).getInt("sbp_ave")<push.getHbpstart()){
						tableData.get(i).set("sbp_ave", tableData.get(i).getString("sbp_ave")+"A");
					}
					if(tableData.get(i).getInt("dbp_ave")<push.getLbpend()|| tableData.get(i).getInt("dbp_ave")<push.getLbpstart()){
						tableData.get(i).set("dbp_ave", tableData.get(i).getString("dbp_ave")+"A");
					}
				}
				
			}
		}
		if(love.size()<=7){
			if(love.size()==7){
				for(int i=0;i<7;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==6){
				for(int i=0;i<8;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==5){
				for(int i=0;i<9;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==4){
				for(int i=0;i<10;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==3){
				for(int i=0;i<11;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==3){
				for(int i=0;i<12;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==2){
				for(int i=0;i<13;i++){
					love.add(tableData.get(i));
				}
			}else if(love.size()==1){
				for(int i=0;i<14;i++){
					love.add(tableData.get(i));
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
	public List<DataRow> queryHealthListLove(Integer pageNum,Integer pageSize) throws SQLException {
		return healthMapper.queryHealthListLove(pageNum,pageSize);
	}
	/**
	 * 查询首页健康数据列表总数
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int queryHealthListCount() throws SQLException {
		return healthMapper.queryHealthListCount();
	}
	/**
	 * 首页当天心率统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHeartrateCount() throws SQLException {
		return healthMapper.queryHeartrateCount();
	}
	/**
	 * 首页当天血压统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryBloodCount() throws SQLException {
		return healthMapper.queryBloodCount();
	}
	/**
	 * 首页当天微循环统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryMicrocirculationCount() throws SQLException {
		return healthMapper.queryMicrocirculationCount();
	}
	/**
	 * 首页当天血氧统计图
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Object[] queryBloodoxygenCount() throws SQLException {
		List<DataRow> bloodoxygen =healthMapper.queryBloodoxygenCount();
		Object[] result={"",0,0,"",0,0,""};
		for(DataRow dataRow: bloodoxygen){
			int gender = dataRow.getInt("gender");
			if(gender==0){//男
				result[1]=dataRow.getInt("bloodoxygen1");//'<94男性'
				result[5]=dataRow.getInt("bloodoxygen2");//'94-99男性
			}else if(gender==1){//女
				result[2]=dataRow.getInt("bloodoxygen1");//'<94女性'
				result[4]=dataRow.getInt("bloodoxygen2");//'94-99女性'
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
	public DataRow queryRespirationrateCount() throws SQLException {
		return healthMapper.queryRespirationrateCount();
	}
	/**
	 * 查询历史健康数据
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryHistoryList(Map<String,Object> map,DataRow messageMap) throws SQLException {
		List<DataRow> result = healthMapper.queryHistoryList(map);
		messageMap.initSuccess(result);
		return messageMap;
	}
	/**
	 * 查询健康数据管理列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryHealthInfoList(DataRow messageMap) throws SQLException {
		List<DataRow> result = healthMapper.queryHealthInfoList();
		messageMap.initSuccess(result);
		return messageMap;
	}
}
