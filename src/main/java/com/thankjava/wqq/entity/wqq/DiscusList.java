package com.thankjava.wqq.entity.wqq;

import java.util.HashMap;
import java.util.Map;

public class DiscusList {

	Map<Long, DiscuInfo> discus = new HashMap<>();

	public Map<Long, DiscuInfo> getDiscus() {
		return discus;
	}
	
	public void setDiscus(Map<Long, DiscuInfo> discus) {
		this.discus = discus;
	}
	
	
}
