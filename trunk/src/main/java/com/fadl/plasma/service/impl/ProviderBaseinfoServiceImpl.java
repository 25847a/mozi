package com.fadl.plasma.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.check.dao.CheckMapper;
import com.fadl.check.entity.Check;
import com.fadl.collectionConfig.service.AreaService;
import com.fadl.common.DataRow;
import com.fadl.common.DateUtil;
import com.fadl.common.IConstants;
import com.fadl.common.ImageUtil;
import com.fadl.common.JsonUtil;
import com.fadl.common.ReadProperties;
import com.fadl.common.SocketUtil;
import com.fadl.common.StringHelper;
import com.fadl.common.entity.Config;
import com.fadl.common.entity.SystemSeq;
import com.fadl.common.service.ConfigService;
import com.fadl.common.service.SystemSeqService;
import com.fadl.cost.dao.ICostGrantMapper;
import com.fadl.cost.entity.CostGrant;
import com.fadl.cost.service.ICostGrantService;
import com.fadl.image.dao.PlasmaImageMapper;
import com.fadl.image.entity.PlasmaImage;
import com.fadl.log.service.LogService;
import com.fadl.plasma.dao.DetailedInfoMapper;
import com.fadl.plasma.dao.ProviderBaseinfoMapper;
import com.fadl.plasma.entity.CardRecord;
import com.fadl.plasma.entity.DetailedInfo;
import com.fadl.plasma.entity.ProviderBaseinfo;
import com.fadl.plasma.entity.ProviderBaseinfoTemp;
import com.fadl.plasma.service.CardRecordService;
import com.fadl.plasma.service.ProviderBaseinfoService;
import com.fadl.plasma.service.ProviderBaseinfoTempService;
import com.fadl.propagandist.dao.PropagandistMapper;
import com.fadl.propagandist.entity.Group;
import com.fadl.propagandist.entity.Propagandist;
import com.fadl.propagandist.service.GroupService;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;
import com.fadl.registries.dao.ProviderRegistriesMapper;
import com.fadl.registries.entity.ProviderRegistries;
import com.fadl.upload.service.HttpClientBuss;
import com.fadl.workflow.service.WorkflowService;
import com.fasterxml.jackson.databind.JsonNode;
/**
 * <p>
 * 浆员基本信息表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-04-10
 */
@Service
public class ProviderBaseinfoServiceImpl extends ServiceImpl<ProviderBaseinfoMapper, ProviderBaseinfo> implements ProviderBaseinfoService {
    @Autowired
    ProviderBaseinfoMapper providerBaseinfoMapper;
    @Autowired
    DetailedInfoMapper detailedInfoMapper;
    @Autowired
    PlasmaImageMapper plasmaImageMapper;
    @Autowired
    ProviderRegistriesMapper providerRegistriesMapper;
    @Autowired
    SystemSeqService systemSeqService;
    @Autowired
    PropagandistMapper propagandistMapper;
    @Autowired
    ConfigService configService;
    @Autowired
    AreaService areaService;
    @Autowired
    CheckMapper checkMapper;
    @Autowired
    ICostGrantMapper iCostGrantMapper;
    @Autowired
    WorkflowService workflowService;
    @Autowired
    ICostGrantService iCostGrantService;
    @Autowired
    CardRecordService cardRecordService;
    @Autowired
	private SocketUtil socketUtil;
    @Autowired
    public ProviderBaseinfoTempService providerBaseinfoTempService;
    @Autowired
    public LogService logService;
    @Autowired
    public RabatinfoService rabatinfoService;
    @Autowired
    GroupService groupService;
    
