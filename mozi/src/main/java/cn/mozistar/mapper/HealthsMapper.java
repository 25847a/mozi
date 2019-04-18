package cn.mozistar.mapper;

import cn.mozistar.pojo.Healths;

import org.apache.ibatis.annotations.InsertProvider;

public interface HealthsMapper {
	


	@InsertProvider(type = HealthsSqlProvider.class, method = "insertSelective")
	int insertSelective(Healths record);





}