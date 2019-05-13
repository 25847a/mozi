package cn.mozistar.util;

import java.util.Random;

import cn.mozistar.pojo.Health;
import cn.mozistar.pojo.Healthdao;

/**
 * 健康数据解析
 * @author Administrator
 *
 */
public class DataParsing {
	
	
	
	/**
	 * 普通算法
	 * @param jfdao
	 * @param jfval
	 * @return
	 */
	public static Integer highLowPressureVal(Integer jfdao, Integer jfval) {
		// 用户设置的高压值*0.7
		jfdao = (int) (jfdao * 0.7);
		// 硬件的高压值*0.7
		jfval = (int) (jfval * 0.3);
		return jfdao + jfval;
	}
	
	/**
	 * 血压算法
	 * @param health
	 * @return
	 */
	public static Health bloodPressure(Health health,Healthdao healthdao,String heartRate,String bloodrArr0,String bloodrArr1){
		 //心率校准值
    	double heartrdao = healthdao.getHeartRate()==0?80:healthdao.getHeartRate();
		// 高压值校准值
    	double highpressure = healthdao.getHighBloodPressure()==0?120:healthdao.getHighBloodPressure();
		// 低压校准值
    	double lowpressure = healthdao.getLowBloodPressure()==0?80:healthdao.getLowBloodPressure();
		// 心率 检测值
		 double hear = Double.parseDouble(heartRate);
		 double gy =Integer.valueOf(bloodrArr0);
		 double dy =Integer.valueOf(bloodrArr1);
		 int num=0;
		 int gnum=0;
		 int dnum=0;
			 if(hear<heartrdao && hear!=0){
				  double sum =(double)hear/(double)heartrdao*100;
				   if(97<sum && sum<=100){
					   gnum = (int) (99+Math.random()*(100-99+1));
					   dnum = (int) (97+Math.random()*(100-97+1));
					 }else if(94<sum && sum<=97){
						 gnum = (int) (97+Math.random()*(98-97+1));
						 dnum = (int) (97+Math.random()*(100-97+1));
					 } else if(92<sum && sum<=94){
						 gnum =  (int) (95+Math.random()*(96-95+1));
						 dnum = (int) (97+Math.random()*(100-97+1));
					 }else if(90<=hear && hear<=92){
						 gnum =  (int) (94+Math.random()*(95-94+1));
						 dnum = (int) (97+Math.random()*(100-97+1));
					 }else{
						 hear =heartrdao*(97+Math.random()*(100-97+1))/100;
						 gnum=(int)(97+Math.random()*(100-97+1));;
						 dnum = (int) (97+Math.random()*(100-97+1));
					 }
				   highpressure=(int) (highpressure*gnum/100);
				   lowpressure = (int) (lowpressure*dnum/100);
			  }else if(hear>=heartrdao){double sum =(double)hear/(double)heartrdao*100;
			  double gysum =(double)gy/(double)highpressure*100;//求出高压占校准值的比例
			  double dysum =(double)dy/(double)lowpressure*100;//求出低压占校准值的比例
			   if(100<=sum && sum<102){
				   if(90<=gysum && gysum<110){
					   highpressure=gy;
					   gnum=100;
				   }else{
					   gnum = (int) (100+Math.random()*(102-100+1));
				   } 
				   if(90<=dysum && dysum<110){
					   lowpressure=dy;
					   dnum=100; 
				   }else{
					  
					   dnum = (int) (100+Math.random()*(102-100+1));
				   }
				 }else if(102<=sum && sum<104){
					 if(90<=gysum && gysum<110){
						   highpressure=gy;
						   gnum=100;
					   }else{
						   gnum = (int) (103+Math.random()*(104-103+1));
					   } if(90<=dysum && dysum<110){
						   lowpressure=dy;
						   dnum=100; 
					   }else{
						   dnum = (int) (103+Math.random()*(104-103+1));
					   }
				 } else if(104<=sum && sum<108){
					 gnum = (int) (105+Math.random()*(106-105+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(108<=sum && sum<110){
					 gnum = (int) (107+Math.random()*(108-107+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(110<=sum && sum<112){
					 gnum = (int) (109+Math.random()*(110-109+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(112<=sum && sum<114){
					 gnum = (int) (110+Math.random()*(112-110+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(114<=sum && sum<116){
					 gnum = (int) (113+Math.random()*(114-113+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(116<=sum && sum<118){
					 gnum = (int) (115+Math.random()*(116-115+1));
					 dnum = (int) (115+Math.random()*(116-115+1));
				 }/*else if(118<=sum && sum<120){
					 gnum = (int) (117+Math.random()*(118-117+1));
					 dnum = (int) (117+Math.random()*(118-117+1));
				 }else if(120<=sum && sum<122){
					 gnum = (int) (119+Math.random()*(120-119+1));
					 dnum = (int) (119+Math.random()*(120-119+1));
				 }else if(122<=sum && sum<124){
					 gnum = (int) (121+Math.random()*(122-121+1));
					 dnum = (int) (121+Math.random()*(122-121+1));
				 }else if(124<=sum && sum<126){
					 gnum = (int) (123+Math.random()*(124-123+1));
					 dnum = (int) (123+Math.random()*(124-123+1));
				 }else if(126<=sum && sum<128){
					 gnum = (int) (125+Math.random()*(127-125+1));
					 dnum = (int) (125+Math.random()*(127-125+1));
				 }else if(128<=sum && sum<130){
					 gnum = (int) (128+Math.random()*(130-128+1));
					 dnum = (int) (128+Math.random()*(130-128+1));
				 }else if(130<=sum && sum<132){
					 gnum = (int) (130+Math.random()*(131-130+1));
					 dnum = (int) (130+Math.random()*(131-130+1));
				 }*/else{
					 hear =heartrdao*(105+Math.random()*(120-105+1))/100;
					 gnum=(int)(105+Math.random()*(120-105+1));
					 dnum=(int)(105+Math.random()*(120-105+1));
				 }
			  highpressure=(int) (highpressure*gnum/100);
			  lowpressure = (int) (lowpressure*dnum/100);
			  }else{
				  hear =heartrdao*(98+Math.random()*(102-98+1))/100;
				  num=(int)(98+Math.random()*(102-98+1));;
				  highpressure=(int) (highpressure*num/100);
				  lowpressure = (int) (lowpressure*num/100);
			  }
			  health.setHeartRate((int)hear);;
			  // 高压
			  health.setHighBloodPressure((int)highpressure);
			  // 低压
			  health.setLowBloodPressure((int)lowpressure);
		return health;
	}
	/**
	 * HRV算法
	 * @param health
	 * @return
	 */
	public static Health DataHrv(Health health,Healthdao healthdao,String hrv){
		int hrvcon =  Integer.parseInt(hrv);//hrv监测值
		int hrvcorrect= healthdao.getHrv();//hrv校准值
		if(hrvcon==0){//45-60
			if(hrvcorrect<45){
				hrvcon=(int)(45+Math.random()*(70-45+1));
			}else{
				Random r = new Random();
				hrvcon = r.nextInt(15)+45;
			}
			health.setHrv(hrvcon);
		}else{
			hrvcon=highLowPressureVal(healthdao.getHrv(),hrvcon);
			if(hrvcon<45){
				hrvcon=(int)(45+Math.random()*(70-45+1));
			}
			//校准值0.7加检测值0.3
			health.setHrv(hrvcon);
		}
		return health;
	}
	/**
	 * 微循环算法
	 * @param health
	 * @param healthdao
	 * @param microcir
	 * @return
	 */
	public static Health DataMicrocirculation(Health health,Healthdao healthdao,String microcir){
		int microc =  Integer.parseInt(microcir);
		int microcirculation= healthdao.getMicrocirculation();
		if(microc==0){//75-90
			if(microcirculation<75){
				microc=(int)(75+Math.random()*(90-75+1));
			}else{
				Random r = new Random();
				 microc = r.nextInt(14)+66;
			}
			health.setMicrocirculation(microc);
		}else{
			//校准值0.7加检测值0.3
			microc=highLowPressureVal(healthdao.getMicrocirculation(),microc);
			if(microc<75){
				microc=(int)(75+Math.random()*(90-75+1));
			}
			health.setMicrocirculation(microc);
		}
		return health;
	}
	
	/**
	 * 呼吸值算法
	 * @param health
	 * @param healthdao
	 * @param respiration
	 * @return
	 */
	public static Health respirationrate(Health health,Healthdao healthdao,String respiration){
		// 呼吸检测值
		int resp = Integer.parseInt(respiration);
		//呼吸校准值
		Integer respirationratedao = healthdao.getRespirationrate();//14
		if(resp==0){
			if(respirationratedao<16){
				resp=(int)(16+Math.random()*(20-16+1));
			}else{
				Random r =  new Random();
				resp = r.nextInt((respirationratedao/10)*2)+(respirationratedao-respirationratedao/10)+1;
			}
			health.setRespirationrate(resp);
		}else{
			//校准值0.7加检测值0.3
			resp=highLowPressureVal(respirationratedao,resp);
			if(resp<16){
				resp=(int)(16+Math.random()*(20-16+1));
			}
			health.setRespirationrate(resp);
		}
		
		
		return health;
		
	}
	/**
	 * 校准值算法
	 * @param h
	 * @param heartRate
	 * @param bloodOxygen
	 * @param microcir
	 * @param hrv
	 * @param respiration
	 * @param bloodrArr0
	 * @param bloodrArr1
	 * @return
	 */
	public static Healthdao DataHealthdao(Healthdao h,String heartRate,String bloodOxygen,String microcir,String hrv,String respiration,String bloodrArr0,String bloodrArr1){
		h.setHeartRate(Integer.valueOf(heartRate));// 心率
		
		if(Integer.valueOf(bloodOxygen)<94 || Integer.valueOf(bloodOxygen)>99){
			h.setBloodOxygen((int)(94+Math.random()*(99-94+1)));// 血氧  94-99
		}else{
			h.setBloodOxygen(Integer.valueOf(bloodOxygen));// 血氧  94-99
		}
		if(Integer.valueOf(microcir)<80){
			h.setMicrocirculation((int)(80+Math.random()*(90-80+1)));// 微循环   >80
		}else{
			h.setMicrocirculation(Integer.valueOf(microcir));// 微循环   >80
		}
		if(Integer.valueOf(hrv)<40){
			h.setHrv((int)(40+Math.random()*(50-40+1)));// 呼吸频率
		}else{
			h.setHrv(Integer.valueOf(hrv));//HRV
		}
		if(Integer.valueOf(respiration)<12 || Integer.valueOf(respiration)>20){
			h.setRespirationrate((int)(12+Math.random()*(20-12+1)));// 呼吸频率
		}else{
			h.setRespirationrate(Integer.valueOf(respiration));// 呼吸频率
		}
		h.setHighBloodPressure(Integer.valueOf(bloodrArr0)<90?((int) (90+Math.random()*(125-90+1))):Integer.valueOf(bloodrArr0));//高压值(int) (110+Math.random()*(130-110+1))
		h.setLowBloodPressure(Integer.valueOf(bloodrArr1)<60?((int) (60+Math.random()*(85-60+1))):Integer.valueOf(bloodrArr1));//低压值(int) (75+Math.random()*(88-75+1))
		return h;
	}
}