    /**
     * 查询浆员详情
     * @param providerNo
     * @param startTime
     * @return
     */
    public DataRow queryProviderBaseInfoDetails(String providerNo,String startTime)throws Exception{
        HashMap<String,Object> map= new HashMap<String,Object>();
        map.put("providerNo",providerNo);
        map.put("startTime",startTime);
        DataRow d =providerBaseinfoMapper.queryProviderBaseInfoDetails(map);
        return getInviteName(d);
    }
    /**
     * 修改浆员信息
     * @param file 文件
     * @param data 浆员详细信息
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void updateProviderBaseInfo(MultipartFile[] file,HashMap<String, String> data,DataRow messageMap)throws Exception{
        ProviderBaseinfo pbi=selectById(Long.valueOf(data.get("id")));//查询对象基本信息
        if(pbi!=null){
        	if(pbi.getIsGrantCard()==1 && !pbi.getProviderNo().startsWith("f")){
        		messageMap.initFial("浆员已发卡，不能修改信息，请先取消发卡");
        		return;
        	}
            DetailedInfo di=detailedInfoMapper.queryByBaseId(pbi.getId()); //查询对象详细信息
            if(di!=null&&pbi.getId().equals(di.getBaseId())){
                for (int i=0;i<file.length;i++){
                    if("".equals(file[i].getOriginalFilename())){
                        continue;
                    }
                    if(i==0){
                    	data.put("imagez",(buildImg(file[i],pbi.getProviderNo(),ReadProperties.getValue("idCardz"),1000,1000)));
                    }else if(i==1){
                    	data.put("imagef",(buildImg(file[i],pbi.getProviderNo(),ReadProperties.getValue("idCardf"),1000,1000)));
                    }else{
                        String imgName=buildImg(file[i],pbi.getProviderNo(),ReadProperties.getValue("spotImg"),1000,1000);
                        data.put("imagespot",imgName);//变更不会变更图片流水表
                    }

                }
                /*int flagBase =providerBaseinfoMapper.updateById(providerBaseinfo);
                detailedInfo.setId(di.getId());
                detailedInfo.setType(0);//浆员信息变更  不允许变更为扩展员，type字段有冲突，需重新设置
                int flagDetaile=detailedInfoMapper.updateById(detailedInfo);
                if(flagBase>0&&flagDetaile>0){
                    messageMap.initSuccess();
                }else{
                    messageMap.initFial();
                    throw new Exception();
                }*/
                data.remove("phone");
                data.remove("history");
                data.remove("remarks");
                data.put("plasmaId", pbi.getPlasmaId());
                data.put("providerNo", pbi.getProviderNo());
                data.put("isMarry", di.getIsMarry()+"");
                String json = JsonUtil.getMapper().writeValueAsString(data);
                ProviderBaseinfoTemp temp = JsonUtil.getMapper().readValue(json, ProviderBaseinfoTemp.class);
                temp.setId(null);
                /*temp.setProviderNo(pbi.getProviderNo());
                temp.setValidDate(detailedInfo.getValidDate());
                temp.setAddressx(detailedInfo.getAddressx());
                temp.setStatus(0);
                temp.setNation(detailedInfo.getNation());
                temp.setPlace(detailedInfo.getPlace());*/
                temp.setStatus(0);
                boolean res = providerBaseinfoTempService.insert(temp);
                if (res) {
					messageMap.initSuccess();
				}else{
					messageMap.initFial();
				}
            }else{
                messageMap.initFial();
            }
        }else{
            messageMap.initFial();
        }

    }
    /**
     * 浆员基本信息审核
     * @param map 审核的浆员id集合
     * @return
     */
    public void updateBaseInfoExamine(HashMap<String,Object> map,DataRow messageMap)throws Exception{
       boolean flag = providerBaseinfoMapper.updateBaseInfoExamine(map);
       if(flag){
           messageMap.initSuccess();
       }else {
           messageMap.initFial();
       }
    }
    /**
     * 点击浆员 查询浆员详细信息
     * @param id 浆员id
     * @return 返回浆员详细信息
     */
    public DataRow queryPlasmaInfoDetail(String id,DataRow messageMap)throws Exception{
       DataRow d= providerBaseinfoMapper.queryPlasmaInfoDetail(id);
        getInviteName(d);
        messageMap.initSuccess(d);
        return messageMap;
    }
    /**
     * 根据浆员卡号查询浆员登记-献浆记录
     * @param page 分页
     * @param providerNo 浆员卡号
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryPlasmaInfoDetailList(Page<DataRow> page, String providerNo)throws Exception{
        return page.setRecords(providerBaseinfoMapper.queryPlasmaInfoDetailList(page, providerNo));
    }
    /**
     * 验证浆员是否合法
     */
    public void queryPlasmaLegal(String birthday,String address,String idNo,String validDate,DataRow messageMap)throws Exception{
        int age =DateUtil.getAgeFromBirthTime(birthday);
        List<Config> configList= configService.getConfig("new_age");
        if(age>Integer.valueOf(configList.get(0).getValue())){  //大于55周岁不允许献浆
            messageMap.initFial("抱歉,您年龄已超过"+Integer.valueOf(configList.get(0).getValue())+"岁");
        }else if(!"".equals(address)&&areaService.judgeArea(address)==null){ //不属于合法区域  不允许献浆
            messageMap.initFial("抱歉，您身份地址不在合法区域");
        }else if(!"".equals(validDate)&&!"长期".equals(validDate) && DateUtil.sdf.parse(validDate).before(new Date())){
            messageMap.initFial("抱歉，您身份证已过期");
        }else{
            HashMap<String,Object> map= new HashMap<String,Object>();
            map.put("idNo",idNo);
            DataRow d =providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(map);
            if(d!=null){
                messageMap.initFial("该浆员已建档");
                return;
            }else {
                messageMap.initSuccess();
            }
        }
    }
    /**
     * 新增浆员信息
     * @param file 文件流
     * @param providerBaseinfo  浆员基本信息
     * @param detailedInfo 浆员详细信息
     * @param validateType 是否验证 0人脸识别
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void insertBaseInfo(MultipartFile[] file, ProviderBaseinfo providerBaseinfo, DetailedInfo detailedInfo, Integer validateType, Integer roadFeeType, BigDecimal roadFee,String group,DataRow messageMap)throws Exception{
    	if(DateUtil.sdf.parse(detailedInfo.getValidDate()).before(new Date())){
            messageMap.initFial("抱歉，您身份证已过期");
            return;
        }
        int age =DateUtil.getAgeFromBirthTime(providerBaseinfo.getBirthday());
       List<Config> configList= configService.getConfig("new_age");
        if(age>Integer.valueOf(configList.get(0).getValue())){  //大于55周岁不允许献浆
            messageMap.initFial("抱歉,您年龄已超过"+Integer.valueOf(configList.get(0).getValue())+"岁");
            return;
        }
        if(areaService.judgeArea(detailedInfo.getAddressx())==null){ //不属于合法区域  不允许献浆
            messageMap.initFial("抱歉，您身份地址不在合法区域");
            return;
        }
        HashMap<String,Object> map= new HashMap<String,Object>();
        map.put("idNo",providerBaseinfo.getIdNo());
        DataRow d =providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(map);
        if(d!=null){
            messageMap.initFial("该浆员已建档");
            return;
        }
        //制空
        map.clear();
        d=null;
        if(null==validateType){
            messageMap.initFial("请先进行面部验证");
            return;
        }
        if(detailedInfo!=null&&null!=detailedInfo.getInviteId()&&null!=detailedInfo.getInviteType()){//如果有邀请人

            if(0==detailedInfo.getInviteType()){//浆员
                map.put("providerNo",detailedInfo.getInviteId());
                 d = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(map);//查询邀请人卡号是否存在
               if(d==null){
                   messageMap.initFial("邀请人卡号不正确");
                   return;
               }
            }else{
                Propagandist p =propagandistMapper.queryByProviderNo(detailedInfo.getInviteId()+"");
                if(p==null){
                    messageMap.initFial("邀请人卡号不正确");
                    return;
                }
            }
        }
        //判断组号(啊健)
        EntityWrapper<Group> ew = new EntityWrapper<Group>();
        ew.eq("num", group);
        List<Group> list =groupService.selectList(ew);
        if(!list.isEmpty()) {
        	 for(int i=0;i<list.size();i++) {
        		 if(group.equals(String.valueOf(list.get(i).getNum()))) {
        			 providerBaseinfo.setGroupId(list.get(i).getId());
        		 }
        	 }
        }else {
        	 messageMap.initFial("系统没有该组号,请重新填写");
             return;
        }
        PlasmaImage plasmaImage = new PlasmaImage(); //图片对象
        SystemSeq systemSeq=systemSeqService.queryNewSystemSeqInfo(0); //获取最新的登记号
        SystemSeq systemSeqTemp=systemSeqService.queryNewSystemSeqInfo(6); //获取临时卡号
        int index=0;
        for (int i=0;i<file.length;i++){
            if(file[i].isEmpty())
                continue;
            if(index==0){
                providerBaseinfo.setImagez(buildImg(file[i],"f"+systemSeqTemp.getValue(),ReadProperties.getValue("idCardz"),1000,1000));
                index++;
            }else if(index==1){
                providerBaseinfo.setImagef(buildImg(file[i],"f"+systemSeqTemp.getValue(),ReadProperties.getValue("idCardf"),1000,1000));
                index++;
            }else if(index==2){
            	providerBaseinfo.setPhoto(buildImg(file[i],"f"+systemSeqTemp.getValue(),ReadProperties.getValue("photo"),1000,1000));
                index++;
            }else{
                String imgName=buildImg(file[i],"f"+systemSeqTemp.getValue(),ReadProperties.getValue("spotImg"),1000,1000);
                plasmaImage.setImgUrl(imgName);
                providerBaseinfo.setImagespot(imgName);
            }
        }
        providerBaseinfo.setProviderNo("f"+systemSeqTemp.getValue());
        int flag=providerBaseinfoMapper.insert(providerBaseinfo);//插入浆员信息
        detailedInfo.setBaseId(providerBaseinfo.getId());
        int flag1 = detailedInfoMapper.insert(detailedInfo);//插入浆员详细信息
        ProviderRegistries providerRegistries = new ProviderRegistries();
        providerRegistries.setProviderNo("f"+systemSeqTemp.getValue()); //临时卡号
        providerRegistries.setAllId(systemSeq.getValue());//全登记号
        Integer regId=Integer.valueOf(String.valueOf(systemSeq.getValue()).substring(8,systemSeq.getValue().toString().length()));//截取登记号
        providerRegistries.setRegistriesNo(regId);//登记号
        providerRegistries.setType(validateType);//验证类型  0人脸识别  1静脉验证
        providerRegistries.setPlasmaType(0);//浆员类型(0非固定、1、固定)
        providerRegistries.setIsNew(0);//是否是新老浆员（0新浆员 1老浆员）
       int flag3= providerRegistriesMapper.insert(providerRegistries);//插入登记信息
        Check c = new Check();
        c.setAllId(systemSeq.getValue());
        c.setProviderNo("f"+systemSeqTemp.getValue());
        int flag4=checkMapper.insert(c);//插入体检信息
        plasmaImage.setImgId(providerRegistries.getId());
        int flag2 = plasmaImageMapper.insert(plasmaImage);//插入现场图片
        //插入胸片记录表
      	Rabatinfo rabat = new Rabatinfo();
      	rabat.setProviderNo(providerRegistries.getProviderNo());
      	rabat.setAllId(providerRegistries.getAllId());
      	rabatinfoService.insert(rabat);
      	//插入费用
        CostGrant costGrant =new CostGrant();
        costGrant.setAllId(systemSeq.getValue());//设置全登记号
        costGrant.setProviderNo("f"+systemSeqTemp.getValue());
        costGrant.setRoadFeeType(roadFeeType);
        costGrant.setRoadFee(roadFee);
        int flag5=iCostGrantMapper.insert(costGrant);//插入费用补助  路程
        int flag6=workflowService.insertWorkflow(systemSeq.getValue());//插入流程
        logService.insertLog(IConstants.MODULE_CREATE_BASEINFO, IConstants.DESCRIBE.replace("{0}", providerBaseinfo.getName())
        		.replace("{1}", "f"+systemSeqTemp.getValue()).replace("{2}", "创建临时浆员信息"),providerBaseinfo.getProviderNo());
        if(flag>0&&flag1>0&&flag2>0&&flag3>0&&flag4>0&&flag5>0&&flag6>0){
            messageMap.initSuccess(providerBaseinfo);//压缩图片
        }else{
            messageMap.initFial();
            throw new Exception();
        }
    }

    /**
     * 生成图片
     * @param file 文件对象
     * @param ProviderNo 图片名字=（浆员卡）+时间戳
     * @param catalog 生成目录
     * @throws Exception
     */
    public String buildImg(MultipartFile file,String ProviderNo,String catalog,int width,int height) throws Exception{
        // 获取图片的文件名
        String fileName = file.getOriginalFilename();
        // 获取图片的扩展名
        String extensionName = StringUtils.substringAfter(fileName, ".")==null?"jpg":StringUtils.substringAfter(fileName, ".");
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        String newFileName =ProviderNo+"-"+ String.valueOf(System.currentTimeMillis()) + "." + extensionName;
        // 文件路径
        File dest = new File(catalog, newFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        ImageUtil.createThumbnail(file.getInputStream(), catalog+File.separator+newFileName,width,height);
        return catalog.substring(2,catalog.length())+"/"+newFileName;
    }
    /**
     * 查询浆员首次建档登记
     * @param page 分页参数
     * @param startTime 根据登记时间查询
     * @return
     * @throws Exception
     */
    public Page<DataRow> queryRegistriesList(Page<DataRow> page,String startTime) throws Exception{
        return page.setRecords(providerBaseinfoMapper.queryRegistriesList(page, startTime));
    }
    /**
     * 查询浆员信息
     * @param page 分页参数
     * @param map 根据 姓名、卡号、身份证查询
     * @return
     * @throws Exception
     */
    public Page<DataRow> queryProviderBaseInfoList(Page<DataRow> page, Map<String,Object> map)throws Exception{
        return page.setRecords(providerBaseinfoMapper.queryProviderBaseInfoList(page, map));
    }
	
    
    /**未审核浆员发送到卫计委系统
	 * @throws Exception */
	/*@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public JSONObject sendPlasmaMsg() throws Exception {
		//return providerBaseinfoMapper.sendPlasmaMsg();
		List<DataRow> result = providerBaseinfoMapper.sendPlasmaMsg();
		//将图片当做流传输（图片正反面的参数都为文件名+文件流）
		for (int i = 0; i < result.size(); i++) {
			String imagez = result.get(i).get("imagez").toString();
			System.out.println("浆站图片地址："+ReadProperties.getValue("baseDir")+imagez);
			String imagezSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagez);
			result.get(i).set("imagez", imagez+imagezSteam);;
			String imagef = result.get(i).get("imagef").toString();
			String imagefSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagef);
			result.get(i).set("imagef",imagef+imagefSteam);
			String imagespot = result.get(i).get("imagespot").toString();
			String imagespotStrenm = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagespot);
			result.get(i).set("imagespot",imagespot+imagespotStrenm);
		}
		HttpClientBuss httpClientBuss = new HttpClientBuss();
		Map<String, String> map = new HashMap<>();
		map.put("postData", JsonUtil.getMapper().writeValueAsString(result));
		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/client/getPlasmaMsg", map);
		JSONObject json = JSONObject.fromObject(posrData);
		//return "获取浆员信息成功".equals(json.get("msg"));
		return json;
	}*/
    
    /**
     * 给检验合格的浆员将临时卡号改为浆员卡号
     * @throws Exception
     *  */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public DataRow changeProviderNo(Map<String, String> map,DataRow messageMap) throws Exception {
    	// 如果是新浆员，判断 是否上传胸片或胸片结果是否合格
    	if (map.get("providerNo").startsWith("f")) {
    		EntityWrapper<Rabatinfo> rabat = new EntityWrapper<Rabatinfo>();
    		rabat.eq("providerNo", map.get("providerNo"));
    		Rabatinfo info = rabatinfoService.selectOne(rabat);
    		if (null==info || info.getResult()==1) {
    			messageMap.initFial("胸片没有上传或胸片检查结果不合格");
        		return messageMap;
			}
		}
    	List<DataRow> list = providerBaseinfoMapper.sendPlasmaMsg(map);
    	if(null==list || list.size()==0){
    		messageMap.initFial("没有需要提交的新浆员");
    		return messageMap;
    	}
    	for (DataRow dataRow : list) {
    		//如果是正式卡号，不需要重新生成
    		if(dataRow.getString("providerNo").startsWith("f")){
    			updateCard(dataRow, messageMap);
    		}else{
    			dataRow.put("newProviderNo", dataRow.getString("providerNo"));
    		}
    		System.out.println(dataRow.getString("providerNo"));
    		//身份证正面图片
    		String imagez = dataRow.getString("imagez");
			String imagezSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagez);
			dataRow.put("imagez", imagezSteam);
			//身份证反面图片
			String imagef = dataRow.get("imagef").toString();
			String imagefSteam = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagef);
			dataRow.put("imagef", imagefSteam);
			//现场照片
			String imagespot = dataRow.get("imagespot").toString();
			String imagespotStrenm = socketUtil.encodeBase64File(ReadProperties.getValue("baseDir")+imagespot);
			dataRow.put("imagespot", imagespotStrenm);
		}
    	HttpClientBuss httpClientBuss = new HttpClientBuss();
		map = new HashMap<>();
		map.put("info", JsonUtil.getMapper().writeValueAsString(list));
		//发送数据到 卫计委
		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/downPlasmaBaseInfo", map);
    	JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
    	if(node.get("error").textValue().equals("-1")) {
    		for (DataRow dataRow : list) {
    			if(dataRow.getString("providerNo").startsWith("f")){
    				//更新相关表的浆员编号
                	map = new HashMap<>();
                	map.put("pNo", dataRow.getString("providerNo"));
                	map.put("newProviderNo", dataRow.getString("newProviderNo"));
                	//TODO
                	try {
                		int res = providerBaseinfoMapper.changeProviderNo(map);
                    	if (res<1) {
                    		deleteProviderInfo(map, dataRow, httpClientBuss, messageMap);
    					}
					} catch (Exception e) {
						deleteProviderInfo(map, dataRow, httpClientBuss, messageMap);
					}
    			}
			}
    		messageMap.initSuccess();
    		return messageMap;
    	}
    	messageMap.initFial(node.get("msg").textValue());
    	throw new Exception();
    }
    
    /**
     * 删除卫计委信息
     * @param map
     * @param dataRow
     * @param httpClientBuss
     * @param messageMap
     * @throws Exception 
     */
    public void deleteProviderInfo(Map<String, String> map,DataRow dataRow,HttpClientBuss httpClientBuss,DataRow messageMap) throws Exception{
    	Config config = configService.getConfig("stock_config", "code");
    	//删除上传的浆员信息
		map = new HashMap<>();
		map.put("plasmaId", config.getValue());
		map.put("providerNo", dataRow.getString("newProviderNo"));
		httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/deleteProviderInfo", map);
		messageMap.initFial();
		throw new SQLException("变更卡号失败");
    }
    
    /**
     * 把临时卡号缓存正式卡号
     * @param dataRow
     * @param messageMap
     * @throws Exception
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void updateCard(DataRow dataRow,DataRow messageMap) throws Exception{
    	String bloodType = dataRow.getString("bloodType");
		//从配置表获取相关血型浆员信息
    	String type="";
    	if("0".equals(bloodType)) {
    		type="A";
    	}else if("1".equals(bloodType)) {
    		type="B";
    	}else if("2".equals(bloodType)) {
    		type="o";
    	}else if("3".equals(bloodType)) {
    		type="AB";
    	}
    	//获取对应的value值
    	EntityWrapper<Config> ews = new EntityWrapper<Config>();
    	ews.eq("type", "blood_type");
    	ews.eq("lable", type);
    	Config con = configService.selectOne(ews);
    	int provider = Integer.valueOf(con.getValue());
    	int next = provider(provider,bloodType);
    	if(next > provider){
    		ews = new EntityWrapper<Config>();
        	ews.eq("id", con.getId());
        	ews.eq("value", con.getValue());
        	//更新配置表数据
        	con.setValue((next)+"");
        	boolean result = configService.update(con, ews);
        	if(!result) {
        		messageMap.initFial("发卡失败");
        		throw new Exception();
        	}
    	}
    	dataRow.put("newProviderNo", next);
    }
    
    /**
     * 获取下一个浆员卡号
     * @param config
     * @return
     * @throws SQLException
     */
    public int provider(int nextProvider,String bloodType)throws SQLException{
    	List<String> list = providerBaseinfoMapper.queryProviderList(bloodType);
    	int j=0;
    	int size=list.size();
    	for(int i=nextProvider;i>100000;i--){
    		if(j>=size){
				break;
			}
    		if(!(i+"").equals(list.get(j))){
    			//补卡操作
				return i;
			}
			j++;
		}
    	return nextProvider+1;
    }
    
    /**
     * 办卡查询（未发卡）
     */
	public Page<DataRow> queryCard(String createDate, Page<DataRow> page) throws SQLException {
		List<DataRow> records = providerBaseinfoMapper.queryCard(createDate,page);
		return page.setRecords(records);
	}
	
	/** 发卡，即给浆员绑定卡号*/
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void grantCard(Map<String, String> map,DataRow messageMap) throws Exception {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("providerNo", map.get("providerNo"));
		DataRow row = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(hashMap);
		if(StringHelper.isEmpty(map.get("icNum"))){
			messageMap.initFial("请先读取IC卡号");
			return;
		}else if(!StringHelper.isEmpty(row.getString("icNumber"))){
			messageMap.initFial("浆员已经发卡");
			return;
		}else if(row.getInt("status")==0){
			messageMap.initFial("浆员信息审核中");
			return;
		}
		if(providerBaseinfoMapper.grantCard(map) > 0) {
			CardRecord entity = new CardRecord();
			entity.setBaseInfoId(Long.valueOf(map.get("id").toString()));
			entity.setIcNumber(map.get("icNum").toString());
			cardRecordService.insert(entity);
			messageMap.initSuccess("发卡成功");
		}else {
			messageMap.initFial("操作失败");
			throw new SQLException("操作失败");
		}
	}
	/**
	 * 取消发卡
	 */
	public int cancelCard (String id) throws Exception{
		//取消发卡后，需要将卫计委系统的浆员的status更改为2（取消发卡）
		HttpClientBuss clientBuss = new HttpClientBuss();
		ProviderBaseinfo base = new ProviderBaseinfo();
		base.setId(Long.valueOf(id));
		String providerNo = providerBaseinfoMapper.selectOne(base).getProviderNo();
		Map<String, String> map = new HashMap<>();
		map.put("providerNo", providerNo);
		clientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/client/changeStatus", map);
		return providerBaseinfoMapper.cancelCard(id);
	}
	
	/**办卡查询（已发卡）*/
	public Page<DataRow> haveGrantCard(Map<String, String> map, Page<DataRow> page) throws SQLException {
		List<DataRow> records = providerBaseinfoMapper.haveGrantCard(map,page);
		return page.setRecords(records);
	}
	
	/**打卡(可以根据idNo或者providerNo进行查询)*/
	public Page<DataRow> queryPunchCard(Map<String, String> map, Page<DataRow> page) throws Exception {
		List<DataRow> list = providerBaseinfoMapper.queryPunchCard(map, page);
		
		return page.setRecords(list);
	}

	/**将以发卡浆员审核状态改成已审核*/
    public int updateStatus(HashMap<String, String> map) throws Exception{
    	return providerBaseinfoMapper.updateStatus(map);
    }
    
    /**
     * 根据邀请人类型和id 查询邀请人
     */
    public DataRow getInviteName(DataRow d)throws Exception{
        if(d!=null){
            String inviteType=d.getString("inviteType");
            if(!StringHelper.isEmpty(inviteType)){//如果有邀请人类型
                if("0".equals(inviteType)){
                    HashMap<String,Object> paramMap = new HashMap<String,Object>();
                    paramMap.put("providerNo",d.getString("inviteId"));
                    DataRow dataRow = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(paramMap);
                    if(dataRow!=null&&!StringHelper.isEmpty(dataRow.getString("providerNo"))){
                        d.put("iproviderNo",dataRow.getString("providerNo"));
                    }
                }else{
                    Propagandist p =propagandistMapper.queryByProviderNo(d.getString("inviteId"));
                    if(p!=null&&!StringHelper.isEmpty(p.getProviderNo())){
                        d.put("iproviderNo",p.getProviderNo());
                    }

                }
            }
        }
        return d;
    }
    
    /**
	 * 献浆员高级查询
	 */
	@Override
	public Page<DataRow> queryPlasmaDetailList(Page<DataRow> page, HashMap<String, String> map) throws Exception {
		map.put("pageNum", ((page.getCurrent() - 1) * page.getSize())+"");
		map.put("pageSize", page.getSize()+"");
		page.setRecords(providerBaseinfoMapper.queryPlasmaDetailList(map));
		page.setTotal(providerBaseinfoMapper.queryPlasmaDetailListCount(map));
		return page;
	}
	
	/**
	 * 献浆员高级查询（导出查询所有）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<DataRow> queryPlasmaDetailList(HashMap<String, String> map) throws Exception{
		return providerBaseinfoMapper.queryPlasmaDetailList(map);
	}
	@Override
	public List<DataRow> queryPlasmaDetailExcel(HashMap<String, String> map) throws SQLException {
		return providerBaseinfoMapper.queryPlasmaDetailExcel(map);
	}
	/**
	 * 基本信息审核
	 */
	@Override
	public Page<DataRow> queryPlasmaExamineList(Page<DataRow> page, HashMap<String, String> map) throws SQLException {
		return page.setRecords(providerBaseinfoMapper.queryPlasmaExamineList(page,map));
	}
	
	/**
	 * 基本信息审核（导出用）
	 */
	@Override
	public List<DataRow> queryPlasmaExamineList(HashMap<String, String> map) throws SQLException {
		return providerBaseinfoMapper.queryPlasmaExamineList(map);
	}
	
	/**
	 * 根据条件更新浆员状态
	 */
	@Override
	public int updateProviderBaseinfoState(HashMap<String, String> map) throws Exception {
		return providerBaseinfoMapper.updateProviderBaseinfoState(map);
	}
    /**
     * 删除浆员信息
     * @param id
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void deleteBaseInfo(String id,DataRow messageMap)throws Exception{
        //查询浆员详细信息id
        DataRow row=providerBaseinfoMapper.queryPlasmaWorkflow(id);
        if(row==null){
            messageMap.initFial();
        }else if(row.getLong("lc")<2){
            //体检过后就不能删除
            providerBaseinfoMapper.deleteById(row.getLong("baseId"));//删除baseInfo表 浆员信息
            detailedInfoMapper.deleteById(row.getLong("detailId"));//删除detail表 浆员详细信息
            providerRegistriesMapper.deleteById(row.getLong("registriesId"));//删除registrirs 浆员登记信息
            plasmaImageMapper.deleteById(row.getLong("imageId")); //删除image 浆员现场照片
            checkMapper.deleteById(row.getLong("checkId"));//删除check 体检信息表
            workflowService.deleteById(row.getLong("workflowId")); //删除workflow  流程
            iCostGrantService.deleteById(row.getLong("costId")); //删除costGrant  路费表
            messageMap.initSuccess();
        }else{
            messageMap.initFial("已经体检完成，不能删除");
        }

    }
    
    /**
     * 取消发卡
     * @return
     * @throws Exception
     */
    public void cancelPlassmaCard(String providerNo,DataRow messageMap)throws Exception{
    	DataRow data = new DataRow();
    	data.put("providerNo", providerNo);
    	data = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(data);
    	if (data.getInt("status")==2) {
    		HttpClientBuss httpClientBuss = new HttpClientBuss();
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("providerNo", providerNo);
    		//发送数据到 卫计委
    		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/queryBaseInfo", map);
        	JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
        	if(node.get("error").textValue().equals("-1")) {
        		/*ProviderBaseinfo base = new ProviderBaseinfo();
    			base.setIcNumber(null);
    			base.setIsGrantCard(0);
    			EntityWrapper<ProviderBaseinfo> ews = new EntityWrapper<ProviderBaseinfo>();
    			ews.eq("providerNo", providerNo);*/
    			int res = providerBaseinfoMapper.cancelPlassmaCard(providerNo);
    			if (res>0) {
    				messageMap.initSuccess();
    			}else{
    				messageMap.initFial();
    				throw new SQLException();
    			}
        	}else{
        		messageMap.initFial("请先在卫计委取消发卡");
        	}
		}else{
			messageMap.initFial("请先申请取消发卡");
		}
    	
    }
    
    /**
     * 申请取消发卡
     */
	@Override
	public void sendCancelCard(HashMap<String, String> map, DataRow messageMap) throws Exception {
		//查询浆员信息
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("providerNo", map.get("providerNo"));
		DataRow row = providerBaseinfoMapper.queryWhereBaseInfoOrDetailObj(m);
		if (row.getInt("isGrantCard")==0) {
			messageMap.initFial("该浆员还未发卡");
			return;
		}
		HttpClientBuss httpClientBuss = new HttpClientBuss();
		//发送数据到 卫计委
		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/receiveCancelCard", map);
    	JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
    	if(node.get("error").textValue().equals("-1")) {
    		map.put("status", "2");
    		int res = providerBaseinfoMapper.updateStatus(map);
    		if (res>0) {
    			messageMap.initSuccess();
			}else{
				messageMap.initFial();
			}
    	}else{
    		messageMap.initFial(node.get("msg").textValue());
    		throw new SQLException();
    	}
	}
	
	/**
	 * 发送信息变更请求到卫计委
	 * @throws Exception
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void submitPlasmaUpdate(Map<String, String> map,DataRow messageMap)throws Exception {
		EntityWrapper<ProviderBaseinfoTemp> ew = new EntityWrapper<ProviderBaseinfoTemp>();
		ew.eq("status", "0");
		ew.eq("providerNo", map.get("providerNo"));
		ew.orderBy("createDate", false);
		ew.last("LIMIT 0,1");
		ProviderBaseinfoTemp temp = providerBaseinfoTempService.selectOne(ew);
		if (null==temp) {
			messageMap.initFial("该浆员没有修改过信息");
			return;
		}else if(temp.getIsGrantCard()==1){
			messageMap.initFial("已提交信息");
			return;
		}
		HttpClientBuss httpClientBuss = new HttpClientBuss();
		//发送数据到 卫计委
		map = new HashMap<>();
		map.put("info", JsonUtil.getMapper().writeValueAsString(temp));
		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/receivePlasmaUpdate", map);
    	JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
    	if(node.get("error").textValue().equals("-1")) {
    		temp.setIsGrantCard(1);
    		providerBaseinfoTempService.updateById(temp);
    		messageMap.initSuccess();
    	}else{
    		messageMap.initFial(node.get("msg").textValue());
    		throw new SQLException();
    	}
	}
	
	/**
	 * 更新浆员信息
	 * @param providerNo
	 * @param messageMap
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
	public void updatePlasmaInfo(String providerNo,DataRow messageMap)throws Exception {
		HttpClientBuss httpClientBuss = new HttpClientBuss();
		Map<String, String> map = new HashMap<String, String>();
		map.put("providerNo", providerNo);
		//查询浆员详细信息
		map.put("status", "0");
		DataRow temp = providerBaseinfoTempService.queryBaseTempInfo(map);
		if(null==temp || temp.size()==0 || temp.getInt("isGrantCard")==0){
			messageMap.initFial("没有修改浆员信息");
			return;
		}
		//发送数据到 卫计委
		String posrData = httpClientBuss.doPost(ReadProperties.getValue("commissionAddress")+"/queryBaseInfo", map);
    	JsonNode node = JsonUtil.getMapper().readValue(posrData, JsonNode.class);
    	if(node.get("error").textValue().equals("-1")) {
    		temp.remove("type");
    		temp.remove("id");
    		//更新浆员详情
    		detailedInfoMapper.updatePlasmaDetailInfo(temp);
    		providerBaseinfoMapper.updatePlasmaInfo(temp);
    		providerBaseinfoTempService.updateProviderBaseInfoTempByProvider(temp);
    		messageMap.initSuccess();
    	}else{
    		messageMap.initFial(node.get("msg").textValue());
    	}
	}
	
	/**
     * 查询新浆员登记列表
     * @param startTime 根据登记时间查询
     * @return
     * @throws SQLException
     */
    public Page<DataRow> queryNewRegistriesList(Page<DataRow> page, String startTime)throws SQLException{
    	return page.setRecords(providerBaseinfoMapper.queryNewRegistriesList(page, startTime)); 
    }
    
    /**
     * 查询未发卡人员 
     * @return
     * @throws SQLException
     */
    public DataRow queryNotGrantCard()throws SQLException{
    	return providerBaseinfoMapper.queryNotGrantCard();
    }
    /**
     * 更新浆员审核状态
     * @param providerNo
     * @return
     * @throws SQLException
     */
    public int updatePrividerStatus(DataRow row)throws SQLException{
    	return providerBaseinfoMapper.updatePrividerStatus(row);
    }
}
