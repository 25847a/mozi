package com.fadl.account.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wangjing
 * @since 2018-04-02
 */
@TableName("f_admin")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 真实姓名
     */
	private String name;
    /**
     * 性别
     */
	private Integer sex;
    /**
     * 用户名
     */
	@NotEmpty(message="用户不能为空")
	private String userName;
    /**
     * 手机号码
     */
	private String mobile;
    /**
     * 密码
     */
	@NotEmpty(message="密码不能为空")
	private String passWord;
    /**
     * 角色
     */
	@TableField(exist = false)
	private Long roleId;
    /**
     * 登陆次数
     */
	private Integer isLoginCount;
    /**
     * 是否禁用(0、否 1、是)
     */
	private Integer isDisable; ;

    /**
     * 是否删除(0、否 1、是)
     */
	@TableField(value = "isDelete")
	@TableLogic
	private Integer isDelete;
    /**
     * 是否超级管理员(0、否 1、是)
     */
	private Integer isSuper;
    /**
     * 头像
     */
	private String img;
    /**
     * 登陆IP
     */
	private String ip;
    /**
     * 登陆错误次数
     */
	private Integer loginErrorCount;
    /**
     * 锁定时间
     */
	private String lockDate;
    /**
     * 第一次登陆时间
     */
	private String firstDate;
    /**
     * 最后次登陆时间
     */
	private String lastDate;
    /**
     * 创建时间
     */
	@TableField(value = "createDate",fill = FieldFill.INSERT )
	private String createDate;
    /**
     * 修改时间
     */
	@TableField(value = "updateDate",fill = FieldFill.INSERT_UPDATE )
	private String updateDate;
    /**
     * 创建人
     */
	@TableField(value = "creater",fill = FieldFill.INSERT )
	private Long creater;
    /**
     * 修改人
     */
	@TableField(value = "updater",fill = FieldFill.INSERT_UPDATE )
	private Long updater;
	
	/**
	 * 所属浆站标识
	 */
	@TableField(fill = FieldFill.INSERT)
	private String plasmaId;


	public String getPlasmaId() {
		return plasmaId;
	}

	public void setPlasmaId(String plasmaId) {
		this.plasmaId = plasmaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getIsLoginCount() {
		return isLoginCount;
	}

	public void setIsLoginCount(Integer isLoginCount) {
		this.isLoginCount = isLoginCount;
	}

	public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}

	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public String getLockDate() {
		return lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Admin{" +
			"id=" + id +
			", name=" + name +
			", sex=" + sex +
			", userName=" + userName +
			", mobile=" + mobile +
			", passWord=" + passWord +
			", roleId=" + roleId +
			", isLoginCount=" + isLoginCount +
			", isDisable=" + isDisable +
			", isDelete=" + isDelete +
			", isSuper=" + isSuper +
			", img=" + img +
			", ip=" + ip +
			", loginErrorCount=" + loginErrorCount +
			", lockDate=" + lockDate +
			", firstDate=" + firstDate +
			", lastDate=" + lastDate +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			", plasmaId=" + plasmaId +
			"}";
	}

}
