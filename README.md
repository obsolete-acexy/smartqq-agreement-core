# smartqq-agreement-core
    你可以在此基础上拓展自己的工具
      Such As
      1. <QQ机器人> 好友给机器人发消息，你自定义解析并回复
      2. <个人的快速资料检索器> 将常用的信息保存自己DB，需要时通过向QQ发送检索条件，它去给你筛选并快速告诉你答案
      3. <更方便的工作，针对开发者> 你有想过你想重启你的测试服务器时只需要向你的QQ发送特殊指令它自动去重启你的应用么
      ...
      And More
      
      这只是一个小小的jar，但是你可以通过该sdk快速实现自己的工具创造无限可能~
      
      需要jdk>1.6
---

###背景

        该项目参考另外一个类似的基于腾讯WebQQ协议的工具，遗憾的是由于腾讯WebQQ协议变动较频繁，稳定性差，功能简略，原工程
    早已经无法正常运行并不再维护，该项目基于此重构整个SDK，调整整个逻辑，基本开发者阅读代码后可以轻松了解腾讯WebQQ协议，该
    项目依赖本人编写的另外两个jar，fast-toolkit&fast-toolkit3d。这两个jar已经发布到Maven中央仓库可直接使用。
        当前版本主要功能测试完成，后续优化后将发布到Maven中央仓库。
---

###使用

        参考com.thankjava.wqq.test.qq.TestSmartQQ.java&com.thankjava.wqq.test.qq.NotifyHander
```java
package com.thankjava.wqq.test.qq;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		logger.debug("smartqq");
		
		// 执行登录
		smartQQClient.login(true, new CallBackListener() {
			// login 接口在得到登录二维码时会调用CallBackListener
			// 并且二维码byte[] 数据会通过ListenerAction.data返回
			@Override
			public void onListener(ListenerAction listenerAction) {
				try {
					
					// 将返回的byte[]数据io处理成一张png图片
					// 位于项目log/qrcode.png
					ImageIO.write(
							(BufferedImage)listenerAction.data, "png", 
							new File("./log/qrcode.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// 然后通过手机QQ扫描登录二维码,允许登录后smartqq-agreement-core工具就正常接收信息了
		// 可以通过SmartQQClient.sendMsg向讨论组或者好友或者群组发送信息
		// smartqq-agreement-core工具在得到好友|讨论组|群组信息后就会调用上面提到的NotifyListener.hander
		// 自此你自需要拓展自己的回复消息的内容,就可以自定义自己的QQ机器人或者组件服务拉
	}
}

```
```java
package com.thankjava.wqq.test.qq;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;

public class NotifyHander {
	
	private static SmartQQClient smartQQClient;
	
	public NotifyHander(SmartQQClient smartQQClient){
		NotifyHander.smartQQClient = smartQQClient;
	}

	// 指定不同类型的不同msg回复
//	public void hander(PollMsg pollMsg) {
//		switch (pollMsg.getMsgType()) {
//		case message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: friend"));
//			break;
//		case group_message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: group"));
//			break;
//		case discu_message:
//			smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: discu"));
//			break;
//		}
//	}
//	
	
	// sendMsg 接口能通过pollMsg得到msg的类型，然后自动回复该类型的msg
	public void hander(PollMsg pollMsg){
		smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg"));
	}
	

}

```
---

###future

        目前该版本才刚刚测试完毕，还有几个功能没有完成，不过主体功能已经可以使用后续完成一下功能
    1. 增加掉线重练机制，通知信息新增session级别通知，在无法正常使用时通知NotifyHander需要手动处理
    2. 部分结果的相关响应码进行相关判断，增加相关接口的重连机制
    3. 稳定后先发布到Maven仓库
    
---
###关于我

        90后的码农，喜欢尝试新东西，乐于研究。
    希望通过自己的技能写点好玩的东西，实现自己的价值，有梦想的咸鱼。
        
