package com.thankjava.wqq.test.qq;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.extend.NotifyListener;

public class MessageHandler implements NotifyListener {

	private static SmartQQClient smartQQClient;

	public MessageHandler(SmartQQClient smartQQClient) {
		MessageHandler.smartQQClient = smartQQClient;
	}

//	@Override
//	public void handler(PollMsg pollMsg) {
//		switch (pollMsg.getMsgType()) {
//		case message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: friend"));
//			break;
//		case group_message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: group"));
//			break;
//		case discu_message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: discu"));
//			break;
//		}
//	}

	// sendMsg 接口能通过pollMsg得到msg的类型，然后自动回复该类型的msg
	@Override
	public void handler(PollMsg pollMsg) {
		smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg"));
	}

}
