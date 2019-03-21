package DDD;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * ≤‚ ‘
 * @author Administrator
 *
 */
public class BluetoothMap {
   	//dddd
	public static  Map<String,String>   mapsb =new ConcurrentHashMap<String, String>();
	
	public static  Map<String,String>   healthmap =new ConcurrentHashMap<String, String>();
	public static void addBs(String text,String sb){
		mapsb.put(text,sb);
	}
	public static String getBs(String text){
		
		return mapsb.get(text);
	}
	
	public  static void deletesb(String key){
		mapsb.remove(key);
	}
	
	public static void addhealthmap(String text,String sb){
		healthmap.put(text,sb);
	}
	public static String gethealthmap(String text){
		
		return healthmap.get(text);
	}
	
	public  static void deletehealthmap(String key){
		healthmap.remove(key);
	}
	
}
