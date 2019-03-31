package com.fadl.image.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.common.DataRow;
import com.fadl.common.ImageUtil;
import com.fadl.common.ReadProperties;
import com.fadl.image.dao.PlasmaImageMapper;
import com.fadl.image.entity.PlasmaImage;
import com.fadl.image.service.PlasmaImageService;

/**
 * <p>
 * 图片存储表 服务实现类
 * </p>
 *
 * @author hu
 * @since 2018-05-09
 */
@Service
public class PlasmaImageServiceImpl extends ServiceImpl<PlasmaImageMapper, PlasmaImage> implements PlasmaImageService {

	@Autowired
	public PlasmaImageMapper plasmaImageMapper;
	
	/**
	 * 保存图片
	 */
	@Override
	public void saveImage(String image,Integer type, Long id,DataRow messageMap) throws Exception {
		//保存现场图片到 图片表
		PlasmaImage plasmaImage = new PlasmaImage();
		plasmaImage.setType(type);
		plasmaImage.setImgId(id);
		String filePath = ReadProperties.getValue("spotImg");
		String time = System.currentTimeMillis()+".jpg";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
        }
		//保存现场图片到 图片表
		ImageUtil.saveImage(image, filePath+File.separator+time);
		plasmaImage.setImgUrl(filePath.substring(filePath.indexOf("/"),filePath.length())+"/"+time);
		boolean r = this.insert(plasmaImage);
		if (r) {
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
			throw new SQLException("插入图片表出错>>>>>>>>>>>>");
		}
	}

	/**
	 * 根据浆员卡号和时间查询现场图片
	 */
	@Override
	public DataRow queryPlasmaImageInfo(HashMap<String, String> map) throws Exception {
		return plasmaImageMapper.queryPlasmaImageInfo(map);
	}
	
	/**
	 * 根据 imgId 和 type 获取现场图片
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryPlasmaImageByImgIdAndType(HashMap<String, String> map)throws SQLException{
		return plasmaImageMapper.queryPlasmaImageByImgIdAndType(map); 
	}
	
	/**
	 * 根据 imgId 和 type 删除数据
	 * @param id
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public int deletePlasmaImage(Long imgId,Integer type)throws SQLException{
		EntityWrapper<PlasmaImage> image = new EntityWrapper<PlasmaImage>();
		image.eq("imgId", imgId);
		image.eq("type", type);
		return plasmaImageMapper.delete(image);
	}
}
