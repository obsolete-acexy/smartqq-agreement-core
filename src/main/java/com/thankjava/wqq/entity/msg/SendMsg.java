package com.thankjava.wqq.entity.msg;

import com.thankjava.wqq.consts.MsgType;

import com.alibaba.fastjson.annotation.JSONField;

public class SendMsg {

	private MsgType msgType;

	@JSONField(name = "group_uin")
	private Long groupUin;
	private Long to;
	private Long did;
	private Content content;

	public SendMsg(long to, MsgType msgType, Content content) {

		switch (msgType) {
		case message: // 好友信息
			this.to = to;
			break;
		case discu_message: // 讨论组信息
			this.did = to;
			break;
		case group_message: // 群信息
			this.groupUin = to;
			break;

		default:
			throw new RuntimeException("msgType can not be null");
		}
		this.msgType = msgType;
		this.content = content;
	}
	
	public SendMsg(long to, MsgType msgType, String msg) {

		switch (msgType) {
		case message: // 好友信息
			this.to = to;
			break;
		case discu_message: // 讨论组信息
			this.did = to;
			break;
		case group_message: // 群信息
			this.groupUin = to;
			break;

		default:
			throw new RuntimeException("msgType can not be null");
		}
		this.msgType = msgType;
		this.content = new Content(msg);
	}

	public SendMsg(PollMsg pollMsg, Content content) {
		msgType = pollMsg.getMsgType();
		long to = pollMsg.getTo();
		switch (msgType) {
		case message: // 好友信息
			this.to = to;
			break;
		case discu_message: // 讨论组信息
			this.did = to;
			break;
		case group_message: // 群信息
			this.groupUin = to;
			break;
		}
		this.content = content;
	}
	
	public SendMsg(PollMsg pollMsg, String msg) {
		msgType = pollMsg.getMsgType();
		long to = pollMsg.getTo();
		switch (msgType) {
		case message: // 好友信息
			this.to = to;
			break;
		case discu_message: // 讨论组信息
			this.did = to;
			break;
		case group_message: // 群信息
			this.groupUin = to;
			break;
		}
		this.content = new Content(msg);
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public Long getGroupUin() {
		return groupUin;
	}

	public Long getTo() {
		return to;
	}

	public Long getDid() {
		return did;
	}

	public Content getContent() {
		return content;
	}

}
