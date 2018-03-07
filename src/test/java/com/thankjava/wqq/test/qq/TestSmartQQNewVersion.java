package com.thankjava.wqq.test.qq;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.SmartQQClientBuilder;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;

/**
 * 新版本SmartQQClient测试代码 version >= 1.1.x
 * 
 * @author acexy
 *
 */
public class TestSmartQQNewVersion {
	
	static SmartQQClient smartQQClient = null;
	static MessageHandler messageHandler = new MessageHandler(smartQQClient);

	public static void main(String[] args) {
		
		smartQQClient = SmartQQClientBuilder.custom(new NotifyListener() {
			
			@Override
			public void handler(PollMsg pollMsg) {
				messageHandler.handler(pollMsg);
			}
			
		}).autoGetInfoAfterLogin() // 设置登录成功后立即拉取一些信息
				.setExceptionRetryMaxTimes(3) // 设置如果请求异常重试3次
				// 设置登录二维码生成到 ./log 目录下
				.create(".\\log", new CallBackListener() {
					@Override
					public void onListener(ActionListener actionListener) {
						System.out.println("登录结果: " + actionListener.getData());
					}
				});
	}
	
}
