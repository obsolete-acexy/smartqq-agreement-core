# smartqq-agreement-core
---
- JDK >= 1.7
- 用处
```
1. <QQ机器人> 好友给机器人发消息，你自定义解析并回复
2. <个人的快速资料检索器> 将常用的信息保存自己DB，需要时通过向QQ发送检索条件，它去给你筛选并快速告诉你答案
3. <更方便工作，针对研发> 通过扩展将它部署在服务器上，在实现代码部署或者shell执行时，你只需要给它发送一个指令一就完成
...
    And More
这只是一个小小的jar，但是你可以通过它快速实现自己的工具，创造无限可能！
```

---
> ### Bug fixes & 升级备注
```
    1.0.1     
        修复腾讯修改二维码校验流程带来的影响      
    1.0.2
        调整代码易读性，增加稳定性等      
        代码结构调整
        增加异常重试机制，增强稳定性
        初始化SmartQQ的实现新增两个可选的构造参数
    1.0.3      
        配合java-toolkit升级，修复async.http模块稳定性
        配合async.http参数变更结构调整等
        解决由于腾讯协议bug导致的自己发送的群消息识别为别人的信息
    1.0.4 
        升级java-toolkit依赖的模块
    1.0.5
        升级依赖组件
        调整代码结构
```     
---
> ### 获取
```xml
<dependency>
  <groupId>com.thankjava.wqq</groupId>
  <artifactId>smartqq-agreement-core</artifactId>
  <version>1.0.5</version>
</dependency>
```
---
> ### 使用

```
参考com.thankjava.wqq.test.qq.TestSmartQQ.java & com.thankjava.wqq.test.qq.NotifyHandler
``` 
```java
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
    static final SmartQQClient SMART_QQ_CLIENT = new WQQClient(new NotifyListener() {

        @Override
        public void handler(PollMsg pollMsg) {
            // 这里让NotifyListener.hander由于拉取到信息而执行时,将执行的方法交由NotifyHander.hander去处理
            // 在NotifyHander里面对消息进行拓展处理
            NOTIFY_HANDLER.hander(pollMsg);
        }

    });

    // 一个自定义用于处理得到消息的拓展类
    static final NotifyHandler NOTIFY_HANDLER = new NotifyHandler(SMART_QQ_CLIENT);


    public static void main(String[] args) {

        logger.debug("SmartQQ登录开始");

        // 执行登录
        SMART_QQ_CLIENT.login(true, new CallBackListener() {
            // login 接口在得到登录二维码时会调用CallBackListener
            // 并且二维码byte[] 数据会通过ListenerAction.data返回
            @Override
            public void onListener(ListenerAction listenerAction) {

                try {
                    // 将返回的byte[]数据io处理成一张png图片
                    // 位于项目log/qrcode.png
                    ImageIO.write((BufferedImage) listenerAction.getData(),
                        "png", new File("./log/qrcode.png"));
                    logger.debug("获取登录二维码完成,手机QQ扫描 ./log/qrcode.png 位置的二维码图片");
                } catch (Exception e) {
                    logger.error("将byte[]写为图片失败", e);
                }

            }
        }, new CallBackListener() {

            // 然后通过手机QQ扫描登录二维码,允许登录后smartqq-agreement-core工具就正常接收信息了
            // 可以通过SmartQQClient.sendMsg向讨论组或者好友或者群组发送信息
            // smartqq-agreement-core工具在得到好友|讨论组|群组信息后就会调用上面提到的NotifyListener.handler
            // 自此你自需要拓展自己的回复消息的内容,就可以自定义自己的QQ机器人或者组件服务拉
            @Override
            public void onListener(ListenerAction listenerAction) {
                // 登陆成功
                logger.debug("登录完成");
            }
        });


    }
}

```

---
> ### Future

    1. 增加掉线重练机制，通知信息新增session级别通知，在无法正常使用时通知NotifyHandler需要手动处理
    2. 部分结果的相关响应码进行相关判断，增加相关接口的重连机制
