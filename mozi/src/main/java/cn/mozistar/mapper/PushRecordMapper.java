package cn.mozistar.mapper;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import cn.mozistar.pojo.PushRecord;
import cn.mozistar.util.DataRow;

public interface PushRecordMapper {

	@Insert({"insert into pushrecord (userId,heartUnusual,highBloodUnusual,lowBloodUnusual,createTime)VALUES(#{userId},#{heartUnusual},#{highBloodUnusual},#{lowBloodUnusual},now())"})
    int insertPushRecord(PushRecord pushRecord);
	
	/**
	 * 查询预警记录的各项总数
	 * @param map
	 * @return
	 */
	@Select({
		"<script>"
		+"SELECT (SUM(CASE WHEN heartUnusual>0 THEN 1 ELSE 0 END)+SUM(CASE WHEN highBloodUnusual>0 THEN 1 ELSE 0 END)+SUM(CASE WHEN lowBloodUnusual>0 THEN 1 ELSE 0 END)) AS count,"
		+"SUM(CASE WHEN heartUnusual>0 THEN 1 ELSE 0 END) AS heartCount,SUM(CASE WHEN highBloodUnusual>0 THEN 1 ELSE 0 END) AS highCount,"
		+"SUM(CASE WHEN lowBloodUnusual>0 THEN 1 ELSE 0 END) AS lowCount FROM pushrecord WHERE userId=#{userId}"
		+ "<choose>"
		+ "<when test='createtime!=null and createtime!=\"\"'>"
		+ " AND DATE_FORMAT(createtime,'%Y-%m-%d')=#{createtime}"
		+ "</when>"
		+ "<otherwise>"
		+ " AND YEARWEEK(DATE_FORMAT(createtime,'%Y-%m-%d'))=YEARWEEK(NOW())"
		+ "</otherwise>"
		+ "</choose>"
		+"</script>"
	})
	public DataRow queryPushRecordCount(DataRow map)throws SQLException;
	/**
	 * 查询预警记录的记录数
	 * @param map
	 * @return
	 */
	@Select({
		"<script>"
		+"SELECT heartUnusual,highBloodUnusual,lowBloodUnusual,DATE_FORMAT(createtime,'%H:%i') as createtime,DATE_FORMAT(createTime,'%Y-%m-%d') as time FROM pushrecord"
  		+" WHERE userId =#{userId}"
  		+ "<choose>"
  		+ "<when test='createtime!=null and createtime!=\"\"'>"
  		+ " AND DATE_FORMAT(createtime,'%Y-%m-%d')=#{createtime}"
  		+ "</when>"
  		+ "<otherwise>"
  		+ " AND YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d'))=YEARWEEK(NOW())"
  		+ "</otherwise>"
  		+ "</choose>"
  		+" ORDER BY time DESC"
		+"</script>"
	})
	public List<DataRow> queryPushRecordList(DataRow map)throws SQLException;
}
