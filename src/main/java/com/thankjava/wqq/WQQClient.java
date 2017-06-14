package com.thankjava.wqq;

import com.thankjava.wqq.consts.ConstsParams;
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
	
	
	/**
	 * 简单构造
	* <p>Title: </p>
	* <p>Description: </p>
	* @param listener
	 */
	public WQQClient(NotifyListener listener){
		if(listener == null){
			throw new NullPointerException("NotifyListener can not be null");
		}
		WQQClient.listener = listener;
	}
	
	/**
	 * 携带初始化参数构造
	* <p>Title: </p>
	* <p>Description: </p>
	* @param exceptionRetryMaxTimes 某些接口由于服务器不稳定返回异常的自动尝试最高次数(默认 3)
	* @param listener
	* @param initLoginInfo 是否登录成功后立即获取相关信息并缓存起来(好友信息，群信息等 默认 false)
	 */
	public WQQClient(boolean initLoginInfo, int exceptionRetryMaxTimes, NotifyListener listener){
		
		if(exceptionRetryMaxTimes < 1){
			throw new IllegalArgumentException("exceptionRetryMaxTimes should >= 1");
		}
		if(listener == null){
			throw new NullPointerException("NotifyListener can not be null");
		}
		
		WQQClient.listener = listener;
		ConstsParams.EXCEPTION_RETRY_MAX_TIME = exceptionRetryMaxTimes;
		ConstsParams.INIT_LOGIN_INFO = initLoginInfo;
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
