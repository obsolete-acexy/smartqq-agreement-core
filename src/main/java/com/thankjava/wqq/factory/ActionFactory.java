package com.thankjava.wqq.factory;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

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
				e.printStackTrace();
			}
		}
		return action;
	}
}
