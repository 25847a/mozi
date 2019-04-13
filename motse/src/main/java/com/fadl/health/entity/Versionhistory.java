package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设备项目版本表
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@TableName("versionhistory")
public class Versionhistory extends Model<Versionhistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 地址
     */
    private String url;
    /**
     * 文件名字
     */
    private String name;
    /**
     * 当前版本号
     */
    private String currentversion;
    /**
     * 子版本号
     */
    private String ziversion;
    /**
     * 主版本号
     */
    private String zhuversion;
    /**
     * 编译号
     */
    private String compilation;
    /**
     * 版本类型
     */
    private String versiontype;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 描述
     */
    private String description;
    /**
     * 机型
     */
    private String model;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentversion() {
        return currentversion;
    }

    public void setCurrentversion(String currentversion) {
        this.currentversion = currentversion;
    }

    public String getZiversion() {
        return ziversion;
    }

    public void setZiversion(String ziversion) {
        this.ziversion = ziversion;
    }

    public String getZhuversion() {
        return zhuversion;
    }

    public void setZhuversion(String zhuversion) {
        this.zhuversion = zhuversion;
    }

    public String getCompilation() {
        return compilation;
    }

    public void setCompilation(String compilation) {
        this.compilation = compilation;
    }

    public String getVersiontype() {
        return versiontype;
    }

    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Versionhistory{" +
        "id=" + id +
        ", url=" + url +
        ", name=" + name +
        ", currentversion=" + currentversion +
        ", ziversion=" + ziversion +
        ", zhuversion=" + zhuversion +
        ", compilation=" + compilation +
        ", versiontype=" + versiontype +
        ", createtime=" + createtime +
        ", description=" + description +
        ", model=" + model +
        "}";
    }
}
