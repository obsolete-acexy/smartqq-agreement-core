package com.thankjava.wqq.core.action;

import com.thankjava.toolkit3d.bean.http.AsyncResponse;
import com.thankjava.wqq.consts.ConfigParams;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.core.request.api.*;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.wqq.DetailedInfo;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.factory.RequestFactory;
import com.thankjava.wqq.util.JSON2Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetInfoAction {

    private static final Logger logger = LoggerFactory.getLogger(GetInfoAction.class);

    private static Session session = Session.getSession();

    private RequestBuilder getUserFriends2 = RequestFactory.getInstance(GetUserFriends2.class);
    private RequestBuilder getGroupNameListMask2 = RequestFactory.getInstance(GetGroupNameListMask2.class);
    private RequestBuilder getDiscusList = RequestFactory.getInstance(GetDiscusList.class);
    private RequestBuilder getSelfInfo2 = RequestFactory.getInstance(GetSelfInfo2.class);
    private RequestBuilder getOnlineBuddies2 = RequestFactory.getInstance(GetOnlineBuddies2.class);
    private RequestBuilder getRecentList2 = RequestFactory.getInstance(GetRecentList2.class);

    /**
     * 获取所有好友信息
     * <p>Function: getFriendsList</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月22日 下午1:34:35
     * @version 1.0
     */
    public FriendsList getFriendsList() {

        AsyncResponse response = null;
        FriendsList friendsList = null;

        for (int i = 1; i <= ConfigParams.EXCEPTION_RETRY_MAX_TIME; i++) {
            response = getUserFriends2.doRequest(null);
            if (!response.isEmptyDataString()) {
                friendsList = JSON2Entity.userFriends2(response.getDataString());
                if (friendsList != null) {
                    session.setFriendsList(friendsList);
                    return friendsList;
                }
            }
        }
        logger.error("getUserFriends2 失败 (已尝试重试)");
        return null;
    }

    /**
     * 异步获取好友列表
     *
     * @param callBackListener
     * @param tryTimes
     */
    public void getFriendsList(final CallBackListener callBackListener, final Integer... tryTimes) {

        final int tryTime = tryTimes == null || tryTimes.length == 0 ? 1 : tryTimes[0] + 1;
        if (tryTime >= ConfigParams.EXCEPTION_RETRY_MAX_TIME) {
            logger.error("getUserFriends2 失败 (已尝试重试)");
            callBackListener.onListener(new ActionListener(null));
            return;
        }

        getUserFriends2.doRequest(new CallBackListener() {

            @Override
            public void onListener(ActionListener actionListener) {
                if (actionListener.getData() != null) {
                    AsyncResponse asyncResponse = (AsyncResponse) actionListener.getData();
                    FriendsList friendsList = JSON2Entity.userFriends2(asyncResponse.getDataString());
                    if (friendsList != null) {
                        session.setFriendsList(friendsList);
                        callBackListener.onListener(new ActionListener(friendsList));
                    } else {
                        getFriendsList(callBackListener, tryTime);
                    }
                } else {
                    getFriendsList(callBackListener, tryTime);
                }
            }

        });
    }

    /**
     * 为当前好友列表数据查询在线状态
     * <p>Function: getOnlineStatus</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2017年6月14日 下午2:46:17
     * @version 1.0
     */
    public FriendsList getOnlineStatus() {

        FriendsList friendsList = session.getFriendsList();
        if (friendsList == null) {
            return null;
        }

        AsyncResponse response = null;

        for (int i = 1; i <= ConfigParams.EXCEPTION_RETRY_MAX_TIME; i++) {
            response = getOnlineBuddies2.doRequest(null);
            if (!response.isEmptyDataString()) {
                friendsList = JSON2Entity.onlineStatus(friendsList, response.getDataString());
                if (friendsList != null) {
                    session.setFriendsList(friendsList);
                    return friendsList;
                }
            }
        }
        logger.error("getOnlineBuddies2 失败 (已尝试重试)");

        return null;
    }


    public void getOnlineStatus(final CallBackListener callBackListener, final Integer... tryTimes) {

        final int tryTime = tryTimes == null || tryTimes.length == 0 ? 1 : tryTimes[0] + 1;
        if (tryTime >= ConfigParams.EXCEPTION_RETRY_MAX_TIME) {
            logger.error("getOnlineBuddies2 失败 (已尝试重试)");
            callBackListener.onListener(new ActionListener(null));
            return;
        }

        getOnlineBuddies2.doRequest(new CallBackListener() {

            @Override
            public void onListener(ActionListener actionListener) {
                FriendsList friendsList = session.getFriendsList();
                if (friendsList == null) {
                    callBackListener.onListener(new ActionListener(null));
                    return;
                }
                if (actionListener.getData() != null) {
                    AsyncResponse asyncResponse = (AsyncResponse) actionListener.getData();
                    friendsList = JSON2Entity.onlineStatus(friendsList, asyncResponse.getDataString());
                    if (friendsList != null) {
                        session.setFriendsList(friendsList);
                        callBackListener.onListener(new ActionListener(friendsList));
                    } else {
                        getOnlineStatus(callBackListener, tryTime);
                    }
                } else {
                    getOnlineStatus(callBackListener, tryTime);
                }
            }

        });
    }

    /**
     * 获取讨论组列表
     * <p>Function: getDiscusList</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月22日 下午4:42:58
     * @version 1.0
     */
    public DiscusList getDiscusList() {
        AsyncResponse response = null;
        DiscusList discusList = null;
        for (int i = 1; i <= ConfigParams.EXCEPTION_RETRY_MAX_TIME; i++) {
            response = getDiscusList.doRequest(null);
            if (!response.isEmptyDataString()) {
                discusList = JSON2Entity.getDiscusList(response.getDataString());
                if (discusList != null) {
                    session.setDiscusList(discusList);
                    return discusList;
                }
            }
        }
        logger.error("getDiscusList 失败 (已尝试重试)");
        return null;
    }

    /**
     * 异步获取讨论组信息
     *
     * @param callBackListener
     * @param tryTimes
     */
    public void getDiscusList(final CallBackListener callBackListener, final Integer... tryTimes) {
        final int tryTime = tryTimes == null || tryTimes.length == 0 ? 1 : tryTimes[0] + 1;

        if (tryTime >= ConfigParams.EXCEPTION_RETRY_MAX_TIME) {
            logger.error("getGroupNameListMask2 失败 (已尝试重试)");
            callBackListener.onListener(new ActionListener(null));
            return;
        }

        getDiscusList.doRequest(new CallBackListener() {
            @Override
            public void onListener(ActionListener actionListener) {
                if (actionListener.getData() != null) {
                    AsyncResponse asyncResponse = (AsyncResponse) actionListener.getData();
                    DiscusList discusList = JSON2Entity.getDiscusList(asyncResponse.getDataString());
                    if (discusList != null) {
                        session.setDiscusList(discusList);
                        callBackListener.onListener(new ActionListener(discusList));
                    } else {
                        getDiscusList(callBackListener, tryTime);
                    }
                } else {
                    getDiscusList(callBackListener, tryTime);
                }
            }

        });
    }

    /**
     * 获取群列表
     * <p>Function: getGroupsList</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月22日 下午1:56:17
     * @version 1.0
     */
    public GroupsList getGroupsList() {
        AsyncResponse response = null;
        GroupsList groupList = null;
        for (int i = 1; i <= ConfigParams.EXCEPTION_RETRY_MAX_TIME; i++) {
            response = getGroupNameListMask2.doRequest(null);
            if (!response.isEmptyDataString()) {
                groupList = JSON2Entity.getGroupsList(response.getDataString());
                if (groupList != null) {
                    session.setGroupsList(groupList);
                    return groupList;
                }
            }
        }
        logger.error("getGroupNameListMask2 失败 (已尝试重试)");
        return null;
    }

    /**
     * 异步获取群组列表
     *
     * @param callBackListener
     * @param tryTimes
     */
    public void getGroupsList(final CallBackListener callBackListener, final Integer... tryTimes) {

        final int tryTime = tryTimes == null || tryTimes.length == 0 ? 1 : tryTimes[0] + 1;

        if (tryTime >= ConfigParams.EXCEPTION_RETRY_MAX_TIME) {
            logger.error("getGroupNameListMask2 失败 (已尝试重试)");
            callBackListener.onListener(new ActionListener(null));
            return;
        }

        getGroupNameListMask2.doRequest(new CallBackListener() {
            @Override
            public void onListener(ActionListener actionListener) {
                if (actionListener.getData() != null) {
                    AsyncResponse asyncResponse = (AsyncResponse) actionListener.getData();
                    GroupsList groupList = JSON2Entity.getGroupsList(asyncResponse.getDataString());
                    if (groupList != null) {
                        session.setGroupsList(groupList);
                        callBackListener.onListener(new ActionListener(groupList));
                    } else {
                        getGroupsList(callBackListener, tryTime);
                    }
                } else {
                    getGroupsList(callBackListener, tryTime);
                }
            }

        });
    }

    /**
     * 获取个人信息(登录人)
     * <p>Function: getSelfInfo</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2017年6月14日 下午4:13:01
     * @version 1.0
     */
    public DetailedInfo getSelfInfo() {
        AsyncResponse response = null;
        DetailedInfo detailedInfo = null;
        for (int i = 1; i <= ConfigParams.EXCEPTION_RETRY_MAX_TIME; i++) {
            response = getSelfInfo2.doRequest(null);
            if (!response.isEmptyDataString()) {
                detailedInfo = JSON2Entity.getSelfInfo(response.getDataString());
                if (detailedInfo != null) {
                    session.setSelfInfo(detailedInfo);
                    return detailedInfo;
                }
            }
        }
        logger.error("getSelfInfo2 失败 (已尝试重试)");

        return null;
    }


    /**
     * 异步获取个人信息
     *
     * @param callBackListener
     * @param tryTimes
     */
    public void getSelfInfo(final CallBackListener callBackListener, final Integer... tryTimes) {

        final int tryTime = tryTimes == null || tryTimes.length == 0 ? 1 : tryTimes[0] + 1;

        if (tryTime >= ConfigParams.EXCEPTION_RETRY_MAX_TIME) {
            logger.error("getSelfInfo2 失败 (已尝试重试)");
            callBackListener.onListener(new ActionListener(null));
            return;
        }

        getSelfInfo2.doRequest(new CallBackListener() {

            @Override
            public void onListener(ActionListener actionListener) {
                if (actionListener.getData() != null) {
                    AsyncResponse asyncResponse = (AsyncResponse) actionListener.getData();
                    DetailedInfo detailedInfo = JSON2Entity.getSelfInfo(asyncResponse.getDataString());
                    if (detailedInfo != null) {
                        session.setSelfInfo(detailedInfo);
                        callBackListener.onListener(new ActionListener(detailedInfo));
                    } else {
                        getSelfInfo(callBackListener, tryTime);
                    }
                } else {
                    getSelfInfo(callBackListener, tryTime);
                }
            }

        });
    }

    /**
     * 获取最近联系的好友，没有实际意义未实现
     */
    @Deprecated
    void getRecentList() {
        getRecentList2.doRequest(null).isEmptyDataString();
    }
}