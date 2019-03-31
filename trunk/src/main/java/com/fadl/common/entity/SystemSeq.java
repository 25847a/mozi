package com.fadl.common.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 序号
 * </p>
 *
 * @author wangjing
 * @since 2018-05-19
 */
@TableName("f_system_seq")
public class SystemSeq extends Model<SystemSeq> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 序号名称
     */
    private String name;
    /**
     * 当前序号值
     */
    private Long value;
    /**
     * 增长值
     */
    private Long increment;
    /**
     * 0、登记号 1、小样号 2、订单号
     */
    private Integer type;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 是否初始化序号值( 0. 是 1.否 )
     */
    private Integer isInit;
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getIncrement() {
        return increment;
    }

    public void setIncrement(Long increment) {
        this.increment = increment;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
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
	
    public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

	@Override
    public String toString() {
        return "SystemSeq{" +
        "id=" + id +
        ", name=" + name +
        ", value=" + value +
        ", increment=" + increment +
        ", type=" + type +
        ", isInit=" + isInit +
        ", version=" + version +
        "}";
    }
}
