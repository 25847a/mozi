package cn.mozistar.configurer;

import java.util.Arrays;
import org.springframework.util.Base64Utils;
public class Sssss {

	public static void main(String[] args) {
		
		while(true){
			try {
				Thread.sleep(500);
				test();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		}
	//	String a ="7Ozw6Ojs6PD4/QD86NC0kGxYZFRAKBPns39PGu7OoopmPhoCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICEiI+Yo6y2vsTHy8/T1dvd4uPl5ufp6ezs8PX2+/4ABAcKDxIXHSAhIiQkJicqLzEzNDU3Nzo+QUVJS05OTExLTU9RUE1HPzMpHRYPCAP++fz17eDYzcK4sKWelo2DgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAipiksLjAxsvP1Nzi6Ovv8O7x8/X6AAMFCgsQERUXHSAiIiUmJyorMjg+Q0xQVVlaXV5eX11fXV9dYmNlZmZlZmBfW1dUTUpDPjcxJyEXBfrw49jLwLato5uSi4OAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIOKlZ6os7/J0tfb29zc29zg5urw8/n8/v4AAAIJDxYcIiMjIh8hISInKy80O0FGTk9SU1VTU1NQUlFUVVhaWFhUUExHRT88NjAoHxYQBgD89Ozk2c3As6mdlIqDgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIC";
	//	byte[] fromBASE64 = Base64Utils.decodeFromString(a);
	//	System.out.println(Arrays.toString(fromBASE64));
	}
	
	
	public static void test(){
		 //心率校准值
    	double heartrdao = 87;
		// 高压值校准值
    	double highpressure = 110;
		// 低压校准值
    	double lowpressure = 75;
		// 心率 检测值
		 double hear = (double) (20+Math.random()*(150-20+1));
		 double gy =(double) (10+Math.random()*(180-10+1));
		 double dy =(double) (10+Math.random()*(180-10+1));
		 
		 double hear1 = hear;
		 double gy1 =gy;
		 double dy1=gy;
		 
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
					 hear =heartrdao*(105+Math.random()*(115-105+1))/100;
					 gnum=(int)(105+Math.random()*(115-105+1));
					 dnum=(int)(105+Math.random()*(115-105+1));
				 }
			  highpressure=(int) (highpressure*gnum/100);
			  lowpressure = (int) (lowpressure*dnum/100);
			  }else{
				  hear =heartrdao*(98+Math.random()*(102-98+1))/100;
				  num=(int)(98+Math.random()*(102-98+1));;
				  highpressure=(int) (highpressure*num/100);
				  lowpressure = (int) (lowpressure*num/100);
			  }
		 System.out.println("心率值："+(int)hear+":"+(int)hear1+"****"+"高压："+highpressure+":"+(int)gy1+"****"+"低压："+lowpressure+":"+(int)dy1);
	}
}
