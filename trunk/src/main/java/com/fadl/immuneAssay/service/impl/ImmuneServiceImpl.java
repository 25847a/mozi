package com.fadl.immuneAssay.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fadl.collection.service.PlasmCollectionService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.immuneAssay.entity.BasicRules;
import com.fadl.immuneAssay.entity.ImmuneRegister;
import com.fadl.immuneAssay.entity.OrdinaryInjection;
import com.fadl.immuneAssay.entity.OrdinaryStrengthenInjection;
import com.fadl.immuneAssay.entity.PrivilegeInjection;
import com.fadl.immuneAssay.entity.PrivilegeStrengthenInjection;
import com.fadl.immuneAssay.service.BasicRulesService;
import com.fadl.immuneAssay.service.ImmuneRegisterService;
import com.fadl.immuneAssay.service.ImmuneSettingService;
import com.fadl.immuneAssay.service.OrdinaryInjectionService;
import com.fadl.immuneAssay.service.OrdinaryStrengthenInjectionService;
import com.fadl.immuneAssay.service.PrivilegeInjectionService;
import com.fadl.immuneAssay.service.PrivilegeStrengthenInjectionService;
import com.fadl.immuneAssay.service.ImmuneService;

@Service
public class ImmuneServiceImpl implements ImmuneService{
	@Autowired
	ImmuneRegisterService immuneRegisterService;
	@Autowired
	ImmuneSettingService immuneSettingService;
	@Autowired
	BasicRulesService basicRulesService;
	@Autowired
	OrdinaryInjectionService ordinaryInjectionService;
	@Autowired
	OrdinaryStrengthenInjectionService ordinaryStrengthenInjectionService;
	@Autowired
	PrivilegeInjectionService privilegeInjectionService;
	@Autowired
	PrivilegeStrengthenInjectionService privilegeStrengthenInjectionService;
	@Autowired
	PlasmCollectionService plasmCollectionService;

