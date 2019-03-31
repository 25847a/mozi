package com.fadl.image.service;

import java.sql.SQLException;
import java.util.HashMap;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.common.DataRow;
import com.fadl.image.entity.PlasmaImage;

/**
 * <p>
 * 图片存储表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-09
 */
public interface PlasmaImageService extends IService<PlasmaImage> {

	/**
	 * 保存图片
	 * @param image
	 * @param id
	 * @throws Exception
	 */
	public void saveImage(String image,Integer type, Long id,DataRow messageMap)throws Exception;

	/**
	 * 根据浆员卡号和时间查询现场图片
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaImageInfo(HashMap<String, String> map)throws Exception;
	
	/**
	 * 根据 imgId 和 type 获取现场图片
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaImageByImgIdAndType(HashMap<String, String> map)throws SQLException;
	
	/**
	 * 根据 imgId 和 type 删除数据
	 * @param id
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public int deletePlasmaImage(Long imgId,Integer type)throws SQLException;
}
