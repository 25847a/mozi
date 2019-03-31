package com.fadl.rabatinfo.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fadl.check.entity.Check;
import com.fadl.check.service.CheckService;
import com.fadl.common.DataRow;
import com.fadl.common.ImageUtil;
import com.fadl.common.ReadProperties;
import com.fadl.rabatinfo.dao.RabatinfoMapper;
import com.fadl.rabatinfo.entity.Rabatinfo;
import com.fadl.rabatinfo.service.RabatinfoService;

/**
 * <p>
 * 胸片检查记录表 服务实现类
 * </p>
 *
 * @author wangjing
 * @since 2018-05-08
 */
@Service
public class RabatinfoServiceImpl extends ServiceImpl<RabatinfoMapper, Rabatinfo> implements RabatinfoService {

	@Autowired
	public RabatinfoMapper rabatinfoMapper;
	@Autowired
	public CheckService checkService;
	
	/**
	 * 查询胸片结果列表
	 */
	@Override
	public void queryRabatinfoListByProviderNo(HashMap<String, String> map, Page<DataRow> page)
			throws SQLException {
		page.setRecords(rabatinfoMapper.queryRabatinfoListByProviderNo(map, page));
	}

	/**
	 * 查询浆员最后一次照胸片时间 
	 */
	@Override
	public DataRow queryProviderLastTime(Rabatinfo rabatinfo) throws SQLException {
		return rabatinfoMapper.queryProviderLastTime(rabatinfo);
	}

	/**
	 * 录入胸片结果
	 * @throws Exception 
	 * @throws  
	 */
	@Override
	public void insertRabatinfo(MultipartFile imageList, Rabatinfo rabatinfo, DataRow messageMap) throws Exception {
		//更新体检记录
        EntityWrapper<Rabatinfo> rabat=new EntityWrapper<Rabatinfo>();
		if(null!=imageList &&imageList.getContentType().contains("image")){
			// 获取图片的文件名
            String fileName = imageList.getOriginalFilename();
            // 获取图片的扩展名
            String extensionName = StringUtils.substringAfter(fileName, ".");
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
            // 文件路径
            String filePath = ReadProperties.getValue("rabat");
            File dest = new File(filePath, newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            String path = ReadProperties.getValue("rabat")+File.separator+newFileName;
            ImageUtil.createThumbnail(imageList.getInputStream(), path,180,210);
            rabatinfo.setRabatImg("/image/upload/rabat/"+newFileName);
		}
		//查询最后一次体检记录
        EntityWrapper<Check> ew=new EntityWrapper<Check>();
		ew.eq("providerNo", rabatinfo.getProviderNo());
		ew.orderBy("createDate", false);
		ew.eq("checkType", "0");
		ew.last("LIMIT 0,1");
    	Check check = checkService.selectOne(ew);
    	
        rabat.eq("allId", check.getAllId());
    	rabatinfo.setAllId(check.getAllId());
    	rabatinfo.setIsCheck(1); //状态改完 已检
        int res = rabatinfoMapper.update(rabatinfo, rabat);
        if(res>0){
        	messageMap.initSuccess();
        }else{
        	messageMap.initFial();
        }
        /*if(res>0){
        	// 如果体检合格 并且 胸片合格，继续往下面走流程
        	EntityWrapper<ProviderRegistries> ews = new EntityWrapper<ProviderRegistries>();
        	ews.eq("allId", check.getAllId());
        	ProviderRegistries providerRegistries = providerRegistriesService.selectOne(ews);
        	if(!(providerRegistries.getPlasmaType()==1)){
        		if (check.getResult()==0 && rabatinfo.getResult()==0) {
        			Config config = configService.getConfig("open_config","isCheck");
        			if (config.getIsDisable()==1) {
        				checkService.nextProcedure(check, providerRegistries, messageMap);
                		if (messageMap.getString(IConstants.RESULT_CODE).equals("-1")) {
                			messageMap.initSuccess();
                			return;
    					}
					}else{
						messageMap.initSuccess();
						return;
					}
				}else{
					//胸片结果不合格,插入体检拒绝信息
					//插入拒绝信息
					RefuseInfo refuseInfo = new RefuseInfo();
					refuseInfo.setProviderNo(check.getProviderNo());
					refuseInfo.setAllId(check.getAllId());
					boolean result = refuseInfoService.insert(refuseInfo);
					if (result) {
						messageMap.initSuccess();
						return;
					}
				}
			}else{
				messageMap.initSuccess();
    			return;
			}
        }*/
	}

	/**
	 * 根据 allid 更新胸片信息
	 * @return
	 * @throws SQLException
	 */
	public int updateRabatInfoByAllId(Long allId)throws SQLException{
		return rabatinfoMapper.updateRabatInfoByAllId(allId);
	}
}
