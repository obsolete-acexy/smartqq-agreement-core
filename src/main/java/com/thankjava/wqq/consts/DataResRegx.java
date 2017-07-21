package com.thankjava.wqq.consts;


/**
 * 解析腾讯返回的数据正则表达式
* <p>Function: DataResRegx</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2016年12月15日 上午11:48:07
* @version 1.0
 */
public enum DataResRegx {

	// 样本数据: ptuiCB('66','0','','0','二维码未失效。(997219492)', '');
	check_login_qrcode_status("ptuiCB\\('(\\d+)','(\\d+)','(.*?)','(\\d+)','(.*?)', '(.*?)'\\)", "检查二维码状态"),
	
	;

	public String regx;
	public String remark;
	
	private DataResRegx(String regx,String remark){
		this.regx = regx;
		this.remark = remark;
		
	}
}
