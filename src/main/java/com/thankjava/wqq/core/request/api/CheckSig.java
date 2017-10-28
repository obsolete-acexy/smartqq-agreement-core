package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

public class CheckSig extends BaseHttpService {

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public AsyncResponse doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected AsyncRequest buildRequestParams() {
		return new AsyncRequest(
				session.getCheckSigUrl(), 
				HttpMethod.get
		);
	}

}
