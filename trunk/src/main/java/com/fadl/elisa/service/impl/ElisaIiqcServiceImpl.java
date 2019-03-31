package com.fadl.elisa.service.impl;

import com.fadl.elisa.entity.ElisaIiqc;
import com.fadl.common.DataRow;
import com.fadl.elisa.dao.ElisaIiqcMapper;
import com.fadl.elisa.service.ElisaIiqcService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 即刻性室内质控记录表 服务实现类
 * </p>
 *
 * @author xim.xie
 * @since 2018-07-20
 */
@Service
public class ElisaIiqcServiceImpl extends ServiceImpl<ElisaIiqcMapper, ElisaIiqc> implements ElisaIiqcService {

	@Override
	public List<ElisaIiqc> findByTciidAndQcId(Long tciid,Long qcId)  throws Exception{
		Wrapper<ElisaIiqc> ew = new EntityWrapper<ElisaIiqc>();
		ew.eq("tciid", tciid);
		ew.eq("qcId", qcId);
		ew.orderBy("createDate", true);
		return baseMapper.selectList(ew);
	}
	
	/**
	 * 根据试剂批号,质控品ID和页码 查询记录(固定20记录为一页),根据CreateDate排序
	 * @param page
	 * @param tciid
	 * @param qcId
	 * @return
	 */
	@Override
	public Page<DataRow> findByTciidAndPageNo(Page<DataRow> page, Long tciid,Long qcId,Integer stratRow, Integer endRow) throws Exception {
	//	page.setSize(20); // 直接限制每页20条记录
		DataRow dataRow = new DataRow();
		dataRow.put("qcId", qcId);
		dataRow.put("tciid", tciid);
		dataRow.put("check_method", "check_method");
		dataRow.put("elisa_check_project", "elisa_check_project");
		dataRow.put("stratRow", stratRow);
		dataRow.put("endRow", endRow);
		int count = baseMapper.findByPageNoCount(dataRow);
		if(count <=20) {
			page.setCurrent(0);
			page.setAsc(false);
		}else {
			count = count -20;
			page.setSize(29);
			// 该属性不代表实际意义,仅用来保存是否减掉第一页的20条记录
			page.setAsc(true);
		}
		page.setTotal(count);
		return page.setRecords(baseMapper.findByPageNo( dataRow));
	}

}
