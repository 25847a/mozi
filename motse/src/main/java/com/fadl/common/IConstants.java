package com.fadl.common;
/**
 * @Description:常量定义
 * @Date:2019-03-27
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
    /**
     * 7.养老院  8.城市合伙人 9.总公司
     */
    public static final String RESULT_BEADHOUSE="7";//养老院
    public static final String RESULT_PARTNER="8";//城市合伙人
    public static final String RESULT_HEADQUARTERS="9";//总公司
    /* **************************  成功、失败  通用封装返回  end  **************************/
    /**
     * 登陆错误次数过多
     */
    public static  final String USER_LOGIN_ERROR_NIMIETY="已错误输入5次,请{0}后重试";
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
    /**
     * 操作成功、失败
     */
    public static final String SUCCESS="操作成功";
    public static final String Fail="操作失败";
    public static final String ERROR="系统异常";
    
    /* **************************  惊凡   ******************************/
    public static final String  user="USER";
	
	public static final String channel_id="mozistar";
	
	public static final String channel_secret="aTjaZcJFBnpk";
}
