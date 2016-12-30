package com.thankjava.wqq.entity.wqq;

import java.util.HashMap;
import java.util.Map;

public class FriendsList {

	// key为分组的 index 分组信息
	private Map<Integer, CategorieInfo> categories = new HashMap<>();

	// key为好友 uin 好友信息
	private Map<Long, FriendInfo> friends = new HashMap<>();
	
	/**
	 * 获取单个好友信息
	* <p>Function: getFriendInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月22日 上午11:40:50
	* @version 1.0
	* @param uin
	* @return
	 */
	public FriendInfo getFriendInfo(long uin){
		return friends.get(uin);
	}
	
	public Map<Integer, CategorieInfo> getCategories() {
		return categories;
	}
	public void setCategories(Map<Integer, CategorieInfo> categories) {
		this.categories = categories;
	}
	public Map<Long, FriendInfo> getFriends() {
		return friends;
	}
	public void setFriends(Map<Long, FriendInfo> friends) {
		this.friends = friends;
	}
}
