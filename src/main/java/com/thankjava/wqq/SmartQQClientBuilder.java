package com.thankjava.wqq;

import com.thankjava.toolkit.core.reflect.ReflectUtil;
import com.thankjava.wqq.consts.ConfigParams;
import com.thankjava.wqq.core.action.LoginAction;
import com.thankjava.wqq.entity.enums.LoginResultStatus;
import com.thankjava.wqq.entity.sys.LoginResult;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.NotifyListener;
import com.thankjava.wqq.factory.ActionFactory;

/**
 * SmartQQClient 实力创建工具指南
 *
 * @author acexy
 */
public class SmartQQClientBuilder {

    private static SmartQQClient smartQQClient;

    private SmartQQClientBuilder() {
    }

    /**
     * 声明需要自定义参数化 SmartQQClient
     *
     * @param notifyListener
     * @return
     */
    public static SmartQQClientBuilder custom(NotifyListener notifyListener) {

        smartQQClient = new WQQClient();

        if (notifyListener == null) {
            throw new NullPointerException("notifyListener can not be null");
        }

        ReflectUtil.setFieldVal(smartQQClient, "notifyListener", notifyListener);

        return new SmartQQClientBuilder();
    }

    /**
     * 设置单次请求与异常的最大重试次数
     *
     * @param times
     * @return
     */
    public SmartQQClientBuilder setExceptionRetryMaxTimes(int times) {
        if (times < 1) {
            throw new IllegalArgumentException("exceptionRetryMaxTimes should >= 1");
        }
        ConfigParams.EXCEPTION_RETRY_MAX_TIME = times;
        return this;
    }

    /**
     * 登录成功后立即获取一些信息并缓存下来(自己的信息，讨论组，好友信息)
     *
     * @return
     */
    public SmartQQClientBuilder setAutoGetInfoAfterLogin() {
        ConfigParams.INIT_LOGIN_INFO = true;
        return this;
    }

    /**
     * 设置若登录二维码过期则自动重新拉取下一张二维码
     *
     * @return
     */
    public SmartQQClientBuilder setAutoRefreshQrcode() {
        ConfigParams.AUTO_REFRESH_QR_CODE = true;
        return this;
    }

    /**
     * 当系统计算出登陆状态异常时会重新登录，连续异常到限定阀值后放弃
     *
     * @param times
     * @return
     */
    public SmartQQClientBuilder setAutoLoginMaxRetryTimes(int times) {
        if (times < 1) {
            throw new IllegalArgumentException("autoLoginMaxRetryTimes should >= 1");
        }
        ConfigParams.AUTO_RE_LOGIN_RETRY_MAX_TIME = times;
        return this;
    }

    /**
     * 注册一个掉线通知
     *
     * @param offlineListener
     * @return
     */
    public SmartQQClientBuilder setOffLineListener(CallBackListener offlineListener) {
        ReflectUtil.setFieldVal(smartQQClient, "offlineListener", offlineListener);
        return this;
    }

    /**
     * 创建SmartQQClient实例并登录
     *
     * @param getQrListener 获取到登录二维码后将进行回调
     * @param loginListener 登录完毕后的回调函数，函数会返回一个LoginResult的登录反馈值
     */
    public void createAndLogin(final CallBackListener getQrListener, final CallBackListener loginListener) {

        if (getQrListener == null || loginListener == null) {
            throw new NullPointerException("listener can not be null");
        }

        LoginAction loginAction = ActionFactory.getInstance(LoginAction.class);

        loginAction.login(getQrListener, new CallBackListener() {

            @Override
            public void onListener(ActionListener actionListener) {
                loginListener.onListener(new ActionListener(new LoginResult(smartQQClient, (LoginResultStatus) actionListener.getData())));
            }

        });
    }
}
