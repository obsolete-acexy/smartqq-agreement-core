package com.thankjava.wqq;

import com.thankjava.wqq.core.action.LoginAction;
import com.thankjava.wqq.core.action.SendMsgAction;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;
import com.thankjava.wqq.factory.ActionFactory;

public class WQQClient implements SmartQQClient {
	
	private static NotifyListener listener;
	
	private LoginAction loginAction = ActionFactory.getInstance(LoginAction.class);
	private SendMsgAction sendMsgAction = ActionFactory.getInstance(SendMsgAction.class);
	
	public WQQClient(NotifyListener listener){
		if(listener == null){
			throw new RuntimeException("NotifyListener can not be null");
		}
		WQQClient.listener = listener;
	}
	
	public static NotifyListener getNotifyListener(){
		return listener;
	}
	
	@Override
	public void login(boolean autoRefreshQRcode, CallBackListener listener) {
		
		loginAction.login(autoRefreshQRcode, listener);
	}

	@Override
	public void sendMsg(SendMsg sendMsg) {
		sendMsgAction.sendMsg(sendMsg);
	}

}
