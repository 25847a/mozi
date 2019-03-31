package cn.mozistar.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;


public class Uit {
    public static String  readBinary() throws Exception {  
    	  String src = "C:/Users/Administrator/Desktop/外包/data.bin";  
          String dec = "E:/data/data.txt";  
            String text =null;
          try {  
              FileInputStream in = new FileInputStream(src);  
              FileOutputStream out = new FileOutputStream(dec);  
              byte buffer[] = new byte[1024];  
                StringBuffer sb = new StringBuffer();
              int count,i;  
              while((count=in.read(buffer))!=-1){  
                  // for循环保证只写入count个byte, 否则会写入1024个byte  
                  for(i=0; i<count; i++){  
                	
                      out.write(buffer[i]);  
                      sb.append(buffer[i]);
                  }  
              }  
              System.out.println("得到的数据"+ Uit.encodeBase64(sb.toString().getBytes()));
              text=Uit.encodeBase64(sb.toString().getBytes());
              in.close();  
              out.close();  
    
          } catch (FileNotFoundException e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
          } catch (IOException e) {  
              // TODO Auto-generated catch block  
              e.printStackTrace();  
          }  
        return text;
      }  
    public static String readBinary1() throws Exception {
    	   String text =null;
        try {  
            DataInputStream is = new DataInputStream(  
                    new BufferedInputStream(new FileInputStream(  
                            "C:/Users/Administrator/Desktop/外包/data.bin")));  
            System.out.println(is.readByte());  
            byte buffer[] = new byte[1024];  
            StringBuffer sb = new StringBuffer();
          int count,i;  
          while((count=is.read(buffer))!=-1){  
              // for循环保证只写入count个byte, 否则会写入1024个byte  
              for(i=0; i<count; i++){  
                  sb.append(buffer[i]);
              }  
          }  
          System.out.println("得到的数据"+ Uit.encodeBase64(sb.toString().getBytes()));
          text=Uit.encodeBase64(sb.toString().getBytes());
          is.close();
        } catch (IOException e) {  
  
        }  
        return text;
    }  
    public static void main(String[] args) throws Exception {
    	readBinary1();
	}
    /*** 
     * encode by Base64 
     */  
    public static String encodeBase64(byte[]input) throws Exception{  
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
        Method mainMethod= clazz.getMethod("encode", byte[].class);  
        mainMethod.setAccessible(true);  
         Object retObj=mainMethod.invoke(null, new Object[]{input});  
         return (String)retObj;  
    }  
    /*** 
     * decode by Base64 
     */  
    public static byte[] decodeBase64(String input) throws Exception{  
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
        Method mainMethod= clazz.getMethod("decode", String.class);  
        mainMethod.setAccessible(true);  
         Object retObj=mainMethod.invoke(null, input);  
         return (byte[])retObj;  
    }  
	
}
