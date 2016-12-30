package com.thankjava.wqq;

import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.extend.CallBackListener;

/**
 * SmartQQ 应用接口
* <p>Function: SmartQQClient</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2016年12月9日 下午2:17:14
* @version 1.0
 */
public interface SmartQQClient{
	
	/**
	 * 登录接口
	* <p>Function: login</p>
	* <p>Description: 扫描登录</p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月28日 下午11:42:34
	* @version 1.0
	* @param autoRefreshQRcode 指定二维码过期是否自动刷新
	* @param listener 获取到二维码后的回调函数
	 */
	public void login(boolean autoRefreshQRcode, CallBackListener listener);
	
	/**
	 * 发送信息
	* <p>Function: sendMsg</p>
	* <p>Description: 只要构造好正确的SendMsg对象就能自动发送好友|讨论组|群组信息</p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月28日 下午11:43:45
	* @version 1.0
	* @param sendMsg
	 */
	public void sendMsg(SendMsg sendMsg);
}
