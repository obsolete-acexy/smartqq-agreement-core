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

	/**
	 * 根据消息类型获取消息回复的目标id
	 * @return
	 */
	public long getTargetFromId(){
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

	/**
	 * 获取消息所属发送用户的uin
	 * @return
	 */
	public long getMsgUin(){
		return value.getFromUin();
	}

	/**
	 * 获取对方发送的消息文本
	 * @return
	 */
	public String getMsgContext() {
		if(value == null){
			return "";
		}
		if(value.getContent() == null){
			return "";
		}
		return value.getContent().getMsgContext();
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
