package com.tanyouping.weixiao.web.client;

public class ClientInfoHolder {
	
	private static final ThreadLocal<ClientInfo> clientInfoThreadLocal = new ThreadLocal<ClientInfo>();
	
	/**
	 * 必然会有值
	 * @return
	 */
	public static ClientInfo get(){
		ClientInfo clientInfo = clientInfoThreadLocal.get();
		if(clientInfo == null){
			clientInfo = new ClientInfo();
			clientInfoThreadLocal.set(clientInfo);
		}
		return clientInfo;
	}
	
	
	
	public static void remove(){
		clientInfoThreadLocal.remove();
	}

}
