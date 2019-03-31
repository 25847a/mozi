package com.fadl.common;

/**
 * @Description:常量定义
 * @Date:2018-02-28
 */
public class IConstants {

    //后台管理session-key
    public static final String  SESSION_ADMIN_USER="admin";

    /* **************************  成功、失败  通用封装返回  start  **************************/
    /**
     * code data message
     */
    public static final String RESULT_CODE="code";
    public static final String RESULT_DATA="data";
    public static final String RESULT_MESSAGE="message";
    /**
     * -2异常 成功 -1  失败 1  -3接口调用失败
     */
    public static final String RESULT_FAIL_CODE="1";
    public static final String RESULT_SUCCESS_CODE="-1";
    public static final String RESULT_ERROR_CODE="-2";
    public static final String RESULT_INTERFACE_CODE="-3";


    /* **************************  成功、失败  通用封装返回  end  **************************/

    /* **************************  权限控制提示 start *******************************/

    /**
     *  角色已绑定权限
     */
    public static final String ROLE_BINDING_PERMISSION="该角色已绑定权限";
    /**
     *  用户已绑定角色
     */
    public static final String ROLE_BINDING_ADMIN="该角色已绑定用户";
    /**
     * 菜单已有子级
     */
    public static final String MENU_BINDING_PARENT="菜单已绑定子菜单";

    /**
     * 无权限提示
     */
    public static final String NO_PERMISSION_MESSAGE="无权限";
    /**
     * 无页面提示
     */
    public static final String NO_PAGE_MESSAGE="无页面";
    /**
     * 无权限code
     */
    public static final String NO_PERMISSION_CODE="-3";
    /**
     * 跳转403
     */
    public static final String NO_PAGE_CODE="-4";

    /* **************************  权限控制提示 end ******************************/
    
    /* **************************  日志操作模板start ******************************/
    /**
     * 献浆员建档
     */
    public static final String MODULE_CREATE_BASEINFO="献浆员建档";
    /**
     * 硫酸铜检验 新增
     */
    public static final String MODULE_CREATE_BRPC="添加血红蛋白含量";
    /**
     * 硫酸铜检验 保存
     */
    public static final String MODULE_SAVE_BRPC="修改血红蛋白含量";
    
    /**
     * 硫酸铜检验 撤回
     */
    public static final String MODULE_RETRACT_BRPC="撤回血红蛋白含量";
    
    /**
     * 蛋白含量 新增
     */
    public static final String MODULE_CREATE_PC="添加蛋白含量";
    /**
     * 蛋白含量 保存
     */
    public static final String MODULE_SAVE_PC="修改蛋白含量";
    
    /**
     * 蛋白含量 撤回
     */
    public static final String MODULE_RETRACT_PC="撤回蛋白含量";
    /**
     * 血红蛋白检测 新增
     */
    public static final String MODULE_CREATE_DP="添加血红蛋白检测";
    /**
     * 血红蛋白检测 保存
     */
    public static final String MODULE_SAVE_DP="修改血红蛋白检测";
    
    /**
     * 血红蛋白检测 撤回
     */
    public static final String MODULE_RETRACT_DP="撤回血红蛋白检测";
    /**
     * 检验登记 新增
     */
    public static final String MODULE_CREATE_FPR="添加检验登记";
    /**
     * 检验登记 保存
     */
    public static final String MODULE_SAVE_FPR="修改检验登记";
    
    /**
     * 检验登记 撤回
     */
    public static final String MODULE_RETRACT_FPR="撤回检验登记";
    
    /**
     * 检验配置
     */
    public static final String MODULE_TCI="检验配置";
    
