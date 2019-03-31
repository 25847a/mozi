package com.fadl.collectionConfig.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 采浆机号
 * </p>
 *
 * @author caijian&guokang
 * @since 2018-04-21
 */
@TableName("f_machine_number")
public class MachineNumber extends Model<MachineNumber> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 机器名称
     */
	private String name;
    /**
     * 机器状态(0、正常 1、损坏 2、报废)
     */
	private Integer status;
    /**
     * 采浆机型编号
     */
	private Long plasmTypeId;
    /**
     * 机号类型(0、未使用 1、使用中)
     */
	private Integer type;
	/**
     * 是否删除(0、否 1、是)
     */
	private Integer isDelete;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPlasmTypeId() {
		return plasmTypeId;
	}

	public void setPlasmTypeId(Long plasmTypeId) {
		this.plasmTypeId = plasmTypeId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
		return "MachineNumber [id=" + id + ", name=" + name + ", status="
				+ status + ", plasmTypeId=" + plasmTypeId + ", type=" + type
				+ ", isDelete=" + isDelete + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + ", creater=" + creater
				+ ", updater=" + updater + "]";
	}
}
