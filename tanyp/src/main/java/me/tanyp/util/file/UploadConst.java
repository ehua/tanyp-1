/***********************************************************************
 * 深圳市云游天下科技有限公司 版权所有
 ***********************************************************************/
package me.tanyp.util.file;
import me.tanyp.util.basic.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UploadConst {

	private static volatile UploadConst uploadConst;

	/** 上传文件根目录 */
	private String uploadRootPath;
	/** 顶级文件夹 */
	public String uploadedTopFolder;

	/** 图片最大大小 byte */
	@Value("${upload.maxImgSize}")
	public String maxImgSize;

	/** 图片类型包含的后缀格式 */
	@Value("${upload.imgType}")
	public String imgType;

	/** 文档类型包含的后缀格式 */
	@Value("${upload.documentType}")
	public String documentType;

	/** 视频类型包含的后缀格式 */
	@Value("${upload.videoType}")
	public String videoType;

	/** 音频类型包含的后缀格式 */
	@Value("${upload.audioType}")
	public String audioType;

	/** 所有允许的类型包含的后缀格式 */
	@Value("${upload.allType}")
	public String allType;

	/** 图片或下载对应的域名 */
	@Value("${upload.displayDomain}")
	public String displayDomain;

	@PostConstruct
	public void init(){
		uploadConst = this;
	}

	@Value("${upload.rootPath}")
	public void setUploadRootPath(String uploadRootPath) {
		if(!uploadRootPath.endsWith("/")
				&& !uploadRootPath.endsWith("\\")){
			this.uploadRootPath = uploadRootPath + "/";
		}else{
			this.uploadRootPath = uploadRootPath;
		}
	}
	@Value("${upload.topFolder}")
	public void setUploadedTopFolder(String uploadedTopFolder) {
		if(!uploadedTopFolder.endsWith("/")
				&& !uploadedTopFolder.endsWith("\\")){
			this.uploadedTopFolder = uploadedTopFolder + "/";
		}else{
			this.uploadedTopFolder = uploadedTopFolder;
		}
	}

	public static UploadConst get(){
		while(uploadConst == null){
			throw new RuntimeException("uploadConst is null");
		}
		return uploadConst;
	}

	/**
	 * 获取上传路径信息（uploadRootPath为空时自动设置到与WEB-INF目录平级的uploads）
	 *
	 * @return
	 */
	public UploadRootPathInfo getUploadBaseRootPath() {
		UploadRootPathInfo uploadRootPathInfo = new UploadRootPathInfo();
		// 如果没有配置就放到容器下（web工程在tomcat部署的位置）
		if (StringUtils.isEmpty(uploadRootPath)) {
			String classesPath = this.getClass().getClassLoader().getResource("").getPath();
			uploadRootPathInfo.ifDefault = true;
			uploadRootPathInfo.uploadBaseRootPath = classesPath.substring(0, classesPath.indexOf("WEB-INF/")) + "uploads/";
		} else {
			uploadRootPathInfo.ifDefault = false;
			uploadRootPathInfo.uploadBaseRootPath = uploadRootPath;
		}
		return uploadRootPathInfo;
	}


	public static class UploadRootPathInfo{
		private String uploadBaseRootPath;
		public boolean ifDefault;

		public String getUploadRootPath() {
			return uploadBaseRootPath + UploadConst.get().uploadedTopFolder;
		}

		public String getUploadBaseRootPath() {
			return uploadBaseRootPath;
		}
	}

}
