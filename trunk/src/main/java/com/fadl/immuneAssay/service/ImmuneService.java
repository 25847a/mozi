package com.fadl.immuneAssay.service;


import com.fadl.common.DataRow;

/**
 * 验证免疫流程的逻辑(由于各公司浆站的业务逻辑有差异,目前已知两种逻辑,写单独写个类区分免疫业务逻辑)啊健
 * @author Administrator
 *
 */
public interface ImmuneService {
	/**
	 * 验证是否需要进行免疫流程(nan)
	 * @param providerNo
	 * @return
	 */
	public void immuneRegister(String providerNo,DataRow messageMap)throws Exception;
	/**
	 * 进行免疫流程(nan)
	 * @param providerNo
	 * @return
	 */
	public void operationImmunity(String providerNo,DataRow messageMap)throws Exception;
	/**
	 * 验证是否需要进行免疫流程(莱士)
	 * @param providerNo
	 * @return
	 */
	public void immuneRegisterAlone(String providerNo,DataRow messageMap)throws Exception;
	/**
	 * 进行免疫流程(莱士)
	 * @param providerNo
	 * @return
	 */
	public void operationImmunityAlone(String providerNo,DataRow messageMap)throws Exception;
}
