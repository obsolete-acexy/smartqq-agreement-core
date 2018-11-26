package com.thankjava.wqq.core.action;

import com.thankjava.toolkit3d.bean.http.AsyncResponse;
import com.thankjava.wqq.core.request.api.SendBuddyMsg2;
import com.thankjava.wqq.core.request.api.SendDiscuMsg2;
import com.thankjava.wqq.core.request.api.SendQunMsg2;
import com.thankjava.wqq.entity.msg.SendMsg;

public class SendMsgAction {

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
        new SendBuddyMsg2(sendMsg).doRequest(null);
        return true;
    }

    private boolean sendGroupMsg(SendMsg sendMsg) {
        new SendQunMsg2(sendMsg).doRequest(null);
        return true;
    }

    private boolean sendDiscuMsg(SendMsg sendMsg) {
        new SendDiscuMsg2(sendMsg).doRequest(null);
        return true;
    }
}
