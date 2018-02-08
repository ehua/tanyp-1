package com.tanyouping.weixiao.web.client;

import com.tanyouping.weixiao.util.StringUtils;

import java.io.Serializable;
import java.util.Locale;

/**
 * 客户端信息的封装
 * @author liuyi
 *
 */
public class ClientInfo implements Serializable {

    /** 请求协议 */
	private String protocol;
	/** 当前域名  */
	private String currentDomain;
	private String uri;
	/** 空字符串 或者 /xxx */
	private String topLevelPath = "";
	private String ip;
	/** 请求端口 */
	private int port;
	private String browser;
	private Locale locale;
	
	
	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the currentDomain
	 */
	public String getCurrentDomain() {
		return currentDomain;
	}
	/**
	 * @param currentDomain the currentDomain to set
	 */
	public void setCurrentDomain(String currentDomain) {
		this.currentDomain = currentDomain;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTopLevelPath() {
		return topLevelPath;
	}
	public void setTopLevelPath(String topLevelPath) {
		this.topLevelPath = topLevelPath;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public void setLocaleString(String localeString) {
		if(StringUtils.isNotEmpty(localeString)){
			int index = localeString.indexOf("_");
			if(index != -1 && index != localeString.length() - 1){
				this.locale =  new Locale(localeString.substring(0, index).toLowerCase(), localeString.substring(index + 1).toUpperCase());
			}else{
				this.locale =  new Locale(localeString.toLowerCase());
			}
		}else{
			this.locale = null;
		}
	}
	
}
