package com.fadl.health.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 上传下载表
 * </p>
 *
 * @author jian
 * @since 2019-04-19
 */
@TableName("uploaddownload")
public class Uploaddownload extends Model<Uploaddownload> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文件名字
     */
    private String name;
    /**
     * 地址
     */
    private String url;
    /**
     * 设备号
     */
    private String imei;
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
     * 1.公测  2.发布   3.测试
     */
    private String versiontype;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 更新时间
     */
    private String updatetime;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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
        return "Uploaddownload{" +
        "id=" + id +
        ", name=" + name +
        ", url=" + url +
        ", imei=" + imei +
        ", currentversion=" + currentversion +
        ", ziversion=" + ziversion +
        ", zhuversion=" + zhuversion +
        ", compilation=" + compilation +
        ", versiontype=" + versiontype +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", description=" + description +
        ", model=" + model +
        "}";
    }
}
