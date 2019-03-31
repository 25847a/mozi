package com.fadl.electrophoresis.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *  血清电泳
 * </p>
 *
 * @author hu
 * @since 2018-06-06
 */
@TableName("f_serum_electrophoresis")
public class SerumElectrophoresis extends Model<SerumElectrophoresis> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 浆员卡号
     */
    private String providerNo;
    /**
     * 白蛋白( 0.>=50%  1.<50% )
     */
    private Integer albumin;
    /**
     * 图谱（0.正常 1.不正常）
     */
    private Integer atlas;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 血清蛋白电泳批号
     */
    private String batchNO;
    /**
     * 血清蛋白电泳编号
     */
    private String serialNO;
    /**
     * 全登记号
     */
    private Long allId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd")
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

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public Integer getAlbumin() {
        return albumin;
    }

    public void setAlbumin(Integer albumin) {
        this.albumin = albumin;
    }

    public Integer getAtlas() {
        return atlas;
    }

    public void setAtlas(Integer atlas) {
        this.atlas = atlas;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}

	public String getSerialNO() {
		return serialNO;
	}

	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}

	public Long getAllId() {
		return allId;
	}

	public void setAllId(Long allId) {
		this.allId = allId;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SerumElectrophoresis{" +
        "id=" + id +
        ", providerNo=" + providerNo +
        ", albumin=" + albumin +
        ", atlas=" + atlas +
        ", remarks=" + remarks +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
