package com.fadl.system.service.impl;

import com.fadl.system.entity.Nurse;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.dao.NurseMapper;
import com.fadl.system.service.NurseService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 护士表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Service
public class NurseServiceImpl extends ServiceImpl<NurseMapper, Nurse> implements NurseService {
	
	/**
	 * 查询分属护士的信息
	 * @param map
	 * @return
	 */
	@Override
	public DataRow queryNurseInfo(Admin admin, DataRow messageMap) throws SQLException {
		EntityWrapper<Nurse> ew = new EntityWrapper<Nurse>();
		ew.eq("adminId", admin.getId());
		List<Nurse> list =this.selectList(ew);
		messageMap.initSuccess(list);
		return messageMap;
	}

}
