package com.fadl.hr.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jian
 * @since 2019-05-10
 */
@TableName("hr")
public class Hr extends Model<Hr> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别0.女  1.男
     */
    private Integer gender;
    /**
     * 岗位
     */
    private String post;
    /**
     * 出生日期
     */
    private String birth;
    /**
     * 学历(0.初高中 1.大专  2.本科  3.硕士  4.博士)
     */
    private Integer education;
    /**
     * 户籍
     */
    private String register;
    /**
     * 婚否 1.是  2 否
     */
    private Integer isMarry;
    /**
     * 入职日期
     */
    private String entryDate;
    /**
     * 手机
     */
    private String phone;
    /**
     * 供应商ID
     */
    private Long agentId;
    /**
     * 是否删除(0、否 1、是)
     */
    private Integer isDelete;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Integer getIsMarry() {
        return isMarry;
    }

    public void setIsMarry(Integer isMarry) {
        this.isMarry = isMarry;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
        return "Hr{" +
        "id=" + id +
        ", name=" + name +
        ", gender=" + gender +
        ", post=" + post +
        ", birth=" + birth +
        ", education=" + education +
        ", register=" + register +
        ", isMarry=" + isMarry +
        ", entryDate=" + entryDate +
        ", phone=" + phone +
        ", agentId=" + agentId +
        ", isDelete=" + isDelete +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
