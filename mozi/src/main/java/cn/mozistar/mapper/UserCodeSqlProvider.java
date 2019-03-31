package cn.mozistar.mapper;

import cn.mozistar.pojo.UserCode;
import org.apache.ibatis.jdbc.SQL;

public class UserCodeSqlProvider {

    public String insertSelective(UserCode record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("usercode");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phoen", "#{phoen,jdbcType=VARCHAR}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserCode record) {
        SQL sql = new SQL();
        sql.UPDATE("usercode");
        
        if (record.getPhone() != null) {
            sql.SET("phoen = #{phoen,jdbcType=VARCHAR}");
        }
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}