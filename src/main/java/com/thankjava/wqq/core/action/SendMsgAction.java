package com.thankjava.wqq.core.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.wqq.core.request.http.SendBuddyMsg2;
import com.thankjava.wqq.core.request.http.SendDiscuMsg2;
import com.thankjava.wqq.core.request.http.SendQunMsg2;
import com.thankjava.wqq.entity.msg.SendMsg;

public class SendMsgAction {

	private static final Logger logger = LoggerFactory.getLogger(SendMsgAction.class);

	public boolean sendMsg(SendMsg sendMsg) {
		switch (sendMsg.getMsgType()) {
		case message:
			return sendFriendMsg(sendMsg);
		case group_message:
			return sendGroupMsg(sendMsg);
		case discu_message:
			return sendDiscuMsg(sendMsg);
		default:
			return false;
		}
	}

	private boolean sendFriendMsg(SendMsg sendMsg) {
		String content = new SendBuddyMsg2(sendMsg).doRequest(null).getContent();
		logger.debug("发送好友信息返回内容: " + content);
		return false;
	}

	private boolean sendGroupMsg(SendMsg sendMsg) {
		String content = new SendQunMsg2(sendMsg).doRequest(null).getContent();
		logger.debug("发送群组信息返回内容: " + content);
		return false;
	}
	
	private boolean sendDiscuMsg(SendMsg sendMsg){
		String content = new SendDiscuMsg2(sendMsg).doRequest(null).getContent();
		logger.debug("发送讨论组信息返回内容: " + content);
		return false;
	}
}
