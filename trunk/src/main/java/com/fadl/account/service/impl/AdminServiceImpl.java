package com.fadl.account.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.account.dao.AdminMapper;
import com.fadl.account.dao.SysAdminRoleMapper;
import com.fadl.account.entity.Admin;
import com.fadl.account.entity.SysAdminRole;
import com.fadl.account.service.AdminService;
import com.fadl.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import java.io.File;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    SysAdminRoleMapper sysAdminRoleMapper;
    /**
     * 查询用户信息
     * @param page 分页参数
     * @param map 根据 姓名、角色、电话查询
     * @return
     * @throws Exception
     */
    public Page<DataRow> queryAdminList(Page<DataRow> page, Map<String,Object> map)throws Exception{
        return page.setRecords(adminMapper.queryAdminList(page, map));
    }
    /**
     * 读取配置文件数据库信息
     * @return
     */
    public void queryDatabase(DataRow messageMap) throws  Exception{
        Map<String, Object> map = new HashMap<String, Object>();
            String dataName = ReadProperties.getValue("username");
            String dataWord = ReadProperties.getValue("password");
            String ip = ReadProperties.getValue("url");
            String regexString = ".*(\\d{3}(\\.\\d{1,3}){3}).*";
            String IPString = ip.replaceAll(regexString, "$1");
            String name = ReadProperties.getValue("url");
            String database = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("?")-1);
            map.put("dataName", dataName);
            map.put("dataWord", dataWord);
            map.put("IPString", IPString);
            map.put("database", database);
        messageMap.initSuccess(map);
    }
    /**
     * 删除 用户信息
     * @param ids 逻辑删除
     * @return
     */
    public void deleteAdmin(List<Long> ids, DataRow messageMap)throws Exception{
        boolean flag=deleteBatchIds(ids);
        if(flag){
            messageMap.initSuccess();
        }else{
            messageMap.initFial();
        }
    }
    /**
     * 根据手机查询用户信息
     */
    public Admin qeuryByMobile(String mobile)throws SQLException{
        return adminMapper.queryByMobile(mobile);
    }
    /**
     * 根据名称查询用户信息
     */
    public Admin qeuryByMobileAndPassWord(String mobile,String passWord)throws SQLException{
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("mobile",mobile);
        map.put("passWord",passWord);
        return adminMapper.queryByMobileAndPassWord(map);
    }
    /**
     * 添加用户信息
     * @param admin 用户对象
     * @return
     */
	@Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void insertAdmin(MultipartFile imageList, Admin admin, DataRow messageMap) throws Exception {
            Admin admins = adminMapper.queryByMobile(admin.getMobile());
            if(admins!=null){
            	messageMap.initFial(IConstants.USER_EXIST);
            }else{
                if (imageList!=null&&imageList.getContentType().contains("image")) {
                    // 获取图片的文件名
                    String fileName = imageList.getOriginalFilename();
                    // 获取图片的扩展名
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    // 文件路径
                    String filePath = ReadProperties.getValue("adminImg");
                    File dest = new File(filePath, newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    admin.setImg(newFileName);
                	admin.setPassWord(PasswordUtil.encryptPassWord(admin.getPassWord(),admin.getMobile()));
                	admin.setIsLoginCount(0);
                	admin.setIsDelete(0);
                	InetAddress address = InetAddress.getLocalHost();
                    String hostAddress = address.getHostAddress();
                	admin.setIp(hostAddress);
                	admin.setLoginErrorCount(0);
                	admin.setLockDate(DateUtil.sf.format(new Date()));
                	admin.setFirstDate(DateUtil.sf.format(new Date()));
                	admin.setLastDate(DateUtil.sf.format(new Date()));
                    int flagAdmin=adminMapper.insert(admin);
                    SysAdminRole sysAdminRole = new SysAdminRole();
                    sysAdminRole.setAdminId(admin.getId());
                    sysAdminRole.setRoleId(admin.getRoleId());
                    int flagRole =sysAdminRoleMapper.insert(sysAdminRole);
                    if(flagAdmin>0&&flagRole>0){
                        //压缩图片
                        ImageUtil.createThumbnail(imageList.getInputStream(), ReadProperties.getValue("adminImg")+File.separator+newFileName,100,100);
                        messageMap.initSuccess();
                    }else{
                        messageMap.initFial();
                    }
                }else{
                    messageMap.initFial(IConstants.IMG_NOT);
                }
            }
	}
	/**
     * 修改用户信息
     * @param admin 用户信息
     * @return
     */
	@Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updateAdmin(MultipartFile imageList, Admin admin,DataRow messageMap) throws Exception {
		Admin admins = adminMapper.selectById(admin.getId());
        if(admins==null){
            messageMap.initFial(IConstants.ROLE_NOT);
        }else {
            Admin admin1 = adminMapper.queryByMobile(admin.getMobile());
            if (admin1!=null && !admin1.getMobile().equals(admin.getMobile())) {
                messageMap.initFial(IConstants.USER_EXIST);
            }else {
                if (null!=imageList && imageList.getContentType().contains("image")) {
                    // 获取图片的文件名
                    String fileName = imageList.getOriginalFilename();
                    // 获取图片的扩展名
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
                    if (!StringUtils.isEmpty(admins.getImg())) {//如果图片存在则先删除
                        String oldFilePath = admins.getImg();
                        File oldFile = new File(ReadProperties.getValue("adminImg")+"/" + oldFilePath);
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    ImageUtil.createThumbnail(imageList.getInputStream(), ReadProperties.getValue("adminImg") + File.separator + newFileName, 100, 100);
                    admin.setImg(newFileName);
                }
                admin.setId(admins.getId());
                //修改密码
                if(!("").equals(admin.getPassWord()) && null!=admin.getPassWord()) {
                	admin.setPassWord(PasswordUtil.encryptPassWord(admin.getPassWord(),admin.getMobile()));
                }else {
                	admin.setPassWord(null);
                }
                SysAdminRole sysAdminRole = new SysAdminRole();
                sysAdminRole.setAdminId(admin.getId());
                sysAdminRole.setRoleId(admin.getRoleId());
                EntityWrapper ew = new EntityWrapper();
                ew.eq("adminId",admin.getId());
                int flagRole =sysAdminRoleMapper.update(sysAdminRole,ew);
                int flag = adminMapper.updateById(admin);
                if (flag>0&&flagRole>0) {
                    messageMap.initSuccess();
                } else {
                    messageMap.initFial();
                }
            }
        }
	}
	
	/**
     * 修改密码
     * @param admin 用户信息
     * @return
     */
	@Override
	public void updatePassword(Admin admin, DataRow messageMap) throws Exception {
		admin.setPassWord(PasswordUtil.encryptPassWord(admin.getPassWord(),admin.getMobile()));
		int result =adminMapper.updateById(admin);
		if(result>0) {
			messageMap.initSuccess();
		}else {
			messageMap.initFial();
		}
	}
}
