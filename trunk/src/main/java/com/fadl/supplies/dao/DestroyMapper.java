package com.fadl.supplies.dao;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.common.DataRow;
import com.fadl.supplies.entity.Destroy;
 
/**
 * <p>
  * 耗材销毁信息表 Mapper 接口
 * </p>
 *
 * @author CJ
 * @since 2018-05-03
 */
public interface DestroyMapper extends BaseMapper<Destroy> {
	/**
	 * 查询报损列表 
	 * @param pagination
	 * @param destroy
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryDestroyList(Pagination pagination,Destroy destroy)throws Exception;
	/**
	 * 查询报损详情列表 
	 * @param pagination
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryDestroyDatelis(Pagination pagination,Map<String,String> map)throws Exception;
}