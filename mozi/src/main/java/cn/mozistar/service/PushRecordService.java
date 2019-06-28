package cn.mozistar.service;

import java.util.Map;

import cn.mozistar.util.ResultData;

public interface PushRecordService {

	/**
	 * 查询预警记录的各项总数
	 * @param map
	 * @return
	 */
	public ResultData<Map<String,Object>> queryPushRecordInfo(Map<String,String> map,ResultData<Map<String,Object>> re)throws Exception;
}
