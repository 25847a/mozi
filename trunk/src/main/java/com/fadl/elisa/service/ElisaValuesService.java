package com.fadl.elisa.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.fadl.elisa.entity.ElisaInfo;
import com.fadl.elisa.entity.ElisaValues;

/**
 * <p>
 * 酶标仪检测记录表 服务类
 * </p>
 *
 * @author xim.xie
 * @since 2018-05-30
 */
public interface ElisaValuesService extends IService<ElisaValues> {
	
	/**
	 * 发布酶标仪检测信息 批量发布 
	 * @param ei
	 * @param evids
	 * @param type   0 发布  1 取消发布
	 * @return
	 * @throws Exception
	 */
	boolean releaseElisaValue(ElisaInfo ei, String evids, String type) throws Exception;

	
	/**
	 * 根据小样号查询检测记录
	 * @param sampleNos
	 * @return
	 */
	List<ElisaValues> selectBysampleNos(String[] sampleNos);
}
