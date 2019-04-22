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
	}
	
	
	public static void test(){
		 //心率校准值
    	double heartrdao = 88;
		// 高压值校准值
    	double highpressure = 134;
		// 低压校准值
    	double lowpressure = 80;
		// 心率 检测值
		 double hear =88;//(double) (50+Math.random()*(140-50+1));
		 double gy =130;
		 double dy =78;
		 int gnum=0;
		 int dnum=0;
		  if(hear<heartrdao && hear!=0){
			  double sum =(double)hear/(double)heartrdao*100;
			   if(97<sum && sum<=100){
				   gnum = (int) (97+Math.random()*(100-97+1));
				   dnum = (int) (97+Math.random()*(100-97+1));
				 }else if(94<sum && sum<=97){
					 gnum = (int) (94+Math.random()*(96-94+1));
					 dnum = (int) (97+Math.random()*(100-97+1));
				 } else if(92<sum && sum<=94){
					 gnum =  (int) (92+Math.random()*(93-92+1));
					 dnum = (int) (97+Math.random()*(100-97+1));
				 }else if(90<=hear && hear<=92){
					 gnum =  (int) (90+Math.random()*(91-90+1));
					 dnum = (int) (97+Math.random()*(100-97+1));
				 }else{
					 hear =hear*(97+Math.random()*(100-97+1))/100;
					 gnum=(int)(97+Math.random()*(100-97+1));;
					 dnum = (int) (97+Math.random()*(100-97+1));
				 }
			   highpressure=(int) (highpressure*gnum/100);
			   lowpressure = (int) (lowpressure*dnum/100);
		  }else if(hear>=heartrdao){
			  double sum =(double)hear/(double)heartrdao*100;
			  double gysum =(double)highpressure/(double)gy*100;//求出高压占校准值的比例
			  double dysum =(double)lowpressure/(double)dy*100;//求出低压占校准值的比例
			   if(100<=sum && sum<104){
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
				 }else if(104<=sum && sum<108){
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
				 } else if(108<=sum && sum<112){
					 gnum = (int) (105+Math.random()*(106-105+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(112<=sum && sum<116){
					 gnum = (int) (107+Math.random()*(108-107+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(116<=sum && sum<120){
					 gnum = (int) (109+Math.random()*(110-109+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(120<=sum && sum<124){
					 gnum = (int) (110+Math.random()*(112-110+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(124<=sum && sum<128){
					 gnum = (int) (113+Math.random()*(114-113+1));
					 dnum = (int) (103+Math.random()*(104-103+1));
				 }else if(128<=sum && sum<132){
					 gnum = (int) (115+Math.random()*(116-115+1));
					 dnum = (int) (115+Math.random()*(116-115+1));
				 }else if(132<=sum && sum<136){
					 gnum = (int) (117+Math.random()*(118-117+1));
					 dnum = (int) (117+Math.random()*(118-117+1));
				 }else if(136<=sum && sum<140){
					 gnum = (int) (119+Math.random()*(120-119+1));
					 dnum = (int) (119+Math.random()*(120-119+1));
				 }else if(140<=sum && sum<144){
					 gnum = (int) (121+Math.random()*(122-121+1));
					 dnum = (int) (121+Math.random()*(122-121+1));
				 }else if(144<=sum && sum<148){
					 gnum = (int) (123+Math.random()*(124-123+1));
					 dnum = (int) (123+Math.random()*(124-123+1));
				 }else if(148<=sum && sum<152){
					 gnum = (int) (125+Math.random()*(127-125+1));
					 dnum = (int) (125+Math.random()*(127-125+1));
				 }else if(152<=sum && sum<156){
					 gnum = (int) (128+Math.random()*(130-128+1));
					 dnum = (int) (128+Math.random()*(130-128+1));
				 }else if(156<=sum && sum<158){
					 gnum = (int) (130+Math.random()*(131-130+1));
					 dnum = (int) (130+Math.random()*(131-130+1));
				 }else{
					 hear =hear*(100+Math.random()*(104-100+1))/100;
					 gnum=(int)(100+Math.random()*(104-100+1));
					 dnum=(int)(100+Math.random()*(104-100+1));
				 }
			  highpressure=(int) (highpressure*gnum/100);
			  lowpressure = (int) (lowpressure*dnum/100);
		  }else{
			  hear =heartrdao*(98+Math.random()*(102-98+1))/100;
			  gnum=(int)(98+Math.random()*(102-98+1));
			  dnum=(int)(98+Math.random()*(102-98+1));
			  highpressure=(int) (highpressure*gnum/100);
			  lowpressure = (int) (lowpressure*dnum/100);
		  }
		 System.out.println("心率值："+(int)hear+"****"+"高压："+highpressure+"****"+"低压："+lowpressure);
	}
}
