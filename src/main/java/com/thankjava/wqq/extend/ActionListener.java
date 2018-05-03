package com.thankjava.wqq.extend;


/**
 * 声明事件函数
* <p>Function: ActionListener</p>
* <p>Description: </p>
* @author acexy@thankjava.com
* @date 2016年12月20日 上午10:07:23
* @version 1.0
 */
public class ActionListener {

	public ActionListener(){}
	
	public ActionListener(Object data){
		this.data = data;
	}
	
	private Object data;

	public Object getData() {
		return data;
	}

//	public void setData(Object data) {
//		this.data = data;
//	}
	
}
