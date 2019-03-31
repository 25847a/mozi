package cn.mozistar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.InvitationListMapper;
import cn.mozistar.pojo.InvitationList;
import cn.mozistar.service.InvitationListService;

@Transactional
@Service
public class InvitationListServiceImpl implements InvitationListService{

	@Autowired
	private InvitationListMapper invitationListMapper;
	
	
	public int insert(InvitationList il) {
		return invitationListMapper.insertSelective(il);
	}


	public List<InvitationList> selectByPhone(String phone) {
		return invitationListMapper.selectByPhone(phone);
	}


	@Override
	public int deleteByPhone(String phone) {
		return invitationListMapper.deleteByPhone(phone);
	}

}
