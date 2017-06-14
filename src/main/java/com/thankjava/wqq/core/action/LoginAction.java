package com.thankjava.wqq.core.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.DataResRegx;
import com.thankjava.wqq.core.event.MsgPollEvent;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.core.request.api.CheckLoginQRcodeStatus;
import com.thankjava.wqq.core.request.api.CheckSig;
import com.thankjava.wqq.core.request.api.GetLoginQRcode;
import com.thankjava.wqq.core.request.api.GetVfWebqq;
import com.thankjava.wqq.core.request.api.Login2;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ListenerAction;
import com.thankjava.wqq.factory.ActionFactory;
import com.thankjava.wqq.factory.RequestFactory;
import com.thankjava.wqq.util.RegexUtil;
import com.alibaba.fastjson.JSONObject;

public class LoginAction {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	Session session = Session.getSession();
	
	RequestBuilder getLoginQRcode = RequestFactory.getInstance(GetLoginQRcode.class);
	RequestBuilder checkSig = RequestFactory.getInstance(CheckSig.class);
	RequestBuilder getVfWebqq = RequestFactory.getInstance(GetVfWebqq.class);
	RequestBuilder login2 = RequestFactory.getInstance(Login2.class);
	RequestBuilder checkLoginQRcodeStatus = RequestFactory.getInstance(CheckLoginQRcodeStatus.class);

	GetInfoAction getInfo = ActionFactory.getInstance(GetInfoAction.class);
	
	public void login(final boolean autoRefreshQRcode, final CallBackListener getQrListener, final CallBackListener loginListener){
		
		ResponseParams responseParams = getLoginQRcode.doRequest(null);
		if(responseParams == null){
			logger.error("获取二维码失败,执行重试");
			login(autoRefreshQRcode, getQrListener, loginListener);
		}

		ListenerAction listenerAction = null;
		try {
			// 得到二维码数据
			listenerAction= new ListenerAction(ImageIO.read(new ByteArrayInputStream(responseParams.getBytes())));
		} catch (IOException e) {
			logger.error("获取二维码数据失败", e);
		}
		
		getQrListener.onListener(listenerAction);
		
		logger.debug("获取二维码完成,启动二维码状态检查");
		
		checkLoginQRcodeStatus(autoRefreshQRcode, getQrListener, loginListener);
	}
	
	/**
	 * 检查当前的二维码的状态
	* <p>Function: checkLoginQRcodeStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月19日 下午4:19:00
	* @version 1.0
	* @return
	 */
	private void checkLoginQRcodeStatus(boolean autoRefreshQRcode, CallBackListener getQRListener, CallBackListener loginListener) {

		try {
			Thread.sleep(ConstsParams.CHECK_QRCODE_WITE_TIME);
		} catch (InterruptedException e) {
			logger.error("线程等待异常", e);
		}
			
		String[] data = RegexUtil.doRegex(checkLoginQRcodeStatus.doRequest(null).getContent(), DataResRegx.check_login_qrcode_status);
		
		if(data == null){
			logger.error("解析二维码状态失败,重试二维码状态检查");
			checkLoginQRcodeStatus(autoRefreshQRcode, getQRListener, loginListener);
		}
		
		Integer statusCode = Integer.valueOf(data[0]);
		if (statusCode == null){
			logger.error("无法解析出有效的二维码状态码,重试二维码状态检查");
			checkLoginQRcodeStatus(autoRefreshQRcode, getQRListener, loginListener);
		}
		
		switch (statusCode) {
		case 0: // 二维码认证成功
			logger.debug("二维码验证完成");
			session.setCheckSigUrl(data[2]);
			if(beginLogin()){
				loginListener.onListener(null);
			} else {
				logger.error("登录失败");
			}
			break;
		case 65: // 二维码认证过期
			if(autoRefreshQRcode){ // 如果指定过期自动刷新二维码
				logger.debug("二维码已过期,重新获取二维码...");
				login(autoRefreshQRcode, getQRListener, loginListener);
				break;
			}
			logger.debug("当前二维码已过期...");
			break;
		default: // 二维码处于认证中|等待认证
			logger.debug("二维码状态: " + data[4]);
			checkLoginQRcodeStatus(autoRefreshQRcode, getQRListener, loginListener);
			break;
		}
	}
	
	/**
	 * 扫码认证完成后执行登录态相关的认证登录
	* <p>Function: beginLogin</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月20日 下午2:49:21
	* @version 1.0
	 */
	private boolean beginLogin(){

		// checkSig
		ResponseParams responseParams = checkSig.doRequest(null);
		
		// checkSig 成功后 从cookie中得到ptwebqq 确切的说其实这个cookie是检查qrcode成功后服务器下发的
		Cookie cookie = responseParams.getCookies().getCookie("ptwebqq");
		if(cookie == null){
			logger.error("未能获取到ptwebqq数据");
			return false;
		}
		String ptwebqq = cookie.getValue();
		if(ptwebqq == null || ptwebqq.length() == 0){
			logger.error("未能成功获取ptwebqq数据");
			return false;
		}
		
		// getvfWebqq
		session.setPtwebqq(ptwebqq);
		responseParams = getVfWebqq.doRequest(null);
		if(responseParams.isEmptyContent()){
			logger.error("未能成功获取vfwebqq数据");
			return false;
		}
		String content = responseParams.getContent();
		JSONObject jsonObject,result;
		try {
			jsonObject = JSONObject.parseObject(content);
			result = (JSONObject)jsonObject.get("result");
			session.setVfwebqq(result.get("vfwebqq").toString());
		} catch (Exception e) {
			logger.error("未能成功解析vfwebqq数据", e);
			return false;
		}
		
		// login2
		responseParams = login2.doRequest(null);
		if(responseParams.isEmptyContent()){
			logger.error("未能成功获取登录数据");
			return false;
		}
		content = responseParams.getContent();
		try {
			jsonObject = JSONObject.parseObject(content);
			result = (JSONObject)jsonObject.get("result");
			session.setUin(result.getLongValue("uin"));
			session.setPsessionid(result.getString("psessionid"));
		} catch (Exception e){
			logger.error("未能成功解析登录数据", e);
			return false;
		}
		
		if (ConstsParams.INIT_LOGIN_INFO){
			
			FriendsList friendsList = getInfo.getFriendsList();
			if (friendsList == null){
				logger.error("获取好友列表失败");
			} else {
				friendsList = getInfo.appendOnlineStatus();
				if(friendsList == null){
					logger.error("为好友列表查询在线状态失败");
				}
			}
			getInfo.getGroupsList();
			getInfo.getDiscusList();
			getInfo.getSelfInfo();
			getInfo.getRecentList();
		}

		
		// 启动消息Poll
		new Thread(new Runnable() {
			@Override
			public void run() {
				ActionFactory.getInstance(MsgPollEvent.class).poll();
			}
		}).start();
		
		return true;
	}
}
