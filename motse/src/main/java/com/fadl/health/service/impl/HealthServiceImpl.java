package com.fadl.health.service.impl;

import com.fadl.health.entity.Health;
import com.fadl.common.ArrayUtil;
import com.fadl.common.DataRow;
import com.fadl.common.IConstants;
import com.fadl.health.dao.HealthMapper;
import com.fadl.health.service.EquipmentService;
import com.fadl.health.service.HealthService;
import com.fadl.health.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.sql.SQLException;
import java.util.List;
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
	
	@Override
	public DataRow queryBeadhouseList(DataRow messageMap,String page) throws Exception {
		DataRow equipment = equipmentService.queryEquipmentState();//设备
		messageMap.put("equipment", equipment);
		DataRow userGender  = userService.queryUserGender();//男女
		messageMap.put("userGender", userGender);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>:::::"+page);
		List<DataRow> tableData = healthMapper.queryHealthList(((Integer.valueOf(page) - 1) * 14),14);//列表数据
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>:::::"+tableData);
		int count = healthMapper.queryHealthListCount();
		messageMap.put("count", count);
		List<DataRow> heartrate = healthMapper.queryHeartrateCount();//首页当天心率统计图      数组
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
		List<DataRow> bloodoxygen = healthMapper.queryBloodoxygenCount();//首页当天血氧统计图     数组
		Object[] result={0,0,0,0};
		for(DataRow dataRow: bloodoxygen){
			int gender = dataRow.getInt("gender");
			if(gender==0){
				result[0]=dataRow.getInt("bloodoxygen1");
				result[3]=dataRow.getInt("bloodoxygen2");
			}else if(gender==1){
				result[1]=dataRow.getInt("bloodoxygen1");
				result[2]=dataRow.getInt("bloodoxygen2");
			}else if(gender==2){
			//	messageMap.put("bloodoxygenOther", result);
			}
		}
		messageMap.put("bloodoxygen", result);
		DataRow respirationrate = healthMapper.queryRespirationrateCount();//首页当天呼吸统计图
		messageMap.put("respirationrate", respirationrate);
		messageMap.customValue(tableData,IConstants.SUCCESS,IConstants.RESULT_BEADHOUSE);
		return messageMap;
	}
	/**
	 * 查询首页健康数据列表
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<DataRow> queryHealthList(Integer pageNum,Integer pageSize) throws SQLException {
		return healthMapper.queryHealthList(pageNum,pageSize);
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
	public List<DataRow> queryBloodoxygenCount() throws SQLException {
		return healthMapper.queryBloodoxygenCount();
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
}
