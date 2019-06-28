package cn.mozistar.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.mozistar.pojo.Health;
import cn.mozistar.pojo.Healthdao;
import cn.mozistar.pojo.InvitationList;
import cn.mozistar.pojo.Positionig;
import cn.mozistar.pojo.Push;
import cn.mozistar.pojo.Relation;
import cn.mozistar.pojo.User;
import cn.mozistar.pojo.UserCode;
import cn.mozistar.service.HealthService;
import cn.mozistar.service.HealthdaoService;
import cn.mozistar.service.HealthsService;
import cn.mozistar.service.InvitationListService;
import cn.mozistar.service.PositionigService;
import cn.mozistar.service.PushService;
import cn.mozistar.service.RelationService;
import cn.mozistar.service.UserCodeService;
import cn.mozistar.service.UserService;
import cn.mozistar.util.DeleteFileUtil;
import cn.mozistar.util.HealthtoolUtils;
import cn.mozistar.util.MD5Util;
import cn.mozistar.util.MyStringUtil;
import cn.mozistar.util.ResultBase;
import cn.mozistar.util.ResultData;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private final String baseUrl = "http://120.76.201.150:8080/";
	private final String iconPath = "http://120.76.201.150:8080/avatars/120.png";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private UserCodeService userCodeService;
	@Autowired
	private RelationService relationService;
	@Autowired
	private HealthService healthService;
	@Autowired
	private HealthsService healthsService;
	@Autowired
	private HealthdaoService healthdaoService;
	@Autowired
	private InvitationListService invitationListService;
	@Autowired
	private PushService pushService;
	@Autowired
	private PositionigService positionigService;
	/**
	 * 跳转android操作提示页面
	 * @return
	 */
	@RequestMapping(value = "phone")
	public ModelAndView phone() {
		ModelAndView mo = new ModelAndView();
		mo.setViewName("phone");
		return mo;
	}
	/**
	 * 跳转android测量结果说明页面
	 * @return
	 */
	@RequestMapping(value = "measure")
	public ModelAndView measure() {
		ModelAndView mo = new ModelAndView();
		mo.setViewName("measure");
		return mo;
	}
	/**
	 * 用户登陆
	 * @param m
	 * @return
	 */
	@RequestMapping("landingUser")
	@ResponseBody
	public ResultData<User> landingUser(@RequestBody User u) {
		System.out.println(u.toString());
		ResultData<User> re = new ResultData<User>();

		// 查询账号
		String phone = u.getPhone();
		User user = userService.selectUserByAccount(phone);
		if (user != null) {
			if (u.getPassword() != null) {
				String password = MD5Util.MD5(u.getPassword());
				// 登录查询,校验密码
				if (password.equals(user.getPassword())) {
					user.setAtlasttime(new Date());
					userService.update(user);
					user.setPassword("");
					re.setData(user);
					re.setCode(200);
					re.setMessage("登录成功");
				} else {
					re.setCode(350);
					re.setMessage("密码错误");
				}
			} else {
				re.setCode(350);
				re.setMessage("请输入密码");
			}

		} else {
			re.setCode(350);
			re.setMessage("帐号不存在");
		}
		System.out.println(re.toString());
		return re;
	}

	/**
	 * 注册用户
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping("registered")
	@ResponseBody
	public ResultBase registered(@RequestBody User u) {
		ResultBase re = new ResultBase();
		try {
		// 检查手机号是否已经注册
		if (!userService.checkAccount(u.getPhone())) {
			// 检查验证码
			UserCode usercode = new UserCode();
			usercode.setPhone(u.getPhone());
			usercode.setCode(u.getCode());
			if (userCodeService.checkCode(usercode)) {
				u.setName(u.getPhone());
				u.setAvatar(iconPath);
				u.setAccount(u.getPhone());
				userService.addUser(u);

				re.setCode(200);
				re.setMessage("注册成功");

				User user = userService.selectUserByAccount(u.getAccount());
				user.setPassword("");
				//添加默认校准数据
				Healthdao healthdao = new Healthdao();
				healthdao.setBloodOxygen(0);
				healthdao.setCreatetime(new Date());
				healthdao.setHeartRate(0);
				healthdao.setHighBloodPressure(0);
				healthdao.setLowBloodPressure(0);
				healthdao.setHrv(0);
				healthdao.setMicrocirculation(0);
				healthdao.setRespirationrate(0);
				healthdao.setUserId(user.getId());
				healthdao.setPhone(user.getPhone());
				healthdaoService.insertSelective(healthdao);
				//添加第一次默认健康数据
				Health health = new Health();
				health.setPhone(user.getAccount());
				health.setHrv(0);
				health.setHighBloodPressure(0);
				health.setLowBloodPressure(0);
				health.setHeartRate(0);
				health.setBloodOxygen(0);
				health.setMicrocirculation(0);
				health.setRespirationrate(0);
				health.setAmedicalreport("数据采集失败，请重新检测！注意，按压均匀，避免漏光。");//amedicalreport
				health.setUserId(user.getId());
				health.setCreatetime(new Date());
				healthService.insertSelective(health);
				//添加默认预警信息
				Push push = new Push();
				push.setUserId(user.getId());
				push.setAlias(user.getId());
				push.setAllNotifyOn(1);
				push.setHeartHigThd(100);
				push.setHeartLowThd(55);
				push.setHbpstart(80);
				push.setHbpend(140);
				push.setLbpstart(60);
				push.setLbpend(100);
				push.setCreateTime(new Date());
				pushService.insertSelective(push);
			
				// 查询邀请列表
				List<InvitationList> list = invitationListService.selectByPhone(user.getPhone());
				if (list != null && list.size() > 0) {
					for (InvitationList invitationList : list) {
						Relation relation = new Relation();
						relation.setUserId(user.getId());
						relation.setObserveId(invitationList.getUserId());
						Push push2 = new Push();
						push2.setUserId(invitationList.getUserId());
						push2.setAlias(user.getId());
						push2.setAllNotifyOn(1);
						push2.setHeartHigThd(100);
						push2.setHeartLowThd(55);
						push2.setHbpstart(80);
						push2.setHbpend(140);
						push2.setLbpstart(60);
						push2.setLbpend(100);
						push.setCreateTime(new Date());
						pushService.insertSelective(push2);
						relationService.insert(relation);
					}
				}
				invitationListService.deleteByPhone(user.getPhone());
				HealthtoolUtils.registered("mozistar"+user.getId(),"12345", "123456");
				
			} else {
				re.setCode(400);
				re.setMessage("验证码不正确");
			}
		} else {
			re.setCode(400);
			re.setMessage("用户已经存在");
		}
		} catch (Exception e) {
			re.setCode(400);
			re.setMessage("注册失败");
		}
		return re;
	}

	/**
	 * 获取验证码
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("sendSMS")
	@ResponseBody
	public ResultBase sendSMS(@RequestBody Map<String, Object> m) {

		String phone = String.valueOf(m.get("phone"));
		System.out.println("获取验证码================" + phone);
		ResultBase re = new ResultBase();


		Integer smsMsg = userService.sendSMS(phone);
			if (smsMsg != 0) {
				UserCode c = new UserCode();
				c.setCode(String.valueOf(smsMsg));
				c.setPhone((String) m.get("phone"));
				UserCode selectUserCode = userCodeService.selectUserCode(phone);
				if(selectUserCode!=null){
					userCodeService.update(c);
				}else{
					userCodeService.addUsercode(c);
				}
				re.setCode(200);
				re.setMessage("发送验证码成功！！！");
			} else {
				re.setCode(400);
				re.setMessage("发送验证码失败！！！");
			}
		return re;
	}
 
	/**
	 * 上传头像
	 * 
	 * @param avatar
	 * @param id
	 * @return
	 */
	@RequestMapping("uploadavatar")
	@ResponseBody
	public ResultData<String> uploadavatar(
			@RequestParam(value = "avatar", required = false) CommonsMultipartFile avatar, Integer id) {
		ResultData<String> re = new ResultData<String>();
		new File("E:\\Project\\avatars").mkdirs();
		MyStringUtil.arrayUploadFile("E:\\Project\\avatars", avatar);
		boolean status = userService.uploadavatar("avatars/" + avatar.getOriginalFilename(), id);
		if (status) {
			re.setCode(200);
			String url = baseUrl + "avatars/" + avatar.getOriginalFilename();
			re.setData(url);
			re.setMessage("头像上传成功！！!");
		} else {
			re.setMessage("头像上传失败！！！");
			re.setCode(400);

		}

		return re;
	}

	/**
	 * 更新头像
	 * 
	 * @param avatar
	 * @param id
	 * @return
	 */
	@RequestMapping("updateavatar")
	@ResponseBody
	public ResultData<String> updateavatar(
			@RequestParam(value = "avatar", required = false) MultipartFile avatar, Integer id) {
		ResultData<String> re = new ResultData<String>();
		
		User u = userService.getUser(id);
		logger.info(id.toString());
		try {
			String[] st = u.getAvatar().split("/");
			if(!st[4].equals("120.png")){
				DeleteFileUtil.deleteFile("E:/Project/" + st[3] + "/" + st[4]);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		new File("E:\\Project\\avatars").mkdirs();
		MyStringUtil.arrayUploadFile("E:\\Project\\avatars", avatar);
		boolean status = userService.uploadavatar("avatars/" + avatar.getOriginalFilename(), id);
		if (status) {
			String url = baseUrl + "avatars/" + avatar.getOriginalFilename();
			u.setAvatar(url);
			userService.update(u);
			re.setData(url);
			re.setCode(200);
			re.setMessage("修改头像成功！！!");
		} else {
			re.setMessage("修改头像失败！！！");
			re.setCode(400);
		}
		return re;
	}

	/**
	 * 首页数据
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("selectHomePage")
	@ResponseBody
	public ResultData<List<Object>> selectHomePage(@RequestBody JSONObject json) {
		ResultData<List<Object>> re = new ResultData<>();
		List<Object> arrayList = new ArrayList<>();

		int userId = json.getInt("userId");
		logger.info("APP首页访问>>>>>>>>>>>>>>>>>>>>>>>:===="+userId);
		// 查询我观察的用户Id
		List<Integer> observeIdList = new ArrayList<>();
		observeIdList.add(userId);
		List<Integer> observeIdList2 = relationService.getObserveIdList(json.getInt("userId"));
		if (observeIdList2 != null && observeIdList2.size() > 0) {
			observeIdList.addAll(observeIdList2);
		}

		Map<String, Object> map = null;
		for (Integer integer : observeIdList) {
			Map<String, Object> dmap = new LinkedHashMap<>();
			map = new LinkedHashMap<>();
			List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
			User user = userService.getUser(integer);
			String name = user.getName();
			Integer id = user.getId();
			String phone = user.getPhone();
			String avatar = user.getAvatar();
			map.put("userId", id);
			map.put("name", name);
			map.put("phone", phone);
			map.put("avatar", avatar);
			map.put("calibration", user.getCalibration());
			map.put("coordinate", user.getCoordinate());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Health health = healthService.getHealthByUserId(integer);
			if (health == null) {
				health = new Health();
			}
			map.put("updatetime",health.getCreatetime()==null?"":sf.format(health.getCreatetime()));
			dmap.put("user", map);
			
			map = new HashMap<String, Object>();
			map.put("name", "stepWhen");
			map.put("desc", "步数");
			map.put("category", "13");
			map.put("lastestValue", health.getStepWhen() == null ? 0 : health.getStepWhen());
			map.put("unit", "步");
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("name", "heartrate");
			map.put("desc", "心率");
			map.put("category", "2");
			map.put("lastestValue", health.getHeartRate() == null ? 0 : health.getHeartRate());
			map.put("unit", "次/分");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "pressure");
			map.put("desc", "血压");
			map.put("category", "3");
			String a = health.getHighBloodPressure() == null ?"0": health.getHighBloodPressure().toString();
			String b = health.getLowBloodPressure() == null ? "0" : health.getLowBloodPressure().toString();
			map.put("lastestValue",a+"/"+b);
			map.put("unit", "mmHg");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "hrv");
			map.put("desc", "心率变异性HRV");
			map.put("category", "8");
			map.put("lastestValue", health.getHrv() == null ? 0 : health.getHrv());
			map.put("unit", "ms");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "mocrocirculation");
			map.put("desc", "微循环");
			map.put("category", "4");
			map.put("lastestValue", health.getMicrocirculation() == null ? 0 : health.getMicrocirculation());
			map.put("unit", "%");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "qxygen");
			map.put("desc", "血氧");
			map.put("category", "10");
			map.put("lastestValue", health.getBloodOxygen() == null ? 0 : health.getBloodOxygen());
			map.put("unit", "%");
			list.add(map);

			map = new HashMap<String, Object>();
			map.put("name", "carrieroad");
			map.put("desc", "卡里路");
			map.put("category", "14");
			map.put("lastestValue", health.getCarrieroad() == null ? 0 : health.getCarrieroad());
			map.put("unit", "焦耳/天");
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("name", "breathe");
			map.put("desc", "呼吸");
			map.put("category", "12");
			map.put("lastestValue", health.getRespirationrate() == null ? 0 : health.getRespirationrate());
			map.put("unit", "次/分钟");
			list.add(map);

			dmap.put("health", list);
			dmap.put("amedicalreport", health.getAmedicalreport() == null ? "" : health.getAmedicalreport());
			dmap.put("waveform", health.getWaveform() == null ? "" : health.getWaveform());
			
			arrayList.add(dmap);
		}
		re.setCode(200);
		re.setMessage("获取全部首页");
		re.setData(arrayList);
		return re;
	}

	/**
	 * 下拉刷新数据(单独一个用户的数据)
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("selectUserPage")
	@ResponseBody
	public ResultData<List<Object>> selectUserPage(@RequestBody JSONObject json) {

		ResultData<List<Object>> re = new ResultData<>();
		List<Object> arrayList = new ArrayList<>();

		int userId = json.getInt("userId");

		Map<String, Object> map = null;

		Map<String, Object> dmap = new LinkedHashMap<>();

		map = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		User user = userService.getUser(userId);
		String name = user.getName();
		Integer id = user.getId();
		String phone = user.getPhone();
		String avatar = user.getAvatar();
		map.put("userId", id);
		map.put("name", name);
		map.put("phone", phone);
		map.put("avatar", avatar);
		map.put("calibration", user.getCalibration());
		SimpleDateFormat updatetimedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String updatetime = updatetimedf.format(new Date());
		map.put("updatetime", updatetime);
		dmap.put("user", map);

		Health health = healthService.getHealthByUserId(userId);
		if (health == null) {
			health = new Health();
		}

		map = new HashMap<String, Object>();
		map.put("name", "heartrate");
		map.put("desc", "心率");
		map.put("category", "2");
		map.put("lastestValue", health.getHeartRate() == null ? "" : health.getHeartRate());
		map.put("unit", "次/分");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "pressure");
		map.put("desc", "血压");
		map.put("category", "3");

		map.put("lastestValue",
				health.getHighBloodPressure() == null ? ""
						: health.getHighBloodPressure() + "/" + health.getLowBloodPressure() == null ? ""
								: health.getLowBloodPressure());
		map.put("unit", "mmHg");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "hrv");
		map.put("desc", "心率变异性HRV");
		map.put("category", "8");
		map.put("lastestValue", health.getHrv() == null ? "" : health.getHrv());
		map.put("unit", "ms");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "mocrocirculation");
		map.put("desc", "微循环");
		map.put("category", "4");
		map.put("lastestValue", health.getMicrocirculation() == null ? "" : health.getMicrocirculation());
		map.put("unit", "%");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "qxygen");
		map.put("desc", "血氧");
		map.put("category", "10");
		map.put("lastestValue", health.getBloodOxygen() == null ? "" : health.getBloodOxygen());
		map.put("unit", "%");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "breathe");
		map.put("desc", "呼吸");
		map.put("category", "12");
		map.put("lastestValue", health.getRespirationrate() == null ? "" : health.getRespirationrate());
		map.put("unit", "次/分钟");
		list.add(map);


		dmap.put("health", list);
		dmap.put("amedicalreport", health.getAmedicalreport() == null ? "" : health.getAmedicalreport());
		dmap.put("waveform", health.getWaveform() == null ? "" : health.getWaveform());
		arrayList.add(dmap);

		re.setCode(200);
		re.setMessage("获取个人首页");
		re.setData(arrayList);
		return re;
	}

	/**
	 * 邀请用户
	 * 
	 * @param relation
	 * @return
	 */
	@RequestMapping("invitationUser")
	@ResponseBody
	public ResultBase invitationUser(@RequestBody InvitationList il) {

		ResultBase re = new ResultBase();
		// 其他用户的手机号
		String phone = il.getPhone();
		// 我自己的id
		Integer userId = il.getUserId();

		User user = userService.selectUserByAccount(phone);

		if (user == null) {
			invitationListService.insert(il);
			re.setCode(350);
			re.setMessage("邀请成功,该手机号还未注册,注册后加入关注列表");

		} else {
			if (user.getId().equals(userId)) {
				re.setCode(350);
				re.setMessage("不能邀请自己的手机号");
			} else {
				Relation relation = new Relation();
				relation.setUserId(user.getId());
				relation.setObserveId(userId);

				Relation selectRelation = relationService.selectRelation(relation);
				if (selectRelation == null) {
					Push push = new Push();
					push.setUserId(userId);
					push.setAlias(user.getId());
					push.setAllNotifyOn(1);
					push.setHeartHigThd(100);
					push.setHeartLowThd(55);
					push.setHbpstart(80);
					push.setHbpend(140);
					push.setLbpstart(60);
					push.setLbpend(100);
					push.setCreateTime(new Date());
					pushService.insertSelective(push);
					relationService.insert(relation);
					re.setCode(200);
					re.setMessage("邀请成功");
				} else {
					re.setCode(200);
					re.setMessage("邀请成功");
				}
			}
		}
		return re;
	}

	/**
	 * 添加关注对象
	 * 
	 * @param relation
	 * @return
	 */
	@RequestMapping("addRelation")
	@ResponseBody
	public ResultBase addRelation(@RequestBody Relation relation) {

		
		ResultBase re = new ResultBase();
		Integer observeId = relation.getObserveId();
		User user = userService.getUser(observeId);
		
		if(user!=null){
		
		Relation selectRelation = relationService.selectRelation(relation);
		if (selectRelation == null) {
			Push push = new Push();
			push.setUserId(relation.getObserveId());
			push.setAlias(relation.getUserId());
			push.setAllNotifyOn(1);
			push.setHeartHigThd(100);
			push.setHeartLowThd(55);
			push.setHbpstart(80);
			push.setHbpend(140);
			push.setLbpstart(60);
			push.setLbpend(100);
			push.setCreateTime(new Date());
			pushService.insertSelective(push);
			relationService.insert(relation);
			re.setCode(200);
			re.setMessage("操作成功");
		} else {
			re.setCode(350);
			re.setMessage("不能重复关注");
		}
		}else{
			re.setCode(350);
			re.setMessage("请检查二维码");
		}

		return re;
	}

	/**
	 * 关注人列表
	 */
	@RequestMapping("relationList")
	@ResponseBody
	public ResultData<Map<String, Object>> relationList(@RequestBody JSONObject json) {

		ResultData<Map<String, Object>> re = new ResultData<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		int userId = json.getInt("userId");// 我关注
		int observeId = userId;// 关注我的

		// 我关注
		List<JSONObject> selectMyAttention = relationService.selectMyAttention(userId);
		map.put("MyAttention", selectMyAttention);
		// 关注我的
		List<JSONObject> AttentionMy = relationService.selectAttentionMy(observeId);
		map.put("AttentionMy", AttentionMy);
		re.setData(map);
		re.setCode(200);
		re.setMessage("获取关注列表");
		return re;
	}

	/**
	 * 个人资料
	 */
	@RequestMapping("getUserInfo")
	@ResponseBody
	public ResultData<User> getUserInfo(@RequestBody JSONObject json) {

		ResultData<User> re = new ResultData<>();
		User user2 = userService.getUser(json.getInt("userId"));
		user2.setPassword("");
		re.setCode(200);
		re.setMessage("获取用户信息");
		re.setData(user2);
		return re;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param u
	 * @return
	 */
	@RequestMapping("updateUser")
	@ResponseBody
	public ResultBase updateUser(@RequestBody JSONObject json) {
		ResultBase re = new ResultBase();
		re.setCode(200);
		re.setMessage("修改成功");
		
		int userId = json.getInt("userId");
		json.remove("userId");
		String born = json.optString("born");
		String coordinate = json.optString("coordinate");
		User user = (User)JSONObject.toBean(json, User.class);
		try {
			if(born!=null){
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); //加上时间
				Date date = sDateFormat.parse(born);
				user.setBorn(date);
			}
			if(coordinate!=null){
				Positionig p = new Positionig();
				p.setUserId(userId);
				p.setCratetime(new Date());
				p.setCoordinate(coordinate);
				positionigService.insertSelective(p);
			}
		} catch (ParseException e) {
		}
		
		user.setId(userId);

		boolean status = userService.updateUser(user);
		if (!status) {
			re.setMessage("修改失败");
			re.setCode(400);
		}
		return re;
	}

	/**
	 * \ 忘记密码,重置密码
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("recoverpassword")
	@ResponseBody
	public ResultBase recoverpassword(@RequestBody JSONObject m) {
		ResultBase re = new ResultBase();
		String phone = m.getString("phone");
		String code = m.getString("code");
		String password = m.getString("password");
		if (phone != null && phone != "" && password != null && password != "") {
			UserCode userCode = new UserCode();
			userCode.setCode(code);
			userCode.setPhone(phone);
			if (userCodeService.checkCode(userCode)) {
				User u = userService.selectUserByAccount(phone);
				if (u != null) {
					u.setPassword(MD5Util.MD5(password));
					userService.updateUser(u);
					re.setCode(200);
					re.setMessage("操作成功");
				}else {
					re.setCode(350);
					re.setMessage("帐号不存在,请重新注册");
				}
			} else {
				re.setCode(350);
				re.setMessage("验证码错误");
			}
		} else {
			re.setCode(350);
			re.setMessage("密码不能为空");
		}
		return re;
	}
	/**
	 * 上传健康数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addHealth")
	@ResponseBody
	public ResultData<ResultBase> addHealth(@RequestBody JSONObject json){
		ResultData<ResultBase> re = new ResultData<ResultBase>();
		String waveform = json.getString("waveform");
		Integer userId = json.getInt("userId");
		User user = userService.getUser(userId);
		logger.info("userId:"+userId+">>>名字:>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getName());
		logger.info("userId:"+userId+">>>waveform:>>>>"+json.getString("waveform"));
		if(!waveform.equals("")){
			Healthdao healthdao = healthdaoService.getHealthdaoByUserId(userId);
			
			re=HealthtoolUtils.addT14Health(json, user,healthdao,userService,healthdaoService, healthService,re,healthsService);
		}else{
			String data=json.getString("data");
			byte[] fromBASE64 = Base64Utils.decodeFromString(data);
			System.out.println(Arrays.toString(fromBASE64));
			int headA = fromBASE64[3];//A0	//48:0  49:1 A0:0  A1:1
			int headB = fromBASE64[5];//0
			int clothC = fromBASE64[23];//A1
			int clothD = fromBASE64[25];//0
			if(headA==48){
				if(headB==48){
					re.setMessage("检测到传感器被拔除");
				}else if(clothC==49){
					if(clothD==48){
						re.setMessage("检测到传感器未穿戴");
					}else{
						re.setMessage("采集数据失败,请检查传感器");
					}
				}
			}
			re.setCode(400);
		}
		return re;
	}
	/**
	 * 查询校准数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectHealthdao")
	@ResponseBody
	public ResultData<Healthdao> selectHealthdao(@RequestBody Healthdao healthdao){
		
		ResultData<Healthdao> re = new ResultData<Healthdao>();
		Integer userId = healthdao.getUserId();
		Healthdao dao = healthdaoService.getHealthdaoByUserId(userId);
		re.setData(dao);
		re.setCode(200);
		re.setMessage("操作成功");
		return re;
	}
	/**
	 * 更新校准数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateHealthdao")
	@ResponseBody
	public ResultBase updateHealthdao(@RequestBody Healthdao healthdao){
		
		ResultBase re = new ResultBase();
		healthdao.setCreatetime(new Date());
		healthdaoService.updateHealthdaoByUserId(healthdao);
		re.setCode(200);
		re.setMessage("操作成功");
		return re;
	}
	/**
	 * 更新预警数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updatePush")
	@ResponseBody
	public ResultBase updatePush(@RequestBody Push push){
		
		ResultBase re = new ResultBase();
		push.setUpdateTime(new Date());
		pushService.updatePush(push);
		re.setCode(200);
		re.setMessage("操作成功");
		return re;
	}
	/**
	 * 查询预警数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectPush")
	@ResponseBody
	public ResultData<Push> selectPush(@RequestBody Push push){
		
		ResultData<Push> re = new ResultData<Push>();
		Push pu = pushService.selectPushByAliasAndUserId(push);
		re.setCode(200);
		re.setData(pu);
		re.setMessage("操作成功");
		return re;
	}
	/**
	 * 删除关联关系
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delectRelation")
	@ResponseBody
	public ResultBase delectRelationAttentionMy(@RequestBody JSONObject json){
		
		ResultBase re = new ResultBase();
		Integer type = json.getInt("type");
		Integer userId = json.getInt("userId");
		String phone = json.getString("phone");
		
		User user = userService.selectUserByAccount(phone);
		Relation relation = new Relation();
		//type==1就是删除我关注的,type==2就是关注我的
		if(type==1){
			relation.setUserId(user.getId());
			relation.setObserveId(userId);
		}else{
			relation.setUserId(userId);
			relation.setObserveId(user.getId());
		}
		//删除关联关系
		relationService.deleteRelation(relation);
		//删除推送开关push
		Push push = new Push();
		push.setAlias(relation.getUserId());
		push.setUserId(relation.getObserveId());
		pushService.deletePush(push);
		
		
		re.setCode(200);
		re.setMessage("操作成功");
		return re;
	}

	/**
	 * 协议页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "agreement")
	public ModelAndView agreement() {
		ModelAndView mo = new ModelAndView();
		mo.setViewName("agreement");
		return mo;
	}
	/**
	 * 修改目标步数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateWalkCount")
	@ResponseBody
	public ResultBase updateWalkCount(@RequestBody Map<String,Object> map){
		ResultBase re = new ResultBase();
		try {
			re=userService.updateWalkCount(map,re);
			
		} catch (Exception e) {
			logger.error("updateWalkCount>>>>>>>>>>>>>>",e);
		}
		return re;
	}
	

}
