package com.thankjava.wqq.entity.msg;

import com.alibaba.fastjson.JSONArray;

public class Content {

	private Font font = new Font();
	private Object[] msg;

	public Content(){
		
	}
	
	/**
	 * 发送一条信息
	* <p>Title: </p>
	* <p>Description: </p>
	* @param content
	 */
	Content(String content){
		msg = new Object[]{content};
	}
	
	/**
	 * 发送一条信息
	* <p>Title: </p>
	* <p>Description: Object 里面必须是 String | Integer, Integer 代表的是QQ默认表情的编号</p>
	* @param msg
	 */
	public Content(Object[] msg){
		this.msg = msg;
	}
	
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Object[] getMsg() {
		return msg;
	}
	public void setMsg(Object[] msg) {
		this.msg = msg;
	}
	
	// =========
	
	public String toSendMsg(){
		JSONArray array = new JSONArray();
		
		if(msg == null || msg.length == 0){
			array.add("");
		}else{
			for (Object m : msg) {
				if(String.class == m.getClass()){
					array.add(m);
				}else if (Integer.class == m.getClass()){
					JSONArray faceArray = new JSONArray();
					faceArray.add("face");
					faceArray.add((Integer)m);
					array.add(faceArray);
				}
			}
		}
		array.add(font.toFontArray());
		return array.toJSONString();
	}
	
	
	public String toGetMsgText(){
		if(msg == null || msg.length == 0){
			return "";
		}
		StringBuffer sbf = new StringBuffer();
		for (Object m : msg) {
			if(String.class == m.getClass()){
				sbf.append(m);
			}
		}
		return sbf.toString();
	}
}
