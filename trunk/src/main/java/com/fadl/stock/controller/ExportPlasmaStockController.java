package com.fadl.stock.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.ExcelUtil;
import com.fadl.stock.service.PlasmaStockService;

/**
 * 导出浆库数据
 * @author hu
 *
 */
@Controller
@RequestMapping("/plasmaStock")
public class ExportPlasmaStockController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(ExportPlasmaStockController.class);
	
	@Autowired
	public PlasmaStockService plasmaStockService;
	
	/**
	 * 导出浆库数据
	 * @return
	 */
	@RequestMapping("/downPlasmaStockList")
	@RequiresPermissions("queryStock:down")
	public String downPlasmaStockList(@RequestParam HashMap<String, String> data,HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("浆库数据列表", "UTF-8") + ".xlsx");
			// 设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");
			
			List<DataRow> list = plasmaStockService.querySeniorStockList(data);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("providerNo", "献浆员卡号");
			map.put("bname", "姓名");
			map.put("sex", "性别");
			map.put("allId", "登记号");
			map.put("bloodType", "血型");
			map.put("ccreateDate", "采浆时间");
			map.put("type", "浆员类型");
			map.put("status", "是否报废");
			map.put("isStorage", "是否入库");
			map.put("boxId", "箱子编号");
			map.put("plasmaNo", "血浆编号");
			map.put("createDate", "入库时间");
			map.put("name", "入库人");
			exportData("浆库数据列表",map,list,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("ExportPlasmaStockController>> downPlasmaStockList >>Exception:",e);
		}
		return null;
	}
	
	/**
	 * 导出体检数据
	 * @param title
	 * @param headMap
	 * @param list
	 * @param datePattern
	 * @param colWidth
	 * @param out
	 */
	public static void exportData(String title,Map<String, String> headMap,List<DataRow> list,int colWidth, OutputStream out){
		String datePattern="yyyy-MM-dd";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        //设置列宽
        int minBytes = colWidth<17?17:colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        ExcelUtil.setClomnWidth(sheet, colWidth, headMap, properties, headers, arrColWidth, minBytes);
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        //标题样式
        CellStyle titleStyle = ExcelUtil.getTitleStyle(workbook);
        //头部样式
        CellStyle headStyle = ExcelUtil.getHeaderStyle(workbook);
        //表格样式
        CellStyle cellStyle = ExcelUtil.getStyle(workbook);
        for (int k=0;k<list.size();k++) {
            if(rowIndex == 65535 || rowIndex == 0){
                if ( rowIndex != 0 ) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示

                SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

                SXSSFRow headerRow = sheet.createRow(1); //列头 rowIndex =1
                for(int i=0;i<headers.length;i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headStyle);
                }
                rowIndex = 2;//数据内容从 rowIndex=2开始
            }
            DataRow jo =list.get(k);
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                SXSSFCell newCell = dataRow.createCell(i);

                Object o =  jo.get(properties[i]);
                if(null==o){
                	continue;
                }
                if(properties[i].equals("sex")){
                	if(jo.getInt(properties[i])==0){
                		o="男";
                	}else{
                		o="女";
                	}
                }else if(properties[i].equals("bloodType")){
                	if(jo.getInt(properties[i])==0){
                		o="A";
                	}else if(jo.getInt(properties[i])==1){
                		o="B";
                	}else if(jo.getInt(properties[i])==2){
                		o="O";
                	}else{
                		o="AB";
                	}
                }else if(properties[i].equals("type")){
                	if(jo.getInt(properties[i])==0){
                		o="普通";
                	}else if(jo.getInt(properties[i])==1){
                		o="普免";
                	}else{
                		o="特免";
                	}
                }else if(properties[i].equals("isStorage")){
                	if(jo.getInt(properties[i])==0){
                		o="未入库";
                	}else{
                		o="已入库";
                	}
                }else if(properties[i].equals("status")){
                	if(jo.getInt(properties[i])==0){
                		o="正常";
                	}else{
                		o="报废";
                	}
                }
                String cellValue = ""; 
                if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                else if(o instanceof Float || o instanceof Double) 
                    cellValue= new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                else cellValue = o.toString();

                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        try {
            workbook.write(out);
            workbook.close();
            workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 导出出库列表
	 * @param data
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/downOutPlasmaStockList")
	@RequiresPermissions("plasmaStock:outDown")
	public String downOutPlasmaStockList(@RequestParam HashMap<String, String> data,HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("浆库数据列表", "UTF-8") + ".xlsx");
			// 设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");
			
			List<DataRow> list = plasmaStockService.queryOutPlasmaStock(data);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("id", "箱号");
			map.put("sNumber", "数量");
			map.put("fstatus", "状态");
			map.put("immuneName", "类型");
			map.put("createDate", "创建时间");
			map.put("updateDate", "入库时间");
			exportData("浆库数据列表",map,list,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("ExportPlasmaStockController>> downOutPlasmaStockList >>Exception:",e);
		}
		return null;
	}
}
