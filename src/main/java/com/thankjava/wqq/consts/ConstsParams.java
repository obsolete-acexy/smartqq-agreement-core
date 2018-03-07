package com.thankjava.wqq.consts;

public class ConstsParams {
	
	/**
	 * 每次检查二维码的时间间隔
	 */
	public static final long CHECK_QRCODE_WITE_TIME = 2000;
	
	/**
	 * APPID 应该是腾讯内部webqq产品所属的APPID
	 */
	public static final int APP_ID = 501004106;
	
	/**
	 * CLIENT_ID 同上
	 */
	public static final int CLIENT_ID = 53999199;
	
	/**
	 * 初始化的消息ID
	 */
	public static final int INIT_MSG_ID = 1704000;
	
	/**
	 * 一些接口最大的异常尝试次数
	 */
	public static int EXCEPTION_RETRY_MAX_TIME = 1;
	
	/**
	 * 登录完成后是否立即获取好友信息，群信息，讨论组信息
	 */
	public static boolean INIT_LOGIN_INFO = true;
}
