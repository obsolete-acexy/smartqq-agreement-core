package com.thankjava.wqq.entity.enums;

/**
 * 获取消息的消息状态情况
 * @author acexy
 *
 */
public enum PullMsgStatus {
	normal, // 正常
	http_status_error, // http状态非200
	http_response_error, //响应无效的内容
	http_exception // http请求异常
}
