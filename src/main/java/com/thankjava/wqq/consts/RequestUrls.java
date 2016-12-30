package com.thankjava.wqq.consts;

/**
 * 腾讯wqq url 汇总
* <p>Function: Urls</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2016年12月9日 下午3:23:44
* @version 1.0
 */
public enum RequestUrls {

	get_login_qrcode("https://ssl.ptlogin2.qq.com/ptqrshow", "获取登录的二维码"),
	check_qrcode_status("https://ssl.ptlogin2.qq.com/ptqrlogin", "校验登录二维码当前状态"),
	getvfwebqq("http://s.web2.qq.com/api/getvfwebqq", "验证登录状态"),
	login2("http://d1.web2.qq.com/channel/login2", "登录系统得到登录态"),
	get_user_friends2("http://s.web2.qq.com/api/get_user_friends2", "获取用户好友信息"),
	get_online_buddies2("http://d1.web2.qq.com/channel/get_online_buddies2", "获取好友状态"),
	get_group_name_list_mask2("http://s.web2.qq.com/api/get_group_name_list_mask2", "获取群列表信息"),
	get_discus_list("http://s.web2.qq.com/api/get_discus_list", "获取讨论组信息"),
	get_self_info2("http://s.web2.qq.com/api/get_self_info2", "获取用户自己的个人信息"),
	get_recent_list2("http://d1.web2.qq.com/channel/get_recent_list2", "获取会话列表"),
	poll2("https://d1.web2.qq.com/channel/poll2", "消息查询"),
	send_buddy_msg2("https://d1.web2.qq.com/channel/send_buddy_msg2", "发送个人信息"),
	send_qun_msg2("https://d1.web2.qq.com/channel/send_qun_msg2", "发送群消息"),
	send_discu_msg2("https://d1.web2.qq.com/channel/send_discu_msg2", "发送讨论组信息"),
	
	// ====referer
	referer_check_qrcode_status("https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&"
			+ "target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0"
			+ "&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&"
			+ "f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001", "检查二维码的referer"),
	referer_getvfwebqq("http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1", "getvfwebqq的referer"),
	referer_common("http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2", "公共的referer"),
	referer_about_msg("https://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1", "msg信息相关的referer")
	;
	
	public String url;
	public String remark;
	
	private RequestUrls(String url,String remark){
		
		this.url = url;
		this.remark = remark;
		
	}
}
