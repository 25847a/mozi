package com.fadl.account.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fadl.account.entity.Admin;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fadl.common.DataRow;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
  * 用户表 Mapper 接口
 * </p>
 *
 * @author wangjing
 * @since 2018-04-02
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 查询用户信息
     * @param page 分页参数
     * @param map 根据 姓名、角色、电话查询
     * @return
     * @throws Exception
     */
    public List<DataRow> queryAdminList(Pagination page, Map<String,Object> map)throws SQLException;
    /**
     * 根据手机查询用户信息
     */
    public Admin queryByMobile(String mobile);
    /**
     * 根据名称查询用户信息
     */
    public Admin queryByMobileAndPassWord(Map<String,String> map)throws SQLException;

    /**
     * 查询用户列表信息
     * @param param 条件参数 参数于数据库对应
     * @return
     */
    public  List<DataRow> queryWhereAdminInfoList(HashMap<String, String> data)throws SQLException;
    /**
     * 查询用户信息
     * @param param 条件参数 参数于数据库对应
     * @return
     */
    public  DataRow queryWhereAdminInfoObject(HashMap<String, String> data)throws SQLException;
}