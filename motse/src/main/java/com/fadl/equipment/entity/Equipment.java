package com.fadl.equipment.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author jian
 * @since 2019-04-12
 */
@TableName("equipment")
public class Equipment extends Model<Equipment> {

    private static final long serialVersionUID = 1L;

    /**
     * Id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 设备imei
     */
    private String imei;
    /**
     * 主机电量
     */
    private Integer lordpower;
    /**
     * 主机信号
     */
    private String signalxhao;
    /**
     * 蓝牙类型
     */
    @TableField("bluetooth_type")
    private String bluetoothType;
    /**
     * 设备状态(在线或者离线)
     */
    @TableField("eq_status")
    private String eqStatus;
    /**
     * 注册时间
     */
    private String createtime;
    /**
     * 最近更新数据时间
     */
    private String updatetime;
    /**
     * 设备类型
     */
    private String eqtype;
    /**
     * 蓝牙名称
     */
    @TableField("bluetooth_name")
    private String bluetoothName;
    /**
     * 蓝牙状态(连接，未连接)
     */
    @TableField("bluetooth_status")
    private String bluetoothStatus;
    /**
     * 蓝牙列表
     */
    @TableField("bluetooth_list")
    private String bluetoothList;
    /**
     * 蓝牙电量
     */
    @TableField("bluetooth_electricity")
    private Integer bluetoothElectricity;
    /**
     * 时钟
     */
    private String clock;
    /**
     * 紧急联系人1
     */
    private String phone1;
    /**
     * 紧急联系人2
     */
    private String phone2;
    /**
     * 昵称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 设备更新时间
     */
    private String uploadtime;
    /**
     * 蓝牙mac地址
     */
    private String bluetoothmac;
    /**
     * 代理商id
     */
    private Integer agentid;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getLordpower() {
        return lordpower;
    }

    public void setLordpower(Integer lordpower) {
        this.lordpower = lordpower;
    }

    public String getSignalxhao() {
        return signalxhao;
    }

    public void setSignalxhao(String signalxhao) {
        this.signalxhao = signalxhao;
    }

    public String getBluetoothType() {
        return bluetoothType;
    }

    public void setBluetoothType(String bluetoothType) {
        this.bluetoothType = bluetoothType;
    }

    public String getEqStatus() {
        return eqStatus;
    }

    public void setEqStatus(String eqStatus) {
        this.eqStatus = eqStatus;
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

    public String getEqtype() {
        return eqtype;
    }

    public void setEqtype(String eqtype) {
        this.eqtype = eqtype;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getBluetoothStatus() {
        return bluetoothStatus;
    }

    public void setBluetoothStatus(String bluetoothStatus) {
        this.bluetoothStatus = bluetoothStatus;
    }

    public String getBluetoothList() {
        return bluetoothList;
    }

    public void setBluetoothList(String bluetoothList) {
        this.bluetoothList = bluetoothList;
    }

    public Integer getBluetoothElectricity() {
        return bluetoothElectricity;
    }

    public void setBluetoothElectricity(Integer bluetoothElectricity) {
        this.bluetoothElectricity = bluetoothElectricity;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getBluetoothmac() {
        return bluetoothmac;
    }

    public void setBluetoothmac(String bluetoothmac) {
        this.bluetoothmac = bluetoothmac;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
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
        return "Equipment{" +
        "id=" + id +
        ", imei=" + imei +
        ", lordpower=" + lordpower +
        ", signalxhao=" + signalxhao +
        ", bluetoothType=" + bluetoothType +
        ", eqStatus=" + eqStatus +
        ", createtime=" + createtime +
        ", updatetime=" + updatetime +
        ", eqtype=" + eqtype +
        ", bluetoothName=" + bluetoothName +
        ", bluetoothStatus=" + bluetoothStatus +
        ", bluetoothList=" + bluetoothList +
        ", bluetoothElectricity=" + bluetoothElectricity +
        ", clock=" + clock +
        ", phone1=" + phone1 +
        ", phone2=" + phone2 +
        ", name=" + name +
        ", version=" + version +
        ", uploadtime=" + uploadtime +
        ", bluetoothmac=" + bluetoothmac +
        ", agentid=" + agentid +
        ", model=" + model +
        "}";
    }
}
