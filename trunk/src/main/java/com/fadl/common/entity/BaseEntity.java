package com.fadl.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

public abstract class  BaseEntity<T> extends Model<BaseEntity<T>>  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 121321312321L;

	/**
	 * 实体编号（唯一标识）
	 */
	@TableId(value = "id", type = IdType.AUTO)
	protected Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    /**
     * 创建时间
     */
	@TableField(value = "createDate",fill = FieldFill.INSERT )
	protected String createDate;
    /**
     * 修改时间
     */
	@TableField(value = "updateDate",fill = FieldFill.INSERT_UPDATE )
    protected String updateDate;
    /**
     * 创建人
     */
	@TableField(value = "creater",fill = FieldFill.INSERT )
	protected Long creater;
    /**
     * 修改人
     */
	@TableField(value = "updater",fill = FieldFill.INSERT_UPDATE )
	protected Long updater;
	
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
	
	
	
	
}
