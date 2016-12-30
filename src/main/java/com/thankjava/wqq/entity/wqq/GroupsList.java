package com.thankjava.wqq.entity.wqq;

import java.util.HashMap;
import java.util.Map;

public class GroupsList {

	Map<Long, GroupInfo> groups = new HashMap<Long, GroupInfo>();

	public GroupInfo getGroupInfo(long gid){
		return groups.get(gid);
	}
	
	public Map<Long, GroupInfo> getGroups() {
		return groups;
	}
	public void setGroups(Map<Long, GroupInfo> groups) {
		this.groups = groups;
	}
	
	
}
