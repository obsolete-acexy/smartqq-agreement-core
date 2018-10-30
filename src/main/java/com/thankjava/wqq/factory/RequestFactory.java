package com.thankjava.wqq.factory;

import com.thankjava.toolkit3d.core.aop.CglibAopProxy;
import com.thankjava.wqq.core.request.RequestBuilder;

import java.util.HashMap;
import java.util.Map;


public class RequestFactory {

    private static final Map<Class<?>, RequestBuilder> instances = new HashMap<>();

    private RequestFactory() {
    }

    public static RequestBuilder getInstance(Class<?> requestClass) {
        RequestBuilder baseFunction = instances.get(requestClass);

        if (baseFunction == null) {
            baseFunction = (RequestBuilder) CglibAopProxy.createProxyObject(requestClass);
            instances.put(requestClass, baseFunction);
        }

        return baseFunction;
    }
}
