package com.thankjava.wqq.test.qq;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.extend.NotifyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener implements NotifyListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void handler(SmartQQClient smartQQClient, PollMsg pollMsg) {

        // 获取到的消息内容
        logger.debug("received msg : " + pollMsg.getMsgContext());
        switch (pollMsg.getMsgType()) {
            case message:
                smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: `Friend`"));
                break;
            case group_message:
                smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: `Group`"));
                break;
            case discu_message:
                smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: `Discu`"));
                break;
        }
    }

    // sendMsg 接口能通过pollMsg得到msg的类型，然后自动回复该类型的msg
//	@Override
//	public void handler(SmartQQClient smartQQClient, PollMsg pollMsg) {
//      logger.debug("received msg : " + pollMsg.getMsgContext());
//		smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg"));
//	}

}
