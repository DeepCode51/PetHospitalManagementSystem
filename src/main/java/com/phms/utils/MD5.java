package com.phms.utils;

import org.springframework.util.DigestUtils;

/**
 * class name:MD5 <BR>
 * class description: please write your description <BR>
 */
public class MD5 {
	private MD5() {
		throw new IllegalStateException("MD5 Utility class");
	}

	/**
	 * Method name: md5 <BR>
	 * Description: 加密密码 <BR>
	 * Remark: <BR>
	 * 
	 * @param text 明文
	 * @return String 密文<BR>
	 */
	public static String md5(String text) {
		// 加密后的字符串
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	/**
	 * Method name: verify <BR>
	 * Description: 验证密码是否正确 <BR>
	 * Remark: <BR>
	 * 
	 * @param text 明文
	 * @param md5  密文
	 * @return boolean 结果<BR>
	 */
	public static boolean verify(String text, String md5) {
		// 根据传入的密钥进行验证
		String md5Text = md5(text);
		return md5Text.equals(md5);
	}
}
