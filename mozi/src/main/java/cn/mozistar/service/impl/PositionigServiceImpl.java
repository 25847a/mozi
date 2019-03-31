package cn.mozistar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.PositionigMapper;
import cn.mozistar.pojo.Positionig;
import cn.mozistar.service.PositionigService;


@Transactional
@Service
public class PositionigServiceImpl implements PositionigService{

	
	@Autowired
	private PositionigMapper positionigMapper;
	
	public int insertSelective(Positionig positionig){
		return positionigMapper.insertSelective(positionig);
	}
	
}
