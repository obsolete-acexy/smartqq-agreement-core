package com.thankjava.wqq.test.qq;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.SmartQQClientBuilder;

/**
 * 新版本SmartQQClient测试代码 version >= 1.1.x
 * 
 * @author acexy
 *
 */
public class TestSmartQQNewVersion {

	static SmartQQClient smartQQClient = null;

	public static void main(String[] args) {
		
		SmartQQClientBuilder builder = SmartQQClientBuilder.custom(new MessageHandler(smartQQClient));
	}
}
