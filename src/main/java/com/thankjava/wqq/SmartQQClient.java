package com.thankjava.wqq;

import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;

/**
 * SmartQQ 应用接口
 * <p>Function: SmartQQClient</p>
 * <p>Description: 基础应用接口</p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2016年12月9日 下午2:17:14
 */
public interface SmartQQClient {

    /**
     * 获取讨论组信息
     * <p>Function: getDiscusList</p>
     * <p>Description: </p>
     *
     * @param isFromServer 是否从服务器获取最新
     * @return
     * @author acexy@thankjava.com
     * @date 2017年1月4日 下午6:59:20
     * @version 1.0
     */
    public DiscusList getDiscusList(boolean isFromServer);

    /**
     * 获取群组信息
     * <p>Function: getGroupsList</p>
     * <p>Description: </p>
     *
     * @param isFromServer 是否从服务器获取最新
     * @return
     * @author acexy@thankjava.com
     * @date 2017年1月4日 下午6:59:52
     * @version 1.0
     */
    public GroupsList getGroupsList(boolean isFromServer);

    /**
     * 获取好友信息
     * <p>Function: getFriendsList</p>
     * <p>Description: </p>
     *
     * @param isFromServer 是否从服务器获取最新
     * @return
     * @author acexy@thankjava.com
     * @date 2017年1月4日 下午7:00:06
     * @version 1.0
     */
    public FriendsList getFriendsList(boolean isFromServer);

    /**
     * 发送信息
     * <p>Function: sendMsg</p>
     * <p>Description: 只要构造好正确的SendMsg对象就能自动发送好友|讨论组|群组信息</p>
     *
     * @param sendMsg
     * @author acexy@thankjava.com
     * @date 2016年12月28日 下午11:43:45
     * @version 1.0
     */
    public void sendMsg(SendMsg sendMsg);

    /**
     * 关闭SmartQQClient
     */
    public void shutdown();
}
