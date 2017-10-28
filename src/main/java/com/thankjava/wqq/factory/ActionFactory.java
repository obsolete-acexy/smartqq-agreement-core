package com.thankjava.wqq.factory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionFactory {

	private static final Logger logger = LoggerFactory.getLogger(ActionFactory.class);
	
	private ActionFactory(){}
	
	private static final Map<Class<?>, Object> instances = new HashMap<>();
	
	public static <T> T getInstance(Class<T> actionClass){
		
		@SuppressWarnings("unchecked")
		T action = (T) instances.get(actionClass);
		if(action == null){
			try {
				action = actionClass.newInstance();
				instances.put(actionClass, action);
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("创建ActionFactory<" + actionClass +">失败", e);
			}
		}
		return action;
	}
}
