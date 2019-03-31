package com.fadl.supplies.service.impl;
 
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.entity.Config;
import com.fadl.common.service.ConfigService;
import com.fadl.supplies.dao.DetailedMapper;
import com.fadl.supplies.dao.StreamMapper;
import com.fadl.supplies.entity.Detailed;
import com.fadl.supplies.entity.Template;
import com.fadl.supplies.service.DetailedService;
import com.fadl.supplies.service.TemplateService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 耗材模板明细 服务实现类
 * </p>
 *
 * @author 啊健
 * @since 2018-04-23
 */
@Service
public class DetailedServiceImpl extends ServiceImpl<DetailedMapper, Detailed> implements DetailedService {

	@Autowired
	DetailedMapper detailedMapper;
	@Autowired
	StreamMapper streamMapper;
	@Autowired
	ConfigService configService;
	@Autowired
	TemplateService templateService;
	
	/**
	 * 查询详情页面列表
	 * @param pageing
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public void queryTemplateDatelis(Page<DataRow> pageing,String templateId)throws Exception{
		pageing.setRecords(detailedMapper.queryTemplateDatelis(pageing, templateId));
	}
	/**
	 * 使用模板扣除
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public DataRow useDetailed(Long templateId,DataRow messageMap) throws Exception{
			messageMap.initSuccess();
		  	Calendar c = Calendar.getInstance();
			Template t = templateService.selectById(templateId);
			Date date = DateUtil.sf.parse(t.getEndDate());
			c.setTime(date);
			c.add(Calendar.DATE, -1);//当前时间加一天
			if(new Date().getTime()>=c.getTime().getTime() && new Date().getTime()<=date.getTime()) {//如果当前大于等于结束时间前一天,提示快到期了
				messageMap.initSuccess("模板明天到期,请及时更新结束时间");
			}else if(new Date().getTime()>=date.getTime()) {
				messageMap.initFial("模板到期,无法使用,请及时更新结束时间");
				return messageMap;
			}
			List<Map<String, String>> list = detailedMapper.queryDetailed(templateId);//获取耗材信息templateId,num,batchNumber
			if(null!=list) {
				Config config = configService.getConfigByValue("stream_num", "1");
				for(Map<String,String> map:list) {
					Integer result =streamMapper.updateStream(map);
					if(result==0) {
						messageMap.initFial("库存扣除失败,请重试");
						return messageMap;
					}
					Integer ro = streamMapper.queryStreamNum(map);
					if(ro==0){
						messageMap.initFial( "库存为0,无法使用模板");
						return messageMap;
					}else if(null!=ro && ro<=Integer.valueOf(config.getLable())) {
						messageMap.initSuccess( "库存低于"+Integer.valueOf(config.getLable())+"个,只剩"+ro+"个");
					}
				}
			}else {
				messageMap.initFial();
				return messageMap;
			}
			int version = t.getVersion();
			t.setVersion(version+1);
			EntityWrapper<Template> ew = new EntityWrapper<Template>();
			ew.eq("id", t.getId());
			ew.eq("version", version);
			boolean res = templateService.update(t, ew);
			if (!res) {
				throw new SQLException();
			}
			return messageMap;
	}
	/**
	 *  批量插入(用)
	 * @param detailed
	 * @throws Exception
	 */
	@Override
	public void insertDetailed(List<Detailed> detailed) throws Exception {
		this.insertBatch(detailed);
	}
}
