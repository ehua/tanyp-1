package me.tanyp.util.file;

import me.tanyp.controller.common.SystemException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author liuyi 建于2014年9月1日 修改于2014年9月1日
 */
public class FileType {

	private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private FileType() {
	}

	static {
		getAllFileType(); // 初始化文件类型信息
	}

	private static void getAllFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG
		FILE_TYPE_MAP.put("png", "89504E47"); // PNG
		FILE_TYPE_MAP.put("gif", "47494638"); // GIF
		FILE_TYPE_MAP.put("tif", "49492A00"); // TIFF
		FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap
		FILE_TYPE_MAP.put("dwg", "41433130"); // CAD
		FILE_TYPE_MAP.put("psd", "38425053"); // Adobe Photoshop
		FILE_TYPE_MAP.put("rtf", "7B5C727466"); // Rich Text Format
		FILE_TYPE_MAP.put("html", "68746D6C3E"); // HTML
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); // Email[thorough
																	// only]
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); // Outlook Express (dbx)
		FILE_TYPE_MAP.put("pst", "2142444E");// Outlook (pst)
		FILE_TYPE_MAP.put("doc", "D0CF11E0"); // MS Excel、Word、Msi
		FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");// MS Access
		FILE_TYPE_MAP.put("wpd", "FF575043"); // WordPerfect
		FILE_TYPE_MAP.put("pdf", "255044462D312E");
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); // Quicken
		FILE_TYPE_MAP.put("pwl", "E3828596"); // Windows Password
		FILE_TYPE_MAP.put("zip", "504B0304"); // ZIP Archive
		FILE_TYPE_MAP.put("rar", "526172211"); // RAR Archive
		FILE_TYPE_MAP.put("wav", "57415645"); // WAVE
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD");
		FILE_TYPE_MAP.put("css", "48544d4c207b0d0a0942");
		FILE_TYPE_MAP.put("js", "696b2e71623d696b2e71");
		FILE_TYPE_MAP.put("vsd", "d0cf11e0a1b11ae10000");
		FILE_TYPE_MAP.put("rmvb", "2E524D46"); // rmvb、rm
		FILE_TYPE_MAP.put("flv", "464c5601050000000900"); // flv、f4v
		FILE_TYPE_MAP.put("mp4", "00000020667479706d70");
		FILE_TYPE_MAP.put("mp4", "0000001c667479706d70");
		FILE_TYPE_MAP.put("mp3", "49443303000000002176");
		FILE_TYPE_MAP.put("mpg", "000001BA");
		FILE_TYPE_MAP.put("mpg", "000001B3");
		FILE_TYPE_MAP.put("mov", "6D6F6F76"); // Quicktime
		FILE_TYPE_MAP.put("wmv", "3026B2758E66CF11"); // wmv、asf
		FILE_TYPE_MAP.put("mid", "4D546864");
		FILE_TYPE_MAP.put("ini", "235468697320636f6e66");
		FILE_TYPE_MAP.put("jar", "504b03040a0000000000");
		FILE_TYPE_MAP.put("exe", "4d5a9000030000000400");
		FILE_TYPE_MAP.put("jsp", "3c25402070616765206c");
		FILE_TYPE_MAP.put("mf", "4d616e69666573742d56");
		FILE_TYPE_MAP.put("xml", "3c3f786d6c2076657273");
		FILE_TYPE_MAP.put("sql", "494e5345525420494e54");
		FILE_TYPE_MAP.put("java", "7061636b616765207765");
		FILE_TYPE_MAP.put("bat", "406563686f206f66660d");
		FILE_TYPE_MAP.put("gz", "1f8b0800000000000000");
		FILE_TYPE_MAP.put("properties", "6c6f67346a2e726f6f74");
		FILE_TYPE_MAP.put("class", "cafebabe0000002e0041");
		FILE_TYPE_MAP.put("chm", "49545346030000006000");
		FILE_TYPE_MAP.put("mxp", "04000000010000001300");
		FILE_TYPE_MAP.put("docx", "504b0304140006000800");
		FILE_TYPE_MAP.put("wps", "d0cf11e0a1b11ae10000");// WPS(wps、et、dps)
		FILE_TYPE_MAP.put("torrent", "6431303a637265617465");
		FILE_TYPE_MAP.put("txt", "");
	}

	/**
	 * 得到上传文件的文件头
	 * 
	 * @param src
	 * @return
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (null == src || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 判断文件类型是否正确
	 * 
	 * @param inputStream
	 * @param suffix
	 * @return
	 */
	public static boolean isRightFile(BufferedInputStream inputStream, String suffix) {
		String fileHead = FILE_TYPE_MAP.get(suffix);
		if (fileHead == null) {
			throw new SystemException("不支持的文件类型判断");
		}

		String fileCode = getFileHead(inputStream, fileHead.length());
		return fileCode.toLowerCase().contains(fileHead.toLowerCase())
				|| fileHead.toLowerCase().contains(fileCode.toLowerCase());
	}

	/**
	 * 判断文件类型是否正确
	 * 
	 * @param file
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
//	public static boolean isRightFile(File file, String suffix) {
//		BufferedInputStream is = null;
//		try {
//			is = new BufferedInputStream(new FileInputStream(file));
//			return isRightFile(is, suffix);
//		} catch (FileNotFoundException e) {
//			throw new SystemException("未找到指定文件");
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * 通过输入流获取文件真实类型
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String getRealType(String fileName, BufferedInputStream inputStream) {
		if(fileName != null && fileName.endsWith(".txt")){return "txt";}

		String realType = null;
		String fileCode = getFileHead(inputStream, 10);
		for (Map.Entry<String, String> entry : FILE_TYPE_MAP.entrySet()) {
			if (entry.getValue().toLowerCase().contains(fileCode.toLowerCase())
					|| fileCode.toLowerCase().contains(entry.getValue().toLowerCase())) {
				realType = entry.getKey();
				break;
			}
		}

		if(realType == null){
			throw new SystemException("文件类型检查失败,不是系统允许范围内的附件类型");
		}
		if(fileName != null){
			if(realType.equals("doc")|| realType.equals("vsd") || realType.equals("wps")
					|| realType.equals("zip")){
				realType = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
			}
		}

		return realType;
	}

	/**
	 * 通过文件获取文件真实类型
	 * 
	 * @param file
	 * @return
	 */
//	public static String getRealType(File file) {
//		BufferedInputStream is = null;
//		try {
//			is = new BufferedInputStream(new FileInputStream(file));
//			return getRealType(null,is);
//		} catch (FileNotFoundException e) {
//			throw new SystemException("未找到指定文件");
//		} finally {
//			try {
//				if (is != null) {
//					is.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * 获取文件头串
	 * 
	 * @param inputStream
	 * @param length
	 * @return
	 */
	private static String getFileHead(BufferedInputStream inputStream, Integer length) {
		inputStream.mark(50);
		byte[] b = new byte[length];
		int leng = 0;
		try {
			leng = inputStream.read(b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (leng < b.length) {
			throw new SystemException("文件损坏无法判断");
		}
		return bytesToHexString(b);
	}
}
