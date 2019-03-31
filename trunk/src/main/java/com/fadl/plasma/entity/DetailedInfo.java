package com.fadl.plasma.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 浆员详细信息表
 * </p>
 *
 * @author wangjing
 * @since 2018-04-11
 */
@TableName("f_detailed_info")
public class DetailedInfo extends Model<DetailedInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
    /**
     * 浆员id、扩展员id、业务员id
     */
	private Long baseId;
    /**
     * 职业(0、务农 1、非农)
     */
	private Integer vocation;
    /**
     * 电话
     */
	private String phone;
    /**
     * 现住地址
     */
	private String addressx;
    /**
     * 籍贯
     */
	private String place;
    /**
     * 备注
     */
	private String remarks;
    /**
     * 病史
     */
	private String history;
    /**
     * 民族
     */
	private Long nation;
    /**
     * 身份证有效期
     */
	private String validDate;
    /**
     * 用户类型(0、浆员 1、扩展员 2、业务员)
     */
	private Integer type;
    /**
     * 邀请人
     */
	private Long inviteId;
	/**
	 * 邀请人类型(0、浆员 1、扩展员 2、业务员)
	 */
	private  Integer inviteType;
	/**
	 * 邀请时间
	 */
	private String inviteDate;
	/**
	 * 乡镇
	 */
	private String country;
	/**
	 * 村委会
	 */
	private String village;
	/**
	 * 是否结婚 0、已婚  1未婚
	 */
	private Integer isMarry;
	/**
	 * 村小组
	 */
	private String vgroup;
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

	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}

	public Integer getVocation() {
		return vocation;
	}

	public void setVocation(Integer vocation) {
		this.vocation = vocation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressx() {
		return addressx;
	}

	public void setAddressx(String addressx) {
		this.addressx = addressx;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Long getNation() {
		return nation;
	}

	public void setNation(Long nation) {
		this.nation = nation;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getInviteId() {
		return inviteId;
	}

	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}

	public Integer getInviteType() {
		return inviteType;
	}

	public void setInviteType(Integer inviteType) {
		this.inviteType = inviteType;
	}

	public String getInviteDate() {
		return inviteDate;
	}

	public void setInviteDate(String inviteDate) {
		this.inviteDate = inviteDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public Integer getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(Integer isMarry) {
		this.isMarry = isMarry;
	}

	public String getVgroup() {
		return vgroup;
	}

	public void setVgroup(String vgroup) {
		this.vgroup = vgroup;
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
		return "DetailedInfo{" +
			"id=" + id +
			", baseId=" + baseId +
			", vocation=" + vocation +
			", phone=" + phone +
			", addressx=" + addressx +
			", place=" + place +
			", remarks=" + remarks +
			", history=" + history +
			", nation=" + nation +
			", validDate=" + validDate +
			", type=" + type +
			", inviteId=" + inviteId +
			", inviteType=" + inviteType +
			", inviteDate="+inviteDate+
			", country="+country+
			", village="+village+
			", isMarry="+isMarry+
			", vgroup="+vgroup+
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
