package com.fadl.cost.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 活动发布表
 * </p>
 *
 * @author zhanll
 * @since 2018-05-11
 */
@TableName("f_activity")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value="id",type=IdType.AUTO)
    private Long id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 针次条件
     */
    private Integer count;
    /**
     * 是否固定金额(0、固定 1、递增)
     */
    private Integer moneyType;
    /**
     * 0.新浆员 1.老浆员
     */
    private Integer isNew;
    /**
     * 活动开始时间
     */
    private String startDate;
    /**
     * 活动结束时间
     */
    private String endDate;
    /**
     * 活动金额
     */
    private BigDecimal money;
    /**
     * 是否删除（0、否，1、是）
     * */
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
    
    /**
	 * 所属浆站标识
	 */
    @TableField(fill = FieldFill.INSERT)
	private String plasmaId;
    /**
     * 结束针次
     */
    private Integer endCount;

    public Integer getEndCount() {
		return endCount;
	}

	public void setEndCount(Integer endCount) {
		this.endCount = endCount;
	}

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Activity{" +
        "id=" + id +
        ", name=" + name +
        ", count=" + count +
        ", moneyType=" + moneyType +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", money=" + money +
        ",isDelete=" + isDelete +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
