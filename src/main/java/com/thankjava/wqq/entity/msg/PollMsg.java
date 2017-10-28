package com.thankjava.wqq.entity.msg;

import com.thankjava.wqq.consts.MsgType;

import com.alibaba.fastjson.annotation.JSONField;

public class PollMsg {

	@JSONField(name = "poll_type")
	private MsgType msgType;
	
	private Value value;	
	
	public Value getValue() {
		return value;
	}
	public MsgType getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = MsgType.valueOf(msgType);
	}
	public void setValue(Value value) {
		this.value = value;
	}
	
	// 提供给发送信息时快速获取发送方信息
	public long getFrom(){
	
		switch (msgType) {
		case message: // 好友信息
			return value.getFromUin();
		case discu_message: // 讨论组信息
			return  value.getDid();
		case group_message: // 群信息
			return value.getGroupCode();
		}
		
		return 0;
	}
	
	public long getFromUin(){
		return value.getFromUin();
	}
	
	public String toGetMsgText() {
		if(value == null){
			return "";
		}
		if(value.getContent() == null){
			return "";
		}
		return value.getContent().toGetMsgText();
	}
	
}
