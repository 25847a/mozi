package cn.mozistar.service;

import cn.mozistar.util.DataRow;
import cn.mozistar.util.ResultData;

public interface PushRecordService {

	/**
	 * 查询预警记录的各项总数
	 * @param map
	 * @return
	 */
	public ResultData<DataRow> queryPushRecordInfo(DataRow map,ResultData<DataRow> re)throws Exception;
}
