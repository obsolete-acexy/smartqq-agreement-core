![license](https://img.shields.io/badge/license-Apache2.0-100000.svg)
[![author](https://img.shields.io/badge/author-@thankjava-blue.svg)](https://github.com/thankjava/)
![version](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/thankjava/wqq/smartqq-agreement-core/maven-metadata.xml.svg)
> ### smartqq-agreement-core
---
- JDK >= 1.7
- [更多介绍](https://www.thankjava.com/opensource/069239e5eee95a2299b804d9f98f1f9a)

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
        调整了测试代码优化了大量代码注释
        
```     
---
> ### 获取
```xml
<dependency>
  <groupId>com.thankjava.wqq</groupId>
  <artifactId>smartqq-agreement-core</artifactId>
  <version>1.1.1</version>
</dependency>
```
---
> ### 快速入手
![Demo](https://github.com/thankjava/smartqq-agreement-core/raw/master/docs/imgs/demo.gif)
```
参考com.thankjava.wqq.test.qq.TestSmartQQ & com.thankjava.wqq.test.qq.MessageListener
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
public class TestSmartQQ {

    private static final Logger logger = LoggerFactory.getLogger(TestSmartQQ.class);

    public static void main(String[] args) {

        /**
         * step 1 > 利用指定使用SmartQQClientBuilder指南来构建SmartQQClient实例
         */
        SmartQQClientBuilder builder = SmartQQClientBuilder.custom(

                // 注册一个通知事件的处理器，它将在SmartQQClient获得到相关信息时被调用执行
                new MessageListener()
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



                    // 业务处理
                    // TODO:

                }
            }
        };

        // C: 进行登录,启动服务
        builder.createAndLogin(getQrListener, loginListener);
    }

}



```
---
> ### Future
- 持续解决用户Issues
- 跟进腾讯WebQQ协议更改的影响
