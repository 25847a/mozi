package cn.mozistar.mapper;

import cn.mozistar.pojo.Relation;
import org.apache.ibatis.jdbc.SQL;

public class RelationSqlProvider {

    public String insertSelective(Relation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("relation");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("userId", "#{userId,jdbcType=INTEGER}");
        }
        
        if (record.getObserveId() != null) {
            sql.VALUES("observeId", "#{observeId,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Relation record) {
        SQL sql = new SQL();
        sql.UPDATE("relation");
        
        if (record.getUserId() != null) {
            sql.SET("userId = #{userId,jdbcType=INTEGER}");
        }
        
        if (record.getObserveId() != null) {
            sql.SET("observeId = #{observeId,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}