# smartqq-agreement-core
---
- 基于jdk开发 不依赖第三方代码 (JDK >= 1.7)
```
    你可以在此基础上拓展自己的工具
      Such As
      1. <QQ机器人> 好友给机器人发消息，你自定义解析并回复
      2. <个人的快速资料检索器> 将常用的信息保存自己DB，需要时通过向QQ发送检索条件，它去给你筛选并快速告诉你答案
      3. <更方便的工作，针对开发者> 你有想过你想重启你的测试服务器时只需要向你的QQ发送特殊指令它自动去重启你的应用么
      ...
      And More
      需要jdk最低1.7
      这只是一个小小的jar，但是你可以通过该sdk快速实现自己的工具创造无限可能~
```   

---
> ### bug fix & 升级备注

    1.0.1 修复腾讯修改二维码校验流程带来的影响
    1.0.2 调整代码易读性，增加稳定性等
        代码结构调整
        增加异常重试机制，增强稳定性
        初始化SmartQQ的实现新增两个可选的构造参数
    1.0.3 配合java-toolkit升级，修复async.http模块稳定性
        配合async.http参数变更结构调整等
        解决由于腾讯协议bug导致的自己发送的群消息识别为别人的信息
    1.0.4 升级java-toolkit
        升级java-toolkit
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

    参考com.thankjava.wqq.test.qq.TestSmartQQ.java&com.thankjava.wqq.test.qq.NotifyHandler

---

> ### future

        目前该版本才刚刚测试完毕，还有几个功能没有完成，不过主体功能已经可以使用后续完成一下功能
    1. 增加掉线重练机制，通知信息新增session级别通知，在无法正常使用时通知NotifyHander需要手动处理
    2. 部分结果的相关响应码进行相关判断，增加相关接口的重连机制
    3. 稳定后先发布到Maven仓库
    
