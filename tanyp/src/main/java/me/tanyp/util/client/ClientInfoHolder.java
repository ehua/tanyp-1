package me.tanyp.util.client;

public class ClientInfoHolder {

	private static final ThreadLocal<ClientInfo> clientInfoThreadLocal = new ThreadLocal<>();

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
