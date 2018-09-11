package me.tanyp.util.md5;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static String encryptPassword(String pwd){
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(pwd.getBytes("UTF-8"));
			byte[] digest = alga.digest();
			return byte2hex(digest);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return "";
	}
	
	public static String encryptPasswordForBASE64(String pwd){
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(pwd.getBytes());
			byte[] digesta = alga.digest();
			BASE64Encoder base64Encoder = new BASE64Encoder(); // 初始化base64编码
			return base64Encoder.encode(byte2hex(digesta).getBytes());
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return "";
	}
	
	private static String byte2hex(byte[] b) { // 二行制转字符串
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = (Integer.toHexString(b[i] & 0XFF));// “&”是实现按位与运算
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) {
		String md5 =  encryptPassword("123456");
		System.out.println(md5);
		BASE64Encoder base64Encoder = new BASE64Encoder(); // 初始化base64编码
		String base64 = base64Encoder.encode(md5.getBytes());
		System.out.println(base64 + ": " + base64.length());
		String b64 = encryptPasswordForBASE64("123456");
		System.out.println(b64 + ": " + b64.length());
	}
}