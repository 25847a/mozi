package com.plasma.common;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.plasma.common.util.MD5Util;


/**
 * 3DES加密工具
 * @author wangjing
 *
 */
public class Encrypt_DES {
			// 数据密钥
			 public final static String secretKey = "01378bddf7418d65d65a10f3071db8dc";
			// 密码密钥
			 public final static String passWordKey ="63e5621e652c766fb7f97d5df856c3bd";
			// 向量
			private final static String iv = "01234567";
			// 加解密?使用的编码方式?
			private final static String encoding = "utf-8";
		
		/**
		 * 3DES加密
		 * @param plainText 内容
		 * @return
		 * @throws Exception
		 */
		public static String encode(String plainText,String type) throws Exception {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(type.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede"); //实例化密钥工厂
			deskey = keyfactory.generateSecret(spec);//生成密钥

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64.encode(encryptData);
		}

	/**
	 * 3DES解密
	 * @param encryptText 加密文本
	 * @param key
	 * @return
	 * @throws Exception
	 */
		public static String decode(String encryptText,String key) throws Exception {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede"); //实例化密钥工厂
			deskey = keyfactory.generateSecret(spec);//生成密钥 
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

			return new String(decryptData, encoding);
		}
		
		public static void main(String[] args) throws Exception {
			System.out.println(MD5Util.Md5ByKey("bbbbbb"));
		}
}
