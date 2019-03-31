package com.fadl.account.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangjing
 * @since 2018-04-17
 */
@TableName("f_sys_permission")
public class SysPermission extends Model<SysPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型
     */
    private String masterType;
    /**
     * 名称
     */
    private String name;

    /**
     * 资源路径.
     */
    private String url;
    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
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
     * 是否可用（0、可用 1、不否可）
     */
    private Integer isDisable; // 是否可用,如果不可用将不会添加给用户
    /**
     * 父编号列表
     */
    private String parentIds;
    
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

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Integer getIsDisable() {
        return isDisable;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
        "id=" + id +
        ", name=" + name +
        ", masterType=" + masterType +
        ", url=" + url +
        ", permission=" + permission +
        ", parentId=" + parentId +
        ", isDisable=" + isDisable +
        ", parentIds=" + parentIds +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", creater=" + creater +
        ", updater=" + updater +
        "}";
    }
}
