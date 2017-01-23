package com.thankjava.wqq.entity;

import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.entity.wqq.DetailedInfo;

public class Session {

	private final static Session session = new Session();
	
	private Session(){};
	
	public static Session getSession(){
		return session;
	}
	
	// ----
	private String checkSigUrl;
	private String ptwebqq;
	private String vfwebqq;
	private String psessionid;
	private long uin;

	// 好友列表
	private FriendsList friendsList;
	// 群列表
	private GroupsList groupsList;
	// 讨论组列表
	private DiscusList discusList;
	// 个人信息
	private DetailedInfo selfInfo;
	
	public String getPtwebqq() {
		return ptwebqq;
	}
	public void setPtwebqq(String ptwebqq) {
		this.ptwebqq = ptwebqq;
	}
	public String getCheckSigUrl() {
		return checkSigUrl;
	}
	public void setCheckSigUrl(String checkSigUrl) {
		this.checkSigUrl = checkSigUrl;
	}
	public String getVfwebqq() {
		return vfwebqq;
	}
	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}
	public long getUin() {
		return uin;
	}
	public void setUin(long uin) {
		this.uin = uin;
	}
	public FriendsList getFriendsList() {
		return friendsList;
	}
	public void setFriendsList(FriendsList friendsList) {
		this.friendsList = friendsList;
	}
	public GroupsList getGroupsList() {
		return groupsList;
	}
	public void setGroupsList(GroupsList groupsList) {
		this.groupsList = groupsList;
	}
	public String getPsessionid() {
		return psessionid;
	}
	public void setPsessionid(String psessionid) {
		this.psessionid = psessionid;
	}
	public DiscusList getDiscusList() {
		return discusList;
	}
	public void setDiscusList(DiscusList discusList) {
		this.discusList = discusList;
	}
	public DetailedInfo getSelfInfo() {
		return selfInfo;
	}
	public void setSelfInfo(DetailedInfo selfInfo) {
		this.selfInfo = selfInfo;
	}
	
	
}
