package cn.mozistar.service;

import cn.mozistar.util.DataRow;
import cn.mozistar.util.ResultData;

public interface MessageService {
	
	public ResultData<DataRow> queryMessage(DataRow map,ResultData<DataRow> re)throws Exception;
	
}
