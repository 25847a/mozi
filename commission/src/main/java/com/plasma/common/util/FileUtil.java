package com.plasma.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.plasma.common.ReadProperties;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {

	/** 
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！) 
     * 
     * @param res            原字符串 
     * @param filePath 文件路径 
     * @return 成功标记 
	 * @throws IOException 
     */ 
    public static void string2File(String data, String filePath) throws Exception { 
    	//对字节数组字符串进行Base64解码并生成图片
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	byte[] b = decoder.decodeBuffer(data);
		File file = new File(filePath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		//将流写入filePath中
		OutputStream os = new FileOutputStream(filePath);
		os.write(b);
		os.flush();
		os.close();
    }
    
    /**
	 * 根据路径转字节码
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);;
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);

	}
}
