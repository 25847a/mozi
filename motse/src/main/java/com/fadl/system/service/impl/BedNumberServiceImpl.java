package com.fadl.system.service.impl;

import com.fadl.system.entity.BedNumber;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.system.dao.BedNumberMapper;
import com.fadl.system.service.BedNumberService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 床位表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-05
 */
@Service
public class BedNumberServiceImpl extends ServiceImpl<BedNumberMapper, BedNumber> implements BedNumberService {

	
	
	/**
	 * 查询床位数据
	 * @return
	 */
	@Override
	public DataRow queryBedNumberInfo(Admin admin, DataRow messageMap) throws SQLException {
		EntityWrapper<BedNumber> ew = new EntityWrapper<BedNumber>();
		ew.eq("adminId", admin.getId());
		List<BedNumber> list =this.selectList(ew);
		messageMap.initSuccess(list);
		return messageMap;
	}

}
