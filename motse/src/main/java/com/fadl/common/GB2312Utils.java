package com.fadl.common;

public class GB2312Utils {

	public static void main(String[] args) throws Exception {
		String str  = "AB中A文AB";
		String gb2312eecode = gb2312eecode(BCConvert.bj2qj(str));
		System.out.println(gb2312eecode);
		
	}
	
	
	
	/**
	    * gb2312解码 
	    
	 * @throws Exception */
	public static String gb2312eecode(String str) throws Exception {
		
		StringBuffer gbkStr = new StringBuffer();
		byte[] gbkDecode = BCConvert.bj2qj(str).getBytes("gb2312");
		for (byte b : gbkDecode) {
			String hexString = Integer.toHexString(b & 0xFF);
 		 gbkStr.append(hexString);
		}
		 return gbkStr.toString().toUpperCase();
		}
	
	/**
	    * gb2312编码 
	    */
	public static String gb2312decode(String str)throws Exception {
		
		byte[] bytes = new byte[str.length() / 2];
		for(int i = 0; i < bytes.length; i ++){
			 byte high = Byte.parseByte(str.substring(i * 2, i * 2 + 1), 16);
			 byte low = Byte.parseByte(str.substring(i * 2 + 1, i * 2 + 2), 16);
			 bytes[i] = (byte) (high << 4 | low);
		}
		String string = new String(bytes, "gb2312");
		return string;
	}

}
