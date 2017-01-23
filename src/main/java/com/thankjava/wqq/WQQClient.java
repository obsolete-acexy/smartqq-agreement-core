package com.thankjava.wqq;

import com.thankjava.wqq.core.action.GetInfoAction;
import com.thankjava.wqq.core.action.LoginAction;
import com.thankjava.wqq.core.action.SendMsgAction;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;
import com.thankjava.wqq.factory.ActionFactory;

public class WQQClient implements SmartQQClient {
	
	private static NotifyListener listener;
	
	private Session session = Session.getSession();
	
	private LoginAction loginAction = ActionFactory.getInstance(LoginAction.class);
	private SendMsgAction sendMsgAction = ActionFactory.getInstance(SendMsgAction.class);
	private GetInfoAction getInfo = ActionFactory.getInstance(GetInfoAction.class);
	
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
	public void login(boolean autoRefreshQRcode, CallBackListener getQrlistener,CallBackListener loginListener) {
		loginAction.login(autoRefreshQRcode, getQrlistener, loginListener);
	}

	@Override
	public void sendMsg(SendMsg sendMsg) {
		sendMsgAction.sendMsg(sendMsg);
	}

	@Override
	public DiscusList getDiscusList(boolean isFromServer) {
		if(isFromServer){
			session.setDiscusList(getInfo.getDiscusList());
		}
		return session.getDiscusList();
	}

	@Override
	public GroupsList getGroupsList(boolean isFromServer) {
		if(isFromServer){
			session.setGroupsList(getInfo.getGroupsList());
		}
		return session.getGroupsList();
	}

	@Override
	public FriendsList getFriendsList(boolean isFromServer) {
		if(isFromServer){
			session.setFriendsList(getInfo.getFriendsList());
		}
		return session.getFriendsList();
	}

}
