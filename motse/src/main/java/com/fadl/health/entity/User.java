package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备号
     */
    private String imei;
    /**
     * 角色
     */
    private String role;
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 是否校准过,0未校准,1已校准
     */
    private String calibration;
    /**
     * 设置围栏的半径,单位米
     */
    private String radius;
    /**
     * 设置围栏的中心点(圆心)坐标
     */
    private String midpoint;
    /**
     * 健康数据的上传时间
     */
    private String jfdataUpdateTime;
    /**
     * 注册时间
     */
    private String createtime;
    /**
     * 最后登陆时间
     */
    private String atlasttime;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否删除（0、否1、是）
     */
    private Integer isDelete;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private String gender;
    /**
     * 邮箱地址
     */
    private String address;
    /**
     * 微信
     */
    private String wechat;
    /**
     * QQ号码
     */
    private String qq;
    /**
     * 体重
     */
    private Float weight;
    /**
     * 出生
     */
    private String born;
    /**
     * 身高
     */
    private Float height;
    /**
     * 验证码
     */
    private String code;
    /**
     * 通知的步行数
     */
    private String walkCount;
    /**
     * 上次的通知时间
     */
    private String walkPushTime;
    /**
     * 1.重点关爱  0.不重点关爱
     */
    private Integer love;
    /**
     * 床位ID(0.代表无床位)
     */
    private Long bedId;
    /**
     * 护士ID
     */
    private Long nurseId;
    /**
     * 入住时间(养老院)
     */
    private String liveTime;
    /**
     * 病史
     */
    private String illness;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCalibration() {
        return calibration;
    }

    public void setCalibration(String calibration) {
        this.calibration = calibration;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getMidpoint() {
        return midpoint;
    }

    public void setMidpoint(String midpoint) {
        this.midpoint = midpoint;
    }

    public String getJfdataUpdateTime() {
        return jfdataUpdateTime;
    }

    public void setJfdataUpdateTime(String jfdataUpdateTime) {
        this.jfdataUpdateTime = jfdataUpdateTime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAtlasttime() {
        return atlasttime;
    }

    public void setAtlasttime(String atlasttime) {
        this.atlasttime = atlasttime;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWalkCount() {
        return walkCount;
    }

    public void setWalkCount(String walkCount) {
        this.walkCount = walkCount;
    }

    public String getWalkPushTime() {
        return walkPushTime;
    }

    public void setWalkPushTime(String walkPushTime) {
        this.walkPushTime = walkPushTime;
    }

    public Integer getLove() {
		return love;
	}

	public void setLove(Integer love) {
		this.love = love;
	}

	public Long getBedId() {
		return bedId;
	}

	public void setBedId(Long bedId) {
		this.bedId = bedId;
	}

	public Long getNurseId() {
		return nurseId;
	}

	public void setNurseId(Long nurseId) {
		this.nurseId = nurseId;
	}

	public String getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(String liveTime) {
		this.liveTime = liveTime;
	}

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", imei=" + imei +
        ", role=" + role +
        ", account=" + account +
        ", name=" + name +
        ", phone=" + phone +
        ", calibration=" + calibration +
        ", radius=" + radius +
        ", midpoint=" + midpoint +
        ", jfdataUpdateTime=" + jfdataUpdateTime +
        ", createtime=" + createtime +
        ", atlasttime=" + atlasttime +
        ", password=" + password +
        ", isDelete=" + isDelete +
        ", age=" + age +
        ", avatar=" + avatar +
        ", gender=" + gender +
        ", address=" + address +
        ", wechat=" + wechat +
        ", qq=" + qq +
        ", weight=" + weight +
        ", born=" + born +
        ", height=" + height +
        ", code=" + code +
        ", walkCount=" + walkCount +
        ", walkPushTime=" + walkPushTime +
        ", love=" + love +
        ", bedId=" + bedId +
        ", nurseId=" + nurseId +
          ", liveTime=" + liveTime +
        ", illness=" + illness +
        "}";
    }
}
