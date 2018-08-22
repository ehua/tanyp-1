package me.tanyp.util.file;

import java.io.Serializable;

/**
 * 文件上传计数器对象
 * @author liuyi
 *     建于2014年9月2日
 *     修改于2014年9月2日
 */
public class UploadFileCounterDto implements Serializable {

	private static final long serialVersionUID = -8823941921125969607L;
	/** 第几个文件夹了 */
	private Integer page;
	/** 文件夹中有多少个文件了 */
	private Integer count;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
