package com.plasma.buss.lock.dao;

import java.sql.SQLException;
import java.util.List;

import com.plasma.common.DataRow;

/**
 * @author:wangjing
 * @Description:电子加密狗
 * @Date:2018-12-13
 */
public interface PasswordLockDao {
    /**
     * 查询登记的电子加密狗
     */
    public List<DataRow> queryPasswrodLockList(DataRow data) throws SQLException;
    /**
     * 查询登记的电子加密狗数量
     */
    public Integer queryPasswrodLockListCount(DataRow data)throws SQLException;

    /**
     * 插入写入电子加密狗信息
     * @param data
     * @throws SQLException
     */
    public int insertPasswrodLock(DataRow data)throws SQLException;
    /**
     * 查询hid是否存在并返回对象
     */
    public DataRow queryHidExits(String hid)throws SQLException;
    /**
     * 删除加密狗记录信息
     */
    public int deleteHid(String hid)throws SQLException;
    /**
     * 修改写入的电子加密狗信息
     * @param data
     * @throws SQLException
     */
    public int updatePasswrodLock(DataRow data)throws SQLException;
}
