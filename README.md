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
    1.1.0
        标注废除历史版本登录和应用初始化相关代码预计1.1.1将彻底移除)
        新增提供基于Fluent Interface风格代码的初始化，并提供测试案例代码
        调整部分代码注释，调整可配参数代码
        废弃主动登录接口，合并到初始化自动完成
        闭环登录环节的相关异常，各个需要业务控制的回调均提供反馈调用
        增加稳定性，新增应用健康状态监控，提供优化掉线自动重连机制
    1.1.1
        移除了历史版本登录测试方法和历史登录的相关支持代码
        
```     
---
> ### 获取
```xml
<dependency>
  <groupId>com.thankjava.wqq</groupId>
  <artifactId>smartqq-agreement-core</artifactId>
  <version>1.1.0</version>
</dependency>
```
---
> ### 使用
- 1.1.0之前(后续会彻底废弃)

```
参考com.thankjava.wqq.test.qq.TestSmartQQ & com.thankjava.wqq.test.qq.MessageListener
``` 
```java
package com.thankjava.wqq.test.qq;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.thankjava.wqq.extend.ActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;

@Deprecated
public class TestSmartQQ {

    private static final Logger logger = LoggerFactory.getLogger(TestSmartQQ.class);

    // 初始化SmartQQClient
    // 需要指明一个NotifyListener 该接口的实例会在 SmartQQClient 拉取到信息时被执行调用
    static final SmartQQClient SMART_QQ_CLIENT = new WQQClient(new MessageHandler());


    public static void main(String[] args) {

        logger.debug("SmartQQ登录开始");

        // 执行登录
        SMART_QQ_CLIENT.login(true, new CallBackListener() {
            // login 接口在得到登录二维码时会调用CallBackListener
            // 并且二维码byte[] 数据会通过ListenerAction.data返回
            @Override
            public void onListener(ActionListener actionListener) {

                try {
                    // 将返回的byte[]数据io处理成一张png图片
                    // 位于项目log/qrcode.png
                    ImageIO.write((BufferedImage) actionListener.getData(), "png", new File("./log/qrcode.png"));
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
            // 登录完毕后会返回LoginResult 已反馈当前登录结果
            @Override
            public void onListener(ActionListener actionListener) {
                // 登陆成功
                logger.debug("登录结果: " + actionListener.getData());
            }
        });


    }
}

```
- 1.1.0之后新版
```
参考com.thankjava.wqq.test.qq.TestSmartQQNewVersion & com.thankjava.wqq.test.qq.MessageListener
```
```java
package com.thankjava.wqq.test.qq;

import com.thankjava.toolkit3d.fastjson.FastJson;
import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.SmartQQClientBuilder;
import com.thankjava.wqq.entity.enums.LoginResultStatus;
import com.thankjava.wqq.entity.sys.LoginResult;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 新版本SmartQQClient测试代码 version >= 1.1.x
 *
 * @author acexy
 */
public class TestSmartQQNewVersion {

    private static final Logger logger = LoggerFactory.getLogger(TestSmartQQNewVersion.class);

    public static void main(String[] args) {

        /**
         * step 1 > 利用指定使用SmartQQClientBuilder指南来构建SmartQQClient实例
         */
        SmartQQClientBuilder builder = SmartQQClientBuilder.custom(

                // 注册一个通知事件的处理器，它将在SmartQQClient获得到相关信息时被调用执行
                new MessageHandler()
        );


        /**
         * step 2 > 自定义可选参数(为方便查看可选方法，设置参数的函数均以set关键字命名开始)
         */
        builder
                .setAutoGetInfoAfterLogin() // 设置登录成功后立即拉取一些信息
                .setExceptionRetryMaxTimes(3) // 设置如果请求异常重试3次
                .setAutoRefreshQrcode() // 设置若发现登录二维码过期则自动重新拉取
//                .setOffLineListener(new CallBackListener() { // 注册一个离线通知 掉线后将被调用执行
//                    @Override
//                    public void onListener(ActionListener actionListener) {
//                        logger.info("登录的QQ已由掉线无法继续使用(系统已经尝试自动处理)");
//                    }
//                })
        ;

        /**
         * step 3 > create SmartQQClient 实例 并进行登录
         */

        // A: 声明一个获取到登录二维码的回调函数，将返回二维码的byte数组数据
        CallBackListener getQrListener = new CallBackListener() {

            // login 接口在得到登录二维码时会调用CallBackListener
            // 并且二维码byte[] 数据会通过ListenerAction.data返回

            @Override
            public void onListener(ActionListener actionListener) {

                try {
                    // 将返回的byte[]数据io处理成一张png图片
                    // 位于项目log/qrcode.png
                    ImageIO.write((BufferedImage) actionListener.getData(), "png", new File("./log/qrcode.png"));
                    logger.debug("获取登录二维码完成,手机QQ扫描 ./log/qrcode.png 位置的二维码图片");
                } catch (Exception e) {
                    logger.error("将byte[]写为图片失败", e);
                }

            }
        };

        // B: 声明一个登录结果的函数回调，在登录成功或者失败或异常时进行回调触发
        CallBackListener loginListener = new CallBackListener() {

            // ListenerAction.data 返回登录结果 com.thankjava.wqq.entity.sys.LoginResult
            @Override
            public void onListener(ActionListener actionListener) {
                LoginResult loginResult = (LoginResult) actionListener.getData();
                logger.info("登录结果: " + loginResult.getLoginStatus());
                if (loginResult.getLoginStatus() == LoginResultStatus.success) {

                    SmartQQClient smartQQClient = loginResult.getClient();

                    // TODO: 后续就可以利用smartQQClient调用API
                    logger.info("获取到的好友列表信息: " + FastJson.toJSONString(smartQQClient.getFriendsList(true)));

                }
            }
        };

        // C: 进行登录动作
        builder.createAndLogin(getQrListener, loginListener);
    }

}


```
---
> ### Future

    1. 持续优化可能存在的BUG
    2. 响应可能用户提出的优化或建议方案
    3. 跟进TX协议修改后的改动
    4. 提高可用性
