package cn.mozistar.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.mozistar.mapper.UserMapper;
import cn.mozistar.pojo.User;
import cn.mozistar.service.UserService;
import cn.mozistar.util.MD5Util;
import cn.mozistar.util.ResultBase;

@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	private final String baseUrl = "http://120.76.201.150:8080/";
	
	@Autowired 
	private UserMapper userMapper;
	
	/**
	 * 添加用户
	 * @param u
	 * @return
	 */
	public boolean addUser(User u) {
		
		boolean b = false;
		
		u.setCreatetime(new Date());
		u.setAtlasttime(new Date());
		
		String p=u.getPassword();
		u.setPassword(MD5Util.MD5(p));
		int num = userMapper.insertSelective(u);
		 if (num != 0) {
				b = true;
			} 
		 return b;
	}
	
	public  Integer sendSMS(String phone) {

		int smsMsg = getRandNum(1, 999999);
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = "LTAIcx9GAEhZ8XOE";//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = "FqdButvAztVgmDSCKw2dpInGTXax6z";//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);

		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
		 request.setPhoneNumbers(phone);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("墨子星");
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode("SMS_136045082");
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 JSONObject js = new JSONObject();
		 js.put("code", smsMsg);
		 request.setTemplateParam(js.toString());
		 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");

		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		 request.setOutId("yourOutId");

		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		//请求成功
		}
			return smsMsg;
	}
	
	public static int getRandNum(int min, int max) {
		int randNum = min + (int) (Math.random() * ((max - min) + 1));
		return randNum;
	}

	/**
	 * 查询账号是否存在
	 */
	public boolean checkAccount(String account) {
		
		boolean b = false;
		User user = selectUserByAccount(account);
		
		if(user!=null){
			b = true;
		}
		return b;
		
	}
	
	public User selectUserByAccount(String account){
		User user = userMapper.selectUserByAccount(account);
		return user;
	}


	@Override
	public void update(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	public boolean uploadavatar(String avatar, Integer id) {
		User u = new User();
		u.setAvatar(baseUrl+avatar);
		u.setId(id);
		Integer num =userMapper.updateByPrimaryKeySelective(u);
		 if (num != 0) {
				return true;
			} else {
				return false;
			}
	}

	@Override
	public User getUser(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public boolean updateUser(User u) {
		int i = userMapper.updateByPrimaryKeySelective(u);
		return i>0?true:false;
	}
	/**
	 * 修改目标步数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultBase updateWalkCount(Map<String, Object> map, ResultBase re) throws Exception {
		int row =userMapper.updateWalkCount(map);
		if(row>0){
			re.setCode(200);
			re.setMessage("修改成功");
		}else{
			re.setCode(400);
			re.setMessage("修改异常,请重试");
		}
		return re;
	}
}
