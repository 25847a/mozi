package cn.mozistar.mapper;

import cn.mozistar.pojo.Health;
import org.apache.ibatis.jdbc.SQL;

public class HealthSqlProvider {

    public String insertSelective(Health record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("health");
        
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
        if (record.getArrhythmia() != null) {
            sql.VALUES("arrhythmia", "#{arrhythmia,jdbcType=INTEGER}");
        }
        
        if (record.getMood() != null) {
            sql.VALUES("mood", "#{mood,jdbcType=INTEGER}");
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
        
        if (record.getWaveform() != null) {
            sql.VALUES("waveform", "#{waveform,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Health record) {
        SQL sql = new SQL();
        sql.UPDATE("health");
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getHrv() != null) {
            sql.SET("hrv = #{hrv,jdbcType=INTEGER}");
        }
        
        if (record.getHighBloodPressure() != null) {
            sql.SET("highBloodPressure = #{highBloodPressure,jdbcType=INTEGER}");
        }
        
        if (record.getLowBloodPressure() != null) {
            sql.SET("lowBloodPressure = #{lowBloodPressure,jdbcType=INTEGER}");
        }
        
        if (record.getHeartRate() != null) {
            sql.SET("heartRate = #{heartRate,jdbcType=INTEGER}");
        }
        
        if (record.getBloodOxygen() != null) {
            sql.SET("bloodOxygen = #{bloodOxygen,jdbcType=INTEGER}");
        }
        
        if (record.getMicrocirculation() != null) {
            sql.SET("microcirculation = #{microcirculation,jdbcType=INTEGER}");
        }
        
        if (record.getAmedicalreport() != null) {
            sql.SET("amedicalreport = #{amedicalreport,jdbcType=VARCHAR}");
        }
        
        if (record.getRespirationrate() != null) {
            sql.SET("respirationrate = #{respirationrate,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("userId = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCreatetime() != null) {
            sql.SET("createtime = #{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getWaveform() != null) {
            sql.SET("waveform = #{waveform,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}