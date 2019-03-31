package com.fadl.supplies.service.impl;
 
import com.fadl.supplies.entity.Imexport;
import com.fadl.common.DataRow;
import com.fadl.supplies.dao.ImexportMapper;
import com.fadl.supplies.service.ImexportService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存进出表 服务实现类
 * </p>
 *
 * @author CJ
 * @since 2018-05-21
 */
@Service
public class ImexportServiceImpl extends ServiceImpl<ImexportMapper, Imexport> implements ImexportService {

	@Autowired
	ImexportMapper imexportMapper;
	/**
	 * 查询出入库流水表列表
	 * @param map
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public void queryUsedList(Map<String, Object> map, Page<DataRow> pageing) throws Exception {
		pageing.setRecords(imexportMapper.queryUsedList(map, pageing));
		
	}
	/**
	 * 插入库存进出表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertImexport(List<Imexport> t) throws Exception {
		this.insertBatch(t);
	}
}
