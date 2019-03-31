package com.fadl.image.dao;

import java.sql.SQLException;
import java.util.HashMap;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.DataRow;
import com.fadl.image.entity.PlasmaImage;

/**
 * <p>
 * 图片存储表 Mapper 接口
 * </p>
 *
 * @author hu
 * @since 2018-05-09
 */
public interface PlasmaImageMapper extends BaseMapper<PlasmaImage> {

	/**
	 * 根据浆员卡号和时间查询现场图片
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaImageInfo(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 根据 imgId 和 type 获取现场图片
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaImageByImgIdAndType(HashMap<String, String> map)throws SQLException;
}
