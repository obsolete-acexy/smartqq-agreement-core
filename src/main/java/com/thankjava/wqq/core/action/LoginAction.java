package com.thankjava.wqq.core.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.DataResRegx;
import com.thankjava.wqq.core.event.MsgPollEvent;
import com.thankjava.wqq.core.request.Request;
import com.thankjava.wqq.core.request.http.CheckLoginQRcodeStatus;
import com.thankjava.wqq.core.request.http.CheckSig;
import com.thankjava.wqq.core.request.http.GetLoginQRcode;
import com.thankjava.wqq.core.request.http.GetVfWebqq;
import com.thankjava.wqq.core.request.http.Login2;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ListenerAction;
import com.thankjava.wqq.factory.ActionFactory;
import com.thankjava.wqq.factory.RequestFactory;

import com.alibaba.fastjson.JSONObject;

public class LoginAction {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	Session session = Session.getSession();
	
	Request getLoginQRcode = RequestFactory.getInstance(GetLoginQRcode.class);
	Request checkSig = RequestFactory.getInstance(CheckSig.class);
	Request getVfWebqq = RequestFactory.getInstance(GetVfWebqq.class);
	Request login2 = RequestFactory.getInstance(Login2.class);
	
	public void login(final boolean autoRefreshQRcode, final CallBackListener getQrListener, final CallBackListener loginListener){
		
		ResponseParams responseParams = getLoginQRcode.doRequest(null);
		if(responseParams == null){
			login(autoRefreshQRcode, getQrListener, loginListener);
		}
		ListenerAction listenerAction = null;
		try {
			// 得到二维码数据
			listenerAction= new ListenerAction(ImageIO.read(
					new ByteArrayInputStream(responseParams.getBytes())
				));
		} catch (IOException e) {
			logger.error("获取二维码数据失败", e);
		}
		
		getQrListener.onListener(listenerAction);
		
		logger.debug("获取二维码完成，启动二维码状态检查");
		
		checkLoginQRcodeStatus(autoRefreshQRcode, getQrListener, loginListener,
				responseParams.getCookies().getCookie("qrsig").getValue());
		
//		// 获取登录二维码
//		getLoginQRcode(new CallBackListener() {
//			
//			@Override
//			public void onListener(ListenerAction listenerAction) {
//
//				if (listenerAction.getData() == null) {
//					// 获取二维码失败
//					login(autoRefreshQRcode, getQrListener, loginListener);
//				}
//				
//				ResponseParams responseParams = (ResponseParams) listenerAction.getData();
//				
//				try {
//					// 得到二维码数据
//					listenerAction.setData(ImageIO.read(
//							new ByteArrayInputStream(responseParams.getBytes())
//						));
//				} catch (IOException e) {
//					logger.error("获取二维码数据失败", e);
//				}
//				
//				// 回调业务端处理二维码
//				getQrListener.onListener(listenerAction);
//				
//				logger.debug("获取二维码完成，启动二维码状态检查");
//				
//				checkLoginQRcodeStatus(autoRefreshQRcode, getQrListener, loginListener,
//						responseParams.getCookies().getCookie("qrsig").getValue());
//			}
//		});
	}
	
	/**
	 * 获取登录的二维码
	* <p>Function: getLoginQRcode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月19日 下午4:18:42
	* @version 1.0
	* @return
	 */
	private void getLoginQRcode(CallBackListener listener){
		listener.onListener(new ListenerAction(getLoginQRcode.doRequest(null)));
	}
	
	/**
	 * 使用正则去匹配返回的数据
	 * @param content
	 * @param dataResRegx
	 * @return
	 */
	private static String[] analysis(String content, DataResRegx dataResRegx){
		Pattern pattern = Pattern.compile(dataResRegx.regx);
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			String[] values = new String[matcher.groupCount()];
			for(int i = 1 ; i < matcher.groupCount(); i ++){
				values[i - 1] = matcher.group(i);
			}
			return values;
		}
		return null;
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
	private void checkLoginQRcodeStatus(boolean autoRefreshQRcode, CallBackListener getQRListener,
			CallBackListener loginListener, String qrsig) {

		try {
			Thread.sleep(ConstsParams.CHECK_QRCODE_WITE_TIME);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
			
		String[] data = analysis(
				new CheckLoginQRcodeStatus(qrsig).doRequest(null).getContent(),
				DataResRegx.check_login_qrcode_status
			);
		
		if(data == null){
			checkLoginQRcodeStatus(autoRefreshQRcode, getQRListener, loginListener, qrsig);
		}
		
		switch (Integer.valueOf(data[0])) {
		case 0: // 二维码认证成功
			logger.debug("二维码扫描登录完成,进行登录动作 checkSigUrl: " + data[2]);
			session.setCheckSigUrl(data[2]);
			beginLogin();
			loginListener.onListener(new ListenerAction());
			break;
		case 65: // 二维码认证过期
			if(autoRefreshQRcode){ // 如果指定过期自动刷新二维码
				logger.debug("二维码已过期,重新获取二维码..");
				login(autoRefreshQRcode, getQRListener, loginListener);
				break;
			}
			logger.debug("当前二维码已过期...");
			break;
		default: // 二维码处于认证中|等待认证
			logger.debug("二维码状态: " + data[4]);
			checkLoginQRcodeStatus(autoRefreshQRcode, getQRListener, loginListener, qrsig);
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
	private void beginLogin(){
		
		// checkSig
		ResponseParams responseParams = checkSig.doRequest(null);
		
		// checkSig 成功后 从cookie中得到ptwebqq 确切的说其实这个cookie是检查qrcode成功后服务器下发的
		String ptwebqq = responseParams.getCookies().getCookie("ptwebqq").getValue();
		session.setPtwebqq(ptwebqq);
		logger.debug("checkSig得到的ptwebqq: " + ptwebqq);
		
		// getvfWebqq
		responseParams = getVfWebqq.doRequest(null);
		String content = responseParams.getContent();
		
		logger.debug("getvfwebqq得到的返回内容: " + content);
		JSONObject jsonObject = JSONObject.parseObject(content);
		JSONObject result = (JSONObject)jsonObject.get("result");
		session.setVfwebqq(result.get("vfwebqq").toString());
		
		// login2
		responseParams = login2.doRequest(null);
		content = responseParams.getContent();
		logger.debug("login2得到的返回内容: " + content);
		
		jsonObject = JSONObject.parseObject(content);
		result = (JSONObject)jsonObject.get("result");
		session.setUin(result.getLongValue("uin"));
		session.setPsessionid(result.getString("psessionid"));
		
		// 初始化的一些信息获取
		GetInfoAction getInfo = ActionFactory.getInstance(GetInfoAction.class);
		getInfo.getFriendsList();
		getInfo.getGroupsList();
		getInfo.getDiscusList();
		getInfo.getSelfInfo();
		getInfo.getRecentList();
		
		// 启动消息Poll
		new Thread(new Runnable() {
			@Override
			public void run() {
				ActionFactory.getInstance(MsgPollEvent.class).poll();
			}
		}).start();
		
	}
}
