package com.thankjava.wqq.entity.msg;

import com.alibaba.fastjson.annotation.JSONField;

public class Value {
	
	private Content content;
	
	private Long did;
	
	@JSONField(name = "send_uin")
	private Long sendUin;
	
	@JSONField(name = "from_uin")
	private Long fromUin;
	
	@JSONField(name = "msg_id")
	private Long msgId;
	
	@JSONField(name = "msg_type")
	private Integer msgType;
	
	private Long time;
	
	@JSONField(name = "to_uin")
	private Long toUin;
	
	@JSONField(name = "group_code")
	private Long groupCode;
	
	public Long getDid() {
		return did;
	}
	public void setDid(Long did) {
		this.did = did;
	}
	public Long getSendUin() {
		return sendUin;
	}
	public void setSendUin(Long sendUin) {
		this.sendUin = sendUin;
	}
	public Long getFromUin() {
		return fromUin;
	}
	public void setFromUin(Long fromUin) {
		this.fromUin = fromUin;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getToUin() {
		return toUin;
	}
	public void setToUin(Long toUin) {
		this.toUin = toUin;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public Long getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(Long groupCode) {
		this.groupCode = groupCode;
	}
	
	
}
