package cn.mozistar.mapper;

import cn.mozistar.pojo.Positionig;
import org.apache.ibatis.jdbc.SQL;

public class PositionigSqlProvider {

    public String insertSelective(Positionig record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("positionig");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getCoordinate() != null) {
            sql.VALUES("coordinate", "#{coordinate,jdbcType=VARCHAR}");
        }
        
        if (record.getCratetime() != null) {
            sql.VALUES("cratetime", "#{cratetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Positionig record) {
        SQL sql = new SQL();
        sql.UPDATE("positionig");
        
        if (record.getCoordinate() != null) {
            sql.SET("coordinate = #{coordinate,jdbcType=VARCHAR}");
        }
        
        if (record.getCratetime() != null) {
            sql.SET("cratetime = #{cratetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("userId = #{userId,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}