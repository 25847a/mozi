package cn.mozistar.service;

import java.util.List;

import cn.mozistar.pojo.InvitationList;

public interface InvitationListService {

	int insert(InvitationList il);

	List<InvitationList> selectByPhone(String phone);

	int deleteByPhone(String phone);

}
