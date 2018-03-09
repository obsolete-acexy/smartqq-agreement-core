package com.thankjava.wqq.core.request.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thankjava.toolkit3d.aop.entity.AopParam;
import com.thankjava.toolkit.reflect.ReflectHelper;
import com.thankjava.toolkit3d.http.async.AsyncHttpClient;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.wqq.core.request.api.BaseHttpService;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ActionListener;

public class DoRequest {

    private static final String proxyMethodName = "buildRequestParams";
    
    private static Logger logger = LoggerFactory.getLogger(DoRequest.class);

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
            try {
                actionListener.setData(asyncHttpClient.syncRequestWithSession(asyncRequest));
            } catch (Throwable e) {
            	logger.error("http request error", e);
            	actionListener.setData(null);
            }
            
            //TODO:
            logger.debug(String.valueOf(listener == null));
            
            listener.onListener(actionListener);
            
        } else {
        	try {
                aopParam.setResult(asyncHttpClient.syncRequestWithSession(asyncRequest));
        	}catch (Throwable e) {
        		aopParam.setResult(null);
        		logger.error("http request error", e);
			}
        }
        
        // 通过普通的方式返回结果
        return aopParam;
    }
}
