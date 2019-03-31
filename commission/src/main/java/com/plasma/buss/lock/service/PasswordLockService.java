package com.plasma.buss.lock.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plasma.buss.lock.dao.PasswordLockDao;
import com.plasma.common.DataRow;
import com.plasma.common.IConstants;
import com.plasma.common.SocketUtil;
import com.plasma.common.StringHelper;
import com.plasma.common.base.PageBean;

/**
 * @author:wangjing
 * @Description:电子加密狗
 * @Date:2018-12-13
 */
@Service
public class PasswordLockService {
    @Autowired
    PasswordLockDao passwordLockDao;
    @Autowired
    SocketUtil socketUtil;
    /**
     * 查询登记的电子加密狗
     */
    public PageBean queryPasswrodLockList(DataRow dr,PageBean pageBean) throws SQLException{
        dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
        dr.put("pageSize", pageBean.getPageSize());
        List<DataRow> list = passwordLockDao.queryPasswrodLockList(dr);
        int totalNum = passwordLockDao.queryPasswrodLockListCount(dr);
        pageBean.setPage(list);
        pageBean.setTotalNum(totalNum);
        return pageBean;
    }

    /**
     * 插入写入电子加密狗信息
     * @param data
     * @throws SQLException
     */
    public int insertPasswrodLock(DataRow data,DataRow messageMap)throws Exception{
        String str =new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        data.put("rawID",str+rannum);//'加密标识，写入需要的字符串。申请UID',
        data.put("inputData",data.getString("plasmaId")+rannum);
        HashMap<String,String> map = new HashMap<String, String>();//发送写入加密锁
        map.put("rawID",str+rannum);
        map.put("checkData",data.getString("plasmaId")+rannum);
        //验证加密狗是否存在
        int result=0;
        String hid= returnLock(data.getString("ip"), data.getInt("port"),messageMap);
        if(!StringHelper.isEmpty(hid)){
        DataRow lockInfo = passwordLockDao.queryHidExits(hid);
        if("1".equals(data.getString("type"))&&!StringHelper.isEmpty(hid)){//重置加密狗
        	passwordLockDao.deleteHid(hid);//删除数据库中匹配加密狗中的hid
        	socketUtil.readInfo(data.getString("ip"),data.getInt("port"),"softdogw",map,messageMap);//重新写入加密狗
        	Map<String,String> d =(Map<String, String>) messageMap.get("data");
            data.put("uid",d.get("UID"));
            data.put("hid",d.get("HID"));
            result=passwordLockDao.insertPasswrodLock(data);//重新插入数据
        }else if(!"1".equals(data.getString("type"))){
	        if(lockInfo==null||StringHelper.isEmpty(hid)){//如果数据库不存在或加密狗没写过 则认为第一次写入
	        	socketUtil.readInfo(data.getString("ip"),data.getInt("port"),"softdogw",map,messageMap);//写入加密狗
	            if("-1".equals(messageMap.getString("error"))){
	            	Map<String,String> d =(Map<String, String>) messageMap.get("data");
	                data.put("uid",d.get("UID"));
	                data.put("hid",d.get("HID"));
	                result=passwordLockDao.insertPasswrodLock(data);
	            }
	        }else{
	        	messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_FAIL_CODE);
	        	messageMap.put(IConstants.APP_RESULT_MSG, "该加密锁已经绑定了,标识为:"+hid);
	        }
        }
        }
       return  result;
    }
    /**
     * 验证是否接入加密狗并返回加密狗标识
     */
    public String returnLock(String ip,int port,DataRow messageMap){
    	socketUtil.readInfo(ip,port,"softdogf",null,messageMap);//写入加密狗
    	if("-1".equals(messageMap.getString("error"))){
    		Map<String,String> d =(Map<String, String>) messageMap.get("data");
        	if(d!=null){
        		return d.get("HID");
        	}
    	}
    	return "";
    }

	/**
     * 查询hid是否存在
     */
    public DataRow queryHidExits(String hid)throws SQLException{
    	return passwordLockDao.queryHidExits(hid);
    }
    
    /**
     * 修改写入的电子加密狗信息
     * @param data
     * @throws SQLException
     */
    public int updatePasswrodLock(DataRow data)throws SQLException{
        return passwordLockDao.updatePasswrodLock(data);
    }
}
