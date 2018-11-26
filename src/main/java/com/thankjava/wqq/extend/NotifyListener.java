package com.thankjava.wqq.extend;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;

/**
 * 声明通知处理函数
 */
public interface NotifyListener {


    /**
     * 消息通知处理定义
     * @param smartQQClient
     * @param pollMsg
     */
    public void handler(SmartQQClient smartQQClient, PollMsg pollMsg);
}
