package com.fadl.hr.service.impl;

import com.fadl.hr.entity.Hr;
import com.fadl.account.dao.AdminMapper;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;
import com.fadl.common.SessionUtil;
import com.fadl.hr.dao.HrMapper;
import com.fadl.hr.service.HrService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-05-10
 */
@Service
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements HrService {
	
	@Autowired
	HrMapper hrMapper;
	@Autowired
	AdminMapper adminMapper;
	/**
	 * 查询机构管理页面下工作人员的信息列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataRow queryHrList(Map<String,Object> map, DataRow messageMap) throws Exception {
		int pageNum = Integer.valueOf((String) map.get("pageNum"));
		int pageSize = Integer.valueOf((String) map.get("pageSize"));
		map.put("pageNum", (pageNum-1)*pageSize);
		map.put("pageSize", pageSize);
		Admin admin = SessionUtil.getSessionAdmin();
		DataRow data =adminMapper.queryAdminAgentInfo(admin.getId());
		map.put("id", data.getInt("id"));
		List<DataRow> list =hrMapper.queryHrList(map);
		int total = hrMapper.queryHrListCount(map);
		messageMap.initSuccess(list);
		messageMap.put("total", total);
		return messageMap;
	}
	 /**
     * 删除供应商的个人信息
     * map
     * @return
     */
	@Override
	public DataRow deleteHrInfo(Hr hr, DataRow messageMap) throws Exception {
		hr.setIsDelete(1);
		int row =hrMapper.updateById(hr);
		if(row>0){
			messageMap.initSuccess("删除成功!");
		}else{
			messageMap.initSuccess("删除失败,请重新尝试!");
		}
		return messageMap;
	}
}
