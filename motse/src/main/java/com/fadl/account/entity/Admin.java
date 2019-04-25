package com.fadl.account.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 后台页面用户表
 * </p>
 *
 * @author jian
 * @since 2019-03-27
 */
@TableName("admin")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账号  (等同于 orgcode 机构代码)
     */
    private String account;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 登陆次数
     */
    private Integer isLoginCount;
    /**
     * 姓名  (等同于 management 机构负责人)
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 职位
     */
    private String position;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 地址 (等同于 orgname 机构名称)
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 微信
     */
    private String wechat;
    /**
     * QQ号码
     */
    private String qq;
    /**
     * 职称ID
     */
    private Long positionId;
    /**
     * 是否禁用(0、否 1、是)
     */
    private Integer isDisable;
    /**
     * 登陆错误次数
     */
    private Integer loginErrorCount;
    /**
     * 角色
     */
    private String role;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createDate;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateDate;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long creater;
    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 第一次登陆时间
     */
    private String firstDate;
    /**
     * 最后次登陆时间
     */
    private String lastDate;
    /**
     * 锁定时间
     */
    private String lockDate;
    /**
     * 备注
     */
    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
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

	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}

	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
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

	public String getLockDate() {
		return lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}

	@Override
    public String toString() {
        return "Admin{" +
        "id=" + id +
        ", account=" + account +
        ", passWord=" + passWord +
        ", name=" + name +
        ", age=" + age +
        ", gender=" + gender +
        ", position=" + position +
        ", phone=" + phone +
        ", address=" + address +
        ", avatar=" + avatar +
        ", wechat=" + wechat +
        ", qq=" + qq +
        ", positionId=" + positionId +
        ", role=" + role +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", remarks=" + remarks +
        ", isLoginCount=" + isLoginCount +
        ", isDisable=" + isDisable +
        ", loginErrorCount=" + loginErrorCount +
        ", firstDate=" + firstDate +
        ", lastDate=" + lastDate +
        ", lockDate=" + lockDate +
        
        "}";
    }
}
