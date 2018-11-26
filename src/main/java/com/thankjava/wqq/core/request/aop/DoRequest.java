package com.thankjava.wqq.core.request.aop;

import com.thankjava.toolkit.bean.aop.entity.AopArgs;
import com.thankjava.toolkit.core.reflect.ReflectUtil;
import com.thankjava.toolkit3d.bean.http.AsyncRequest;
import com.thankjava.toolkit3d.bean.http.AsyncResponse;
import com.thankjava.toolkit3d.bean.http.AsyncResponseCallback;
import com.thankjava.toolkit3d.core.http.AsyncHttpClient;
import com.thankjava.wqq.core.request.api.BaseHttpService;
import com.thankjava.wqq.extend.ActionListener;
import com.thankjava.wqq.extend.CallBackListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class DoRequest {

    private static final String proxyMethodName = "buildRequestParams";

    private static Logger logger = LoggerFactory.getLogger(DoRequest.class);

    private static AsyncHttpClient asyncHttpClient = BaseHttpService.asyncHttpClient;

    public void doRequest(AopArgs aopArgs) {

        // 指定代理的函数不要被执行
        aopArgs.setInvokeProxyMethod(false);

        // 获取被代理的函数的参数列
        final CallBackListener listener = (CallBackListener) aopArgs.getInvokeArgs()[0];

        // 执行buildRequestParams 得到请求的参数体
        Object proxyInstance = aopArgs.getProxyInstance();

        Method method = ReflectUtil.getMethod(proxyInstance.getClass(), proxyMethodName);
        AsyncRequest asyncRequest = (AsyncRequest) ReflectUtil.invokeMethod(proxyInstance, method);

        if (listener != null) {

            // 如果传递了listener 则通过listener的方式回调返回
            try {

                asyncHttpClient.asyncRequestWithSession(asyncRequest, new AsyncResponseCallback() {

                    @Override
                    public void completed(AsyncResponse asyncResponse) {
                        listener.onListener(new ActionListener(asyncResponse));
                    }

                    @Override
                    public void failed(Exception e) {
                        listener.onListener(new ActionListener(null));
                    }

                    @Override
                    public void cancelled() {
                        listener.onListener(new ActionListener(null));
                    }

                });
            } catch (Throwable e) {
                logger.error("http request error", e);
                listener.onListener(new ActionListener());
            }

        } else {

            try {
                aopArgs.setReturnResult(asyncHttpClient.syncRequestWithSession(asyncRequest));
            } catch (Throwable e) {
                aopArgs.setReturnResult(null);
                logger.error("http request error", e);
            }

        }
    }
}
