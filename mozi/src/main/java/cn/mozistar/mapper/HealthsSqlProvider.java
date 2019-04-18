package cn.mozistar.mapper;

import org.apache.ibatis.jdbc.SQL;
import cn.mozistar.pojo.Healths;

public class HealthsSqlProvider {
	
	public String insertSelective(Healths record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("healths");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getHrv() != null) {
            sql.VALUES("hrv", "#{hrv,jdbcType=INTEGER}");
        }
        
        if (record.getHighBloodPressure() != null) {
            sql.VALUES("highBloodPressure", "#{highBloodPressure,jdbcType=INTEGER}");
        }
        
        if (record.getLowBloodPressure() != null) {
            sql.VALUES("lowBloodPressure", "#{lowBloodPressure,jdbcType=INTEGER}");
        }
        
        if (record.getHeartRate() != null) {
            sql.VALUES("heartRate", "#{heartRate,jdbcType=INTEGER}");
        }
        
        if (record.getBloodOxygen() != null) {
            sql.VALUES("bloodOxygen", "#{bloodOxygen,jdbcType=INTEGER}");
        }
        
        if (record.getMicrocirculation() != null) {
            sql.VALUES("microcirculation", "#{microcirculation,jdbcType=INTEGER}");
        }
        
        if (record.getAmedicalreport() != null) {
            sql.VALUES("amedicalreport", "#{amedicalreport,jdbcType=VARCHAR}");
        }
        
        if (record.getRespirationrate() != null) {
            sql.VALUES("respirationrate", "#{respirationrate,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createtime", "#{createtime,jdbcType=TIMESTAMP}");
        }
        return sql.toString();
    }

}
