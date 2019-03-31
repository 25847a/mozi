package com.fadl.stock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.ExcelUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.inspection.entity.NewCard;
import com.fadl.inspection.service.NewCardService;
import com.fadl.stock.service.PlasmaStockService;

/**
 * <p>
 * 导出浆站数据到公司
 * </p>
 *
 * @author hu
 * @since 2018-05-24
 */
@Controller
@RequestMapping("/plasmaStock")
public class ExportPlasmaDataInfoController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(ExportPlasmaDataInfoController.class);
	 
	@Autowired
	public PlasmaStockService plasmaStockService;
	@Autowired 
	private NewCardService ncService;
	@Autowired
	public ConfigService configService;
	
	/**
	 * 导出数据到公司
	 * @return
	 */
	@RequestMapping("/exportPlasmaToCompany")
	public String exportPlasma(){
		return "/plasmaStock/export_plasma_company";
	}
	
	/**
	 * 导出浆站数据到公司
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/exportPlasmaInfo")
	@InvokeLog(name = "exportPlasmaInfo", description = "导出浆站数据到公司")
	@ResponseBody
	public DataRow exportPlasmaInfo(@RequestParam HashMap<String, String> data){
		try {
			Config config = configService.getConfig("stock_config", "code");
			plasmaInfo(data,"@"+config.getValue()+".xlsx");
			boxInfo(data,"@"+config.getValue()+".xlsx");
			plasmCollection(data,"@"+config.getValue()+".xlsx");
			refuseInfo(data,"@"+config.getValue()+".xlsx");
			testResult(data,"@"+config.getValue()+".xlsx");
			exportPlasmaStockImmune(data,"@"+config.getValue()+".xlsx");
			queryTmAssayInfo(data,"@"+config.getValue()+".xlsx");
			messageMap.initSuccess();
		} catch (Exception e) {
			logger.error("ExportPlasmaDataInfoController>>>>>>>>>exportPlasmaInfo",e);
		}
		return messageMap;
	}
	
	/**
	 * 导出浆员信息
	 * @param data
	 * @throws Exception
	 */
	public void plasmaInfo(HashMap<String, String> data,String suffix)throws Exception{
		List<HashMap<String, String>> list =plasmaStockService.exportPlasmaDataInfo(data);
		Map<String, String> map =  new LinkedHashMap<String, String>();
		map.put("BaseNo", "BaseNo");
		map.put("ICNo", "ICNo");
		map.put("IDNo", "IDNo");
		map.put("Name", "Name");
		map.put("Sex", "Sex");
		map.put("ProviderNo", "ProviderNo");
		map.put("BirthDay", "BirthDay");
		map.put("BloodType", "BloodType");
		map.put("ProviderType", "ProviderType");
		map.put("IsRPRCheck", "IsRPRCheck");
		map.put("NativePlace", "NativePlace");
		map.put("IsMarried", "IsMarried");
		map.put("Nation", "Nation");
		map.put("Address", "Address");
		map.put("Zip", "Zip");
		map.put("Tel", "Tel");
		map.put("ProviderState", "ProviderState");
		map.put("FileDate", "FileDate");
		map.put("Photo", "Photo");
		map.put("IDCardPhoto", "IDCardPhoto");
		map.put("FingerMark", "FingerMark");
		map.put("Times", "Times");
		map.put("SickHistory", "SickHistory");
		map.put("Remark", "Remark");
		map.put("RegistDate", "RegistDate");
		map.put("Register", "Register");
		map.put("RefuseDate", "RefuseDate");
		map.put("RefuseReason", "RefuseReason");
		map.put("CollectionDate", "CollectionDate");
		map.put("LastDateBooking", "LastDateBooking");
		map.put("SmallNo", "SmallNo");
		map.put("TeamNo", "TeamNo");
		map.put("IsAuditing", "IsAuditing");
		map.put("Assessor", "Assessor");
		map.put("OLdNewRedatebz", "OLdNewRedatebz");
		map.put("SFFZ", "SFFZ");
		map.put("update_flag", "update_flag");
		map.put("update_date", "update_date");
		map.put("org_id", "org_id");
		map.put("web_flag", "web_flag");
		map.put("uppic_flag", "uppic_flag");
		map.put("zw_downflag", "zw_downflag");
		map.put("FZDate", "FZDate");
		map.put("FZEmpName", "FZEmpName");
		map.put("FZUnitName", "FZUnitName");
		map.put("Fzsm", "Fzsm");
		map.put("JmgCpbh", "JmgCpbh");
		map.put("JmgXlh", "JmgXlh");
		map.put("FZEmpID", "FZEmpID");
		map.put("FZUnitID", "FZUnitID");
		map.put("OldIcNo", "OldIcNo");
		
		exportExcel("浆员基本信息",map,list,0,"T_ProviderBaseInfo"+suffix);
	}
	
	/**
	 * 导出采浆记录信息
	 */
	public void plasmCollection(HashMap<String, String> data,String suffix)throws Exception{
		List<HashMap<String, String>> list =plasmaStockService.exportCollectionDataInfo(data);
		Map<String, String> map =  new LinkedHashMap<String, String>();
		map.put("Serial", "Serial");
		map.put("CollectionNo", "CollectionNo");
		map.put("PlasmaNo", "PlasmaNo");
		map.put("FactoryPlasmaNo", "FactoryPlasmaNo");
		map.put("PlasmaType", "PlasmaType");
		map.put("IDNo", "IDNo");
		map.put("ProviderNo", "ProviderNo");
		map.put("SmallNo", "SmallNo");
		map.put("CollectionDate", "CollectionDate");
		map.put("CollectionTime", "CollectionTime");
		map.put("CollectionWorker", "CollectionWorker");
		map.put("PlasmaGross", "PlasmaGross");
		map.put("SendPlace", "SendPlace");
		map.put("InStorageDate", "InStorageDate");
		map.put("InStorageTime", "InStorageTime");
		map.put("InStorageWorker", "InStorageWorker");
		map.put("BoxNo", "BoxNo");
		map.put("FactBoxNo", "FactBoxNo");
		map.put("InPacker", "InPacker");
		map.put("BQHD", "BQHD");
		map.put("IsOut", "IsOut");
		map.put("IsAuditing", "IsAuditing");
		map.put("Assessor", "Assessor");
		map.put("org_id", "org_id");
		map.put("update_flag", "update_flag");
		map.put("update_date", "update_date");
		map.put("web_flag", "web_flag");
		map.put("sw_isinto", "sw_isinto");
		map.put("ProviderNoType", "ProviderNoType");
		
		exportExcel("采浆记录",map,list,0,"T_PlasmStock"+suffix);
	}
	
	/**
	 * 导出箱子信息
	 * @param data
	 * @throws Exception
	 */
	public void boxInfo(HashMap<String, String> data,String suffix)throws Exception{
		List<HashMap<String, String>> list =plasmaStockService.exportBoxDataInfo(data);
		int size = list.size();
		String time = DateUtil.formatDate(new Date(),DateUtil.yyyyMMdd);
		for(int i=1;i<=size;i++){
			HashMap<String, String> hashMap = list.get(i-1);
			hashMap.put("Serial",time +String.format("%0"+size+"d", i));
		}
		Map<String, String> map =  new LinkedHashMap<String, String>();
		map.put("Serial", "Serial");
		map.put("BoxNo", "BoxNo");
		map.put("FacBoxNo", "FacBoxNo");
		map.put("BoxSize", "BoxSize");
		map.put("BoxType", "BoxType");
		map.put("IncTime", "IncTime");
		map.put("SendPlace", "SendPlace");
		map.put("SendTime", "SendTime");
		map.put("Sender", "Sender");
		map.put("IsOut", "IsOut");
		map.put("Remark", "Remark");
		map.put("org_id", "org_id");
		map.put("update_flag", "update_flag");
		map.put("update_date", "update_date");
		map.put("web_flag", "web_flag");
		
		exportExcel("箱子信息",map,list,0,"T_BoxNo"+suffix);
	}
	
	/**
	 * 导出拒绝信息
	 * @param data
	 * @throws Exception
	 */
	public void refuseInfo(HashMap<String, String> data,String suffix)throws Exception{
		List<HashMap<String, String>> list =plasmaStockService.queryRefuseDataInfo(data);
		Map<String, String> map =  new LinkedHashMap<String, String>();
		map.put("RefuseId", "RefuseId");
		map.put("providerno", "providerno");
		map.put("RefuseDate", "RefuseDate");
		map.put("RefuseReason", "RefuseReason");
		map.put("isBodyCheck", "isBodyCheck");
		map.put("isAssayCheck", "isAssayCheck");
		map.put("worker", "worker");
		map.put("providernostate", "providernostate");
		map.put("org_id", "org_id");
		map.put("update_flag", "update_flag");
		map.put("update_date", "update_date");
		map.put("web_flag", "web_flag");
		map.put("SerialID", "SerialID");
		exportExcel("拒绝信息",map,list,0,"T_RefuseRecord"+suffix);
	}
	
	/**
	 * 导出化验信息
	 * @param data
	 * @throws Exception
	 */
	public void testResult(HashMap<String, String> data,String suffix)throws Exception{
		NewCard newCard = new NewCard();
		newCard.setStartTime(data.get("startDate"));
		newCard.setEndTime(data.get("endDate"));
		newCard.setIsAssay(1);
		List<HashMap<String, String>> lst = ncService.queryListByUpdateDateAndIsAssayWith1( newCard);
		for(HashMap<String, String> hm : lst) {
			if("0".equals(hm.get("alt"))) {
				hm.put("alt","3");
			}else {
				hm.put("alt","2");
			}
		}
		Map<String, String> map =  new LinkedHashMap<String, String>();
		map.put("AssayNo", "AssayNo");
		map.put("BodyCheckNo", "BodyCheckNo");
		map.put("idNo", "IDNo");
		map.put("providerNo", "ProviderNo");
		map.put("sampleNo", "SmallNo");
		map.put("updateDate", "AssayDate");
		map.put("reportAdminid", "AssayWorker");
		map.put("rechecked", "IsRepeat");
		map.put("hbsag", "HBsAg");
		map.put("hcv", "HCV");
		map.put("alt", "ALT"); // 3<=25 2>25 1>=57 0>=97
		map.put("hiv", "HIV");
		map.put("proteinValue", "Proteide");
		map.put("syphilis", "Syphilis");
		map.put("wholeBlood", "AllBlood");
		map.put("Icterus", "Icterus");
		map.put("TempletNo", "TempletNo");
		map.put("result", "AssayResult");
		map.put("IsTemplet", "IsTemplet");
		map.put("opinion", "docResult");
		map.put("day", "refuseday");
		map.put("remarks", "Remark");
		map.put("IsAuditing", "IsAuditing");
		map.put("reportAdminid", "Assessor");
		map.put("YMShang", "YMShang");
		map.put("YMXia", "YMXia");
		map.put("BZHG", "BZHG");
		map.put("BZBHG", "BZBHG");
		map.put("KangA", "KangA");
		map.put("KangB", "KangB");
		map.put("AHSBao", "AHSBao");
		map.put("BHSBao", "BHSBao");
		map.put("PDXX", "PDXX");
		map.put("LJZT", "LJZT");
		map.put("JGPD", "JGPD");
		map.put("LSTJG", "LSTJG");
		map.put("ZSYKDZ", "ZSYKDZ");
		map.put("DBZJG", "DBZJG");
		map.put("XHDB_JCZ", "XHDB_JCZ");
		map.put("XHDB_JG", "XHDB_JG");
		map.put("org_id", "org_id");
		map.put("update_flag", "update_flag");
		map.put("update_date", "update_date");
		map.put("web_flag", "web_flag");
		map.put("XYH_SmallNo", "XYH_SmallNo");
		
		exportExcel("特免化验记录",map,lst,0,"T_AssayRecord"+suffix);
		
	}
	/**
	 * 导出浆员免疫信息表
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException 
	 */
	public void exportPlasmaStockImmune(HashMap<String,String> map,String suffix) throws SQLException {
		List<HashMap<String, String>> list = plasmaStockService.exportPlasmaStockImmune(map);
		Map<String,String> result = new LinkedHashMap<String,String>();
		result.put("ID", "ID");
		result.put("SortNo", "SortNo");
		result.put("ImmunityName", "ImmunityName");
		result.put("BasePin", "BasePin");
		result.put("StrengthenPin", "StrengthenPin");
		result.put("Abate", "Abate");
		result.put("BasicDeductM", "BasicDeductM");
		result.put("StrengthenDeductM", "StrengthenDeductM");
		result.put("BoxbagNumber", "BoxbagNumber");
		result.put("AbateTime", "AbateTime");
		result.put("SortType", "SortType");
		result.put("Remark", "Remark");
		result.put("org_id", "org_id");
		result.put("update_flag", "update_flag");
		result.put("update_date", "update_date");
		result.put("web_flag", "web_flag");
		exportExcel("免疫类别设置",result,list,0,"M_ImmunitySort"+suffix);
	}
	/**
	 * 导出浆员免疫化验记录表
	 * @param map
	 * @return
	 * @throws SQLException 
	 */
	public void queryTmAssayInfo(HashMap<String,String> map,String suffix) throws SQLException {
		List<HashMap<String, String>> list = plasmaStockService.queryTmAssayInfo(map);
		Map<String,String> result = new LinkedHashMap<String,String>();
		result.put("ID", "ID");
		result.put("TMRegNo", "TMRegNo");
		result.put("ProviderType", "ProviderType");
		result.put("ProviderNo", "ProviderNo");
		result.put("SmallNo", "SmallNo");
		result.put("State", "State");
		result.put("AssayDate", "AssayDate");
		result.put("SXH", "SXH");
		result.put("Assayer", "Assayer");
		result.put("regno", "regno");
		result.put("PinCount", "PinCount");
		result.put("Avalue", "Avalue");
		result.put("AssCount", "AssCount");
		result.put("AssResult", "AssResult");
		result.put("Remark", "Remark");
		result.put("IsAuditing", "IsAuditing");
		result.put("Assessor", "Assessor");
		result.put("org_id", "org_id");
		result.put("update_flag", "update_flag");
		result.put("update_date", "update_date");
		result.put("web_flag", "web_flag");
		result.put("org_id_SW", "org_id_SW");
		result.put("update_flag_SW", "update_flag_SW");
		result.put("update_date_SW", "update_date_SW");
		result.put("web_flag_SW", "web_flag_SW");
		result.put("org_id_QT", "org_id_QT");
		result.put("update_flag_QT", "update_flag_QT");
		result.put("update_date_QT", "update_date_QT");
		result.put("web_flag_QT", "web_flag_QT");
		exportExcel("化验记录",result,list,0,"T_TmAssay"+suffix);
	}
	/**
	 * @param title
	 * @param headMap
	 * @param list
	 * @param datePattern
	 * @param colWidth
	 * @param out
	 */
	public static void exportExcel(String title,Map<String, String> headMap,List<HashMap<String, String>> list,int colWidth,String outputPath){
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        //设置列宽
        int minBytes = colWidth<17?17:colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        ExcelUtil.setClomnWidth(sheet, colWidth, headMap, properties, headers, arrColWidth, minBytes);
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        //标题样式
        //CellStyle titleStyle = ExcelUtil.getTitleStyle(workbook);
        //头部样式
       CellStyle headStyle = ExcelUtil.getHeaderStyle(workbook);
        //表格样式
        CellStyle cellStyle = ExcelUtil.getStyle(workbook);
        for (int k=0;k<list.size();k++) {
            if(rowIndex == 65535 || rowIndex == 0){
                if ( rowIndex != 0 ) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示

               // SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
           //  titleRow.createCell(0).setCellValue(title);
               //titleRow.getCell(0).setCellStyle(titleStyle);
               // sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, list.get(0).size() - 1));

                SXSSFRow headerRow = sheet.createRow(0); //列头 rowIndex =1
                for(int i=0;i<headers.length;i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headStyle);
                }
                rowIndex = 1;//数据内容从 rowIndex=2开始
            }
            HashMap<String, String> jo =list.get(k);
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                SXSSFCell newCell = dataRow.createCell(i);

                Object o =  jo.get(properties[i]);
                newCell.setCellValue(null!=o ? String.valueOf(o) : "");
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        try {
        	String path = ReadProperties.getValue("baseDir");
        	File file = new File(path+File.separator+DateUtil.formatDate(new Date(), DateUtil.yyyy_MM_dd_EN));
        	if (!file.exists()) {
        		file.mkdirs();
			}
        	file = new File(file.getPath(),outputPath);
    		if (!file.exists()) {
    			file.createNewFile();
    		}else{
    			file.delete();
    			file.createNewFile();
    		}
        	OutputStream out = new FileOutputStream(file);
            workbook.write(out);
            workbook.close();
            workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
