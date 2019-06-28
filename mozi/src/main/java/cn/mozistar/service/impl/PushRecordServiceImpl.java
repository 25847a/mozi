package cn.mozistar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mozistar.mapper.PushRecordMapper;
import cn.mozistar.service.PushRecordService;
import cn.mozistar.util.ResultData;
@Transactional
@Service
public class PushRecordServiceImpl implements PushRecordService{

	
	@Autowired
	PushRecordMapper pushRecordMapper;
	/**
	 * 查询预警记录的各项总数
	 * @param map
	 * @return
	 */
	@Override
	public ResultData<Map<String,Object>> queryPushRecordInfo(Map<String, String> map,ResultData<Map<String,Object>> re) throws Exception {
		Map<String,Object> data = pushRecordMapper.queryPushRecordCount(map);
		if(data!=null){
			List<Map<String,String>> list =pushRecordMapper.queryPushRecordList(map);
			data.put("chartData", list);
			re.setData(data);
			re.setCode(200);
			re.setMessage("获取成功");
		}else{
			re.setCode(350);
			re.setMessage("当天无预警记录");
		}
		return re;
	}
}
