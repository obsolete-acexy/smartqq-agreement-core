package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import com.thankjava.toolkit3d.http.async.entity.Headers;
import com.thankjava.toolkit3d.http.async.entity.Parameters;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

import com.alibaba.fastjson.JSONObject;

public class Login2 extends BaseHttpService {

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public AsyncResponse doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected AsyncRequest buildRequestParams() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ptwebqq", session.getPtwebqq());
		jsonObject.put("clientid", ConstsParams.CLIENT_ID);
		jsonObject.put("psessionid", "");
		jsonObject.put("status", "online");
		Parameters params = new Parameters("r", jsonObject.toJSONString());
		Headers headers = new Headers("Referer", RequestUrls.referer_common.url);
		return new AsyncRequest(RequestUrls.login2.url, HttpMethod.post, params, headers);
	}

}
