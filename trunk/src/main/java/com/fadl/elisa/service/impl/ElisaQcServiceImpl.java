package com.fadl.elisa.service.impl;

import com.fadl.elisa.entity.ElisaQc;
import com.fadl.common.DataRow;
import com.fadl.elisa.dao.ElisaQcMapper;
import com.fadl.elisa.service.ElisaQcService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 质控品和检验方法绑定表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-31
 */
@Service
public class ElisaQcServiceImpl extends ServiceImpl<ElisaQcMapper, ElisaQc> implements ElisaQcService {
	/**
	 * 根据项目编号查询有效的试剂名称
	 * @param qc
	 * @return
	 */
	@Override
	public List<DataRow> querySuppliesListByProjectId(ElisaQc qc) {
		return baseMapper.querySuppliesListByProjectId(qc);
	}

}
