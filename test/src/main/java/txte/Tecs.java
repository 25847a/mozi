package txte;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Tecs {
	
	private static Map<String, String> cacheMap = new HashMap<String, String>(); 
	
	/**
     * 初始化缓存
     * 
     * @param account
     */
	public static void add(String account,String acc) {
        // 一般是进行数据库查询，将查询的结果进行缓存
        cacheMap.put(account, acc);
    }
    
    /**
	 * 输入imei,获取SocketChannel
	 * @param clientId
	 * @return
	 */
	public static String get(String clientId) {
		return cacheMap.get(clientId);
	}
	
	public static void remove(String socketChannel) {
		for (Map.Entry entry : cacheMap.entrySet()) {
			if (entry.getValue() == socketChannel) {
				cacheMap.remove(entry.getKey());
			}
		}
	}

}
