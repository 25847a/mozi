package com.fadl.common;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
/**
 * @author:jian
 * @Description:采用shiro内部加密算法对密码进行加密，算法采用MD5加密加盐方式
 * @Date:2019-03-27
 */
public class PasswordUtil {
	private static String hashAlgorithmName= "MD5";//加密方式
    private static int hashIterations=3;//加密3次
    
    public static  String encryptPassWord(String passWord,String account){
		return new SimpleHash(hashAlgorithmName,passWord,ByteSource.Util.bytes(account),hashIterations).toHex();
    }
    
    public static void main(String[] args) {
    System.out.println(new SimpleHash(hashAlgorithmName,"123456",ByteSource.Util.bytes("15989980774"),hashIterations).toHex());

	}
}
