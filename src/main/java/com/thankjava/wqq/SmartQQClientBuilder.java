package com.thankjava.wqq;

import com.thankjava.toolkit.reflect.ReflectHelper;
import com.thankjava.wqq.consts.ConfigParams;
import com.thankjava.wqq.extend.NotifyListener;

/**
 * SmartQQClient 实力创建工具指南
 * 
 * @author acexy
 *
 */
public class SmartQQClientBuilder {

	private SmartQQClientBuilder() {
	}

	private static WQQClient client;

	/**
	 * 声明需要自定义参数化 SmartQQClient
	 * 
	 * @param notifyListener
	 * @return
	 */
	public static SmartQQClientBuilder custom(NotifyListener notifyListener) {
		
		client = new WQQClient();

		if (notifyListener == null) {
			throw new NullPointerException("notifyListener can not be null");
		}

		ReflectHelper.setFieldVal(client, "listener", notifyListener);

		return new SmartQQClientBuilder();
	}

	/**
	 * 设置单次请求与异常的最大重试次数
	 * 
	 * @param times
	 * @return
	 */
	public SmartQQClientBuilder setExceptionRetryMaxTimes(short times) {
		if (times < 1) {
			throw new IllegalArgumentException("exceptionRetryMaxTimes should >= 1");
		}
		ConfigParams.EXCEPTION_RETRY_MAX_TIME = times;
		return this;
	}
	
	/**
	 * 登录成功后立即获取一些信息并缓存下来(自己的信息，讨论组，好友信息)
	 * @return
	 */
	public SmartQQClientBuilder autoGetDefaultInfoAfterLogin() {
		ConfigParams.INIT_LOGIN_INFO = true;
		return this;
	}

}
