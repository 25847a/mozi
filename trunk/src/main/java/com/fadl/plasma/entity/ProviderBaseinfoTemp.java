package com.fadl.plasma.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 浆员基本信息临时表
 * </p>
 *
 * @author zll
 * @since 2018-09-02
 */
@TableName("f_provider_baseinfo_temp")
public class ProviderBaseinfoTemp extends Model<ProviderBaseinfoTemp> {

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
     * 血型(0、A 1、B 2、O 3、AB)
     */
    private Integer bloodType;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 0.正常，1.暂时拒绝，2.永久拒绝
     */
    private Integer plasmaState;
    /**
     * 类型 0.取消发卡 1.申请变更信息
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
     * 是否审核（0、未审核 1、已审核）
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
     * 血管等级
     */
    private Integer bloodGrade;
    /**
     * 0：未发放；1：已发放
     */
    private Integer isGrantCard;
    /**
     * 所属浆站标识
     */
    @TableField(fill = FieldFill.INSERT)
    private String plasmaId;
    /**
     * 身份证地址
     */
    private String addressx;
    /**
     * 籍贯
     */
    private String place;
    private Long nation;
    /**
     * 身份证有效期
     */
    private String validDate;
    /**
     * 是否结婚
     */
    private Integer isMarry;


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

    public String getImagespot() {
        return imagespot;
    }

    public void setImagespot(String imagespot) {
        this.imagespot = imagespot;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public Integer getBloodGrade() {
        return bloodGrade;
    }

    public void setBloodGrade(Integer bloodGrade) {
        this.bloodGrade = bloodGrade;
    }

    public Integer getIsGrantCard() {
        return isGrantCard;
    }

    public void setIsGrantCard(Integer isGrantCard) {
        this.isGrantCard = isGrantCard;
    }

    public String getPlasmaId() {
        return plasmaId;
    }

    public void setPlasmaId(String plasmaId) {
        this.plasmaId = plasmaId;
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

    public Integer getIsMarry() {
        return isMarry;
    }

    public void setIsMarry(Integer isMarry) {
        this.isMarry = isMarry;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProviderBaseinfoTemp{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", icNumber=" + icNumber +
        ", name=" + name +
        ", sex=" + sex +
        ", idNo=" + idNo +
        ", imagez=" + imagez +
        ", imagef=" + imagef +
        ", imagespot=" + imagespot +
        ", bloodType=" + bloodType +
        ", birthday=" + birthday +
        ", plasmaState=" + plasmaState +
        ", type=" + type +
        ", immuneId=" + immuneId +
        ", immuneRegisterId=" + immuneRegisterId +
        ", status=" + status +
        ", level=" + level +
        ", groupId=" + groupId +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        ", bloodGrade=" + bloodGrade +
        ", isGrantCard=" + isGrantCard +
        ", plasmaId=" + plasmaId +
        ", addressx=" + addressx +
        ", place=" + place +
        ", nation=" + nation +
        ", validDate=" + validDate +
        ", isMarry=" + isMarry +
        "}";
    }
}
