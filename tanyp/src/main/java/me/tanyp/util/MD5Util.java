package me.tanyp.util;

public class MD5Util {

	public static String[] encryptPasswordWithSalt(String pwd, String salt) {
		if (StringUtils.isEmpty(salt)) {
			salt = UUIDGenerator.generate(null);
		}
		return new String[] { MD5.encryptPassword(MD5.encryptPassword(pwd) + salt), salt };
	}
	
	public static void main(String[] args) {
		String aa = "000000000000000000Bc1bcts888...";
		System.out.println(MD5Util.encryptPasswordWithSalt(aa, null)[1]);
	}
}
