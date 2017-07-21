package com.thankjava.wqq.entity.wqq;

public class CategorieInfo {
	
	// 分组的组名
	private String name;
	
	// 分组的默认下标位 index从0开始
	private int index;
	
	// 排序后的位置 sort从1开始
	private int sort;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	
}
