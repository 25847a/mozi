package com.fadl.cost.controller;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fadl.common.AbstractController;
import com.fadl.common.DataRow;
import com.fadl.common.ExcelUtil;
import com.fadl.common.annotation.InvokeLog;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;

/**
 * 费用发放 以及 费用发放查询  前端控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/costGrant")
public class CostGrantController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CostGrantController.class);
	@Autowired
	private ICostGrantService costGrantService;
	
	/**
	 * 
	 * */
	@RequestMapping("/index")
	@RequiresPermissions("cost:view")
	public String costGrant() {
		return "/cost/grant_cost_list";
	}
	
	/**
	 * 费用管理 ----->浆员费用信息列表(未发放)
	 * @param page
	 * @param limit
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryNotGrantList")
	@InvokeLog(name = "queryNotGrantList", description = "费用信息列表(未发放)")
	@ResponseBody
	@RequiresPermissions("cost:view")
	public DataRow queryNotGrantList (@RequestParam(name="page") Integer page,@RequestParam(name="limit") Integer limit,CostGrant cost) throws Exception {
		try {
			Page<DataRow> paging = new Page<>(page,limit);
			costGrantService.queryNotGrantList(cost,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryPlasmaCost>>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 读卡判断是否存在此浆员
	 * @param cost
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryNotGrantInfo")
	@ResponseBody
	public DataRow queryNotGrantInfo (CostGrant cost) throws Exception {
		try {
			EntityWrapper<CostGrant> ew = new EntityWrapper<CostGrant>();
			ew.eq("providerNo", cost.getProviderNo());
			ew.eq("DATE_FORMAT(updateDate,'%Y-%m-%d')", cost.getUpdateDate());//本来是UpdateDate,但是改动太大
			ew.and();ew.isNotNull("collDate");
			CostGrant grant =costGrantService.selectOne(ew);
			messageMap.initSuccess(grant);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryNotGrantInfo>>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 查看头部文件
	 * @param cost
	 * @return
	 */
	@RequestMapping("/queryCostHeadInfo")
	@ResponseBody
	public DataRow queryCostHeadInfo(CostGrant cost) {
		try {
			EntityWrapper<CostGrant> ew = new EntityWrapper<CostGrant>();
			ew.eq("providerNo", cost.getProviderNo());
			ew.eq("allId", cost.getAllId());
			ew.eq("isGrant", cost.getIsGrant());
			CostGrant grant=costGrantService.selectOne(ew);
			messageMap.initSuccess(grant);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryCostHeadInfo>>>>>>", e);
		}
		return messageMap;
	}
	/**
	 *  费用管理 ----->浆员费用信息列表(已发放)
	 * @param page
	 * @param limit
	 * @param createDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryGrantList")
	@InvokeLog(name = "queryGrantList", description = "费用信息列表(已发放)")
	@ResponseBody
	@RequiresPermissions("cost:view")
	public DataRow queryGrantList (@RequestParam(name="page") Integer page,@RequestParam(name="limit") Integer limit,@RequestParam String createDate) throws Exception {
		try {	
			Page<DataRow> paging = new Page<>(page,limit);
			costGrantService.queryGrantList(createDate,paging);
			messageMap.initPage(paging);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryPlasmaCost>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理 ----->费用发放记录查询（分页）
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="/getCostGrants",method=RequestMethod.POST)
	@InvokeLog(name = "getCostGrants", description = "费用发放记录查询")
	@ResponseBody
	@RequiresPermissions("admin:view")
	public DataRow getCostGrants(Integer page,Integer limit) {
		try {
			List<CostGrant> dataList = costGrantService.getCostGrant(page, limit);
			messageMap.initSuccess(dataList);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>getCostGrants>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理 ----->根据相关条件查询费用发放信息页面
	 * @return
	 */
	@RequestMapping("/getCostGrantDetailMemu")
	public String getCostGrantDetailMenu() {
		
		return "/cost/plasma_cost_detail";
	}
	
	/**
	 * 费用管理 ----->根据providerNo相关条件查询费用发放信息
	 * @param param
	 * @return
	 */
	@RequestMapping("/getCostGrantDetailList")
	@InvokeLog(name = "getCostGrantDetailList", description = "根据providerNo相关条件查询费用发放信息")
	@ResponseBody
	public DataRow getCostGrantDetailList(String param) {
		try {
			List<DataRow> list = costGrantService.getCostGrantDetailList(param);
			if(list.size() > 0) {
				messageMap.initSuccess(list);
			}
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>getCostGrantDetailList>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理 ----->费用发放首页（根据费用发放表的主键id进行查询）
	 * @param id
	 * @param createDate
	 * @return
	 */
	@RequestMapping("/queryCostHead")
	@InvokeLog(name = "queryAdminLogin", description = "费用发放首页（根据费用发放表的主键id进行查询）")
	@ResponseBody
	@RequiresPermissions("cost:view")
	public DataRow queryCostHead(Long id,String createDate,String code) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("createDate", createDate);
			map.put("code", code);
			Map<String, Object> list = costGrantService.queryCostHead(map);
		if(list.size() > 0) {
			messageMap.initSuccess(list);
		}
		}catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryCostHead>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 发放费用
	 * @param costGrant
	 * @param image
	 * @return
	 */
	@RequestMapping("/updateStatusTo1")
	@InvokeLog(name = "updateStatusTo1", description = "修改发放状态(取消发放--发放)")
	@ResponseBody
	@RequiresPermissions("cost:grant")
	public DataRow updateStatusTo1(CostGrant costGrant,String image) {
		try {
			if(costGrantService.updateStatusTo1(costGrant,image,messageMap) >0) {
				messageMap.initSuccess();
			}
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>updateStatus>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 取消发放
	 * @param costGrant
	 * @param image
	 * @return
	 */
	@RequestMapping("/updateStatusTo0")
	@InvokeLog(name = "updateStatusTo0", description = "修改发放状态(取消发放--发放)")
	@ResponseBody
	public DataRow updateStatusTo0(CostGrant costGrant) {
		try {
			if(null==costGrant.getId()){
				messageMap.initFial("请选择浆员");
			}else{
				int res = costGrantService.updateStatusTo0(costGrant, messageMap);
				if (res > 0) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
			}
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>updateStatus>>>>>>", e);
		}
		return messageMap;
	}
	/**
	 * 费用发放验证是否已经进行免疫流程
	 * @param providerNo
	 * @return
	 */
	@RequestMapping(value="/costImmunity")
	@InvokeLog(name = "costImmunity", description = "验证是否需要进行免疫流程")
	@ResponseBody
	public DataRow costImmunity(String providerNo) {
		try {
			costGrantService.costImmunity(providerNo,messageMap);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>costImmunity",e);
		}
		return messageMap;
	}
	/**
	 * 费用管理 ----->进入费用发放查询页面
	 * @return
	 */
	@RequestMapping("/toCostQuery")
	public String toCostQuery() {
		return "/cost/cost_query_list";
	}
	
	/**
	 * 费用管理 ----->费用发放查询页面（根据相关条件查询）
	 * @param page
	 * @param limit
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryCostGrantList")
	@InvokeLog(name = "queryCostGrantList", description = "费用发放查询页面（根据相关条件查询）")
	@ResponseBody
	public DataRow queryCostGrantList(Integer page,Integer limit,@RequestParam HashMap<String, String> map) {
		try {	
			Page<DataRow> page2 = new Page<>(page,limit);
			// 参数全部为空时，返回空
			costGrantService.queryCostGrantList(map, page2);
			messageMap.initPage(page2);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>queryCostGrantList>>>>>>", e);
		}
		return messageMap;
	}
	
	/**
	 * 费用管理 ----->费用发放---->费用详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/grantCostDetail")
	public String toGrantCostDetail(Long id) {
		return "/cost/grant_cost_detail";
	}
	
	@RequestMapping("/grantCostDetailData")
	@InvokeLog(name = "grantCostDetailData", description = "费用发放详情")
	@ResponseBody
	public DataRow grantCostDetail(Long id) {
		List<DataRow> list = null;
		try {
			list = costGrantService.costDetail(id);
			messageMap.initSuccess(list);
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>grantCostDetail>>>>>>", e);
		}
		return messageMap;
	}
	
	
	/**
	 * 费用管理 ----->费用发放导出Excel（根据相关条件查询）
	 * @throws Exception 
	 * */
	@RequestMapping(value="/exportCostGrant",method=RequestMethod.GET)
	@InvokeLog(name = "exportCostGrant", description = "费用发放导出Excel")
	public String exportCostGrant(@RequestParam HashMap<String, String> data,HttpServletResponse response,HttpServletRequest request) {
		try {
			//设置表头
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode("费用发放表", "UTF-8") + ".xlsx");
			//设置字符集
			response.setContentType("application/msexcel;charset=UTF-8");
			data.put("pageNum", "0");
			data.put("pageSize", "100000");
			List<DataRow> list = costGrantService.exportCostGrant(data);
			Map<String, String> map =  new LinkedHashMap<String, String>();
			map.put("providerNo", "浆员卡号");
			map.put("name", "姓名");
			map.put("sex", "性别");
			map.put("bloodType", "血型");
			map.put("type", "浆员类型");
			map.put("collDate", "采浆时间");
			map.put("money", "发放金额");
			map.put("createDate", "发放日期");
			map.put("updater", "发放人");
			exportData("费用发放表",map,list,0,response.getOutputStream());
		} catch (Exception e) {
			logger.error("CostGrantController>>>>>>>>>exportCostGrant>>>>>>",e);
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
                }else if(properties[i].equals("bloodType")){
                	if((int)o==0){
                		o="A";
                	}else if((int)o==1){
                		o="B";
                	}else if((int)o==2){
                		o="O";
                	}else if((int)o==3){
                		o="AB";
                	}
                }else if(properties[i].equals("type")) {
                	if((int)o==0){
                		o="普通";
                	}else if((int)o==1){
                		o="普免";
                	}else if((int)o==2){
                		o="特免";
                	}
                }
                String cellValue = ""; 
                if(o instanceof Date) cellValue = new SimpleDateFormat("yyyy-MM-dd").format(o);
                else if(o instanceof Float || o instanceof Double) 
                    cellValue= new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                else cellValue = o.toString();
                if(i==6){
                	cellValue = (jo.getDouble("money")+jo.getDouble("fmoney")+jo.getDouble("roadFee"))+"";
                }
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
