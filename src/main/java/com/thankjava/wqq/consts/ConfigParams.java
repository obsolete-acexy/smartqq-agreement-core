package com.thankjava.wqq.consts;

/**
 * SmartQQ的可配置参数化列表
 * @author acexy
 *
 */
public class ConfigParams {

	/**
	 * 一些接口最大的异常尝试次数
	 */
	public static int EXCEPTION_RETRY_MAX_TIME = 1;
	
	/**
	 * 登录完成后是否立即获取好友信息，群信息，讨论组信息
	 */
	public static boolean INIT_LOGIN_INFO = false;
	
	/**
	 * 若获取的二维码超时未登录则自动重新获取
	 */
	public static boolean AUTO_REFRESH_QR_CODE = false;
}
