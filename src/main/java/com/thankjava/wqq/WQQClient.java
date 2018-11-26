package com.thankjava.wqq;

import com.thankjava.wqq.core.action.GetInfoAction;
import com.thankjava.wqq.core.action.SendMsgAction;
import com.thankjava.wqq.core.request.api.BaseHttpService;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;
import com.thankjava.wqq.factory.ActionFactory;


public class WQQClient implements SmartQQClient {

    // 通知
    private static NotifyListener notifyListener = null;

    // 系统停止回调(掉线)
    private static CallBackListener offlineListener = null;

    private Session session = Session.getSession();

    private SendMsgAction sendMsgAction = ActionFactory.getInstance(SendMsgAction.class);
    private GetInfoAction getInfo = ActionFactory.getInstance(GetInfoAction.class);

    private static WQQClient instance = null;

    public WQQClient() {
        instance = this;
    }

    public static NotifyListener getNotifyListener() {
        return notifyListener;
    }

    public static CallBackListener getOfflineListener() {
        return offlineListener;
    }

    public static SmartQQClient getInstance() {
        if (instance == null) {
            throw new NullPointerException("SmartQQClient is not instantiated");
        }
        return instance;
    }

    @Override
    public void sendMsg(SendMsg sendMsg) {
        sendMsgAction.sendMsg(sendMsg);
    }

    @Override
    public void shutdown() {
        BaseHttpService.shutdownAsyncHttpClient();
    }

    @Override
    public DiscusList getDiscusList(boolean isFromServer) {
        if (isFromServer) {
            session.setDiscusList(getInfo.getDiscusList());
        }
        return session.getDiscusList();
    }

    @Override
    public GroupsList getGroupsList(boolean isFromServer) {
        if (isFromServer) {
            session.setGroupsList(getInfo.getGroupsList());
        }
        return session.getGroupsList();
    }

    @Override
    public FriendsList getFriendsList(boolean isFromServer) {
        FriendsList friendsList = null;
        if (isFromServer) {
            friendsList = getInfo.getFriendsList();
            if (friendsList == null) {
                return null;
            } else {
                friendsList = getInfo.getOnlineStatus();
                if (friendsList == null) {
                    return null;
                }
            }
        }
        friendsList = session.getFriendsList();
        if (friendsList == null) {
            friendsList = getFriendsList(true);
        }
        return friendsList;
    }

}
