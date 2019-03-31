package txte;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class Tecs {
	
	private static Map<String, String> cacheMap = new HashMap<String, String>(); 
	
	/**
     * ��ʼ������
     * 
     * @param account
     */
	public static void add(String account,String acc) {
        // һ���ǽ������ݿ��ѯ������ѯ�Ľ�����л���
        cacheMap.put(account, acc);
    }
    
    /**
	 * ����imei,��ȡSocketChannel
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
