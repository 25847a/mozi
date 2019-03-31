package cn.mozistar.mapper;

import cn.mozistar.pojo.Push;
import org.apache.ibatis.jdbc.SQL;

public class PushSqlProvider {

    public String insertSelective(Push record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("push");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAlias() != null) {
            sql.VALUES("alias", "#{alias,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=INTEGER}");
        }
        
        if (record.getAllNotifyOn() != null) {
            sql.VALUES("allNotifyOn", "#{allNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getHeartNotifyOn() != null) {
            sql.VALUES("heartNotifyOn", "#{heartNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getBoolPreNotifyOn() != null) {
            sql.VALUES("boolPreNotifyOn", "#{boolPreNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getFallNotifyOn() != null) {
            sql.VALUES("fallNotifyOn", "#{fallNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getWalkNotifyOn() != null) {
            sql.VALUES("walkNotifyOn", "#{walkNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getFenceNotifyOn() != null) {
            sql.VALUES("fenceNotifyOn", "#{fenceNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getHeartLowThd() != null) {
            sql.VALUES("heartLowThd", "#{heartLowThd,jdbcType=INTEGER}");
        }
        
        if (record.getHeartHigThd() != null) {
            sql.VALUES("heartHigThd", "#{heartHigThd,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("createTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("updateTime", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLbpstart() != null) {
            sql.VALUES("lbpstart", "#{lbpstart,jdbcType=INTEGER}");
        }
        
        if (record.getLbpend() != null) {
            sql.VALUES("lbpend", "#{lbpend,jdbcType=INTEGER}");
        }
        
        if (record.getHbpstart() != null) {
            sql.VALUES("hbpstart", "#{hbpstart,jdbcType=INTEGER}");
        }
        
        if (record.getHbpend() != null) {
            sql.VALUES("hbpend", "#{hbpend,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Push record) {
        SQL sql = new SQL();
        sql.UPDATE("push");
        
        if (record.getAlias() != null) {
            sql.SET("alias = #{alias,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("userId = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=INTEGER}");
        }
        
        if (record.getAllNotifyOn() != null) {
            sql.SET("allNotifyOn = #{allNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getHeartNotifyOn() != null) {
            sql.SET("heartNotifyOn = #{heartNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getBoolPreNotifyOn() != null) {
            sql.SET("boolPreNotifyOn = #{boolPreNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getFallNotifyOn() != null) {
            sql.SET("fallNotifyOn = #{fallNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getWalkNotifyOn() != null) {
            sql.SET("walkNotifyOn = #{walkNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getFenceNotifyOn() != null) {
            sql.SET("fenceNotifyOn = #{fenceNotifyOn,jdbcType=INTEGER}");
        }
        
        if (record.getHeartLowThd() != null) {
            sql.SET("heartLowThd = #{heartLowThd,jdbcType=INTEGER}");
        }
        
        if (record.getHeartHigThd() != null) {
            sql.SET("heartHigThd = #{heartHigThd,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("createTime = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("updateTime = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLbpstart() != null) {
            sql.SET("lbpstart = #{lbpstart,jdbcType=INTEGER}");
        }
        
        if (record.getLbpend() != null) {
            sql.SET("lbpend = #{lbpend,jdbcType=INTEGER}");
        }
        
        if (record.getHbpstart() != null) {
            sql.SET("hbpstart = #{hbpstart,jdbcType=INTEGER}");
        }
        
        if (record.getHbpend() != null) {
            sql.SET("hbpend = #{hbpend,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
    public String updatePush(Push record) {
    	SQL sql = new SQL();
    	sql.UPDATE("push");
    	
    	if (record.getAllNotifyOn() != null) {
    		sql.SET("allNotifyOn = #{allNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getHeartNotifyOn() != null) {
    		sql.SET("heartNotifyOn = #{heartNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getBoolPreNotifyOn() != null) {
    		sql.SET("boolPreNotifyOn = #{boolPreNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getFallNotifyOn() != null) {
    		sql.SET("fallNotifyOn = #{fallNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getWalkNotifyOn() != null) {
    		sql.SET("walkNotifyOn = #{walkNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getFenceNotifyOn() != null) {
    		sql.SET("fenceNotifyOn = #{fenceNotifyOn,jdbcType=INTEGER}");
    	}
    	
    	if (record.getHeartLowThd() != null) {
    		sql.SET("heartLowThd = #{heartLowThd,jdbcType=INTEGER}");
    	}
    	
    	if (record.getHeartHigThd() != null) {
    		sql.SET("heartHigThd = #{heartHigThd,jdbcType=INTEGER}");
    	}
    	
    	if (record.getCreateTime() != null) {
    		sql.SET("createTime = #{createTime,jdbcType=TIMESTAMP}");
    	}
    	
    	if (record.getUpdateTime() != null) {
    		sql.SET("updateTime = #{updateTime,jdbcType=TIMESTAMP}");
    	}
    	
    	if (record.getLbpstart() != null) {
    		sql.SET("lbpstart = #{lbpstart,jdbcType=INTEGER}");
    	}
    	
    	if (record.getLbpend() != null) {
    		sql.SET("lbpend = #{lbpend,jdbcType=INTEGER}");
    	}
    	
    	if (record.getHbpstart() != null) {
    		sql.SET("hbpstart = #{hbpstart,jdbcType=INTEGER}");
    	}
    	
    	if (record.getHbpend() != null) {
    		sql.SET("hbpend = #{hbpend,jdbcType=INTEGER}");
    	}
    	
    	sql.WHERE("userId = #{userId,jdbcType=INTEGER} and alias = #{alias,jdbcType=INTEGER}");
    	
    	return sql.toString();
    }
}