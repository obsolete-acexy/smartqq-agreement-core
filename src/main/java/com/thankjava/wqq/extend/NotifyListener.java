package com.thankjava.wqq.extend;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;

/**
 * 声明通知处理函数
 */
public interface NotifyListener {

    public void handler(SmartQQClient smartQQClient, PollMsg pollMsg);
}
