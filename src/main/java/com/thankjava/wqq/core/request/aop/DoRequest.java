package com.thankjava.wqq.core.request.aop;

import java.lang.reflect.Method;

import com.thankjava.toolkit3d.aop.entity.AopParam;
import com.thankjava.toolkit.reflect.ReflectHelper;
import com.thankjava.toolkit3d.http.async.AsyncHttpClient;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.wqq.core.request.api.BaseHttpService;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ActionListener;

public class DoRequest {

    private static final String proxyMethodName = "buildRequestParams";

    private static AsyncHttpClient asyncHttpClient = BaseHttpService.asyncHttpClient;

    public AopParam doRequest(AopParam aopParam) {

        // 指定代理的函数不要被执行
        aopParam.setInvokeProxyMethod(false);

        // 获取被代理的函数的参数列
        CallBackListener listener = (CallBackListener) aopParam.getParams()[0];

        // 执行buildRequestParams 得到请求的参数体
        Object proxyInstance = aopParam.getProxyInstance();

        Method method = ReflectHelper.getMethod(proxyInstance, proxyMethodName);
        AsyncRequest asyncRequest = (AsyncRequest) ReflectHelper.invokeMethod(proxyInstance, method);
        if (listener != null) {
            // 如果传递了listener 则通过listener的方式回调返回
            ActionListener actionListener = new ActionListener();
            actionListener.setData(asyncHttpClient.syncRequestWithSession(asyncRequest));
            listener.onListener(actionListener);
        } else {
            aopParam.setResult(asyncHttpClient.syncRequestWithSession(asyncRequest));
        }
        // 通过普通的方式返回结果
        return aopParam;
    }
}
