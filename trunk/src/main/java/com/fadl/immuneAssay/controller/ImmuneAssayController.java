package com.fadl.immuneAssay.controller;


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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.account.entity.Admin;
import com.fadl.account.service.AdminService;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.ExcelUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.immuneAssay.entity.ImmuneAssay;
import com.fadl.immuneAssay.service.ImmuneAssayService;
/**
 * <p>
 * 免疫化验 前端控制器
 * </p>
 *
 * @author wangjing
 * @since 2018-05-15
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("/immuneAssay")
public class ImmuneAssayController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(ImmuneAssayController.class);
	@Autowired
	public ImmuneAssayService immuneAssayService;
	@Autowired
	AdminService adminservice;
	@Autowired
	ConfigService configservice;
	/**
	 * 特免化验
	 * @return
	 */
	@RequestMapping("/toSpecialImmune")
	@RequiresPermissions("immuneAssay:view")
	public String toSpecialImmune() {
		return "/immune/immunoassay/special_immune";
	}
	/**
	 * 跳转免疫流程配置页面
	 * @return
	 */
	@RequestMapping("/immuneConfigPage")
	public String immuneConfigPage() {
		return "/collectionConfig/immune_config";
	}

	/**
	 * 特免化验查询页面
	 * @return
	 */
	@RequestMapping("/toQuerySpecialImmune")
	@RequiresPermissions("immuneAssayQuery:view")
	public String toQuerySpecialImmune() {
		return "/immune/immunoassay/query_special_immune";
	}
	/**
	 * 免疫流程配置列表
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/immuneConfig")
	@ResponseBody
	public DataRow immuneConfig (@RequestParam Map<String,String> map,Integer page,Integer limit) {
		try {
			Page<Config> pageing = new Page<Config>(page,limit);
			EntityWrapper<Config> ew = new EntityWrapper<Config>();
			ew.eq("type", map.get("type"));
			if(map.containsKey("isDisable") && !"".equals(map.get("isDisable"))) {
				ew.eq("isDisable", map.get("isDisable"));
			}
			if(map.containsKey("configName") && !"".equals(map.get("configName"))) {
				ew.and();
				ew.like("configName", map.get("configName"), SqlLike.DEFAULT);
			}
			 pageing =configservice.selectPage(pageing, ew);
			 messageMap.initPage(pageing);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>immuneConfig",e);
		}
		return messageMap;
	}
	/**
	 * 修改免疫流程配置状态
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateImmuneConfigType")
	@InvokeLog(name = "updateImmuneConfigType", description = "修改免疫流程配置状态")
	@ResponseBody
	public DataRow updateImmuneConfigType(String type,String id) {
		try {
			immuneAssayService.updateImmuneConfigType(type,id,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>updateImmuneConfigType",e);
		}
		return messageMap;
	}
	/**
	 * 查询最后效价日期，效价值
	 * @return
	 */
	@RequestMapping("/queryImmuneAssayLast")
	@InvokeLog(name = "queryImmuneAssayLast", description = "查询最后效价日期，效价值")
	@ResponseBody
	public DataRow queryImmuneAssayLast(ImmuneAssay immuneAssay){
		try {
			EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new ImmuneAssay());
            ew.eq("providerNo",immuneAssay.getProviderNo());
            ew.orderBy("createDate", false);
            ew.last("LIMIT 1,1");
            ImmuneAssay imm =immuneAssayService.selectOne(ew);
            messageMap.initSuccess(imm);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>queryImmuneAssayLast",e);
		}
		return messageMap;
	}
	/**
	 * 免疫化验   特免化验（未化验）
	 * @param workTime
	 * @return
	 */
	@RequestMapping("/specialNotAssay")
	@InvokeLog(name = "specialNotAssay", description = "免疫化验   特免化验（未化验）")
	@RequiresPermissions("immuneAssay:listW")
	@ResponseBody
	public DataRow specialNotAssay(String createDate,Integer page,Integer limit) {
		try {
			if(StringHelper.isEmpty(createDate)) {
				createDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			immuneAssayService.specialNotAssay(createDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>>specialNotAssay>>>>>>",e);
		}
		return messageMap;
	}
	
	/**
	 * 免疫化验   特免化验（已化验）
	 * @param workTime
	 * @return
	 */
	@RequestMapping("/specialHaveAssay")
	@InvokeLog(name = "specialHaveAssay", description = "免疫化验   特免化验（已化验）")
	@RequiresPermissions("immuneAssay:listY")
	@ResponseBody
	public DataRow specialHaveAssay(String createDate,Integer page,Integer limit) {
		try {
			if(StringHelper.isEmpty(createDate)) {
				createDate=DateUtil.sfDay.format(new Date());
			}
			Page<DataRow> paging = new Page<>(page,limit);
			immuneAssayService.specialHaveAssay(createDate, paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>>specialHaveAssay>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 扫描器自动更新浆员信息
	 * @param allId
	 * @return
	 */
	@RequestMapping("/updateAssayNumber")
	@ResponseBody
	public DataRow updateAssayNumber(String allId) {
		try {
			immuneAssayService.updateAssayNumber(allId,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>>updateAssayNumber>>>>>>",e);
		}
		return messageMap;
		
	}
	/**
	 * 根据时间和卡号查询头部信息(啊健)
	 * @param providerNo
	 * @return
	 */
	@RequestMapping("/queryHeadInfo")
	@InvokeLog(name = "queryHeadInfo", description = "根据时间和卡号查询头部信息(啊健)")
	@ResponseBody
	public DataRow queryHeadInfo(@RequestParam Map<String,String> map) {
		try {
			immuneAssayService.queryHeadInfo(map,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>>specialAssayHead>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	
	/**
	 * 获取所有用户信息(找不到公共接口,自己写一个)(啊健)
	 * @return
	 */
	@RequestMapping("/getImmuneAssayAdmin")
	@InvokeLog(name = "getImmuneAssayAdmin", description = "获取所有用户信息")
	@ResponseBody
	public DataRow getImmuneAssayAdmin() {
		try {
			List<Admin> result = adminservice.selectList(null);
			messageMap.initSuccess(result);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>getImmuneAssayAdmin",e);
		}
		return messageMap;
	}
	/**
	 * 免疫化验   特免化验  --> 发布化验
	 * @param entity
	 * @return
	 */
	@RequestMapping("/publishAssay")
	@InvokeLog(name = "publishAssay", description = "特免化验发布")
	@RequiresPermissions("immuneAssay:fabu")
	@ResponseBody
	public DataRow publishAssay(ImmuneAssay entity) {
		try {
			immuneAssayService.publishAssay(entity,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>publishAssay",e);
		}
		return messageMap;
	}
	
	/**
	 * 免疫化验   特免化验  --> 取消化验
	 * @param entity
	 * @return
	 */
	@RequestMapping("/cancelAssay")
	@InvokeLog(name = "cancelAssay", description = "特免取消发布")
	@RequiresPermissions("immuneAssay:quxiao")
	@ResponseBody
	public DataRow cancelAssay(ImmuneAssay entity) {
		try {
			immuneAssayService.cancelAssay(entity,messageMap);
		} catch (Exception e) {
			logger.error("ImmuneSettingController>>>>>>>>>>>>>publishAssay",e);
		}
		return messageMap;
	}
	/**
	 * 特免化验查询结果
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping("/querySpecialImmune")
	@InvokeLog(name = "querySpecialImmune", description = "特免化验查询")
	@RequiresPermissions("immuneAssayQuery:list")
	@ResponseBody
	public DataRow querySpecialImmune(Integer page,Integer limit,@RequestParam HashMap<String, String> map) {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			immuneAssayService.querySpecialImmune(map,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>>querySpecialImmune>>>>>>",e);
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
	 * 特免化验查询结果导出EXCEL
	 * @param data
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/exportSpecialImmune",method=RequestMethod.GET)
	@RequiresPermissions("immuneAssayQuery:dao")
	@InvokeLog(name = "exportSpecialImmune", description = "特免化验查询结果导出EXCEL")
	public String exportSpecialImmune(@RequestParam HashMap<String, String> data,HttpServletResponse response,HttpServletRequest request) {
		try {
			//设置表头
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("特免检测查询结果表", "UTF-8") + ".xlsx");
			//设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");

			List<DataRow> list = immuneAssayService.exportSpecialImmune(data);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("number", "序号");
			map.put("providerNo", "献浆卡号");
			map.put("name", "姓名");
			map.put("sex", "性别");
			map.put("immuneId", "免疫编号");
			map.put("immuneName", "类型");
			map.put("updateDate", "检测日期");
			map.put("effectiveValue", "效价");
			map.put("result", "检测结果");
			map.put("jyName", "检测人");
			exportData("特免检测查询",map,list,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("ImmuneAssayController>>>>>>>>>exportCostGrant>>>>>>",e);
		}
		
		return null;
	}
	public static void exportData(String title,Map<String, String> headMap,List<DataRow> list,int colWidth, OutputStream out){
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
                	if((int)o==0){
                		o="男";
                	}else{
                		o="女";
                	}
                }else if(properties[i].equals("result")) {
                	if((int)o==0){
                		o="合格";
                	}else if((int)o==1){
                		o="不合格";
                	}
                }
                String cellValue = ""; 
                if(o instanceof Date) cellValue = new SimpleDateFormat("yyyy-MM-dd").format(o);
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
}

