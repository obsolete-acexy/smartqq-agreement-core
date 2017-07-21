package com.thankjava.wqq.entity.msg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Font{
	
	private String color = "000000";
	private String name = "宋体";
	private int size = 10;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public JSONArray toFontArray(){
		JSONArray contentArray = new JSONArray();
		contentArray.add("font");
		JSONObject fontJson = new JSONObject();
		fontJson.put("name", name);
		fontJson.put("color", color);
		fontJson.put("size", size);
		JSONArray styleArray = new JSONArray();
		styleArray.add(0);
		styleArray.add(0);
		styleArray.add(0);
		fontJson.put("style", styleArray);
		contentArray.add(fontJson);
		return contentArray;
	}
}
