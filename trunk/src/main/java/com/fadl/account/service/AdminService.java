package com.fadl.account.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.fadl.account.entity.Admin;
import com.fadl.common.DataRow;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-02
 */
public interface AdminService extends IService<Admin> {
    /**
     * 查询用户列表信息
     */
    public Page<DataRow> queryAdminList (Page<DataRow> page, Map<String,Object> map)throws Exception;
    /**
     * 读取配置文件数据库信息
     * @return
     */
    public void queryDatabase(DataRow messageMap) throws Exception;
    /**
     * 删除 用户信息
     * @param ids 逻辑删除
     * @return
     */
    public void deleteAdmin(List<Long> ids,DataRow messageMap)throws Exception;
    /**
     * 根据名称查询用户信息
     */
    public Admin qeuryByMobile(String mobile)throws SQLException;
    /**
     * 根据名称查询用户信息
     */
    public Admin qeuryByMobileAndPassWord(String mobile,String passWord)throws Exception;
    /**
     * 添加用户信息
     * @param admin 用户对象
     * @return
     */
    public void insertAdmin(MultipartFile imageList,Admin admin,DataRow messageMap)throws Exception;
    /**
     * 修改用户信息
     * @param admin 用户信息
     * @return
     */
    public void updateAdmin(MultipartFile imageList,Admin admin,DataRow messageMap)throws Exception;
    /**
     * 修改密码
     * @param admin 用户信息
     * @return
     */
    public void updatePassword(Admin admin,DataRow messageMap)throws Exception;
}