    /**
     * 酶标板化验
     */
    public static final String MODULE_TEST="酶标板化验";
    /**
     * 酶标板化验模板
     */
    public static final String MODULE_TEST_TEMPLATE="酶标板化验模板";
    /**
     * 酶标板质控
     */
    public static final String MODULE_QC="酶标板质控";
    /**
     * 登记
     **/
    public static final String REGISTRIES = "登记";
    /**
     * 取消登记(删除)
     */
    public static final String DELETEREGISTRIES = "取消登记(删除)";
    /**
     * 普免登记
     */
    public static final String IMMUNEREGISTRIES = "免疫登记";
    /**
     * 取消普免登记
     */
    public static final String PUREREGISTRIES = "取消普免登记";
    /**
     * 取消特免登记
     */
    public static final String TERGISTRIES = "取消特免登记";
    /**
     * 普免基础针  注射
     */
    public static final String ordBaseToShot = "普免基础针注射";
    /**
     * 取消普免基础针  注射
     */
    public static final String ordBaseToNotShot = "取消普免基础针注射";
    /**
     * 普免加强针注射
     */
    public static final String ordStrengthenToShot = "普免加强针注射";
    /**
     * 取消普免加强针注射
     */
    public static final String ordStrengthenToNotShot = "取消普免加强针注射";
    /**
     * 特免基础针注射
     */
    public static final String speBaseToShot = "特免基础针注射";
    /**
     * 特免基础针取消注射
     */
    public static final String speBaseToNotShot = "特免基础针取消注射";
    /**
     * 特免加强针注射
     */
    public static final String speStrengthenToShot = "特免基础针注射";
    /**
     * 特免加强针取消注射
     */
    public static final String speStrengthenToNotShot = "特免基础针取消注射";
    /**
     * 免疫化验发布结果
     */
    public static final String publishAssay = "免疫化验发布结果";
    /**
     * 取消免疫化验
     */
    public static final String cancelAssay = "取消免疫化验";
    /**
     * 特免转类  转类
     */
    public static final String transferType = "特免转类  转类";
    /**
     * 耗材订购,录入耗材订购记录表、订购单记录
     */
    public static final String insertOrderform = "耗材订购,录入耗材订购记录表、订购单记录";
    /**
     * 耗材订单修改
     */
    public static final String updateOrder = "耗材订单修改";
    /**
     * 耗材订单删除
     */
    public static final String delectOrder = "耗材订单删除";
    /**
     * 待审批、待检验,更改订购单记录表的订单状态
     */
    public static final String updateStatus = "待审批、待检验,更改订购单记录表的订单状态";
    /**
     * 订单作废,更改订购单记录表的订单状态
     */
    public static final String UselessStatus = "订单作废,更改订购单记录表的订单状态";
    /**
     * 待检验、已完成,更改订购单记录表的订单状态
     */
    public static final String updateBuyStatus = "待检验、已完成,更改订购单记录表的订单状态";
    /**
     * 插入订单信息入库存表
     */
    public static final String insertStock = "插入订单信息入库存表";
    /**
     * 修改盘点数量
     */
    public static final String updatePoint = "修改盘点数量";
    /**
     * 修改入库剩余数量
     */
    public static final String updateSurplusNumber = "修改入库剩余数量";
    /**
     * 新增耗材模板配置
     */
    public static final String insertTemplate = "新增耗材模板配置";
    /**
     * 修改耗材模板配置
     */
    public static final String updateTemplate = "修改耗材模板配置";
    /**
     * 删除耗材模板配置
     */
    public static final String deleteTemplate = "删除耗材模板配置";
    /**
     * 插入出库表
     */
    public static final String insertOutstock = "插入出库表";
    /**
     * 耗材退还操作
     */
    public static final String insertReturn = "耗材退还操作";
    /**
     * 修改退还订单信息
     */
    public static final String updateReturn = "修改退还订单信息";
    /**
     * 退还订单审核批准
     */
    public static final String updateReturnStatus = "退还订单审核批准";
    /**
     * 耗材报损操作
     */
    public static final String insertLoss = "耗材报损操作";
    /**
     * 修改报损订单信息
     */
    public static final String updateLoss = "修改报损订单信息";
    /**
     * 报损订单审核批准
     */
    public static final String updateLossStatus = "报损订单审核批准";
    
    /**
     * 耗材销毁操作
     */
    public static final String insertDestroy = "耗材销毁操作";
    /**
     * 修改销毁订单信息
     */
    public static final String updateDestroy = "修改销毁订单信息";
    /**
     * 销毁订单审核批准
     */
    public static final String updateDestroyStatus = "销毁订单审核批准";
    /**
     * 体检
     */
    public static final String CHECK="体检";
    /**
     * 取消体检
     */
    public static final String NOT_CHECK="取消体检";
    /**
     * 采浆验证
     */
    public static final String YELL="采浆验证";
    /**
     * 采浆
     */
    public static final String COLLECTION="已采浆";
    /**
     * 取消采浆
     */
    public static final String NOT_COLLECTION="取消采浆";
    /**
     * 费用发放
     */
    public static final String GRANT_COST="费用发放";
    /**
     * 描述操作  -1不变自动获取
     */
    public static final String DESCRIBE="操作员：{-1} 为献浆员：{0} 编号：{1} {2}";
    
    public static final String DESC="操作员：{-1} {0}";
    
    /* **************************  日志操作模板end ******************************/

    /* **************************  信息提示  **************************/

    /**
     * 用户不存在
     */
    public static  final String USER_NOT="用户不存在";
    /**
     * 用户已存在
     */
    public static  final String USER_EXIST="用户已存在";
    /**
     * 角色已存在
     */
    public static  final String ROLE_EXIST="角色已存在";
    /**
     * 角色不存在
     */
    public static  final String ROLE_NOT="角色不存在";
    /**
     * 菜单已存在
     */
    public static  final String MENU_EXIST="菜单或权限已存在";
    /**
     * 菜单不存在
     */
    public static final String MENU_NOT="菜单或权限不存在";
    /**
     * 登陆错误次数过多
     */
    public static  final String USER_LOGIN_ERROR_NIMIETY="登陆次数过多,请{0}后重试";

    /**
     * 账户或密码错误
     */
    public static final String USER_USE_PWD_ERROR="账户或密码错误";

    /**
     * 找不到图片
     */
    public static final String IMG_NOT="请上传图片";

    /**
     * 操作成功、失败
     */
    public static final String SUCCESS="操作成功";
    public static final String Fail="操作失败";
    public static final String ERROR="系统异常";

    /**
     * 登记序列号
     */
    public static final String REGISTER_NUM="registerNum";
    /**
     * 小样序列号
     */
    public static final String SAMPLE_NUM="sampleNum";
    /**
     * 小样序列号
     */
    public static final String ORDER_NUM="orderNum";
}
