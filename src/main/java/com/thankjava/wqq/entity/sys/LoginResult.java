package com.thankjava.wqq.entity.sys;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.enums.LoginResultStatus;

public class LoginResult {
	
	private SmartQQClient client;
	private LoginResultStatus loginStatus;
	
	public LoginResult(SmartQQClient client, LoginResultStatus loginStatus) {
		this.client = client;
		this.loginStatus = loginStatus;
	}
	
	public SmartQQClient getClient() {
		return client;
	}
	public LoginResultStatus getLoginStatus() {
		return loginStatus;
	}
	
	
	
}
