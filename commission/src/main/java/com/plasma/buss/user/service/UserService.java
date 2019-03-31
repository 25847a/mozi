package com.plasma.buss.user.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plasma.buss.user.dao.UserDao;
import com.plasma.common.DataRow;
import com.plasma.common.Encrypt_DES;
import com.plasma.common.IConstants;
import com.plasma.common.StringHelper;
import com.plasma.common.base.PageBean;
import com.plasma.common.util.MD5Util;

/**
 * 用户管理
 * @author hu
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserService {
	
	@Autowired
	public UserDao userDao;

	/**
	 * 用户登录
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryUserLogin(DataRow data)throws SQLException{
		return userDao.queryUserLogin(data);
	}
	/**
	 * 查询用户列表
	 * @param dr
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public PageBean queryUserList(DataRow dr,PageBean pageBean) throws SQLException{
		dr.put("pageNum", (pageBean.getPageNum() - 1) * pageBean.getPageSize());
		dr.put("pageSize", pageBean.getPageSize());
		List<DataRow> list = userDao.queryUserList(dr);
		int totalNum = userDao.queryUserListCount(dr);
		pageBean.setPage(list);
		pageBean.setTotalNum(totalNum);
		return pageBean;
	}
	/**
	 * 添加用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int saveUser(DataRow dr)throws SQLException{
		return userDao.saveUser(dr);
	}
	/**
	 * 修改用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int updateUser(DataRow dr)throws SQLException{
		return userDao.updateUser(dr);
	}
	/**
	 * 修改头像
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int updatePicture(DataRow dr)throws SQLException{
		return userDao.updatePicture(dr);
	}
	/**
	 * 修改密码/图像
	 * @param dr
	 * @param messageMap
	 * @throws Exception 
	 */
	public void updateUserInfo(DataRow dr,DataRow messageMap) throws Exception{
		DataRow user = userDao.queryByIdUser(dr);
		if(!StringHelper.isEmpty(dr.getString("newPassword"))){
			//把密码加密后比较,判断原始密码是否输入正确
			dr.put("passWord", MD5Util.Md5ByKey(dr.getString("passWord")));
			if (!user.getString("passWord").equals(dr.getString("passWord"))) {
				messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
				messageMap.put(IConstants.APP_RESULT_MSG, "旧密码不正确");
				return;
			}
			dr.put("passWord", MD5Util.Md5ByKey(Encrypt_DES.decode(dr.getString("newPassword"), Encrypt_DES.passWordKey)));
		}
		dr.put("id", user.getString("id"));
		int res = userDao.updatePassword(dr);
		if (res>0) {
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
		}
	}
	
	/**
	 * 添加或修改用户信息
	 * @param data
	 * @param messageMap
	 * @throws Exception 
	 */
	public void saveOrUpdateUser(DataRow data,DataRow messageMap)throws Exception{
		int result=0;
		String passWord=data.getString("passWord");
		if(!"".equals(passWord)){
			data.put("passWord", MD5Util.Md5ByKey(passWord));
		}
		DataRow dr = userDao.queryPhoneExist(data);
		if(dr!=null){
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, "手机号码已存在");
			return;
		}
		if (StringHelper.isEmpty(data.getString("id"))) {
			 result =userDao.saveUser(data);//新增
		}else{
			result = userDao.updateUser(data);//修改
		}
		if (result>0) {
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_SUCCESS);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_SUCCESS);
		}else{
			messageMap.put(IConstants.APP_RESULT_ERRPR, IConstants.RESULT_CODE_ERROR);
			messageMap.put(IConstants.APP_RESULT_MSG, IConstants.RESULT_FAILURE);
		}
	}
	/**
	 * 删除用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public int deleteUser(DataRow dr)throws SQLException{
		return userDao.deleteUser(dr);
	}
	/**
	 * 查询指定用户信息
	 * @param dr
	 * @return
	 * @throws SQLException
	 */
	public DataRow queryByIdUser(DataRow dr)throws SQLException{
		return userDao.queryByIdUser(dr);
	}
}
