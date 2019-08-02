package cn.mozistar.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.mozistar.mapper.PushRecordMapper;
import cn.mozistar.service.PushRecordService;
import cn.mozistar.util.DataRow;
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
	public ResultData<DataRow> queryPushRecordInfo(DataRow map,ResultData<DataRow> re) throws Exception {
		DataRow data = pushRecordMapper.queryPushRecordCount(map);
		DataRow dataRow = new DataRow();
		DataRow row = null;
		List<DataRow> listDataRow = new ArrayList<DataRow>();
		if(data!=null){
			List<DataRow> list =pushRecordMapper.queryPushRecordList(map);
			if(!list.isEmpty()){
				Set<String> times = new HashSet<String>();
				for(int i=0;i<list.size();i++){
					times.add(list.get(i).getString("time"));
				}
				for(String time:times){
					List<DataRow> listRow = new ArrayList<DataRow>();
					row = new DataRow();
					for(int o=0;o<list.size();o++){//8号
						if(time.equals(list.get(o).getString("time"))){
							if(list.get(o).getInt("heartUnusual")!=0){
								dataRow = new DataRow();
								dataRow.put("unusual", "心率: "+list.get(o).getInt("heartUnusual")+"次/分");
								dataRow.put("createtime", list.get(o).getString("createtime"));
								listRow.add(dataRow);
							}
							if(list.get(o).getInt("highBloodUnusual")!=0){
								dataRow = new DataRow();
								dataRow.put("unusual", "收缩压: "+list.get(o).getInt("highBloodUnusual")+"mmHg");
								dataRow.put("createtime", list.get(o).getString("createtime"));
								listRow.add(dataRow);
							}
							if(list.get(o).getInt("lowBloodUnusual")!=0){
								dataRow = new DataRow();
								dataRow.put("unusual", "舒张压: "+list.get(o).getInt("lowBloodUnusual")+"mmHg");
								dataRow.put("createtime", list.get(o).getString("createtime"));
								listRow.add(dataRow);
							}
						}
						
					}
					row.put("time", time);
					row.put("data", listRow);
					listDataRow.add(row);
					
				}
				data.put("chartData", listDataRow);
				re.setData(data);
				re.setCode(200);
				re.setMessage("获取成功");
			}else{
				re.setCode(350);
				re.setMessage("查询不到预警记录");
			}
		}else{
			re.setCode(350);
			re.setMessage("无预警记录");
		}
		return re;
	}
}
