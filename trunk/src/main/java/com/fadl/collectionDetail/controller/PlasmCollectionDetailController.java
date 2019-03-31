package com.fadl.collectionDetail.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.collectionDetail.entity.PlasmCollectionDetail;
import com.fadl.collectionDetail.service.PlasmCollectionDetailService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hkk
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/plasmCollectionDetail")
public class PlasmCollectionDetailController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(PlasmCollectionDetailController.class);
	@Autowired
	public PlasmCollectionDetailService plasmCollectionDetailService;
	
	/**
	 * 查询采浆记录列表
	 * @return
	 */
	@RequestMapping(value="/queryPlasmCollectionDetailListByCollectionId",method = RequestMethod.POST)
	@ResponseBody
	public DataRow queryPlasmCollectionDetailListByCollectionId(@RequestParam Long id){
		try {
			EntityWrapper<PlasmCollectionDetail> detail = new EntityWrapper<PlasmCollectionDetail>();
			detail.eq("collectionId", id);
			List<PlasmCollectionDetail> list = plasmCollectionDetailService.selectList(detail);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("PlasmCollectionDetailController>>>>>>>>>queryPlasmCollectionDetailListByCollectionId",e);
		}
		return messageMap;
	}
}

