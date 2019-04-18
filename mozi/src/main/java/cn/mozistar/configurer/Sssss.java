package cn.mozistar.configurer;

import java.util.Arrays;
import org.springframework.util.Base64Utils;
public class Sssss {

	public static void main(String[] args) {
		
		/*while(true){
			try {
				Thread.sleep(500);
				test();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		StringBuffer sb = new StringBuffer();
		byte[] fromBASE64 = Base64Utils.decodeFromString("JHxBMDoxDQoAAAAAAAAAAAAAAAAkfEExOjANCgAAAAAAAAAAAAAAAA==");
		
		System.out.println(Arrays.toString(fromBASE64));
		System.out.println(fromBASE64[3]);
		System.out.println(fromBASE64[5]);
		System.out.println(fromBASE64[23]);
		System.out.println(fromBASE64[25]);
	}
	
	
	public static void test(){
		 //心率校准值
    	double heartrdao = 88;
		// 高压值校准值
		Integer highpressure = 134;
		// 低压校准值
		Integer lowpressure = 80;
		// 心率 检测值
		 double hear =(double) (50+Math.random()*(140-50+1));
		 int num=0;
		 if(hear<heartrdao && hear!=0){
			  double sum =(double)hear/(double)heartrdao*100;
			   if(97<sum && sum<=100){
				   num =  (int) (97+Math.random()*(100-97+1));
				 }else if(94<sum && sum<=97){
					 num = (int) (94+Math.random()*(96-94+1));
				 } else if(92<sum && sum<=94){
					 num =  (int) (92+Math.random()*(93-92+1));
				 }else if(90<=hear && hear<=92){
					 num =  (int) (90+Math.random()*(91-90+1));
				 }else{
					 hear =heartrdao*(97+Math.random()*(100-97+1))/100;
					 num=(int)(97+Math.random()*(100-97+1));;
				 }
			   highpressure=(int) (highpressure*num/100);
			   lowpressure = (int) (lowpressure*num/100);
		  }else if(hear>=heartrdao){
			  double sum =(double)hear/(double)heartrdao*100;
			  if(100<=sum && sum<102){
				   num = (int) (100+Math.random()*(102-100+1));
				 }else if(102<=sum && sum<106){
					 num = (int) (103+Math.random()*(104-103+1));
				 } else if(106<=sum && sum<108){
					 num = (int) (105+Math.random()*(106-105+1));
				 }else if(110<=sum && sum<112){
					 num = (int) (107+Math.random()*(108-107+1));
				 }else if(112<=sum && sum<114){
					 num = (int) (109+Math.random()*(110-109+1));
				 }else if(114<=sum && sum<116){
					 num = (int) (110+Math.random()*(112-110+1));
				 }else if(116<=sum && sum<118){
					 num = (int) (113+Math.random()*(114-113+1));
				 }else if(118<=sum && sum<120){
					 num = (int) (115+Math.random()*(116-115+1));
				 }else if(122<=sum && sum<124){
					 num = (int) (117+Math.random()*(118-117+1));
				 }else if(126<=sum && sum<128){
					 num = (int) (119+Math.random()*(120-119+1));
				 }else if(128<=sum && sum<130){
					 num = (int) (121+Math.random()*(122-121+1));
				 }else if(130<=sum && sum<131){
					 num = (int) (123+Math.random()*(124-123+1));
				 }else if(131<=sum && sum<132){
					 num = (int) (125+Math.random()*(127-125+1));
				 }else if(132<=sum && sum<133){
					 num = (int) (128+Math.random()*(130-128+1));
				 }else if(133<=sum && sum<134){
					 num = (int) (130+Math.random()*(131-130+1));
				 }else{
					 hear =heartrdao*(125+Math.random()*(130-125+1))/100;//131   血压也给正常值
					 num=(int)(125+Math.random()*(130-125+1));;
				 }
			  highpressure=(int) (highpressure*num/100);
			  if(highpressure>=165){
				  highpressure=(int) (highpressure*0.9);
			  }
			  lowpressure = (int) (lowpressure*num/100);
			  if(lowpressure>=120){
				  lowpressure=(int) (lowpressure*0.9);
			  }
		  }else{
			  hear =heartrdao*(98+Math.random()*(102-98+1))/100;
			  num=(int)(98+Math.random()*(102-98+1));;
			  highpressure=(int) (highpressure*num/100);
			  lowpressure = (int) (lowpressure*num/100);
		  }
		 System.out.println("心率值："+(int)hear+"****"+"高压："+highpressure+"****"+"低压："+lowpressure);
	}
}
