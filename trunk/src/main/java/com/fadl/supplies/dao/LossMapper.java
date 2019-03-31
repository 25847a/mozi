package com.fadl.supplies.dao;
 
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Loss;
/**
 * <p>
  * 耗材报损记录表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-04-24
 */
public interface LossMapper extends BaseMapper<Loss> {
	/**
	 * 查询报损列表 
	 * @param pagination
	 * @param retur
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryLossList(Pagination pagination,Loss loss)throws Exception;
	/**
	 * 查询报损详情列表 
	 * @param pagination
	 * @param retur
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryLossDatelis(Pagination pagination,Map<String,String> map)throws Exception;
}