package cn.mozistar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mozistar.mapper.MessageMapper;
import cn.mozistar.service.MessageService;
import cn.mozistar.util.DataRow;
import cn.mozistar.util.ResultData;

@Transactional
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageMapper messageMapper;

	@Override
	public ResultData<DataRow> queryMessage(DataRow map, ResultData<DataRow> re) throws Exception {
		List<DataRow> message = messageMapper.queryMessageList(map.getInt("userId"));
		if(!message.isEmpty()){
			re.setCode(200);
			re.setData(message);
			re.setMessage("获取成功!");
		}else{
			re.setCode(400);
			re.setMessage("无消息信息");
		}
		return re;
	}
}
