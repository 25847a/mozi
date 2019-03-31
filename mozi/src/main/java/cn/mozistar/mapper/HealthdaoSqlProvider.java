package cn.mozistar.mapper;

import cn.mozistar.pojo.Healthdao;
import org.apache.ibatis.jdbc.SQL;

public class HealthdaoSqlProvider {

    public String insertSelective(Healthdao record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("healthdao");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
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
        
        if (record.getRespirationrate() != null) {
            sql.VALUES("respirationrate", "#{respirationrate,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCreatetime() != null) {
            sql.VALUES("createtime", "#{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAmedicalreport() != null) {
            sql.VALUES("amedicalreport", "#{amedicalreport,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Healthdao record) {
        SQL sql = new SQL();
        sql.UPDATE("healthdao");
        
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
        
        if (record.getRespirationrate() != null) {
            sql.SET("respirationrate = #{respirationrate,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("userId = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getCreatetime() != null) {
            sql.SET("createtime = #{createtime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAmedicalreport() != null) {
            sql.SET("amedicalreport = #{amedicalreport,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
    public String updateHealthdaoByUserId(Healthdao record) {
    	SQL sql = new SQL();
    	sql.UPDATE("healthdao");
    	
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
    	
    	if (record.getRespirationrate() != null) {
    		sql.SET("respirationrate = #{respirationrate,jdbcType=INTEGER}");
    	}
    	
    	if (record.getCreatetime() != null) {
    		sql.SET("createtime = #{createtime,jdbcType=TIMESTAMP}");
    	}
    	
    	sql.WHERE("userId = #{userId,jdbcType=INTEGER}");
    	
    	return sql.toString();
    }
}