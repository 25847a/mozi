package cn.mozistar.configurer;


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
    	double heartrdao = 90;
		// 高压值校准值
		Integer highpressure = 140;
		// 低压校准值
		Integer lowpressure = 100;
		// 心率 检测值
		 double hear =0;// (double) (60+Math.random()*(140-60+1));
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
					 hear =hear*(97+Math.random()*(100-97+1))/100;
					 num=(int)(97+Math.random()*(100-97+1));;
				 }
			   highpressure=(int) (highpressure*num/100);
			   lowpressure = (int) (lowpressure*num/100);
		  }else if(hear>=heartrdao){
			  double sum =(double)hear/(double)heartrdao*100;
			   if(100<=sum && sum<104){
				   num = (int) (100+Math.random()*(102-100+1));
				 }else if(104<=sum && sum<108){
					 num = (int) (103+Math.random()*(104-103+1));
				 } else if(108<=sum && sum<112){
					 num = (int) (105+Math.random()*(106-105+1));
				 }else if(112<=sum && sum<116){
					 num = (int) (107+Math.random()*(108-107+1));
				 }else if(116<=sum && sum<120){
					 num = (int) (107+Math.random()*(108-107+1));
				 }else if(120<=sum && sum<124){
					 num = (int) (109+Math.random()*(110-109+1));
				 }else if(124<=sum && sum<128){
					 num = (int) (110+Math.random()*(112-110+1));
				 }else if(128<=sum && sum<132){
					 num = (int) (113+Math.random()*(114-113+1));
				 }else if(132<=sum && sum<136){
					 num = (int) (115+Math.random()*(116-115+1));
				 }else if(136<=sum && sum<140){
					 num = (int) (117+Math.random()*(118-117+1));
				 }else if(140<=sum && sum<144){
					 num = (int) (119+Math.random()*(120-119+1));
				 }else if(144<=sum && sum<148){
					 num = (int) (121+Math.random()*(122-121+1));
				 }else if(148<=sum && sum<152){
					 num = (int) (123+Math.random()*(124-123+1));
				 }else if(152<=sum && sum<156){
					 num = (int) (125+Math.random()*(127-125+1));
				 }else if(156<=sum && sum<158){
					 num = (int) (128+Math.random()*(130-128+1));
				 }else{
					 hear =hear*(100+Math.random()*(104-100+1))/100;
					 num=(int)(100+Math.random()*(104-100+1));;
				 }
			  highpressure=(int) (highpressure*num/100);
			  lowpressure = (int) (lowpressure*num/100);
			  System.out.println("心率值："+(int)hear+"****"+"高压："+highpressure+"****"+"低压："+lowpressure);
		  }else{
			  hear =heartrdao*(98+Math.random()*(102-98+1))/100;
			  num=(int)(98+Math.random()*(102-98+1));;
			  highpressure=(int) (highpressure*num/100);
			  lowpressure = (int) (lowpressure*num/100);
			  System.out.println("心率值："+(int)hear+"****"+"高压："+highpressure+"****"+"低压："+lowpressure);
		  }
	}
}
