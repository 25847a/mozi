package com.fadl.account.service.impl;

import com.fadl.account.entity.Admin;
import com.fadl.account.dao.AdminMapper;
import com.fadl.account.service.AdminService;
import com.fadl.common.Base64;
import com.fadl.common.DataRow;
import com.fadl.common.PasswordUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.checkingCodeUtil;
import com.fadl.health.dao.UsercodeMapper;
import com.fadl.health.entity.Usercode;
import com.fadl.health.service.UsercodeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 后台页面用户表 服务实现类
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	@Autowired
	UsercodeMapper usercodeMapper;
	@Autowired
	UsercodeService usercodeService;
	
	/**
	 * 根据用户名查询用户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@Override
	public Admin queryAdminInfo(String account) throws SQLException {
		return adminMapper.queryAdminInfo(account);
	}
	/**
	 * 查询代理商的名字
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdminAgentInfo(Long id) throws SQLException {
		return adminMapper.queryAdminAgentInfo(id);
	}
	/**
	 * 查询用户管理列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public DataRow queryAdminInfoList(Map<String,String> map,DataRow messageMap) throws SQLException {
		List<DataRow> list =adminMapper.queryAdminInfoList(map);
		messageMap.initSuccess(list);
		return messageMap;
	}
	/**
     * 修改供应商的头像
     * avatar
     * @return
     */
	@Override
	public DataRow updateAgentImage(Admin admin, DataRow messageMap) throws Exception {
		String filePath = ReadProperties.getValue("imageAddress");
		String time = System.currentTimeMillis()+".jpg";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
        }
		byte[] by= Base64.decode(admin.getAvatar());
		OutputStream out = new FileOutputStream(new File(filePath+time));
		out.write(by, 0, by.length);
		out.close();
		admin.setAvatar(ReadProperties.getValue("serverUrl")+time);
		int row =adminMapper.updateById(admin);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	 /**
     * 修改供应商的密码
     * map
     * @return
     */
	@Override
	public DataRow updateAgentPassword(Admin admin, Map<String, String> map, DataRow messageMap) throws Exception {
		String oldPassword = PasswordUtil.encryptPassWord(map.get("oldPassword"), admin.getAccount());
		if(admin.getPassWord().equals(oldPassword)){
			admin.setPassWord(PasswordUtil.encryptPassWord(map.get("newPass"), admin.getAccount()));
			adminMapper.updateById(admin);
			messageMap.initSuccess();
		}else{
			messageMap.initFial("密码错误,请重新输入！");
		}
		return messageMap;
	}
	/**
     * 修改密码验证手机号码
     * phone
     * @return
     */
	@Override
	public DataRow checkingPhone(String phone, DataRow messageMap) throws Exception {
		EntityWrapper<Admin> ew = new EntityWrapper<Admin>();
		ew.eq("account", phone);
		Admin admin =this.selectOne(ew);
		messageMap.initSuccess(admin);
		return messageMap;
	}
	/**
     * 通过手机号码获取验证码
     * phone
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow checkingCode(String phone, DataRow messageMap) throws Exception {
		Integer smsMsg = 445405;//checkingCodeUtil.sendSMS(phone);
		if(smsMsg!=0){
			Usercode usercode = usercodeService.queryUsercodeInfo(phone);
			Integer row=0;
			if(usercode!=null){
				usercode.setCode(String.valueOf(smsMsg));
				row=usercodeService.updateUsercode(usercode);
			}else{
				usercode = new Usercode();
				usercode.setPhoen(phone);
				usercode.setCode(String.valueOf(smsMsg));
				row=usercodeService.insertUsercode(usercode);
			}
			if(row>0){
				messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
		}
		return messageMap;
	}
	/**
     * 判断验证码是否正确
     * usercode
     * @return
     */
	@Override
	public DataRow queryCheckingCode(Usercode usercode, DataRow messageMap) throws Exception {
		usercode=usercodeMapper.selectOne(usercode);
		if(usercode!=null){
			messageMap.initSuccess();
		}else{
			messageMap.initFial();
		}
		return messageMap;
	}
	/**
     * 忘记密码更改密码
     * admin
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DataRow updatePassWord(Admin admin, DataRow messageMap) throws Exception {
		String pass = PasswordUtil.encryptPassWord(admin.getPassWord(), admin.getAccount());
		admin.setPassWord(pass);
		EntityWrapper<Admin> ew = new EntityWrapper<Admin>();
		ew.eq("account", admin.getAccount());
		int row =adminMapper.update(admin, ew);
		if(row>0){
			messageMap.initSuccess();
		}else{
			messageMap.initFial("密码修改失败");
		}
		return messageMap;
	}
}
