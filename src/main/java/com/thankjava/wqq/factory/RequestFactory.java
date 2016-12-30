package com.thankjava.wqq.factory;

import java.util.HashMap;
import java.util.Map;

import com.thankjava.wqq.core.request.Request;

import com.thankjava.toolkit3d.aop.core.AopProxyFactory;

public class RequestFactory {

	private RequestFactory(){}
	
	private static final Map<Class<?>, Request> instances = new HashMap<>();
	
	public static Request getInstance(Class<?> requestClass){
		Request baseFunction = instances.get(requestClass);
		
		if(baseFunction == null){
			baseFunction = (Request) AopProxyFactory.createProxyObject(requestClass);
			instances.put(requestClass, baseFunction);
		}
		
		return baseFunction;
	}
}
