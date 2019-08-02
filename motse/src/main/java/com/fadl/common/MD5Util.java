package com.fadl.common;

import java.security.MessageDigest;

import org.springframework.util.StringUtils;

public class MD5Util {
	/**
	 * MD5 加密
	 * @param s
	 * @return
	 */
	public static final String MD5(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * MD5 校验
	 * @param lockId
	 * @param password
	 * @param userId
	 * @param start
	 * @param end
	 * @param sign
	 * @param card
	 * @param currentTime
	 * @param useFlag
	 * @param addType
	 * @return
	 */
	public static final boolean validate(String lockId, String password, String userId, String start, String end,
			String sign, String card, String currentTime, String useFlag, String addType) {
		StringBuilder sb = new StringBuilder();
		sb.append("lockID=").append(lockId);
		if (StringUtils.hasText(password)) {
			sb.append("&password=").append(password);
		}
		if (StringUtils.hasText(card)) {
			sb.append("&card=").append(card);
		}
		if (StringUtils.hasText(currentTime)) {
			sb.append("&currentTime=").append(currentTime);
		}
		if (StringUtils.hasText(userId)) {
			sb.append("&userID=").append(password);
		}
		if (StringUtils.hasText(start)) {
			sb.append("&start=").append(start);
		}
		if (StringUtils.hasText(end)) {
			sb.append("&end=").append(end);
		}
		if (StringUtils.hasText(useFlag)) {
			sb.append("&useFlag=").append(useFlag);
		}
		if (StringUtils.hasText(addType)) {
			sb.append("&addType=").append(addType);
		}
		return sign.equals(sb.toString());
	}

	/**
	 * MD5 获取String
	 * @param lockId
	 * @param password
	 * @param userId
	 * @param start
	 * @param end
	 * @param sign
	 * @param card
	 * @param currentTime
	 * @param useFlag
	 * @param addType
	 * @return
	 */
	public static final String getString(String lockId, String password, String userId, String start, String end,
			String sign, String card, String currentTime, String useFlag, String addType) {
		StringBuilder sb = new StringBuilder();
		sb.append("lockID=").append(lockId);
		if (StringUtils.hasText(password)) {
			sb.append("&password=").append(password);
		}
		if (StringUtils.hasText(card)) {
			sb.append("&card=").append(card);
		}
		if (StringUtils.hasText(currentTime)) {
			sb.append("&currentTime=").append(currentTime);
		}
		if (StringUtils.hasText(userId)) {
			sb.append("&userID=").append(userId);
		}
		if (StringUtils.hasText(start)) {
			sb.append("&start=").append(start);
		}
		if (StringUtils.hasText(end)) {
			sb.append("&end=").append(end);
		}
		if (StringUtils.hasText(useFlag)) {
			sb.append("&useFlag=").append(useFlag);
		}
		if (StringUtils.hasText(addType)) {
			sb.append("&addType=").append(addType);
		}
		return sb.toString();

	}
	
	public static void main(String[] args) {
		String text =MD5("13794847828");
		System.out.println(text);
	}
}
