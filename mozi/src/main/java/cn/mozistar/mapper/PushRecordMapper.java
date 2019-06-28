package cn.mozistar.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import cn.mozistar.pojo.PushRecord;

public interface PushRecordMapper {

	@Insert({"insert into pushrecord (userId,heartUnusual,highBloodUnusual,lowBloodUnusual,createTime)VALUES(#{userId},#{heartUnusual},#{highBloodUnusual},#{lowBloodUnusual},now())"})
    int insertPushRecord(PushRecord pushRecord);
	
	/**
	 * 查询预警记录的各项总数
	 * @param map
	 * @return
	 */
	@Select({"SELECT (SUM(CASE WHEN heartUnusual>0 THEN 1 ELSE 0 END)+SUM(CASE WHEN highBloodUnusual>0 THEN 1 ELSE 0 END)+SUM(CASE WHEN lowBloodUnusual>0 THEN 1 ELSE 0 END)) AS count,"
			+ "SUM(CASE WHEN heartUnusual>0 THEN 1 ELSE 0 END) AS heartCount,SUM(CASE WHEN highBloodUnusual>0 THEN 1 ELSE 0 END) AS highCount,"
			+ "SUM(CASE WHEN lowBloodUnusual>0 THEN 1 ELSE 0 END) AS lowCount FROM pushrecord WHERE userId=#{userId} AND DATE_FORMAT(createTime,'%Y-%m-%d')=#{createTime}"})
	public Map<String,Object> queryPushRecordCount(Map<String,String> map)throws SQLException;
	/**
	 * 查询预警记录的记录数
	 * @param map
	 * @return
	 */
	@Select({"SELECT heartUnusual,highBloodUnusual,lowBloodUnusual,DATE_FORMAT(createTime,'%H:%i') AS createTime FROM pushrecord"
			+ " WHERE userId =#{userId} AND DATE_FORMAT(createTime,'%Y-%m-%d')=#{createTime}"})
	public List<Map<String,String>> queryPushRecordList(Map<String,String> map)throws SQLException;
}
