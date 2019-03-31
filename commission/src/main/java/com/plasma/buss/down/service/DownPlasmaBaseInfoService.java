package com.plasma.buss.down.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.plasma.buss.plasma.dao.ProviderBaseinfoDao;
import com.plasma.buss.plasma.dao.ProviderBaseinfoTempDao;
import com.plasma.buss.site.dao.PlasmaSiteDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.ReadProperties;
import com.plasma.common.util.DateUtils;
import com.plasma.common.util.FileUtil;
import com.plasma.common.util.JsonUtil;

/**
 * 接收浆站传上来的新浆员数据
 * @author fadl
 *
 */
@Service
public class DownPlasmaBaseInfoService {

	@Autowired
	public ProviderBaseinfoDao providerBaseinfoDao;
	@Autowired
	public PlasmaSiteDao plasmaSiteDao;
	@Autowired
	public ProviderBaseinfoTempDao providerBaseinfoTempDao;
	
	/**
	 * 保存浆员信息
	 * @param info
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void savePlasmaBaseInfo(String info,DataRow messageMap) throws Exception{
		JsonNode node = JsonUtil.getMapper().readTree(info);
		List<DataRow> list =JsonUtil.getMapper().readValue(node.toString(), new TypeReference<List<DataRow>>(){});
		for(DataRow dataRow : list)  {
			//判断浆员信息是否存在	
			DataRow row = providerBaseinfoDao.queryProviderBaseinfoByIdNo(dataRow.getString("idNo"));
			if (null!=row && row.getInt("status")==0) {
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
				messageMap.put(IConstants.APP_RESULT_MSG,"已提交浆员信息,请先审核浆员");
				return;
			}
			//如果不存在，直接插入到浆员信息表
			dataRow.put("providerNo", dataRow.get("newProviderNo"));
			//图片正面进行处理
			String cardz = ReadProperties.getValue("cardz")+dataRow.getString("providerNo")+".jpg";
			FileUtil.string2File(dataRow.getString("imagez"),cardz);
			dataRow.put("imagez",cardz.substring(2,cardz.length()));
			//图片反面进行处理
			String cardf = ReadProperties.getValue("cardf")+dataRow.getString("providerNo")+".jpg";
			FileUtil.string2File(dataRow.getString("imagef"),cardf);
			dataRow.put("imagef",cardf.substring(2,cardf.length()));
			//现场图片进行处理
			String xc = ReadProperties.getValue("xc")+System.currentTimeMillis()+".jpg";
			FileUtil.string2File(dataRow.getString("imagespot"),xc);
			dataRow.put("imagespot",xc.substring(2,xc.length()));
			
			dataRow.put("birthday",DateUtils.formatDate(new Date(dataRow.getLong("birthday")), DateUtils.yyyy_MM_dd_EN));
			/*dataRow.remove("updateDate");
			dataRow.remove("updater");
			dataRow.put("createDate", DateUtils.formatDate(new Date(dataRow.getLong("createDate")), DateUtils.yyyy_MM_dd_EN));*/
			
			//如果原来有浆员信息，插入到 浆员信息临时表
			if (null!=row) {
				// 如果存在，插入临时浆员信息表
				DataRow data = new DataRow();
				data.put("status", "0");
				data.put("id", row.get("id"));
				providerBaseinfoDao.updateProviderBaseinfoById(data);
				dataRow.set("status", "0");
				providerBaseinfoTempDao.saveProviderBaseinfoTemp(dataRow);
			}else{
				//插入浆员基本信息表
				providerBaseinfoDao.insertBaseinfo(dataRow);
				//插入浆员详细信息表
				dataRow.put("baseId", dataRow.get("id"));
				providerBaseinfoDao.insertDetail(dataRow);
			}
		}
		messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
		messageMap.put(IConstants.APP_RESULT_MSG,"操作成功");
	}
	
	/**
	 * 对写卡浆员进行验证
	 * @param providerNo
	 * @return
	 * @throws Exception 
	 */
	public void verification(DataRow data,DataRow messageMap)throws Exception{
		DataRow row = providerBaseinfoDao.verification(data.getString("providerNo"));
		if (null!=row) {
			 messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			 messageMap.put(IConstants.APP_RESULT_DATA, row);
			 messageMap.put(IConstants.APP_RESULT_MSG,"查询成功");
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "浆员还未审核");
		}
	}	
}
