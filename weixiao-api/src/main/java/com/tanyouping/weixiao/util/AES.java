package com.tanyouping.weixiao.util;


import com.tanyouping.weixiao.exception.SystemException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES {

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encrypt(String content, String password) {
		return parseByte2HexStr(encryptToByte(content, password));
	}
	public static byte[] encryptToByte(String content, String password) {
		try {
			byte [] byteContent = content.getBytes("utf-8");
			return encryptToByte(byteContent, password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SystemException("加密失败", e);
		}
	}
	public static byte[] encryptToByte(byte[] byteContent, String password) {
		try {
			SecretKeySpec key = getSecretKeySpec(password);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			return cipher.doFinal(byteContent);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new SystemException("加密失败", t);
		}
	}
	
	private static SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password.getBytes());
		keyGen.init(128, secureRandom);
		SecretKey secretKey = keyGen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		return new SecretKeySpec(enCodeFormat, "AES");
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(String content, String password) {
		return new String(decryptToByte(content, password));
	}
	public static byte[] decryptToByte(String content, String password) {
		byte[] decryptContent = parseHexStr2Byte(content);
		return decryptToByte(decryptContent, password);
	}
	
	public static byte[] decryptToByte(byte[] decryptContent, String password) {
		try {
			SecretKeySpec key = getSecretKeySpec(password);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			return cipher.doFinal(decryptContent);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new SystemException("解密失败", t);
		}
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String UID = UUIDGenerator.generate("");
		String str = "123456789";
		System.out.println("key：" + UID);
		System.out.println("原内容：" + str);
		String encrypt = encrypt(str, UID);
		System.out.println("加密后：" + encrypt + ", len=" + encrypt.length());
		System.out.println("解密后："
				+ decrypt(encrypt, UID));

	}

}
