package com.thankjava.wqq.test.qq;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ListenerAction;
import com.thankjava.wqq.extend.NotifyListener;

public class TestSmartQQ {

	private static final Logger logger = LoggerFactory.getLogger(TestSmartQQ.class);
	
	// 初始化SmartQQClient
	// 需要指明一个NotifyListener 该接口的实例会在 SmartQQClient 拉取到信息时被执行调用
	static final SmartQQClient smartQQClient = new WQQClient(new NotifyListener() {
		
		@Override
		public void hander(PollMsg pollMsg) {
			// 这里让NotifyListener.hander由于拉取到信息而执行时,将执行的方法交由NotifyHander.hander去处理
			// 在NotifyHander里面对消息进行拓展处理
			notifyHander.hander(pollMsg);
		}
		
	});
	
	// 一个自定义用于处理得到消息的拓展类
	static final NotifyHander notifyHander = new NotifyHander(smartQQClient);
	
	
	public static void main(String[] args) {
		
		logger.debug("登录开始");
		
		// 执行登录
		smartQQClient.login(true, new CallBackListener() {
			// login 接口在得到登录二维码时会调用CallBackListener
			// 并且二维码byte[] 数据会通过ListenerAction.data返回
			@Override
			public void onListener(ListenerAction listenerAction) {

				try {
					// 将返回的byte[]数据io处理成一张png图片
					// 位于项目log/qrcode.png
					ImageIO.write((BufferedImage)listenerAction.getData(), "png", new File("./log/qrcode.png"));
				} catch (Exception e) {
					logger.error("将byte[]写为图片失败", e);
				}
				
			}
		},new CallBackListener() {
			
			// 然后通过手机QQ扫描登录二维码,允许登录后smartqq-agreement-core工具就正常接收信息了
			// 可以通过SmartQQClient.sendMsg向讨论组或者好友或者群组发送信息
			// smartqq-agreement-core工具在得到好友|讨论组|群组信息后就会调用上面提到的NotifyListener.hander
			// 自此你自需要拓展自己的回复消息的内容,就可以自定义自己的QQ机器人或者组件服务拉
			@Override
			public void onListener(ListenerAction listenerAction) {
				// 登陆成功
				logger.debug("登录完成");
			}
		});
		

	}
}
