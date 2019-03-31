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
 * 浆员基本信息表
 * </p>
 *
 * @author wangjing
 * @since 2018-04-10
 */
@TableName("f_provider_baseinfo")
public class ProviderBaseinfo extends Model<ProviderBaseinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
    /**
     * 浆员卡号
     */
	private String providerNo;
    /**
     * IC卡号
     */
	private String icNumber;
    /**
     * 姓名
     */
	private String name;
    /**
     * 性别(0、男 1、女)
     */
	private Integer sex;
    /**
     * 身份证号码
     */
	private String idNo;
    /**
     * 身份证照片(正面)
     */
	private String imagez;
    /**
     * 身份证照片(反面)
     */
	private String imagef;
	/**
	 * 现场照片
	 */
	private String imagespot;
	/**
	 * 0：未发卡；1：已发卡
	 */
	private Integer isGrantCard;
	/**
	 * 审核状态（0.未审核   1. 已审核）
	 */
	private Integer examineStatus;
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

	public Integer getIsGrantCard() {
		return isGrantCard;
	}

	public void setIsGrantCard(Integer isGrantCard) {
		this.isGrantCard = isGrantCard;
	}

	public String getImagespot() {
		return imagespot;
	}

	public void setImagespot(String imagespot) {
		this.imagespot = imagespot;
	}

	/**
     * 血型(0、A 1、B 2、O 3、AB)
     */
	private Integer bloodType;
    /**
     * 生日
     */
	private String birthday;
	/**
	 * 预约时间
	 */
	private String aboutDate;
	/**
	 * 最后采浆时间
	 */
	private String collectionDate;
	/**
     * 0.正常，1.暂时拒绝，2.永久拒绝
     */
	private Integer plasmaState;
    /**
     * 浆员类型(0、普通 1、普免 2.特免)
     */
	private Integer type;
    /**
     * 免疫类别设置 id
     */
	private String immuneId;
    /**
     * 免疫编号
     */
	private String immuneRegisterId;
	/**
	 * 是否审核(0、未审核  1、已审核)
	 */
	private Integer status;
	/**
	 * 等级
	 */
	private Integer level;
	/**
	 * 组号ID
	 */
	private Long groupId;
	/**
	 * 血管等级
	 */
	private Integer bloodGrade;
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
	 * 身份证图像
	 */
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public String getIcNumber() {
		return icNumber;
	}

	public void setIcNumber(String icNumber) {
		this.icNumber = icNumber;
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getImagez() {
		return imagez;
	}

	public void setImagez(String imagez) {
		this.imagez = imagez;
	}

	public String getImagef() {
		return imagef;
	}

	public void setImagef(String imagef) {
		this.imagef = imagef;
	}

	public Integer getBloodType() {
		return bloodType;
	}

	public void setBloodType(Integer bloodType) {
		this.bloodType = bloodType;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAboutDate() {
		return aboutDate;
	}

	public void setAboutDate(String aboutDate) {
		this.aboutDate = aboutDate;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Integer getPlasmaState() {
		return plasmaState;
	}

	public void setPlasmaState(Integer plasmaState) {
		this.plasmaState = plasmaState;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImmuneId() {
		return immuneId;
	}

	public void setImmuneId(String immuneId) {
		this.immuneId = immuneId;
	}

	public String getImmuneRegisterId() {
		return immuneRegisterId;
	}

	public void setImmuneRegisterId(String immuneRegisterId) {
		this.immuneRegisterId = immuneRegisterId;
	}

	public Integer getStatus(){return status;}

	public void setStatus(Integer status){this.status=status;}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getBloodGrade() {
		return bloodGrade;
	}

	public void setBloodGrade(Integer bloodGrade) {
		this.bloodGrade = bloodGrade;
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
	
	public Integer getExamineStatus() {
		return examineStatus;
	}

	public void setExamineStatus(Integer examineStatus) {
		this.examineStatus = examineStatus;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ProviderBaseinfo{" +
			"id=" + id +
			", providerNo=" + providerNo +
			", icNumber=" + icNumber +
			", name=" + name +
			", sex=" + sex +
			", idNo=" + idNo +
			", imagez=" + imagez +
			", imagef=" + imagef +
			", imagespot="+imagespot+
			", bloodType=" + bloodType +
			", aboutDate=" + aboutDate +
			", collectionDate=" + collectionDate +
			", birthday=" + birthday +
			", plasmaState=" + plasmaState +
			", type=" + type +
			", immuneId=" + immuneId +
			", immuneRegisterId=" + immuneRegisterId +
			", status=" + status +
			", level=" + level +
			", groupId=" + groupId +
			", isGrantCard=" + isGrantCard +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creater=" + creater +
			", updater=" + updater +
			"}";
	}
}