	/**
	 * 验证是否需要进行免疫流程
	 * @param providerNo
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void immuneRegister(String providerNo,DataRow messageMap) throws Exception {
		//先判断免疫类型有没有开启
		// 插入普免登记(到了采浆验证通过之后才能打疫苗,插入到普免特免登记表)
		// 1.先判断免疫登记表是否有记录(),没有记录的话就插入新数据，针次为1。
		// 2.如果有记录的话，判断是否已通过，是通过的话(严谨点，判断针次是否为1,>=1的话继续判断，是否达到第二针的时间,达到三针后，判断加强针，然后。。就往注射表插入数据,没达到就不插)，
		// 不通过不插数据。
		// 效价值的判断
		EntityWrapper<ImmuneRegister> ew = new EntityWrapper<ImmuneRegister>();
		ew.eq("providerNo",providerNo);
		List<ImmuneRegister> imm = immuneRegisterService.selectList(ew);
		if (!imm.isEmpty()) {//有
			for(int i=0;i<imm.size();i++) {
			if (imm.get(i).getStatus() == 0) {// 判断是否通过登记，通过继续,不通过就什么都不操作,让流程继续走
				if(imm.get(i).getType()==1) {//判断是普免还是特免，今晚修改代码，也是从这里开始修改
					//普免注射  先普免再注射
					OrdinaryInjection o =ordinaryInjectionService.queryInjectionInfo(providerNo);
					if(o.getIsShot()!=0) {//已注射
						EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
						ea.eq("immuneId", o.getImmuneId());
						ea.eq("type", 0);
						List<BasicRules> basicRules = basicRulesService.selectList(ea);
						if(!basicRules.isEmpty()) {
							for(BasicRules b:basicRules) {
								if(o.getNum()<b.getNum() && o.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
									
									long day = (new Date().getTime()-DateUtil.sf.parse(o.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
									if(day>b.getIntervalDay()) {
										messageMap.initFial("浆员请注射普免基础第"+(o.getNum()+1)+"针!");return;
									}
								}else if(o.getNum()==basicRules.size()) {
									//////1.插入到特免登记表，2.插入普免加强针
									//针次等于总针次,就判断是否有普免加强针，进入普免加强
									OrdinaryStrengthenInjection ordinaryStreng = ordinaryStrengthenInjectionService.queryStrengInjectionInfo(providerNo);
									if(null!=ordinaryStreng) {//存在数据，就判断是否通过
										if(ordinaryStreng.getIsShot()!=0) {//==1  已通过
											EntityWrapper<BasicRules> eq = new EntityWrapper<BasicRules>();
											eq.eq("immuneId", ordinaryStreng.getImmuneId());
											eq.eq("type", 1);
											List<BasicRules> basic = basicRulesService.selectList(eq);
											for(BasicRules br:basic) {
												if(ordinaryStreng.getNum()<b.getNum() && ordinaryStreng.getNum()!=basic.size()) {
													long days = (new Date().getTime()-DateUtil.sf.parse(ordinaryStreng.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
													if(days>br.getIntervalDay()) {
														messageMap.initFial("浆员请注射普免加强第"+(ordinaryStreng.getNum()+1)+"针!");return;
													}
												}
											}
										}
									}else {//直接插入数据
										messageMap.initFial("浆员请进行普免加强针注射!");return;
									}
								}
							}
						}else {
							messageMap.initFial("请配置注射规则,否则流程不通");return;
						}
					}else {
						messageMap.initFial("浆员请进行普免基础针注射");
					}
				}else if(imm.get(i).getType()==2) {
				//  如果未注射,让流程过了
					//特免基础注射
					PrivilegeInjection privilege = privilegeInjectionService.queryPrivilegeInfo(providerNo);
					if(privilege.getIsShot()!=0) {//已注射
						EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
						ea.eq("immuneId", privilege.getImmuneId());
						ea.eq("type", 0);
						List<BasicRules> basicRules = basicRulesService.selectList(ea);
						if(!basicRules.isEmpty()) {
							for(BasicRules b:basicRules) {
								if(privilege.getNum()<b.getNum() && privilege.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
									long day = (new Date().getTime()-DateUtil.sf.parse(privilege.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
									if(day>b.getIntervalDay()) {
										messageMap.initFial("浆员请注射特免基础第"+(privilege.getNum()+1)+"针!");return;
									}
								}else if(privilege.getNum()==basicRules.size()) {
									//针次等于总针次,就判断是否有特免加强针，进入特免加强
									//特免加强注射
									PrivilegeStrengthenInjection  privilegeStreng = privilegeStrengthenInjectionService.queryStrengPrivilegeInfo(providerNo);
									if(null!=privilegeStreng) {
										if(privilegeStreng.getIsShot()!=0) {
											EntityWrapper<BasicRules> er = new EntityWrapper<BasicRules>();
											er.eq("immuneId", privilegeStreng.getImmuneId());
											er.eq("type", 0);
											List<BasicRules> rules = basicRulesService.selectList(er);
											for(BasicRules s:rules) {
												if(privilege.getNum()<s.getNum() && privilegeStreng.getNum()!=rules.size()) {//1<2  //判断第二针需要多少时间
													long day = (new Date().getTime()-DateUtil.sf.parse(privilegeStreng.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
													if(day>s.getIntervalDay()) {
														messageMap.initFial("浆员请注射特免加强第"+(privilegeStreng.getNum()+1)+"针!");return;
													}
												}
											}
										}
									}else {
										messageMap.initFial("浆员请进行特免加强针注射!");return;
									}
								}
							}
						}else {
							messageMap.initFial("请配置注射规则,否则流程不通");return;
						}
					}else {
						messageMap.initFial("浆员请进行特免基础针注射");
					}
				}
			}else {
				messageMap.initFial("浆员请在免疫模块进行免疫登记!");return;
			}
		}
		} else {// 如果没有记录,默认针次为0
			messageMap.initFial("浆员未进行免疫登记,请先登记!");return;
		}
	}

	/**
	 * 进行免疫流程
	 * @param providerNo
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void operationImmunity(String providerNo,DataRow messageMap) throws Exception {
				EntityWrapper<ImmuneRegister> ew = new EntityWrapper<ImmuneRegister>();
				ew.eq("providerNo",providerNo);
				List<ImmuneRegister> imm = immuneRegisterService.selectList(ew);		//加入转类后，map变成了list
				if (!imm.isEmpty()) {// 有
					for(int i=0;i<imm.size();i++) {
						if (imm.get(i).getStatus() == 0) {// 判断是否通过登记，通过继续,不通过就什么都不操作,让流程继续走
							if(imm.get(i).getType()==1) {
								//普免注射
								OrdinaryInjection o =ordinaryInjectionService.queryInjectionInfo(providerNo);//加入转类后，map变成了list
								if(o.getIsShot()!=0) {//已注射
									EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
									ea.eq("immuneId", o.getImmuneId());
									ea.eq("type", 0);
									List<BasicRules> basicRules = basicRulesService.selectList(ea);
									if(!basicRules.isEmpty()) {
										for(BasicRules b:basicRules) {
											if(o.getNum()<b.getNum() && o.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
												long day = (new Date().getTime()-DateUtil.sf.parse(o.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
												if(day>b.getIntervalDay()) {
													OrdinaryInjection ordinary = new OrdinaryInjection();
													ordinary.setProviderNo(providerNo);
													ordinary.setImmuneId(o.getImmuneId());
													ordinary.setNum(o.getNum()+1);
													ordinary.setIsShot(0);
													ordinaryInjectionService.insert(ordinary);
													messageMap.initSuccess();
													return;
												}
											}else if(o.getNum()==basicRules.size()) {
												//针次等于总针次,就判断是否有普免加强针，进入普免加强
												OrdinaryStrengthenInjection ordinaryStreng = ordinaryStrengthenInjectionService.queryStrengInjectionInfo(providerNo);
												if(null!=ordinaryStreng) {//存在数据，就判断是否通过
													if(ordinaryStreng.getIsShot()!=0) {//==1  已通过
														EntityWrapper<BasicRules> eq = new EntityWrapper<BasicRules>();
														eq.eq("immuneId", ordinaryStreng.getImmuneId());
														eq.eq("type", 1);
														List<BasicRules> basic = basicRulesService.selectList(eq);
														for(BasicRules br:basic) {
															if(ordinaryStreng.getNum()<b.getNum() && ordinaryStreng.getNum()!=basic.size()) {
																long days = (new Date().getTime()-DateUtil.sf.parse(ordinaryStreng.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
																if(days>br.getIntervalDay()) {
																	OrdinaryStrengthenInjection ordinary = new OrdinaryStrengthenInjection();
																	ordinary.setProviderNo(providerNo);
																	ordinary.setImmuneId(ordinaryStreng.getImmuneId());
																	ordinary.setNum(ordinaryStreng.getNum()+1);
																	ordinary.setIsShot(0);
																	ordinaryStrengthenInjectionService.insert(ordinary);
																	messageMap.initSuccess();
																	return;
																}
															}
														}
													}
												}else {//直接插入数据
													OrdinaryStrengthenInjection ordinary = new OrdinaryStrengthenInjection();
													ordinary.setProviderNo(providerNo);
													ordinary.setImmuneId(o.getImmuneId());
													ordinary.setNum(1);
													ordinary.setIsShot(0);
													ordinaryStrengthenInjectionService.insert(ordinary);
													messageMap.initSuccess();
													return;
												}
											}
										}
									}else {
										messageMap.initFial("请配置免疫类型");
									}
								}
							}else if(imm.get(i).getType()==2) {
							//  如果未注射,让流程过了
								//特免基础注射
								PrivilegeInjection privilege = privilegeInjectionService.queryPrivilegeInfo(providerNo);
								if(privilege.getIsShot()!=0) {
									EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
									ea.eq("immuneId", privilege.getImmuneId());
									ea.eq("type", 0);
									List<BasicRules> basicRules = basicRulesService.selectList(ea);
									if(!basicRules.isEmpty()) {
										for(BasicRules b:basicRules) {
											if(privilege.getNum()<b.getNum() && privilege.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
												long day = (new Date().getTime()-DateUtil.sf.parse(privilege.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
												if(day>b.getIntervalDay()) {
													PrivilegeInjection ordinary = new PrivilegeInjection();
													ordinary.setProviderNo(providerNo);
													ordinary.setImmuneId(privilege.getImmuneId());
													ordinary.setNum(privilege.getNum()+1);
													ordinary.setIsShot(0);
													privilegeInjectionService.insert(ordinary);
													messageMap.initSuccess();
													return;
												}
											}else if(privilege.getNum()==basicRules.size()) {
												//针次等于总针次,就判断是否有特免加强针，进入特免加强
												//特免加强注射
												PrivilegeStrengthenInjection  privilegeStreng = privilegeStrengthenInjectionService.queryStrengPrivilegeInfo(providerNo);
												if(null!=privilegeStreng) {
													if(privilegeStreng.getIsShot()!=0) {
														EntityWrapper<BasicRules> er = new EntityWrapper<BasicRules>();
														er.eq("immuneId", privilegeStreng.getImmuneId());
														er.eq("type", 0);
														List<BasicRules> rules = basicRulesService.selectList(er);
														for(BasicRules s:rules) {
															if(privilege.getNum()<s.getNum() && privilegeStreng.getNum()!=rules.size()) {//1<2  //判断第二针需要多少时间
																long day = (new Date().getTime()-DateUtil.sf.parse(privilegeStreng.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
																if(day>s.getIntervalDay()) {
																	PrivilegeStrengthenInjection ordinary = new PrivilegeStrengthenInjection();
																	ordinary.setProviderNo(providerNo);
																	ordinary.setImmuneId(privilegeStreng.getImmuneId());
																	ordinary.setNum(privilegeStreng.getNum()+1);
																	ordinary.setIsShot(0);
																	privilegeStrengthenInjectionService.insert(ordinary);
																	messageMap.initSuccess();
																	return;
																}
															}
														}
													}
												}else {
													PrivilegeStrengthenInjection p = new PrivilegeStrengthenInjection();
													p.setProviderNo(providerNo);
													p.setImmuneId(privilege.getImmuneId());
													p.setNum(1);
													p.setIsShot(0);
													privilegeStrengthenInjectionService.insert(p);
													messageMap.initSuccess();
													return;
												}
											}
										}
									}else {
										messageMap.initFial("请配置免疫类型");
									}
								}
							}
						}
					}
				} else {// 如果没有记录,默认针次为0
					ImmuneRegister immune = new ImmuneRegister();
					immune.setProviderNo(providerNo);// 浆员卡号
					immune.setType(0);
					immuneRegisterService.insert(immune);
					messageMap.initSuccess();
				}
				messageMap.initSuccess();
	}

	/**
	 * 验证是否需要进行免疫流程(莱士)
	 * @param providerNo
	 * @return
	 */
	@Override
	public void immuneRegisterAlone(String providerNo, DataRow messageMap) throws Exception {
		EntityWrapper<ImmuneRegister> ew = new EntityWrapper<ImmuneRegister>();
		ew.eq("providerNo",providerNo);
		List<ImmuneRegister> imm = immuneRegisterService.selectList(ew);
		if (!imm.isEmpty()) {//有
			for(int i=0;i<imm.size();i++) {
			if (imm.get(i).getStatus() == 0) {// 判断是否通过登记，通过继续,不通过就什么都不操作,让流程继续走
				if(imm.get(i).getType()==0) {//判断普通还是特免
					//判断为普通类型，就进行普免注射
					OrdinaryInjection o =ordinaryInjectionService.queryInjectionInfo(providerNo);
					if(o.getIsShot()!=0) {//已注射
						EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
						ea.eq("immuneId", o.getImmuneId());
						ea.eq("type", 0);
						List<BasicRules> basicRules = basicRulesService.selectList(ea);
						if(!basicRules.isEmpty()) {//判断注射规则
							for(BasicRules b:basicRules) {
								if(o.getNum()<b.getNum() && o.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
									long day = (new Date().getTime()-DateUtil.sf.parse(o.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
									if(day>=b.getIntervalDay()) {
										messageMap.initFial("浆员请注射普免基础第"+(o.getNum()+1)+"针!");return;
									}
								}else if(o.getNum()==basicRules.size()) {//如果注射够了三针,就自动插入数据到特免登记表，往后再由特免登记表,选择要登记的免疫类型
									//9.12获取浆站需求,第三针打完,这里已经是第三针判断了,过了一年,最近三个月采浆次数大于等于3次,就可以进行特免登记
									DataRow result = plasmCollectionService.queryImmuneCollectionCount(providerNo);
									long d = (DateUtil.sfDay.parse(o.getUpdateDate()).getTime()-DateUtil.sfDay.parse(result.getString("minDate")).getTime())/(3600*24*1000);
									if(d>365){
										ImmuneRegister register = immuneRegisterService.queryImmuneRegisterInfo(providerNo);
										if(null!=register){
											if(register.getStatus()==1){
												messageMap.initFial("请浆员进行特免登记");return;
											}
										}else{
											messageMap.initFial("浆员已完成基础针注射,请浆员进行特免登记");return;
										}
									}
								}
							}
						}else {
							messageMap.initFial("请配置注射规则,否则流程不通");return;
						}
					}else {
						messageMap.initFial("浆员请进行普免基础针注射");
					}
				}else if(imm.get(i).getType()==2) {
					//特免基础注射
					PrivilegeInjection privilege = privilegeInjectionService.queryPrivilegeInfo(providerNo);
					if(privilege.getIsShot()!=0) {//已注射
						EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
						ea.eq("immuneId", privilege.getImmuneId());
						ea.eq("type", 0);
						List<BasicRules> basicRules = basicRulesService.selectList(ea);
						if(!basicRules.isEmpty()) {//如果注射规则不为空
							for(BasicRules b:basicRules) {
								if(privilege.getNum()<b.getNum() && privilege.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
									long day = (new Date().getTime()-DateUtil.sf.parse(privilege.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
									if(day>=b.getIntervalDay()) {
										messageMap.initFial("浆员请注射特免基础第"+(privilege.getNum()+1)+"针!");return;
									}else if(privilege.getNum()==basicRules.size()) {
										if(imm.get(i).getStatus() != 0){
											//针次等于总针次,就判断是否有普免加强针，进入普免加强
											messageMap.initFial("浆员已完成基础针注射,请浆员进行特免登记");return;
										}
									}
								}//如果特免基础针打完后，就不检测特免加强针了，到这里就结束了
							}
						}else {
							messageMap.initFial("请配置注射规则,否则流程不通");return;
						}
					}else {
						messageMap.initFial("浆员请进行特免基础针注射");
					}
				}
			}else {
				messageMap.initFial("浆员请在免疫模块进行免疫登记!");return;
			}
		}
		} else {// 如果没有记录,默认针次为0
			messageMap.initFial("浆员未进行免疫登记,请先登记!");return;
		}
	
		
	}
	/**
	 * 进行免疫流程(莱士)
	 * @param providerNo
	 * @return
	 */
	@Override
	public void operationImmunityAlone(String providerNo, DataRow messageMap) throws Exception {
		EntityWrapper<ImmuneRegister> ew = new EntityWrapper<ImmuneRegister>();
		ew.eq("providerNo",providerNo);
		List<ImmuneRegister> imm = immuneRegisterService.selectList(ew);//加入转类后，map变成了list
		if (!imm.isEmpty()) {// 有
			for(int i=0;i<imm.size();i++) {
				if (imm.get(i).getStatus() == 0) {// 判断是否通过登记，通过继续,不通过就什么都不操作,让流程继续走
					if(imm.get(i).getType()==0) {
						//如果为0，就进行普免注射
						OrdinaryInjection o =ordinaryInjectionService.queryInjectionInfo(providerNo);//加入转类后，map变成了list
						if(o.getIsShot()!=0) {//已注射
							EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
							ea.eq("immuneId", o.getImmuneId());
							ea.eq("type", 0);
							List<BasicRules> basicRules = basicRulesService.selectList(ea);
							if(!basicRules.isEmpty()) {//判断注射规则有数据
								for(BasicRules b:basicRules) {
									if(o.getNum()<b.getNum() && o.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
										long day = (new Date().getTime()-DateUtil.sf.parse(o.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
										if(day>=b.getIntervalDay()) {
											OrdinaryInjection ordinary = new OrdinaryInjection();
											ordinary.setProviderNo(providerNo);
											ordinary.setImmuneId(o.getImmuneId());
											ordinary.setNum(o.getNum()+1);
											ordinary.setIsShot(0);
											ordinaryInjectionService.insert(ordinary);
											messageMap.initSuccess();
											return;
										}
									}else if(o.getNum()==basicRules.size()) {
										//如果普基础针打完后，就转为 特免登记
										ImmuneRegister register = immuneRegisterService.queryImmuneRegisterInfo(providerNo);
										if(null==register){
											ImmuneRegister immune=new ImmuneRegister();
											immune.setProviderNo(providerNo);// 浆员卡号
											immune.setType(2);
											immuneRegisterService.insert(immune);
											messageMap.initSuccess();return;
										}
									}
								}
							}else {
								messageMap.initFial("请配置免疫类型");
							}
						}
					}else if(imm.get(i).getType()==2) {
					//  如果未注射,让流程过了
						//特免基础注射
						PrivilegeInjection privilege = privilegeInjectionService.queryPrivilegeInfo(providerNo);
						if(privilege.getIsShot()!=0) {//已经注射了
							EntityWrapper<BasicRules> ea = new EntityWrapper<BasicRules>();
							ea.eq("immuneId", privilege.getImmuneId());
							ea.eq("type", 0);
							List<BasicRules> basicRules = basicRulesService.selectList(ea);
							if(!basicRules.isEmpty()) {
								for(BasicRules b:basicRules) {
									if(privilege.getNum()<b.getNum() && privilege.getNum()!=basicRules.size()) {//1<2  //判断第二针需要多少时间
										long day = (new Date().getTime()-DateUtil.sf.parse(privilege.getUpdateDate()).getTime())/(1000 * 60 * 60 * 24);//间隔时间
										if(day>=b.getIntervalDay()) {
											PrivilegeInjection ordinary = new PrivilegeInjection();
											ordinary.setProviderNo(providerNo);
											ordinary.setImmuneId(privilege.getImmuneId());
											ordinary.setNum(privilege.getNum()+1);
											ordinary.setIsShot(0);
											privilegeInjectionService.insert(ordinary);
											messageMap.initSuccess();
											return;
										}
									}//如果就不判断是否已经特免登记了
								}
							}else {
								messageMap.initFial("请配置注射规则,否则流程不通");
							}
						}
					}
				}
			}
		} else {// 如果没有记录,默认针次为0
			ImmuneRegister immune = new ImmuneRegister();
			immune.setProviderNo(providerNo);// 浆员卡号
			immune.setType(0);
			immuneRegisterService.insert(immune);
			messageMap.initSuccess();
		}
		messageMap.initSuccess();
	}

}
