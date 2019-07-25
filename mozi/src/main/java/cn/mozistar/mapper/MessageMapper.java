package cn.mozistar.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import cn.mozistar.util.DataRow;

public interface MessageMapper {
	
	
	 @Select("Select id,userId,observeId,title,content,`read`,type,DATE_FORMAT(createtime,'%H:%m') as createtime from message where userId=#{userId}")
	 public List<DataRow> queryMessageList(int userId);
	

}