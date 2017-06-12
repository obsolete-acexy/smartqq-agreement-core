package com.thankjava.wqq.factory;

import java.util.HashMap;
import java.util.Map;

import com.thankjava.wqq.core.request.RequestBuilder;

import com.thankjava.toolkit3d.aop.core.AopProxyFactory;

public class RequestFactory {

	private RequestFactory(){}
	
	private static final Map<Class<?>, RequestBuilder> instances = new HashMap<>();
	
	public static RequestBuilder getInstance(Class<?> requestClass){
		RequestBuilder baseFunction = instances.get(requestClass);
		
		if(baseFunction == null){
			baseFunction = (RequestBuilder) AopProxyFactory.createProxyObject(requestClass);
			instances.put(requestClass, baseFunction);
		}
		
		return baseFunction;
	}
}
