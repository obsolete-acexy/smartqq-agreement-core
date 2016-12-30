package com.thankjava.wqq.entity.wqq;

import com.alibaba.fastjson.annotation.JSONField;

public class FriendInfo {

	// 是否是VIP
	@JSONField(name = "is_vip")
	private boolean isVip;
	
	// VIP等级 0 表示非VIP
	@JSONField(name = "vip_level")
	private int vipLevel;
	
	// 用户uin标识
	private Long uin;
	
	// 所处分组的index
	@JSONField(name = "categories")
	private Integer categoriesIndex;
	
	// 昵称
	@JSONField(name = "nick")
	private String nickName;
	
	// 备注
	private String markname;
	
	// 客户端类型
	@JSONField(name = "client_type")
	private Integer clientType;

	// 当前状态
	private String status;
	
	// face 估计是用来查头像的
	private Integer face;
	
	// QQ号码 QQ号码需要经过特定的接口后才会有
	private Integer qq;
	
	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public Long getUin() {
		return uin;
	}

	public void setUin(Long uin) {
		this.uin = uin;
	}

	public Integer getCategoriesIndex() {
		return categoriesIndex;
	}

	public void setCategoriesIndex(Integer categoriesIndex) {
		this.categoriesIndex = categoriesIndex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMarkname() {
		return markname;
	}

	public void setMarkname(String markname) {
		this.markname = markname;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFace() {
		return face;
	}

	public void setFace(Integer face) {
		this.face = face;
	}

	public Integer getQq() {
		return qq;
	}

	public void setQq(Integer qq) {
		this.qq = qq;
	}
	
	
	
}
