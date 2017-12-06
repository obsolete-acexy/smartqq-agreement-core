package com.thankjava.wqq.extend;


/**
 * 回调函数参数
* <p>Function: ListenerAction</p>
* <p>Description: </p>
* @author acexy@thankjava.com
* @date 2016年12月20日 上午10:07:23
* @version 1.0
 */
public class ListenerAction {

	public ListenerAction(){}
	
	public ListenerAction(Object data){
		this.data = data;
	}
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
